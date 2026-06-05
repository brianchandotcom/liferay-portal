/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.audiences.web.internal.servlet.taglib;

import com.liferay.frontend.js.audiences.web.internal.configuration.FrontendJSAudiencesConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;
import com.liferay.portal.url.builder.ServletAbsolutePortalURLBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DynamicInclude.class)
public class FrontendJSAudiencesWebTopHeadDynamicInclude
	implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!FeatureFlagManagerUtil.isEnabled(
				themeDisplay.getCompanyId(), "LPD-83647")) {

			return;
		}

		FrontendJSAudiencesConfiguration frontendJSAudiencesConfiguration;

		try {
			frontendJSAudiencesConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FrontendJSAudiencesConfiguration.class,
					themeDisplay.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			throw new IOException(configurationException);
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.println(
			"<script data-senna-track=\"temporary\" type=\"module\">");
		printWriter.println(
			"import {audiences} from '@liferay/frontend-js-audiences-web';");

		if (frontendJSAudiencesConfiguration.enableLog()) {
			printWriter.println("audiences.setLogEnabled(true);");
		}

		printWriter.println("audiences.clear('PAGE');");
		printWriter.print("await audiences.runDetection('");

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		ServletAbsolutePortalURLBuilder servletAbsolutePortalURLBuilder =
			absolutePortalURLBuilder.forServlet("/audiences");

		printWriter.print(servletAbsolutePortalURLBuilder.build());

		printWriter.println("');");
		printWriter.println("await audiences.runHandlers();");
		printWriter.println("</script>");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	@Reference
	private ConfigurationProvider _configurationProvider;

}