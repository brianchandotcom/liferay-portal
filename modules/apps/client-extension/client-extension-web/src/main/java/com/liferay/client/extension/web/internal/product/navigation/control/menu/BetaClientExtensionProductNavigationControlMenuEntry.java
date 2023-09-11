/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return "/admin/beta_client_extension_button.jsp";
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