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

package com.liferay.client.extension.web.internal.product.navigation.control.menu;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminWebKeys;
import com.liferay.client.extension.web.internal.display.context.EditClientExtensionEntryDisplayContext;
import com.liferay.client.extension.web.internal.display.context.ViewClientExtensionEntryDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.TOOLS,
		"product.navigation.control.menu.entry.order:Integer=250"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class BetaClientExtensionProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public String getIconJspPath() {
		return "/admin/client_extension_beta_button.jsp";
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		EditClientExtensionEntryDisplayContext
			editClientExtensionEntryDisplayContext =
				(EditClientExtensionEntryDisplayContext)
					httpServletRequest.getAttribute(
						ClientExtensionAdminWebKeys.
							EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

		if ((editClientExtensionEntryDisplayContext != null) &&
			Objects.equals(
				editClientExtensionEntryDisplayContext.getCET(
				).getType(),
				ClientExtensionEntryConstants.TYPE_FDS_CELL_RENDERER)) {

			return true;
		}

		ViewClientExtensionEntryDisplayContext
			viewClientExtensionEntryDisplayContext =
				(ViewClientExtensionEntryDisplayContext)
					httpServletRequest.getAttribute(
						ClientExtensionAdminWebKeys.
							VIEW_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

		if ((viewClientExtensionEntryDisplayContext != null) &&
			Objects.equals(
				viewClientExtensionEntryDisplayContext.getCET(
				).getType(),
				ClientExtensionEntryConstants.TYPE_FDS_CELL_RENDERER)) {

			return true;
		}

		return false;
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.client.extension.web)"
	)
	private ServletContext _servletContext;

}