/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Pedro Leite
 */
public class EditConfigurationDisplayContext {

	public EditConfigurationDisplayContext(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_userId = themeDisplay.getUserId();
	}

	public Map<String, Object> getReactData() throws Exception {
		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			_userId);

		return HashMapBuilder.<String, Object>put(
			"externalReferenceCode",
			() -> {
				if (accountEntry == null) {
					return null;
				}

				return accountEntry.getAccountEntryId() +
					"-ai-hub-configuration";
			}
		).build();
	}

	private final long _userId;

}