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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.LayoutPrototype;
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
public class LayoutPrototypeCTDisplayRenderer
	extends BaseCTDisplayRenderer<LayoutPrototype> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			LayoutPrototype layoutPrototype)
		throws PortalException {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_layout_prototype.jsp"
		).setParameter(
			"layoutPrototypeId", layoutPrototype.getLayoutPrototypeId()
		).buildString();
	}

	@Override
	public Class<LayoutPrototype> getModelClass() {
		return LayoutPrototype.class;
	}

	@Override
	public String getTitle(Locale locale, LayoutPrototype layoutPrototype)
		throws PortalException {

		return layoutPrototype.getName(locale);
	}

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}