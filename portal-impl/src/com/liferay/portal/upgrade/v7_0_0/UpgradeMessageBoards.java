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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Iván Zaera
 */
public class UpgradeMessageBoards extends BaseUpgradePortletPreferences {

	protected String getEmailSignatureSeparator(
		PortletPreferences portletPreferences) {

		boolean emailHtmlFormat = _MESSAGE_BOARDS_EMAIL_HTML_FORMAT;

		String emailHtmlFormatString = portletPreferences.getValue(
			"emailHtmlFormat", StringPool.BLANK);

		if (Validator.isNotNull(emailHtmlFormatString)) {
			emailHtmlFormat = GetterUtil.getBoolean(emailHtmlFormatString);
		}

		if (emailHtmlFormat) {
			return "<br />--<br />";
		}

		return "\n--\n";
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"19"};
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
			portletPreferences, "emailMessageAddedBody",
			"emailMessageAddedSignature");
		upgradeEmailSignature(
			portletPreferences, "emailMessageUpdatedBody",
			"emailMessageUpdatedSignature");
		upgradeThreadPriorities(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeThreadPriorities(
			PortletPreferences portletPreferences)
		throws ReadOnlyException {

		String[] threadPriorities = portletPreferences.getValues(
			"priorities", StringPool.EMPTY_ARRAY);

		if (ArrayUtil.isNotEmpty(threadPriorities)) {
			String[] upgradedThreadPriorities =
				new String[threadPriorities.length];

			for (int i = 0; i < threadPriorities.length; i++) {
				String[] parts = StringUtil.split(threadPriorities[i]);

				upgradedThreadPriorities[i] = StringUtil.merge(
					parts, StringPool.PIPE);
			}

			portletPreferences.setValues(
				"priorities", upgradedThreadPriorities);
		}
	}

	private static final boolean _MESSAGE_BOARDS_EMAIL_HTML_FORMAT =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.MESSAGE_BOARDS_EMAIL_HTML_FORMAT));

}