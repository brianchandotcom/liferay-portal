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

package com.liferay.layout.apio.internal.util;

import com.liferay.apio.architect.language.Language;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = LayoutResourceCollectionUtil.class)
public class LayoutResourceCollectionUtil {

	public String getBreadcrumb(Layout layout, Language language) {
		List<Layout> ancestorLayouts = null;

		Locale locale = language.getPreferredLocale();

		try {
			ancestorLayouts = layout.getAncestors();
		}
		catch (Exception e) {
			_log.error("Unable to get layout ancestors", e);

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(4 * ancestorLayouts.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(locale, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(locale, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestorLayouts);

		for (Layout ancestorLayout : ancestorLayouts) {
			sb.append(HtmlUtil.escape(ancestorLayout.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	public String getImageURL(Layout layout) {
		Optional<ServiceContext> serviceContextOptional = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext());

		ServiceContext serviceContext = serviceContextOptional.orElse(
			new ServiceContext());

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if ((layout == null) || (themeDisplay == null) ||
			!layout.isIconImage()) {

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/layout_icon?img_id");
		sb.append(layout.getIconImageId());
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(layout.getIconImageId()));

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutResourceCollectionUtil.class);

}