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

package com.liferay.frontend.js.spa.web.internal.servlet.taglib;

import com.liferay.frontend.js.spa.web.internal.constants.SPAWebKeys;
import com.liferay.frontend.js.spa.web.internal.servlet.taglib.util.SPAUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, service = DynamicInclude.class)
public class SPATopHeadJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		request.setAttribute(SPAWebKeys.SPA_UTIL, _spaUtil);

		super.include(request, response, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		boolean singlePageApplicationEnabled = GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.JAVASCRIPT_SINGLE_PAGE_APPLICATION_ENABLED));

		if (singlePageApplicationEnabled) {
			dynamicIncludeRegistry.register(
				"/html/common/themes/top_head.jsp#post");
		}
	}

	@Override
	protected String getJspPath() {
		return "/init.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.js.spa.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setSPAUtil(SPAUtil spaUtil) {
		_spaUtil = spaUtil;
	}

	protected void unsetSPAUtil(SPAUtil spaUtil) {
		_spaUtil = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SPATopHeadJSPDynamicInclude.class);

	private SPAUtil _spaUtil;

}