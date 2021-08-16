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

package com.liferay.custom.elements.web.internal.frontend.taglib.clay.data.set;

import com.liferay.custom.elements.web.internal.constants.CustomElementsClayDataSetDisplayNames;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = "clay.data.provider.key=" + CustomElementsClayDataSetDisplayNames.CUSTOM_ELEMENT_SOURCES,
	service = ClayDataSetActionProvider.class
)
public class CustomElementsSourceClayDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		CustomElementsSourceClayDataSetEntry
			customElementsSourceClayDataSetEntry =
				(CustomElementsSourceClayDataSetEntry)model;

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				RequestBackedPortletURLFactory requestBackedPortletURLFactory =
					RequestBackedPortletURLFactoryUtil.create(
						httpServletRequest);

				dropdownItem.setHref(
					PortletURLBuilder.create(
						requestBackedPortletURLFactory.createRenderURL(
							_getPortletId(httpServletRequest))
					).setMVCRenderCommandName(
						"/custom_elements_source/edit_custom_elements_source"
					).setRedirect(
						ParamUtil.getString(
							httpServletRequest, "currentURL",
							_portal.getCurrentURL(httpServletRequest))
					).setParameter(
						"customElementsSourceId",
						customElementsSourceClayDataSetEntry.
							getCustomElementsSourceId()
					).buildPortletURL());

				dropdownItem.setLabel(_getMessage(httpServletRequest, "edit"));
			}
		).add(
			dropdownItem -> {
				RequestBackedPortletURLFactory requestBackedPortletURLFactory =
					RequestBackedPortletURLFactoryUtil.create(
						httpServletRequest);

				dropdownItem.setHref(
					PortletURLBuilder.create(
						requestBackedPortletURLFactory.createActionURL(
							_getPortletId(httpServletRequest))
					).setActionName(
						"/custom_elements_source/delete_custom_elements_source"
					).setParameter(
						"customElementsSourceId",
						customElementsSourceClayDataSetEntry.
							getCustomElementsSourceId()
					).buildString());

				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					_getMessage(httpServletRequest, "delete"));
			}
		).build();
	}

	private String _getMessage(
		HttpServletRequest httpServletRequest, String key) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		return LanguageUtil.get(resourceBundle, key);
	}

	private String _getPortletId(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getId();
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}