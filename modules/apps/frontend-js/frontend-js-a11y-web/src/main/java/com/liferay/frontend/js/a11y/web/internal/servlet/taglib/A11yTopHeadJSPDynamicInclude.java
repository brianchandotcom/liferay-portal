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
import com.liferay.frontend.js.a11y.web.internal.servlet.taglib.helper.FlagA11yHelper;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

		List<String> denylist = new ArrayList<>();

		String[] denylistConfig = _a11yConfiguration.denylist();

		if (denylistConfig != null) {
			Collections.addAll(denylist, denylistConfig);
		}

		String[] editorsConfig = _a11yConfiguration.editors();

		if (editorsConfig != null) {
			Collections.addAll(denylist, editorsConfig);
		}

		if (_a11yConfiguration.controlMenu()) {
			denylist.add(".control-menu");
		}

		if (_a11yConfiguration.globalMenu()) {
			denylist.add(".applications-menu-modal");
		}

		if (_a11yConfiguration.productMenu()) {
			denylist.add(".lfr-product-menu-panel");
		}

		String[] targetsArray = {_a11yConfiguration.target()};

		String[] anyArray = {"*"};

		JSONObject propsJSONObject = JSONUtil.put(
			"axeOptions",
			JSONUtil.put(
				"frameWaitTime", _a11yConfiguration.axeCoreFrameWaitTime()
			).put(
				"iframes", _a11yConfiguration.axeCoreIframes()
			).put(
				"performanceTimer", _a11yConfiguration.axeCorePerformanceTimer()
			).put(
				"resultTypes", _a11yConfiguration.axeCoreResultTypes()
			).put(
				"runOnly", _a11yConfiguration.axeCoreRunOnly()
			)
		).put(
			"denylist", denylist
		).put(
			"mutations",
			JSONUtil.put(
				"any",
				JSONUtil.put(
					"data-restore-title", anyArray
				).put(
					"draggable", anyArray
				).put(
					"id", anyArray
				)
			).put(
				"body", JSONUtil.put("class", anyArray)
			).put(
				"input",
				JSONUtil.put(
					"id", anyArray
				).put(
					"value", anyArray
				)
			)
		).put(
			"targets", targetsArray
		);

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

		if (_flagA11yHelper.getEnable()) {
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
	private volatile FlagA11yHelper _flagA11yHelper;

	@Reference
	private NPMResolver _npmResolver;

}