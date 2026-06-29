/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.knowledge.base.configuration.KBServiceConfigurationProvider;
import com.liferay.knowledge.base.web.internal.display.context.KBArticleCompanyConfigurationDisplayContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = ConfigurationFormRenderer.class)
public class KBServiceConfigurationFormRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return "com.liferay.knowledge.base.internal.configuration." +
			"KBServiceConfiguration";
	}

	@Override
	public Map<String, Object> getRequestParameters(
		HttpServletRequest httpServletRequest) {

		return HashMapBuilder.<String, Object>put(
			"checkInterval",
			ParamUtil.getInteger(httpServletRequest, "checkInterval")
		).put(
			"expirationDateNotificationDateWeeks",
			ParamUtil.getInteger(
				httpServletRequest, "expirationDateNotificationDateWeeks")
		).build();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			httpServletRequest.setAttribute(
				KBArticleCompanyConfigurationDisplayContext.class.getName(),
				new KBArticleCompanyConfigurationDisplayContext(
					_kbServiceConfigurationProvider));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/admin/knowledge_base_settings" +
						"/kb_article_expiration_date_configuration.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render kb_article_expiration_date_configuration.jsp",
				exception);
		}
	}

	@Reference
	private KBServiceConfigurationProvider _kbServiceConfigurationProvider;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.knowledge.base.web)"
	)
	private ServletContext _servletContext;

}