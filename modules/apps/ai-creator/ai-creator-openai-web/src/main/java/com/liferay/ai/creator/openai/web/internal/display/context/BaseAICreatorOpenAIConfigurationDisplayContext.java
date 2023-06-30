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