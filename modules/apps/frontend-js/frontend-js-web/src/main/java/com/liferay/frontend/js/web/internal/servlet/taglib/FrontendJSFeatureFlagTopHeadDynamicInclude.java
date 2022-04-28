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

package com.liferay.frontend.js.web.internal.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true, service = DynamicInclude.class
)
public class FrontendJSFeatureFlagTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		StringBundler sb = new StringBundler(5);

		sb.append("<script>var Liferay = window.Liferay || {};");
		sb.append("Liferay.__FF__ = Liferay.__FF__ || {};");
		sb.append(
			_buildFeatureFlagJSGlobalVariable(
				"LPS-144630"));
		sb.append(
			_buildFeatureFlagJSGlobalVariable(
				"LPS-148659"));
		sb.append(
			_buildFeatureFlagJSGlobalVariable(
				"LPS-145112"));
		sb.append("</script>");

		printWriter.println(sb);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	private String _buildFeatureFlagJSGlobalVariable(
		String ticketName) {

			boolean validator = GetterUtil.getBoolean(
					PropsUtil.get("feature.flag." + ticketName));

		return StringBundler.concat(
			"Liferay.__FF__['", ticketName, "'] = ", validator,
			StringPool.SEMICOLON);
	}

}