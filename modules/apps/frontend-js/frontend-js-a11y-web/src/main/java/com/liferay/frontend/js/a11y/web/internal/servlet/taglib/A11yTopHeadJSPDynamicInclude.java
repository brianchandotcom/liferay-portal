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

package com.liferay.frontend.js.a11y.web.internal.servlet.taglib;

import com.liferay.frontend.js.a11y.web.internal.configuration.A11yConfiguration;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;

import java.io.IOException;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matuzalem Teles
 */
@Component(
	configurationPid = "com.liferay.frontend.js.a11y.web.internal.configuration.A11yConfiguration",
	service = DynamicInclude.class
)
public class A11yTopHeadJSPDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		JSONObject propsJSONObject = JSONUtil.put(
			"denylist", _a11yConfiguration.denylist());

		ScriptData scriptData = new ScriptData();

		String initModuleName = _npmResolver.resolveModuleName(
			"@liferay/frontend-js-a11y-web/index");

		scriptData.append(
			null,
			"FrontendA11y.default(" + propsJSONObject.toJSONString() + ")",
			initModuleName + " as FrontendA11y", ScriptData.ModulesType.ES6);

		scriptData.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		String nodejsNodeEnv = GetterUtil.getString(
			_props.get(PropsKeys.NODEJS_NODE_ENV));

		if (Objects.equals(nodejsNodeEnv, "development") &&
			_a11yConfiguration.enable()) {

			dynamicIncludeRegistry.register(
				"/html/common/themes/top_head.jsp#post");
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_a11yConfiguration = ConfigurableUtil.createConfigurable(
			A11yConfiguration.class, properties);
	}

	private volatile A11yConfiguration _a11yConfiguration;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Props _props;

}