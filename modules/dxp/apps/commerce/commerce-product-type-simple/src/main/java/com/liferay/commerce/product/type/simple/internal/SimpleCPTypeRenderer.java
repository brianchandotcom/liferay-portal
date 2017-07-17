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

package com.liferay.commerce.product.type.simple.internal;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.type.CPTypeRenderer;
import com.liferay.commerce.product.type.renderer.BaseCPTypeRenderer;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.product.type.simple.internal.display.context.CPSimpleCPTypeDisplayContext;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {"commerce.product.type.name=" + SimpleCPTypeConstants.NAME},
	service = CPTypeRenderer.class
)
public class SimpleCPTypeRenderer extends BaseCPTypeRenderer {

	@Override
	public void render(
			CPDefinition cpDefinition, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CPSimpleCPTypeDisplayContext cpSimpleCPTypeDisplayContext =
			new CPSimpleCPTypeDisplayContext(
				cpDefinition, _cpAttachmentFileEntryLocalService, portal,
				_cpInstanceHelper);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, cpSimpleCPTypeDisplayContext);

		renderJSP(httpServletRequest, httpServletResponse, "/view.jsp");
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.type.simple)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

}