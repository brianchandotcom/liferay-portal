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

package com.liferay.client.extension.web.internal.display.context;

import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.web.internal.util.CETLabelsUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ClientExtensionAdminDisplayContext {

	public ClientExtensionAdminDisplayContext(
		CETFactory cetFactory, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_cetFactory = cetFactory;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CreationMenu getCreationMenu() {
		CreationMenu creationMenu = new CreationMenu();

		for (String type : _cetFactory.getTypes()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						PortletURLBuilder.createRenderURL(
							_renderResponse
						).setMVCRenderCommandName(
							"/client_extension_admin" +
								"/edit_client_extension_entry"
						).setRedirect(
							_getRedirect()
						).setParameter(
							"type", type
						).buildPortletURL());

					dropdownItem.setLabel(
						CETLabelsUtil.getAddLabel(
							_getHttpServletRequest(), type));
				});
		}

		return creationMenu;
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_renderRequest);
	}

	private String _getRedirect() {
		return PortalUtil.getCurrentURL(_getHttpServletRequest());
	}

	private final CETFactory _cetFactory;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}