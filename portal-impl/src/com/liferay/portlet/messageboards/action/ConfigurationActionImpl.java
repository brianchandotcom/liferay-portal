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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.SettingsConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.ContentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurationActionImpl extends SettingsConfigurationAction {

	@Override
	public void postProcessPortletPreferences(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		String emailFromAddress = PortalUtil.getEmailFromAddress(
			portletPreferences, companyId,
			PropsValues.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS);

		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromAddress",
			emailFromAddress);

		String emailFromName = PortalUtil.getEmailFromName(
			portletPreferences, companyId, PropsValues.MESSAGE_BOARDS_EMAIL_FROM_NAME);

		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromName", emailFromName);

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailMessageAddedBody_" + languageId,
			ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailMessageAddedSubject_" + languageId,
			ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailMessageUpdatedBody_" + languageId,
			ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailMessageUpdatedSubject_" + languageId,
			ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT));
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmail(actionRequest, "emailMessageAdded", true);
		validateEmail(actionRequest, "emailMessageUpdated", true);
		validateEmailFrom(actionRequest);
		updateThreadPriorities(actionRequest);
		updateUserRanks(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected boolean isValidUserRank(String rank) {
		if ((StringUtil.count(rank, StringPool.EQUAL) != 1) ||
			rank.startsWith(StringPool.EQUAL) ||
			rank.endsWith(StringPool.EQUAL)) {

			return false;
		}

		return true;
	}

	protected void updateThreadPriorities(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale[] locales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		for (int i = 0; i < locales.length; i++) {
			String languageId = LocaleUtil.toLanguageId(locales[i]);

			List<String> priorities = new ArrayList<String>();

			for (int j = 0; j < 10; j++) {
				String name = ParamUtil.getString(
					actionRequest, "priorityName" + j + "_" + languageId);
				String image = ParamUtil.getString(
					actionRequest, "priorityImage" + j + "_" + languageId);
				double value = ParamUtil.getDouble(
					actionRequest, "priorityValue" + j + "_" + languageId);

				if (Validator.isNotNull(name) || Validator.isNotNull(image) ||
					(value != 0.0)) {

					priorities.add(
						name + StringPool.PIPE + image + StringPool.PIPE +
							value);
				}
			}

			String preferenceName = LocalizationUtil.getPreferencesKey(
				"priorities", languageId);

			setPreference(
				actionRequest, preferenceName,
				priorities.toArray(new String[priorities.size()]));
		}
	}

	protected void updateUserRanks(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale[] locales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String[] ranks = StringUtil.splitLines(
				ParamUtil.getString(actionRequest, "ranks_" + languageId));

			Map<String, String> map = new TreeMap<String, String>(
				new NaturalOrderStringComparator());

			for (String rank : ranks) {
				if (!isValidUserRank(rank)) {
					SessionErrors.add(actionRequest, "userRank");

					return;
				}

				String[] kvp = StringUtil.split(rank, CharPool.EQUAL);

				String kvpName = kvp[0];
				String kvpValue = kvp[1];

				map.put(kvpValue, kvpName);
			}

			ranks = new String[map.size()];

			int count = 0;

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String kvpValue = entry.getKey();
				String kvpName = entry.getValue();

				ranks[count++] = kvpName + StringPool.EQUAL + kvpValue;
			}

			String preferenceName = LocalizationUtil.getPreferencesKey(
				"ranks", languageId);

			setPreference(actionRequest, preferenceName, ranks);
		}
	}

}