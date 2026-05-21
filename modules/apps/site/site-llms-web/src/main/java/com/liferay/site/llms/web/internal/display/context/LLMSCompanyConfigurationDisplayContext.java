/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.llms.web.internal.display.context;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.site.configuration.manager.LLMSConfigurationManager;

/**
 * @author Jonathan McCann
 */
public class LLMSCompanyConfigurationDisplayContext {

	public LLMSCompanyConfigurationDisplayContext(
		LLMSConfigurationManager llmsConfigurationManager,
		ThemeDisplay themeDisplay) {

		_llmsConfigurationManager = llmsConfigurationManager;
		_themeDisplay = themeDisplay;
	}

	public String getContent() throws ConfigurationException {
		return _llmsConfigurationManager.getCompanyContent(
			_themeDisplay.getCompanyId());
	}

	public boolean isEnabled() throws ConfigurationException {
		return _llmsConfigurationManager.isCompanyEnabled(
			_themeDisplay.getCompanyId());
	}

	private final LLMSConfigurationManager _llmsConfigurationManager;
	private final ThemeDisplay _themeDisplay;

}