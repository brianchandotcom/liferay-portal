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

package com.liferay.commerce.product.type.virtual.web.internal;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.type.CPTypeRenderer;
import com.liferay.commerce.product.type.renderer.BaseCPTypeRenderer;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.commerce.product.type.virtual.web.internal.display.context.CPVirtualCPTypeDisplayContext;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {"commerce.product.type.name=" + VirtualCPTypeConstants.NAME},
	service = CPTypeRenderer.class
)
public class VirtualCPTypeRenderer extends BaseCPTypeRenderer {

	@Override
	public void render(
			CPDefinition cpDefinition, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CPVirtualCPTypeDisplayContext cpVirtualCPTypeDisplayContext =
			new CPVirtualCPTypeDisplayContext(
				cpDefinition, _cpAttachmentFileEntryLocalService,
				_cpDefinitionVirtualSettingLocalService, _dlAppLocalService,
				_portal, _cpInstanceHelper);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, cpVirtualCPTypeDisplayContext);

		renderJSP(httpServletRequest, httpServletResponse, "/view.jsp");
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.type.virtual.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

}