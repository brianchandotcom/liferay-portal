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

package com.liferay.frontend.js.walkthrough.web.internal.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.js.walkthrough.web.internal.configuration.WalkthroughConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;

import java.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matuzalem Teles
 * @author Diego Nascimento
 */
@Component(
	configurationPid = "com.liferay.frontend.js.walkthrough.web.internal.configuration.WalkthroughConfiguration",
	service = DynamicInclude.class
)
public class WalkthroughBottomJSPDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ScriptData scriptData = new ScriptData();

		String resolvedModuleName = _npmResolver.resolveModuleName(
			"@liferay/frontend-js-walkthrough-web/index");

		scriptData.append(
			null, "WalkthroughRender.default()",
			resolvedModuleName + " as WalkthroughRender",
			ScriptData.ModulesType.ES6);

		scriptData.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		if (_walkthroughConfiguration.enableWalkthrough()) {
			dynamicIncludeRegistry.register(
				"/html/common/themes/bottom.jsp#post");
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_walkthroughConfiguration = ConfigurableUtil.createConfigurable(
			WalkthroughConfiguration.class, properties);
	}

	@Reference
	private NPMResolver _npmResolver;

	private volatile WalkthroughConfiguration _walkthroughConfiguration;

}