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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;

import java.net.URL;

import java.util.Objects;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class ThemeHelper {

	public static final String TEMPLATE_EXTENSION_FTL = "ftl";

	public static final String TEMPLATE_EXTENSION_JSP = "jsp";

	public static final String TEMPLATE_EXTENSION_VM = "vm";

	public static String getResourcePath(
		ServletContext servletContext, Theme theme, String portletId,
		String path) {

		StringBundler sb = new StringBundler(11);

		String themeContextName = GetterUtil.getString(
			theme.getServletContextName());

		sb.append(themeContextName);

		String servletContextName = StringPool.BLANK;

		String contextPath = servletContext.getContextPath();

		if (!Objects.equals(
				PortalUtil.getPathContext(contextPath),
				PortalUtil.getPathContext())) {

			servletContextName = GetterUtil.getString(
				servletContext.getServletContextName());
		}

		int start = 0;

		if (path.startsWith(StringPool.SLASH)) {
			start = 1;
		}

		int end = path.lastIndexOf(CharPool.PERIOD);

		String extension = theme.getTemplateExtension();

		if (extension.equals(TEMPLATE_EXTENSION_FTL)) {
			sb.append(theme.getFreeMarkerTemplateLoader());
			sb.append(theme.getTemplatesPath());

			if (Validator.isNotNull(servletContextName) &&
				!path.startsWith(StringPool.SLASH.concat(servletContextName))) {

				sb.append(StringPool.SLASH);
				sb.append(servletContextName);
			}

			sb.append(StringPool.SLASH);
			sb.append(path.substring(start, end));
			sb.append(StringPool.PERIOD);

			if (Validator.isNotNull(portletId)) {
				sb.append(portletId);
				sb.append(StringPool.PERIOD);
			}

			sb.append(TEMPLATE_EXTENSION_FTL);

			return sb.toString();
		}
		else if (extension.equals(TEMPLATE_EXTENSION_VM)) {
			sb.append(theme.getVelocityResourceListener());
			sb.append(theme.getTemplatesPath());

			if (Validator.isNotNull(servletContextName) &&
				!path.startsWith(StringPool.SLASH.concat(servletContextName))) {

				sb.append(StringPool.SLASH);
				sb.append(servletContextName);
			}

			sb.append(StringPool.SLASH);
			sb.append(path.substring(start, end));
			sb.append(StringPool.PERIOD);

			if (Validator.isNotNull(portletId)) {
				sb.append(portletId);
				sb.append(StringPool.PERIOD);
			}

			sb.append(TEMPLATE_EXTENSION_VM);

			return sb.toString();
		}
		else {
			return path;
		}
	}

	public static boolean resourceExists(
			ServletContext servletContext, Theme theme, String portletId,
			String path)
		throws Exception {

		Boolean exists = null;

		if (Validator.isNotNull(portletId)) {
			exists = _resourceExists(servletContext, theme, portletId, path);

			if (!exists && PortletIdCodec.hasInstanceId(portletId)) {
				String rootPortletId = PortletIdCodec.decodePortletName(
					portletId);

				exists = _resourceExists(
					servletContext, theme, rootPortletId, path);
			}

			if (!exists) {
				exists = _resourceExists(servletContext, theme, null, path);
			}
		}

		if (exists == null) {
			exists = _resourceExists(servletContext, theme, portletId, path);
		}

		return exists;
	}

	private static boolean _resourceExists(
			ServletContext servletContext, Theme theme, String portletId,
			String path)
		throws Exception {

		if (Validator.isNull(path)) {
			return false;
		}

		String resourcePath = getResourcePath(
			servletContext, theme, portletId, path);

		String extension = theme.getTemplateExtension();

		if (extension.equals(TEMPLATE_EXTENSION_FTL)) {
			return TemplateResourceLoaderUtil.hasTemplateResource(
				TemplateConstants.LANG_TYPE_FTL, resourcePath);
		}
		else if (extension.equals(TEMPLATE_EXTENSION_VM)) {
			return TemplateResourceLoaderUtil.hasTemplateResource(
				TemplateConstants.LANG_TYPE_VM, resourcePath);
		}
		else {
			URL url = null;

			if (theme.isWARFile()) {
				ServletContext themeServletContext = servletContext.getContext(
					theme.getContextPath());

				url = themeServletContext.getResource(resourcePath);
			}
			else {
				url = servletContext.getResource(resourcePath);
			}

			if (url == null) {
				return false;
			}
			else {
				return true;
			}
		}
	}

}