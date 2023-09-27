/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.theme.font.awesome.web.internal.servlet.taglib;

import com.liferay.frontend.theme.font.awesome.web.internal.configuration.CSSFontAwesomeConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(
	configurationPid = "com.liferay.frontend.theme.font.awesome.web.internal.configuration.CSSFontAwesomeConfiguration",
	service = DynamicInclude.class
)
public class FontAwesomeTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		if (!_cssFontAwesomeConfiguration.enableFontAwesome()) {
			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		printWriter.println(
			StringBundler.concat(
				"<link data-senna-track=\"permanent\" href=\"",
				absolutePortalURLBuilder.forBundleStylesheet(
					_bundleContext.getBundle(), "css/main.css"
				).build(),
				"\" rel=\"stylesheet\" type=\"text/css\" />"));
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	@Activate
	@Modified
	protected void activate(
			BundleContext bundleContext, ComponentContext componentContext,
			Map<String, Object> properties)
		throws Exception {

		_bundleContext = bundleContext;

		_lastModified = System.currentTimeMillis();

		_cssFontAwesomeConfiguration = ConfigurableUtil.createConfigurable(
			CSSFontAwesomeConfiguration.class, properties);
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile BundleContext _bundleContext;
	private volatile CSSFontAwesomeConfiguration _cssFontAwesomeConfiguration;
	private volatile long _lastModified;

}