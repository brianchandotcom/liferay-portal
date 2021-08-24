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

package com.liferay.custom.elements.web.internal.display.context;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.CustomElementsSourceLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class CustomElementsPortletDescriptorDisplayContext {

	public CustomElementsPortletDescriptorDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		CustomElementsSourceLocalService customElementsSourceLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_customElementsSourceLocalService = customElementsSourceLocalService;
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCRenderCommandName(
						"/custom_elements_portlet_descriptor" +
							"/edit_custom_elements_portlet_descriptor"
					).setRedirect(
						_getRedirect()
					).buildPortletURL());

				dropdownItem.setLabel(
					_getLabel("add-custom-elements-portlet-descriptor"));
			}
		).build();
	}

	public PortletURL getCurrentPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	public List<CustomElementsSource> getCustomElementSources() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _customElementsSourceLocalService.getCustomElementsSources(
			themeDisplay.getCompanyId());
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_renderRequest);
	}

	private String _getLabel(String label) {
		return LanguageUtil.get(_getHttpServletRequest(), label);
	}

	private String _getRedirect() {
		return PortalUtil.getCurrentURL(_getHttpServletRequest());
	}

	private final CustomElementsSourceLocalService
		_customElementsSourceLocalService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}