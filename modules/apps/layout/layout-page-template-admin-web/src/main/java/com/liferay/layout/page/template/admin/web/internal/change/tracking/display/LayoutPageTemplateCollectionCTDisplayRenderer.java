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

package com.liferay.layout.page.template.admin.web.internal.change.tracking.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTDisplayRenderer.class)
public class LayoutPageTemplateCollectionCTDisplayRenderer
	extends BaseCTDisplayRenderer<LayoutPageTemplateCollection> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws PortalException {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_page_template_admin/edit_layout_page_template_collection"
		).setParameter(
			"layoutPageTemplateCollectionId",
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
		).buildString();
	}

	@Override
	public Class<LayoutPageTemplateCollection> getModelClass() {
		return LayoutPageTemplateCollection.class;
	}

	@Override
	public String getTitle(
			Locale locale,
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws PortalException {

		return layoutPageTemplateCollection.getName();
	}

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}