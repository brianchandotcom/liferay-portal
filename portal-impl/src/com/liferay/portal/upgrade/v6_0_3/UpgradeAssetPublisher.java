/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.upgrade.util.UpgradeAssetPublisherManualEntries;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class UpgradeAssetPublisher extends BaseUpgradePortletPreferences {

	protected String[] getAssetEntryXmls(String[] assetEntryXmls)
		throws Exception {

		String[] newAssetEntryXmls = new String[assetEntryXmls.length];

		for (int i = 0; i < assetEntryXmls.length; i++) {
			String assetEntryXml = assetEntryXmls[i];

			Document document = SAXReaderUtil.read(assetEntryXml);

			Element rootElement = document.getRootElement();

			UpgradeAssetPublisherManualEntries.upgradeToAssetEntryUuidElement(
				rootElement);

			newAssetEntryXmls[i] = document.formattedString(StringPool.BLANK);
		}

		return newAssetEntryXmls;
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"101_INSTANCE_%"};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		boolean changed = false;

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String selectionStyle = portletPreferences.getValue(
			"selection-style", null);

		if (Validator.isNotNull(selectionStyle) &&
			!selectionStyle.equals("dynamic")) {

			String[] assetEntryXmls = portletPreferences.getValues(
				"asset-entry-xml", new String[0]);

			String[] newAssetEntryXmls = getAssetEntryXmls(assetEntryXmls);

			portletPreferences.setValues("asset-entry-xml", newAssetEntryXmls);

			changed = true;
		}

		if (changed) {
			return PortletPreferencesFactoryUtil.toXML(portletPreferences);
		}
		else {
			return xml;
		}
	}

}