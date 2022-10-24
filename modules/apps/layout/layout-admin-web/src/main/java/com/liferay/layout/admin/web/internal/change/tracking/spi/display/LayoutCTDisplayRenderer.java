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

package com.liferay.layout.admin.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class LayoutCTDisplayRenderer extends BaseCTDisplayRenderer<Layout> {

	@Override
	public String[] getAvailableLanguageIds(Layout layout) {
		return layout.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(Layout layout) {
		return layout.getDefaultLanguageId();
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, Layout layout)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_layoutPermission.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.UPDATE) ||
			layout.isSystem()) {

			return null;
		}

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).buildPortletURL();

		String currentURL = _portal.getCurrentURL(httpServletRequest);

		portletURL.setParameter("redirect", currentURL);
		portletURL.setParameter("backURL", currentURL);

		portletURL.setParameter("groupId", String.valueOf(layout.getGroupId()));
		portletURL.setParameter("selPlid", String.valueOf(layout.getPlid()));
		portletURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		return portletURL.toString();
	}

	@Override
	public Class<Layout> getModelClass() {
		return Layout.class;
	}

	@Override
	public String getTitle(Locale locale, Layout layout) {
		return layout.getName(locale);
	}

	@Override
	public boolean isHideable(Layout layout) {
		return layout.isSystem();
	}

	@Override
	public String renderPreview(DisplayContext<Layout> displayContext)
		throws Exception {

		Layout layout = displayContext.getModel();

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return StringBundler.concat(
			"<div style=\"pointer-events: none;\"><iframe frameborder=\"0\" ",
			"onload=\"this.style.height = (this.contentWindow.document.body.",
			"scrollHeight+20) + 'px';\" src=\"",
			_portal.getLayoutFullURL(layout, themeDisplay),
			"?p_l_mode=preview&previewCTCollectionId=",
			layout.getCtCollectionId(), "\" width=\"100%\"></iframe></div>");
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Layout> displayBuilder) {
		Layout layout = displayBuilder.getModel();

		displayBuilder.display(
			"name", layout.getName(displayBuilder.getLocale())
		).display(
			"title", layout.getTitle()
		).display(
			"description", layout.getDescription(displayBuilder.getLocale())
		).display(
			"friendly-url", layout.getFriendlyURL()
		).display(
			"created-by",
			() -> {
				String userName = layout.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", layout.getCreateDate()
		).display(
			"last-modified", layout.getModifiedDate()
		).display(
			"site",
			() -> {
				Group group = layout.getGroup();

				return group.getName(displayBuilder.getLocale());
			}
		).display(
			"theme",
			() -> {
				Theme theme = layout.getTheme();

				return theme.getName();
			}
		).display(
			"color-scheme",
			() -> {
				ColorScheme colorScheme = layout.getColorScheme();

				return colorScheme.getName();
			}
		).display(
			"style-book",
			() -> {
				long styleBookEntryId = layout.getStyleBookEntryId();

				if (styleBookEntryId > 0) {
					StyleBookEntry styleBookEntry =
						_styleBookEntryLocalService.fetchStyleBookEntry(
							layout.getStyleBookEntryId());

					if (styleBookEntry != null) {
						return styleBookEntry.getName();
					}
				}

				return null;
			}
		).display(
			"type", layout.getType()
		).display(
			"type-settings", layout.getTypeSettings()
		).display(
			"css", layout.getCss()
		).display(
			"keywords", layout.getKeywords()
		).display(
			"robots", layout.getRobots()
		).display(
			"hidden", layout.isHidden()
		).display(
			"system", layout.isSystem()
		).display(
			"publish-date", layout.getPublishDate()
		).display(
			"last-publish-date", layout.getLastPublishDate()
		).display(
			"priority", layout.getPriority()
		);
	}

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private Portal _portal;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}