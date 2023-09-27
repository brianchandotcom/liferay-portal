/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.display.context;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public abstract class BaseAICreatorOpenAIConfigurationDisplayContext {

	public BaseAICreatorOpenAIConfigurationDisplayContext(
		HttpServletRequest httpServletRequest) {

		this.httpServletRequest = httpServletRequest;
	}

	public String getAPIKey() throws ConfigurationException {
		String apiKey = ParamUtil.getString(httpServletRequest, "apiKey", null);

		if (apiKey != null) {
			return apiKey;
		}

		return getAICreatorOpenAIAPIKey();
	}

	public boolean isEnabled() throws ConfigurationException {
		String enabled = ParamUtil.getString(
			httpServletRequest, "enableOpenAI", null);

		if (enabled != null) {
			return GetterUtil.getBoolean(enabled);
		}

		return isAICreatorOpenAIEnabled();
	}

	protected abstract String getAICreatorOpenAIAPIKey()
		throws ConfigurationException;

	protected abstract boolean isAICreatorOpenAIEnabled()
		throws ConfigurationException;

	protected final HttpServletRequest httpServletRequest;

}