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

package com.liferay.message.boards.web.internal.portlet.action;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.BaseJSPSettingsConfigurationAction;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
	service = ConfigurationAction.class
)
public class MBAdminConfigurationAction
	extends BaseJSPSettingsConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmail(actionRequest, "emailMessageAdded");
		validateEmail(actionRequest, "emailMessageUpdated");
		validateEmailFrom(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected boolean isValidUserRank(String rank) {
		if ((StringUtil.count(rank, CharPool.EQUAL) != 1) ||
			rank.startsWith(StringPool.EQUAL) ||
			rank.endsWith(StringPool.EQUAL)) {

			return false;
		}

		return true;
	}

	@Override
	protected void updateMultiValuedKeys(ActionRequest actionRequest) {
		super.updateMultiValuedKeys(actionRequest);

		updateThreadPriorities(actionRequest);
		updateUserRanks(actionRequest);
	}

	protected void updateThreadPriorities(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		for (Locale locale :
				LanguageUtil.getAvailableLocales(
					themeDisplay.getSiteGroupId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			List<String> priorities = new ArrayList<>();

			for (int j = 0; j < 10; j++) {
				String name = ParamUtil.getString(
					actionRequest,
					StringBundler.concat("priorityName", j, "_", languageId));
				String image = ParamUtil.getString(
					actionRequest,
					StringBundler.concat("priorityImage", j, "_", languageId));
				double value = ParamUtil.getDouble(
					actionRequest,
					StringBundler.concat("priorityValue", j, "_", languageId));

				if (Validator.isNotNull(name) || Validator.isNotNull(image) ||
					(value != 0.0)) {

					priorities.add(
						StringBundler.concat(
							name, StringPool.PIPE, image, StringPool.PIPE,
							value));
				}
			}

			String preferenceName = LocalizationUtil.getLocalizedName(
				"priorities", languageId);

			setPreference(
				actionRequest, preferenceName,
				priorities.toArray(new String[0]));
		}
	}

	protected void updateUserRanks(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		for (Locale locale :
				LanguageUtil.getAvailableLocales(
					themeDisplay.getSiteGroupId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			String[] ranks = StringUtil.splitLines(
				ParamUtil.getString(actionRequest, "ranks_" + languageId));

			Map<String, String> map = new TreeMap<>(
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

			String preferenceName = LocalizationUtil.getLocalizedName(
				"ranks", languageId);

			setPreference(actionRequest, preferenceName, ranks);
		}
	}

}