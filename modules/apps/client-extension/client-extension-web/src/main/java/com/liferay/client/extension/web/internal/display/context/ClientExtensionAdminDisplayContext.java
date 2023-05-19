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
import com.liferay.client.extension.web.internal.display.context.util.CETLabelUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ClientExtensionAdminDisplayContext {

	public ClientExtensionAdminDisplayContext(
		CETFactory cetFactory, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		_cetFactory = cetFactory;

		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			portletRequest);
		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			portletResponse);
	}

	public CreationMenu getCreationMenu() {
		CreationMenu creationMenu = new CreationMenu();

		for (String type : _cetFactory.getTypes()) {
			String key = CETFactory.FEATURE_FLAG_KEYS.get(type);

			if ((key != null) && !FeatureFlagManagerUtil.isEnabled(key)) {
				continue;
			}

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						PortletURLBuilder.createRenderURL(
							_liferayPortletResponse
						).setMVCRenderCommandName(
							"/client_extension_admin" +
								"/edit_client_extension_entry"
						).setRedirect(
							_getRedirect()
						).setParameter(
							"type", type
						).buildPortletURL());
					dropdownItem.setLabel(
						CETLabelUtil.getAddLabel(
							_liferayPortletRequest.getLocale(), type));
				});
		}

		return creationMenu;
	}

	public String getImportSuccessURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).buildString();
	}

	public String getImportURL() {
		return ResourceURLBuilder.createResourceURL(
			_liferayPortletResponse
		).setResourceID(
			"/client_extension_admin/import"
		).buildString();
	}

	public String getRedirect() {
		return ParamUtil.getString(_liferayPortletRequest, "redirect");
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_liferayPortletRequest);
	}

	private String _getRedirect() {
		return PortalUtil.getCurrentURL(_getHttpServletRequest());
	}

	private final CETFactory _cetFactory;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}