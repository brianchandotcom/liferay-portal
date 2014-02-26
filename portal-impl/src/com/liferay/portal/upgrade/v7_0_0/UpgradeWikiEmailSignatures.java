/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Ivan Zaera
 */
public class UpgradeWikiEmailSignatures extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {PortletKeys.WIKI};
	}

	protected void upgradeEmailSignature(
			PortletPreferences portletPreferences,
			String signaturePortletPreferencesKey,
			String bodyPortletPreferencesKey)
		throws ReadOnlyException {

		String emailMessageSignature = portletPreferences.getValue(
			signaturePortletPreferencesKey, StringPool.BLANK);

		if (Validator.isNotNull(emailMessageSignature)) {
			String emailMessageBody = portletPreferences.getValue(
				bodyPortletPreferencesKey, StringPool.BLANK);

			emailMessageBody =
				emailMessageBody + StringPool.NEW_LINE + emailMessageSignature;

			portletPreferences.setValue(
				bodyPortletPreferencesKey, emailMessageBody);
		}

		portletPreferences.reset(signaturePortletPreferencesKey);
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
			portletPreferences, "emailPageAddedSignature",
			"emailPageAddedBody");

		upgradeEmailSignature(
			portletPreferences, "emailPageUpdatedSignature",
			"emailPageUpdatedBody");

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}