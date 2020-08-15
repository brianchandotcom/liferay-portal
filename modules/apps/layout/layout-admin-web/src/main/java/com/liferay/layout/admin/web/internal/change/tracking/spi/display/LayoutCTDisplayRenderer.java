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

import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.base.BaseCTDisplayRenderer;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class LayoutCTDisplayRenderer extends BaseCTDisplayRenderer<Layout> {

	@Override
	public String getEditURL(
		HttpServletRequest httpServletRequest, Layout layout) {

		if (layout.isSystem()) {
			return null;
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/layout/edit_layout");

		String currentURL = _portal.getCurrentURL(httpServletRequest);

		portletURL.setParameter("backURL", currentURL);
		portletURL.setParameter("redirect", currentURL);

		portletURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		portletURL.setParameter("groupId", String.valueOf(layout.getGroupId()));
		portletURL.setParameter("selPlid", String.valueOf(layout.getPlid()));

		return portletURL.toString();
	}

	@Override
	public Class<Layout> getModelClass() {
		return Layout.class;
	}

	@Override
	public String getTitle(Locale locale, Layout layout)
		throws PortalException {

		return layout.getName(locale);
	}

	@Override
	public boolean isHideable(Layout layout) {
		return layout.isSystem();
	}

	@Override
	protected CTService<Layout> getCTService() {
		return _layoutLocalService;
	}

	@Override
	protected String[] getDisplayAttributeNames() {
		return _DISPLAY_ATTRIBUTE_NAMES;
	}

	@Override
	protected Map<String, Object> getDisplayAttributes(
		Locale locale, Layout layout) {

		Map<String, Object> displayAttributes =
			LinkedHashMapBuilder.<String, Object>put(
				"name", layout.getName(locale)
			).<String, Object>put(
				"friendly-url", layout.getFriendlyURL()
			).<String, Object>put(
				"Type Settings", layout.getTypeSettingsProperties()
			).build();

		String userName = layout.getUserName();

		if (Validator.isNotNull(userName)) {
			displayAttributes.put("Created By", userName);
		}

		displayAttributes.put("Created Date", layout.getCreateDate());
		displayAttributes.put("Last Modified", layout.getModifiedDate());

		Group group = layout.getGroup();

		displayAttributes.put("Site", group.getName(locale));

		try {
			Theme theme = layout.getTheme();

			displayAttributes.put("theme", theme.getName());

			ColorScheme colorScheme = layout.getColorScheme();

			displayAttributes.put("color-scheme", colorScheme.getName());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		try {
			long styleBookEntryId = layout.getStyleBookEntryId();

			if (styleBookEntryId > 0) {
				StyleBookEntry styleBookEntry =
					_styleBookEntryLocalService.getStyleBookEntry(
						layout.getStyleBookEntryId());

				displayAttributes.put("style-book", styleBookEntry.getName());
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		displayAttributes.putAll(super.getDisplayAttributes(locale, layout));

		return displayAttributes;
	}

	private static final String[] _DISPLAY_ATTRIBUTE_NAMES = {
		"title", "description", "keywords", "robots", "type", "hidden",
		"system", "css", "priority", "publishDate", "lastPublishDate", "status",
		"statusByUserName", "statusDate"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutCTDisplayRenderer.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)")
	private ServletContext _servletContext;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private ThemeLocalService _themeLocalService;

}