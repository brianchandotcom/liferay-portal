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

package com.liferay.ai.creator.openai.web.internal.portal.settings.configuration.admin.display;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.ai.creator.openai.web.internal.display.context.AICreatorOpenAIGroupConfigurationDisplayContext;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = SiteSettingsConfigurationScreenContributor.class)
public class AICreatorOpenAISiteSettingsConfigurationScreenContributor
	implements SiteSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "ai-creator";
	}

	@Override
	public String getJspPath() {
		return "/configuration/openai_group_configuration.jsp";
	}

	@Override
	public String getKey() {
		return "ai-creator-openai-group-configuration";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "openai");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/site_settings/save_group_configuration";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public boolean isVisible(Group group) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-179483")) {
			return false;
		}

		return true;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		httpServletRequest.setAttribute(
			AICreatorOpenAIGroupConfigurationDisplayContext.class.getName(),
			new AICreatorOpenAIGroupConfigurationDisplayContext(
				_aiCreatorOpenAIConfigurationManager, httpServletRequest));
	}

	@Reference
	private AICreatorOpenAIConfigurationManager
		_aiCreatorOpenAIConfigurationManager;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.ai.creator.openai.web)"
	)
	private ServletContext _servletContext;

}