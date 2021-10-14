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

package com.liferay.commerce.order.rule.web.internal.entry.type;

import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeJSPContributor;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.order.rule.entry.type.jsp.contributor.key=" + COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT,
	service = COREntryTypeJSPContributor.class
)
public class MinimumAmountCOREntryTypeJSPContributor
	implements COREntryTypeJSPContributor {

	@Override
	public void render(
			long corEntryId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/cor_entry/type/minimum_amount.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.order.rule.web)"
	)
	private ServletContext _servletContext;

}