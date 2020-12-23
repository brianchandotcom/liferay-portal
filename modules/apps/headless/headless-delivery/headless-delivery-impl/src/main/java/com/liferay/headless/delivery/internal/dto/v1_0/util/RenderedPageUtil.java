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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.RenderedPage;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jürgen Kappler
 */
public class RenderedPageUtil {

	public static RenderedPage getRenderedPage(
			DTOConverterContext dtoConverterContext, Layout layout,
			LayoutPageTemplateEntryLocalService
				layoutPageTemplateEntryLocalService,
			Portal portal)
		throws Exception {

		ThemeDisplay themeDisplay = _getThemeDisplay(
			dtoConverterContext.getHttpServletRequest(), layout);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(
				layout, layoutPageTemplateEntryLocalService, portal);

		LayoutPageTemplateEntry masterLayout = _getMasterLayout(
			layout, layoutPageTemplateEntryLocalService);

		return new RenderedPage() {
			{
				renderedPageURL = portal.getLayoutRelativeURL(
					layout, themeDisplay);

				setMasterPageId(
					() -> {
						if (masterLayout != null) {
							return masterLayout.getLayoutPageTemplateEntryKey();
						}

						return null;
					});

				setMasterPageName(
					() -> {
						if (masterLayout != null) {
							return masterLayout.getName();
						}

						return null;
					});

				setPageTemplateId(
					() -> {
						if (layoutPageTemplateEntry != null) {
							return layoutPageTemplateEntry.
								getLayoutPageTemplateEntryKey();
						}

						return null;
					});

				setPageTemplateName(
					() -> {
						if (layoutPageTemplateEntry != null) {
							return layoutPageTemplateEntry.getName();
						}

						return null;
					});
			}
		};
	}

	private static LayoutPageTemplateEntry _getLayoutPageTemplateEntry(
		Layout layout,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		Portal portal) {

		if (layout.getClassNameId() != portal.getClassNameId(
				LayoutPageTemplateEntry.class)) {

			return null;
		}

		return layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
			layout.getClassPK());
	}

	private static LayoutPageTemplateEntry _getMasterLayout(
		Layout layout,
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
			layout.getMasterLayoutPlid());

		if (masterLayout == null) {
			return null;
		}

		return layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
			masterLayout.getClassPK());
	}

	private static ThemeDisplay _getThemeDisplay(
			HttpServletRequest httpServletRequest, Layout layout)
		throws Exception {

		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			httpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(httpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setLayout(layout);
		themeDisplay.setScopeGroupId(layout.getGroupId());
		themeDisplay.setSiteGroupId(layout.getGroupId());

		return themeDisplay;
	}

}