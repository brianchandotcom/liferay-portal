/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.portal.settings.configuration.admin.display;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.ai.creator.openai.web.internal.display.context.AICreatorOpenAICompanyConfigurationDisplayContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class AICreatorOpenAIPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "ai-creator";
	}

	@Override
	public String getJspPath() {
		return "/configuration/openai_company_configuration.jsp";
	}

	@Override
	public String getKey() {
		return "ai-creator-openai-company-configuration";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "openai");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/instance_settings/save_company_configuration";
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
			AICreatorOpenAICompanyConfigurationDisplayContext.class.getName(),
			new AICreatorOpenAICompanyConfigurationDisplayContext(
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