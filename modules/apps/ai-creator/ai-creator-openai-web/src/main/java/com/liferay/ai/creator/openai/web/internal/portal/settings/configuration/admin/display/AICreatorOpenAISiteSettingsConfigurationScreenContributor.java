/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.portal.settings.configuration.admin.display;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.ai.creator.openai.web.internal.display.context.AICreatorOpenAIGroupConfigurationDisplayContext;
import com.liferay.portal.kernel.language.Language;
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