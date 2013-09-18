/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.util.ContentUtil;

import java.io.File;

import javax.servlet.ServletContext;

/**
 * @author Mate Thurzo
 */
public class ThemeExporter {

	public void exportTheme(
			PortletDataContext portletDataContext, Layout layout)
		throws Exception {

		boolean exportTheme = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.THEME);

		if (_log.isDebugEnabled()) {
			_log.debug("Export theme " + exportTheme);
		}

		if (exportTheme && !portletDataContext.isPerformDirectBinaryImport() &&
			!layout.isInheritLookAndFeel()) {

			Theme theme = layout.getTheme();

			StringBundler sb = new StringBundler(6);

			ZipWriter zipWriter = portletDataContext.getZipWriter();

			sb.append(zipWriter.getPath());
			sb.append(StringPool.SLASH);
			sb.append("theme");
			sb.append(StringPool.DASH);
			sb.append(String.valueOf(layout.getLayoutId()));
			sb.append(".zip");

			File themeZipFile = new File(sb.toString());

			exportTheme(theme, themeZipFile);
		}
	}

	public void exportTheme(
			PortletDataContext portletDataContext, LayoutSet layoutSet)
		throws Exception {

		exportTheme(
			portletDataContext, layoutSet.getTheme(),
			layoutSet.getColorSchemeId(), layoutSet.getCss());
	}

	public void exportTheme(
			PortletDataContext portletDataContext,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		exportTheme(
			portletDataContext, layoutSetBranch.getTheme(),
			layoutSetBranch.getColorSchemeId(), layoutSetBranch.getCss());
	}

	protected void exportTheme(
			PortletDataContext portletDataContext, Theme theme,
			String colorSchemeId, String css)
		throws Exception {

		boolean exportTheme = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.THEME);
		boolean exportThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (_log.isDebugEnabled()) {
			_log.debug("Export theme " + exportTheme);
			_log.debug("Export theme settings " + exportThemeSettings);
		}

		Element exportDataRootElement =
			portletDataContext.getExportDataRootElement();
		Element headerElement = exportDataRootElement.element("header");

		if (exportTheme || exportThemeSettings) {
			headerElement.addAttribute("theme-id", theme.getThemeId());
			headerElement.addAttribute("color-scheme-id", colorSchemeId);
		}

		if (exportTheme && !portletDataContext.isPerformDirectBinaryImport()) {
			ZipWriter zipWriter = portletDataContext.getZipWriter();

			File themeZipFile = new File(zipWriter.getPath() + "/theme.zip");

			exportTheme(theme, themeZipFile);
		}

		Element cssElement = headerElement.addElement("css");

		cssElement.addCDATA(css);
	}

	protected void exportTheme(Theme theme, File themeZipFile)
		throws Exception {

		String lookAndFeelXML = ContentUtil.get(
			"com/liferay/portal/dependencies/liferay-look-and-feel.xml.tmpl");

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$TEMPLATE_EXTENSION$]", "[$VIRTUAL_PATH$]"
			},
			new String[] {
				theme.getTemplateExtension(), theme.getVirtualPath()
			}
		);

		String servletContextName = theme.getServletContextName();

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Servlet context not found for theme " +
						theme.getThemeId());
			}

			return;
		}

		ZipWriter themeZipWriter = ZipWriterFactoryUtil.getZipWriter(
			themeZipFile);

		themeZipWriter.addEntry("liferay-look-and-feel.xml", lookAndFeelXML);

		File cssPath = null;
		File imagesPath = null;
		File javaScriptPath = null;
		File templatesPath = null;

		if (!theme.isLoadFromServletContext()) {
			ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
				servletContextName);

			if (themeLoader == null) {
				_log.error(
					servletContextName + " does not map to a theme loader");
			}
			else {
				File file = themeLoader.getFileStorage();

				String realPath =
					file.getPath() + StringPool.SLASH + theme.getName();

				cssPath = new File(realPath + "/css");
				imagesPath = new File(realPath + "/images");
				javaScriptPath = new File(realPath + "/javascript");
				templatesPath = new File(realPath + "/templates");
			}
		}
		else {
			cssPath = new File(servletContext.getRealPath(theme.getCssPath()));
			imagesPath = new File(
				servletContext.getRealPath(theme.getImagesPath()));
			javaScriptPath = new File(
				servletContext.getRealPath(theme.getJavaScriptPath()));
			templatesPath = new File(
				servletContext.getRealPath(theme.getTemplatesPath()));
		}

		exportThemeFiles("css", cssPath, themeZipWriter);
		exportThemeFiles("images", imagesPath, themeZipWriter);
		exportThemeFiles("javascript", javaScriptPath, themeZipWriter);
		exportThemeFiles("templates", templatesPath, themeZipWriter);
	}

	protected void exportThemeFiles(String path, File dir, ZipWriter zipWriter)
		throws Exception {

		if ((dir == null) || !dir.exists()) {
			return;
		}

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				exportThemeFiles(
					path + StringPool.SLASH + file.getName(), file, zipWriter);
			}
			else {
				zipWriter.addEntry(
					path + StringPool.SLASH + file.getName(),
					FileUtil.getBytes(file));
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ThemeExporter.class);

}