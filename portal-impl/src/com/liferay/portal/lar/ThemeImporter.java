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

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Mate Thurzo
 */
public class ThemeImporter {

	public void importTheme(
			PortletDataContext portletDataContext, Layout layout)
		throws Exception {

		boolean importTheme = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.THEME);

		if (_log.isDebugEnabled()) {
			_log.debug("Import theme " + importTheme);
		}

		if (!importTheme || layout.isInheritLookAndFeel()) {
			return;
		}

		StringBundler sb = new StringBundler(4);

		sb.append("theme");
		sb.append(StringPool.DASH);
		sb.append(layout.getLayoutId());
		sb.append(".zip");

		InputStream themeZip = portletDataContext.getZipEntryAsInputStream(
			sb.toString());

		if (themeZip != null) {
			String themeId = layout.getThemeId();
			String colorSchemeId = layout.getColorSchemeId();

			long groupId = portletDataContext.getGroupId();
			boolean privateLayout = portletDataContext.isPrivateLayout();

			Map<Long, Layout> newLayoutsMap =
				(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class + ".layout");

			Layout importedLayout = newLayoutsMap.get(layout.getLayoutId());

			long layoutId = importedLayout.getLayoutId();

			String importThemeId = importTheme(
				groupId, privateLayout, layoutId, themeZip);

			if (importThemeId != null) {
				themeId = importThemeId;

				colorSchemeId =
					ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();
			}

			LayoutLocalServiceUtil.updateLookAndFeel(
				groupId, privateLayout, layoutId, themeId, colorSchemeId,
				importedLayout.getCss(), false);
		}
	}

	public void importTheme(
			PortletDataContext portletDataContext, LayoutSet layoutSet)
		throws Exception {

		boolean importTheme = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.THEME);
		boolean importThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (_log.isDebugEnabled()) {
			_log.debug("Import theme " + importTheme);
			_log.debug("Import theme settings " + importThemeSettings);
		}

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();
		Element headerElement = importDataRootElement.element("header");

		String themeId = layoutSet.getThemeId();
		String colorSchemeId = layoutSet.getColorSchemeId();

		if (importThemeSettings) {
			Attribute themeIdAttribute = headerElement.attribute("theme-id");

			if (themeIdAttribute != null) {
				themeId = themeIdAttribute.getValue();
			}

			Attribute colorSchemeIdAttribute = headerElement.attribute(
				"color-scheme-id");

			if (colorSchemeIdAttribute != null) {
				colorSchemeId = colorSchemeIdAttribute.getValue();
			}
		}

		InputStream themeZip = null;

		if (importTheme) {
			themeZip = portletDataContext.getZipEntryAsInputStream("theme.zip");
		}

		if (themeZip != null) {
			StopWatch stopWatch = null;

			if (_log.isDebugEnabled()) {
				stopWatch = new StopWatch();

				stopWatch.start();
			}

			String importThemeId = importTheme(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(), 0,
				themeZip);

			if (importThemeId != null) {
				themeId = importThemeId;
				colorSchemeId =
					ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Importing theme takes " + stopWatch.getTime() + " ms");
			}
		}

		String css = GetterUtil.getString(headerElement.elementText("css"));

		LayoutSetLocalServiceUtil.updateLookAndFeel(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(), themeId,
			colorSchemeId, css, false);
	}

	protected String importTheme(
			long groupId, boolean privateLayout, long layoutId,
			InputStream themeZip)
		throws Exception {

		ThemeLoader themeLoader = ThemeLoaderFactory.getDefaultThemeLoader();

		if (themeLoader == null) {
			_log.error("No theme loaders are deployed");

			return null;
		}

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(themeZip);

		String lookAndFeelXML = zipReader.getEntryAsString(
			"liferay-look-and-feel.xml");

		StringBundler sb = new StringBundler();

		sb.append(String.valueOf(groupId));

		if (privateLayout) {
			sb.append("-private");
		}
		else {
			sb.append("-public");
		}

		if (Validator.isNotNull(layoutId)) {
			sb.append(StringPool.DASH);
			sb.append(String.valueOf(layoutId));
		}

		if (PropsValues.THEME_LOADER_NEW_THEME_ID_ON_IMPORT) {
			sb.append(StringPool.DASH);
			sb.append(Time.getShortTimestamp());
		}

		String themeId = sb.toString();

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$GROUP_ID$]", "[$THEME_ID$]", "[$THEME_NAME$]"
			},
			new String[] {
				String.valueOf(groupId), themeId, themeId
			}
		);

		FileUtil.deltree(
			themeLoader.getFileStorage() + StringPool.SLASH + themeId);

		List<String> zipEntries = zipReader.getEntries();

		for (String zipEntry : zipEntries) {
			String key = zipEntry;

			sb = new StringBundler(5);

			sb.append(themeLoader.getFileStorage());
			sb.append(StringPool.SLASH);
			sb.append(themeId);
			sb.append(StringPool.SLASH);
			sb.append(key);

			String fileName = sb.toString();

			if (key.equals("liferay-look-and-feel.xml")) {
				FileUtil.write(fileName, lookAndFeelXML.getBytes());
			}
			else {
				InputStream is = zipReader.getEntryAsInputStream(zipEntry);

				FileUtil.write(fileName, is);
			}
		}

		themeLoader.loadThemes();

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			_loadThemesMethodHandler, true);

		clusterRequest.setFireAndForget(true);

		ClusterExecutorUtil.execute(clusterRequest);

		themeId +=
			PortletConstants.WAR_SEPARATOR +
				themeLoader.getServletContextName();

		return PortalUtil.getJsSafePortletId(themeId);
	}

	private static Log _log = LogFactoryUtil.getLog(ThemeImporter.class);

	private static MethodHandler _loadThemesMethodHandler = new MethodHandler(
		new MethodKey(ThemeLoaderFactory.class, "loadThemes"));

}