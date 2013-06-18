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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.SpriteProcessor;
import com.liferay.portal.kernel.image.SpriteProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.plugin.PluginUtil;
import com.liferay.portal.service.base.ThemeLocalServiceBaseImpl;
import com.liferay.portal.theme.ThemeCompanyId;
import com.liferay.portal.theme.ThemeCompanyLimit;
import com.liferay.portal.theme.ThemeGroupId;
import com.liferay.portal.theme.ThemeGroupLimit;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ContextReplace;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class ThemeLocalServiceImpl extends ThemeLocalServiceBaseImpl {

	@Override
	public ColorScheme fetchColorScheme(
		long companyId, String themeId, String colorSchemeId) {

		colorSchemeId = GetterUtil.getString(colorSchemeId);

		Theme theme = fetchTheme(companyId, themeId);

		if (theme == null) {
			return null;
		}

		Map<String, ColorScheme> colorSchemesMap = theme.getColorSchemesMap();

		return colorSchemesMap.get(colorSchemeId);
	}

	@Override
	public Theme fetchTheme(long companyId, String themeId) {
		themeId = GetterUtil.getString(themeId);

		Map<String, Theme> themes = _getThemes(companyId);

		return themes.get(themeId);
	}

	@Override
	public ColorScheme getColorScheme(
			long companyId, String themeId, String colorSchemeId,
			boolean wapTheme)
		throws SystemException {

		colorSchemeId = GetterUtil.getString(colorSchemeId);

		Theme theme = getTheme(companyId, themeId, wapTheme);

		Map<String, ColorScheme> colorSchemesMap = theme.getColorSchemesMap();

		ColorScheme colorScheme = colorSchemesMap.get(colorSchemeId);

		if (colorScheme == null) {
			List<ColorScheme> colorSchemes = theme.getColorSchemes();

			if (colorSchemes.size() > 0) {
				for (int i = (colorSchemes.size() - 1); i >= 0; i--) {
					colorScheme = colorSchemes.get(i);

					if (colorScheme.isDefaultCs()) {
						break;
					}
				}
			}
		}

		if (colorScheme == null) {
			if (wapTheme) {
				colorScheme = ColorSchemeFactoryUtil.getDefaultWapColorScheme();
			}
			else {
				colorScheme =
					ColorSchemeFactoryUtil.getDefaultRegularColorScheme();
			}
		}

		return colorScheme;
	}

	@Override
	public Theme getTheme(long companyId, String themeId, boolean wapTheme)
		throws SystemException {

		themeId = GetterUtil.getString(themeId);

		Map<String, Theme> themes = _getThemes(companyId);

		Theme theme = themes.get(themeId);

		if (theme == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No theme found for specified theme id " + themeId +
						". Returning the default theme.");
			}

			if (wapTheme) {
				themeId = ThemeFactoryUtil.getDefaultWapThemeId(companyId);
			}
			else {
				themeId = ThemeFactoryUtil.getDefaultRegularThemeId(companyId);
			}

			theme = _themes.get(themeId);
		}

		if (theme == null) {
			if (_themes.isEmpty()) {
				if (_log.isDebugEnabled()) {
					_log.debug("No themes are installed");
				}

				return null;
			}

			if (!themeId.contains(PortletConstants.WAR_SEPARATOR)) {
				_log.error(
					"No theme found for default theme id " + themeId +
						". Returning a random theme.");
			}

			for (Map.Entry<String, Theme> entry : _themes.entrySet()) {
				theme = entry.getValue();
			}
		}

		return theme;
	}

	@Override
	public List<Theme> getThemes(long companyId) {
		Map<String, Theme> themes = _getThemes(companyId);

		List<Theme> themesList = ListUtil.fromMapValues(themes);

		return ListUtil.sort(themesList);
	}

	@Override
	public List<Theme> getThemes(
			long companyId, long groupId, long userId, boolean wapTheme)
		throws SystemException {

		List<Theme> themes = getThemes(companyId);

		themes = PluginUtil.restrictPlugins(themes, companyId, userId);

		Iterator<Theme> itr = themes.iterator();

		while (itr.hasNext()) {
			Theme theme = itr.next();

			if (theme.getThemeId().equals("controlpanel") ||
				!theme.isGroupAvailable(groupId) ||
				(theme.isWapTheme() != wapTheme)) {

				itr.remove();
			}
		}

		return themes;
	}

	@Override
	public List<Theme> getWARThemes() {
		List<Theme> themes = ListUtil.fromMapValues(_themes);

		Iterator<Theme> itr = themes.iterator();

		while (itr.hasNext()) {
			Theme theme = itr.next();

			if (!theme.isWARFile()) {
				itr.remove();
			}
		}

		return themes;
	}

	@Override
	public List<Theme> init(
		ServletContext servletContext, String themesPath,
		boolean loadFromServletContext, String[] xmls,
		PluginPackage pluginPackage) {

		return init(
			null, servletContext, themesPath, loadFromServletContext, xmls,
			pluginPackage);
	}

	@Override
	public List<Theme> init(
		String servletContextName, ServletContext servletContext,
		String themesPath, boolean loadFromServletContext, String[] xmls,
		PluginPackage pluginPackage) {

		List<Theme> themeList = new ArrayList<Theme>();

		try {
			for (String xml : xmls) {
				Set<Theme> themes = _readThemes(
					servletContextName, servletContext, themesPath,
					loadFromServletContext, xml, pluginPackage);

				for (Theme theme : themes) {
					if (!themeList.contains(theme)) {
						themeList.add(theme);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		_themesPool.clear();

		return themeList;
	}

	@Override
	public void uninstallThemes(List<Theme> themes) {
		for (Theme theme : themes) {
			String themeId = theme.getThemeId();

			_themes.remove(themeId);

			layoutTemplateLocalService.uninstallLayoutTemplates(themeId);
		}

		_themesPool.clear();
	}

	private List<ThemeCompanyId> _getCompanyLimitExcludes(Element element) {
		List<ThemeCompanyId> includes = new ArrayList<ThemeCompanyId>();

		if (element == null) {
			return includes;
		}

		List<Element> companyIdsElements = element.elements("company-id");

		for (int i = 0; i < companyIdsElements.size(); i++) {
			Element companyIdElement = companyIdsElements.get(i);

			String name = companyIdElement.attributeValue("name");
			String pattern = companyIdElement.attributeValue("pattern");

			ThemeCompanyId themeCompanyId = null;

			if (Validator.isNotNull(name)) {
				themeCompanyId = new ThemeCompanyId(name, false);
			}
			else if (Validator.isNotNull(pattern)) {
				themeCompanyId = new ThemeCompanyId(pattern, true);
			}

			if (themeCompanyId != null) {
				includes.add(themeCompanyId);
			}
		}

		return includes;
	}

	private List<ThemeCompanyId> _getCompanyLimitIncludes(Element element) {
		return _getCompanyLimitExcludes(element);
	}

	private List<ThemeGroupId> _getGroupLimitExcludes(Element element) {
		List<ThemeGroupId> includes = new ArrayList<ThemeGroupId>();

		if (element == null) {
			return includes;
		}

		List<Element> groupIdsElements = element.elements("group-id");

		for (int i = 0; i < groupIdsElements.size(); i++) {
			Element groupIdElement = groupIdsElements.get(i);

			String name = groupIdElement.attributeValue("name");
			String pattern = groupIdElement.attributeValue("pattern");

			ThemeGroupId themeGroupId = null;

			if (Validator.isNotNull(name)) {
				themeGroupId = new ThemeGroupId(name, false);
			}
			else if (Validator.isNotNull(pattern)) {
				themeGroupId = new ThemeGroupId(pattern, true);
			}

			if (themeGroupId != null) {
				includes.add(themeGroupId);
			}
		}

		return includes;
	}

	private List<ThemeGroupId> _getGroupLimitIncludes(Element element) {
		return _getGroupLimitExcludes(element);
	}

	private Map<String, Theme> _getThemes(long companyId) {
		Map<String, Theme> themes = _themesPool.get(companyId);

		if (themes != null) {
			return themes;
		}

		themes = new ConcurrentHashMap<String, Theme>();

		for (Map.Entry<String, Theme> entry : _themes.entrySet()) {
			String themeId = entry.getKey();
			Theme theme = entry.getValue();

			if (theme.isCompanyAvailable(companyId)) {
				themes.put(themeId, theme);
			}
		}

		_themesPool.put(companyId, themes);

		return themes;
	}

	private Version _getVersion(String version) {
		if (version.equals("${current-version}")) {
			version = ReleaseInfo.getVersion();
		}

		return Version.getInstance(version);
	}

	private void _readColorSchemes(
		Element themeElement, Map<String, ColorScheme> colorSchemes,
		ContextReplace themeContextReplace) {

		List<Element> colorSchemeElements = themeElement.elements(
			"color-scheme");

		for (Element colorSchemeElement : colorSchemeElements) {
			ContextReplace colorSchemeContextReplace =
				(ContextReplace)themeContextReplace.clone();

			String id = colorSchemeElement.attributeValue("id");

			colorSchemeContextReplace.addValue("color-scheme-id", id);

			ColorScheme colorSchemeModel = colorSchemes.get(id);

			if (colorSchemeModel == null) {
				colorSchemeModel = ColorSchemeFactoryUtil.getColorScheme(id);
			}

			String name = GetterUtil.getString(
				colorSchemeElement.attributeValue("name"),
				colorSchemeModel.getName());

			name = colorSchemeContextReplace.replace(name);

			boolean defaultCs = GetterUtil.getBoolean(
				colorSchemeElement.elementText("default-cs"),
				colorSchemeModel.isDefaultCs());

			String cssClass = GetterUtil.getString(
				colorSchemeElement.elementText("css-class"),
				colorSchemeModel.getCssClass());

			cssClass = colorSchemeContextReplace.replace(cssClass);

			colorSchemeContextReplace.addValue("css-class", cssClass);

			String colorSchemeImagesPath = GetterUtil.getString(
				colorSchemeElement.elementText("color-scheme-images-path"),
				colorSchemeModel.getColorSchemeImagesPath());

			colorSchemeImagesPath = colorSchemeContextReplace.replace(
				colorSchemeImagesPath);

			colorSchemeContextReplace.addValue(
				"color-scheme-images-path", colorSchemeImagesPath);

			colorSchemeModel.setName(name);
			colorSchemeModel.setDefaultCs(defaultCs);
			colorSchemeModel.setCssClass(cssClass);
			colorSchemeModel.setColorSchemeImagesPath(colorSchemeImagesPath);

			colorSchemes.put(id, colorSchemeModel);
		}
	}

	private Set<Theme> _readThemes(
			String servletContextName, ServletContext servletContext,
			String themesPath, boolean loadFromServletContext, String xml,
			PluginPackage pluginPackage)
		throws Exception {

		Set<Theme> themes = new HashSet<Theme>();

		if (xml == null) {
			return themes;
		}

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		Version portalVersion = _getVersion(ReleaseInfo.getVersion());

		boolean compatible = false;

		Element compatibilityElement = rootElement.element("compatibility");

		if (compatibilityElement != null) {
			List<Element> versionElements = compatibilityElement.elements(
				"version");

			for (Element versionElement : versionElements) {
				Version version = _getVersion(versionElement.getTextTrim());

				if (version.includes(portalVersion)) {
					compatible = true;

					break;
				}
			}
		}

		if (!compatible) {
			_log.error(
				"Themes in this WAR are not compatible with " +
					ReleaseInfo.getServerInfo());

			return themes;
		}

		ThemeCompanyLimit companyLimit = null;

		Element companyLimitElement = rootElement.element("company-limit");

		if (companyLimitElement != null) {
			companyLimit = new ThemeCompanyLimit();

			Element companyIncludesElement = companyLimitElement.element(
				"company-includes");

			if (companyIncludesElement != null) {
				companyLimit.setIncludes(
					_getCompanyLimitIncludes(companyIncludesElement));
			}

			Element companyExcludesElement = companyLimitElement.element(
				"company-excludes");

			if (companyExcludesElement != null) {
				companyLimit.setExcludes(
					_getCompanyLimitExcludes(companyExcludesElement));
			}
		}

		ThemeGroupLimit groupLimit = null;

		Element groupLimitElement = rootElement.element("group-limit");

		if (groupLimitElement != null) {
			groupLimit = new ThemeGroupLimit();

			Element groupIncludesElement = groupLimitElement.element(
				"group-includes");

			if (groupIncludesElement != null) {
				groupLimit.setIncludes(
					_getGroupLimitIncludes(groupIncludesElement));
			}

			Element groupExcludesElement = groupLimitElement.element(
				"group-excludes");

			if (groupExcludesElement != null) {
				groupLimit.setExcludes(
					_getGroupLimitExcludes(groupExcludesElement));
			}
		}

		long timestamp = ServletContextUtil.getLastModified(servletContext);

		List<Element> themeElements = rootElement.elements("theme");

		for (Element themeElement : themeElements) {
			ContextReplace themeContextReplace = new ContextReplace();

			themeContextReplace.addValue("themes-path", themesPath);

			String themeId = themeElement.attributeValue("id");

			if (servletContextName != null) {
				themeId =
					themeId + PortletConstants.WAR_SEPARATOR +
						servletContextName;
			}

			themeId = PortalUtil.getJsSafePortletId(themeId);

			themeContextReplace.addValue("theme-id", themeId);

			Theme theme = _themes.get(themeId);

			if (theme == null) {
				theme = ThemeFactoryUtil.getTheme(themeId);
			}

			theme.setTimestamp(timestamp);

			PluginSetting pluginSetting =
				pluginSettingLocalService.getDefaultPluginSetting();

			theme.setPluginPackage(pluginPackage);
			theme.setDefaultPluginSetting(pluginSetting);

			theme.setThemeCompanyLimit(companyLimit);
			theme.setThemeGroupLimit(groupLimit);

			if (servletContextName != null) {
				theme.setServletContextName(servletContextName);
			}

			theme.setLoadFromServletContext(loadFromServletContext);

			String name = GetterUtil.getString(
				themeElement.attributeValue("name"), theme.getName());

			String rootPath = GetterUtil.getString(
				themeElement.elementText("root-path"), theme.getRootPath());

			rootPath = themeContextReplace.replace(rootPath);

			themeContextReplace.addValue("root-path", rootPath);

			String templatesPath = GetterUtil.getString(
				themeElement.elementText("templates-path"),
				theme.getTemplatesPath());

			templatesPath = themeContextReplace.replace(templatesPath);
			templatesPath = StringUtil.safePath(templatesPath);

			themeContextReplace.addValue("templates-path", templatesPath);

			String cssPath = GetterUtil.getString(
				themeElement.elementText("css-path"), theme.getCssPath());

			cssPath = themeContextReplace.replace(cssPath);
			cssPath = StringUtil.safePath(cssPath);

			themeContextReplace.addValue("css-path", cssPath);

			String imagesPath = GetterUtil.getString(
				themeElement.elementText("images-path"), theme.getImagesPath());

			imagesPath = themeContextReplace.replace(imagesPath);
			imagesPath = StringUtil.safePath(imagesPath);

			themeContextReplace.addValue("images-path", imagesPath);

			String javaScriptPath = GetterUtil.getString(
				themeElement.elementText("javascript-path"),
				theme.getJavaScriptPath());

			javaScriptPath = themeContextReplace.replace(javaScriptPath);
			javaScriptPath = StringUtil.safePath(javaScriptPath);

			themeContextReplace.addValue("javascript-path", javaScriptPath);

			String virtualPath = GetterUtil.getString(
				themeElement.elementText("virtual-path"),
				theme.getVirtualPath());

			String templateExtension = GetterUtil.getString(
				themeElement.elementText("template-extension"),
				theme.getTemplateExtension());

			theme.setName(name);
			theme.setRootPath(rootPath);
			theme.setTemplatesPath(templatesPath);
			theme.setCssPath(cssPath);
			theme.setImagesPath(imagesPath);
			theme.setJavaScriptPath(javaScriptPath);
			theme.setVirtualPath(virtualPath);
			theme.setTemplateExtension(templateExtension);

			Element settingsElement = themeElement.element("settings");

			if (settingsElement != null) {
				List<Element> settingElements = settingsElement.elements(
					"setting");

				for (Element settingElement : settingElements) {
					boolean configurable = GetterUtil.getBoolean(
						settingElement.attributeValue("configurable"));
					String key = settingElement.attributeValue("key");
					String[] options = StringUtil.split(
						settingElement.attributeValue("options"));
					String type = settingElement.attributeValue("type", "text");
					String value = settingElement.attributeValue(
						"value", StringPool.BLANK);
					String script = settingElement.getTextTrim();

					theme.addSetting(
						key, value, configurable, type, options, script);
				}
			}

			theme.setWapTheme(
				GetterUtil.getBoolean(
					themeElement.elementText("wap-theme"), theme.isWapTheme()));

			Element rolesElement = themeElement.element("roles");

			if (rolesElement != null) {
				List<Element> roleNameElements = rolesElement.elements(
					"role-name");

				for (Element roleNameElement : roleNameElements) {
					pluginSetting.addRole(roleNameElement.getText());
				}
			}

			_readColorSchemes(
				themeElement, theme.getColorSchemesMap(), themeContextReplace);
			_readColorSchemes(
				themeElement, theme.getColorSchemesMap(), themeContextReplace);

			Element layoutTemplatesElement = themeElement.element(
				"layout-templates");

			if (layoutTemplatesElement != null) {
				Element standardElement = layoutTemplatesElement.element(
					"standard");

				if (standardElement != null) {
					layoutTemplateLocalService.readLayoutTemplate(
						servletContextName, servletContext, null,
						standardElement, true, themeId, pluginPackage);
				}

				Element customElement = layoutTemplatesElement.element(
					"custom");

				if (customElement != null) {
					layoutTemplateLocalService.readLayoutTemplate(
						servletContextName, servletContext, null, customElement,
						false, themeId, pluginPackage);
				}
			}

			if (!theme.isWapTheme()) {
				_setSpriteImages(servletContext, theme, imagesPath);
			}

			if (!_themes.containsKey(themeId)) {
				_themes.put(themeId, theme);
			}

			themes.add(theme);
		}

		return themes;
	}

	private void _setSpriteImages(
			ServletContext servletContext, Theme theme, String resourcePath)
		throws Exception {

		if (!resourcePath.startsWith(StringPool.SLASH)) {
			resourcePath = StringPool.SLASH.concat(resourcePath);
		}

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		if ((resourcePaths == null) || resourcePaths.isEmpty()) {
			return;
		}

		List<URL> imageURLs = new ArrayList<URL>(resourcePaths.size());

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				_setSpriteImages(servletContext, theme, curResourcePath);
			}
			else if (curResourcePath.endsWith(".png")) {
				URL imageURL = servletContext.getResource(curResourcePath);

				if (imageURL != null) {
					imageURLs.add(imageURL);
				}
				else {
					_log.error(
						"Resource URL for " + curResourcePath + " is null");
				}
			}
		}

		String spriteRootDirName = PropsValues.SPRITE_ROOT_DIR;
		String spriteFileName = resourcePath.concat(
			PropsValues.SPRITE_FILE_NAME);
		String spritePropertiesFileName = resourcePath.concat(
			PropsValues.SPRITE_PROPERTIES_FILE_NAME);
		String rootPath = ServletContextUtil.getRootPath(servletContext);

		Properties spriteProperties = SpriteProcessorUtil.generate(
			servletContext, imageURLs, spriteRootDirName, spriteFileName,
			spritePropertiesFileName, rootPath, 16, 16, 10240);

		if (spriteProperties == null) {
			return;
		}

		String contextPath = ContextPathUtil.getContextPath(servletContext);

		spriteFileName = contextPath.concat(SpriteProcessor.PATH).concat(
			spriteFileName);

		theme.setSpriteImages(spriteFileName, spriteProperties);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ThemeLocalServiceImpl.class);

	private static Map<String, Theme> _themes =
		new ConcurrentHashMap<String, Theme>();
	private static Map<Long, Map<String, Theme>> _themesPool =
		new ConcurrentHashMap<Long, Map<String, Theme>>();

}