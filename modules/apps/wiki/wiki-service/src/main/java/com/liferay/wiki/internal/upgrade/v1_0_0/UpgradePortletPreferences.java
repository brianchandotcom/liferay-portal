/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.wiki.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.constants.WikiPortletKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Iván Zaera
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	protected String getEmailSignatureSeparator(
		PortletPreferences portletPreferences) {

		return StringPool.NEW_LINE;
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {WikiPortletKeys.WIKI};
	}

	protected void upgradeEmailSignature(
			PortletPreferences portletPreferences,
			String emailMessageBodyPortletPreferencesKey,
			String emailMessageSignaturePortletPreferencesKey)
		throws ReadOnlyException {

		String emailMessageSignature = portletPreferences.getValue(
			emailMessageSignaturePortletPreferencesKey, StringPool.BLANK);

		if (Validator.isNotNull(emailMessageSignature)) {
			String emailMessageBody = portletPreferences.getValue(
				emailMessageBodyPortletPreferencesKey, StringPool.BLANK);

			String signatureSeparator = getEmailSignatureSeparator(
				portletPreferences);

			emailMessageBody += signatureSeparator + emailMessageSignature;

			portletPreferences.setValue(
				emailMessageBodyPortletPreferencesKey, emailMessageBody);
		}

		portletPreferences.reset(emailMessageSignaturePortletPreferencesKey);
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeEmailSignature(
			portletPreferences, "emailPageAddedBody",
			"emailPageAddedSignature");
		upgradeEmailSignature(
			portletPreferences, "emailPageUpdatedBody",
			"emailPageUpdatedSignature");

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}