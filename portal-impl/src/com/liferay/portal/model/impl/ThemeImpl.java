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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeHelper;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.model.SpriteImage;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.ThemeSetting;
import com.liferay.portal.theme.ThemeCompanyId;
import com.liferay.portal.theme.ThemeCompanyLimit;
import com.liferay.portal.theme.ThemeGroupLimit;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Raymond Augé
 */
public class ThemeImpl extends PluginBaseImpl implements Theme {

	public ThemeImpl() {
	}

	public ThemeImpl(String themeId) {
		_themeId = themeId;
	}

	public ThemeImpl(String themeId, String name) {
		_themeId = themeId;
		_name = name;
	}

	public void addSetting(
		String key, String value, boolean configurable, String type,
		String[] options, String script) {

		ThemeSetting themeSetting = new ThemeSettingImpl(
			configurable, options, script, type, value);

		_themeSettingsMap.put(key, themeSetting);
	}

	public int compareTo(Theme theme) {
		return getName().compareTo(theme.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Theme)) {
			return false;
		}

		Theme theme = (Theme)obj;

		String themeId = theme.getThemeId();

		if (getThemeId().equals(themeId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public List<ColorScheme> getColorSchemes() {
		List<ColorScheme> colorSchemes = ListUtil.fromMapValues(
			_colorSchemesMap);

		return ListUtil.sort(colorSchemes);
	}

	public Map<String, ColorScheme> getColorSchemesMap() {
		return _colorSchemesMap;
	}

	public Map<String, ThemeSetting> getConfigurableSettings() {
		Map<String, ThemeSetting> configurableSettings =
			new LinkedHashMap<String, ThemeSetting>();

		for (Map.Entry<String, ThemeSetting> entry :
				_themeSettingsMap.entrySet()) {

			ThemeSetting themeSetting = entry.getValue();

			if (themeSetting.isConfigurable()) {
				configurableSettings.put(entry.getKey(), entry.getValue());
			}
		}

		return configurableSettings;
	}

	public String getContextPath() {
		if (!isWARFile()) {
			return PortalUtil.getPathContext();
		}

		String servletContextName = getServletContextName();

		if (ServletContextPool.containsKey(servletContextName)) {
			ServletContext servletContext = ServletContextPool.get(
				servletContextName);

			return ContextPathUtil.getContextPath(servletContext);
		}

		return StringPool.SLASH.concat(servletContextName);
	}

	public String getCssPath() {
		return _cssPath;
	}

	public String getDevice() {
		if (isWapTheme()) {
			return "wap";
		}
		else {
			return "regular";
		}
	}

	public String getFreeMarkerTemplateLoader() {
		if (_loadFromServletContext) {
			return TemplateConstants.SERVLET_SEPARATOR;
		}
		else {
			return TemplateConstants.THEME_LOADER_SEPARATOR;
		}
	}

	public String getImagesPath() {
		return _imagesPath;
	}

	public String getJavaScriptPath() {
		return _javaScriptPath;
	}

	public boolean getLoadFromServletContext() {
		return _loadFromServletContext;
	}

	public String getName() {
		return _name;
	}

	public String getPluginId() {
		return getThemeId();
	}

	public String getPluginType() {
		return Plugin.TYPE_THEME;
	}

	public String getResourcePath(
		ServletContext servletContext, String portletId, String path) {

		if (!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED) {
			return ThemeHelper.getResourcePath(
				servletContext, this, portletId, path);
		}

		String key = path;

		if (Validator.isNotNull(portletId)) {
			key = path.concat(StringPool.POUND).concat(portletId);
		}

		String resourcePath = _resourcePathsMap.get(key);

		if (resourcePath != null) {
			return resourcePath;
		}

		resourcePath = ThemeHelper.getResourcePath(
			servletContext, this, portletId, path);

		_resourcePathsMap.put(key, resourcePath);

		return resourcePath;
	}

	public String getRootPath() {
		return _rootPath;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public String getSetting(String key) {
		String value = null;

		ThemeSetting themeSetting = _themeSettingsMap.get(key);

		if (themeSetting != null) {
			value = themeSetting.getValue();
		}

		return value;
	}

	public String[] getSettingOptions(String key) {
		String[] options = null;

		ThemeSetting themeSetting = _themeSettingsMap.get(key);

		if (themeSetting != null) {
			options = themeSetting.getOptions();
		}

		return options;
	}

	public Map<String, ThemeSetting> getSettings() {
		return _themeSettingsMap;
	}

	public Properties getSettingsProperties() {
		Properties properties = new Properties();

		for (String key : _themeSettingsMap.keySet()) {
			ThemeSetting setting = _themeSettingsMap.get(key);

			if (setting != null) {
				properties.setProperty(key, setting.getValue());
			}
		}

		return properties;
	}

	public SpriteImage getSpriteImage(String fileName) {
		return _spriteImagesMap.get(fileName);
	}

	public String getStaticResourcePath() {
		String proxyPath = PortalUtil.getPathProxy();

		String virtualPath = getVirtualPath();

		if (Validator.isNotNull(virtualPath)) {
			return proxyPath.concat(virtualPath);
		}

		String contextPath = getContextPath();

		if (!isWARFile()) {
			return contextPath;
		}

		return proxyPath.concat(contextPath);
	}

	public String getTemplateExtension() {
		return _templateExtension;
	}

	public String getTemplatesPath() {
		return _templatesPath;
	}

	public ThemeCompanyLimit getThemeCompanyLimit() {
		return _themeCompanyLimit;
	}

	public ThemeGroupLimit getThemeGroupLimit() {
		return _themeGroupLimit;
	}

	public String getThemeId() {
		return _themeId;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	public String getVelocityResourceListener() {
		if (_loadFromServletContext) {
			return TemplateConstants.SERVLET_SEPARATOR;
		}
		else {
			return TemplateConstants.THEME_LOADER_SEPARATOR;
		}
	}

	public String getVirtualPath() {
		return _virtualPath;
	}

	public boolean getWapTheme() {
		return _wapTheme;
	}

	public boolean getWARFile() {
		return _warFile;
	}

	public boolean hasColorSchemes() {
		if (_colorSchemesMap.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return _themeId.hashCode();
	}

	public boolean isCompanyAvailable(long companyId) {
		return isAvailable(getThemeCompanyLimit(), companyId);
	}

	public boolean isGroupAvailable(long groupId) {
		return isAvailable(getThemeGroupLimit(), groupId);
	}

	public boolean isLoadFromServletContext() {
		return _loadFromServletContext;
	}

	public boolean isWapTheme() {
		return _wapTheme;
	}

	public boolean isWARFile() {
		return _warFile;
	}

	public boolean resourceExists(
			ServletContext servletContext, String portletId, String path)
		throws Exception {

		if (!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED) {
			return ThemeHelper.resourceExists(
				servletContext, this, portletId, path);
		}

		if (Validator.isNull(path)) {
			return false;
		}

		String key = path;

		if (Validator.isNotNull(portletId)) {
			key = path.concat(StringPool.POUND).concat(portletId);
		}

		Boolean resourceExists = _resourceExistsMap.get(key);

		if (resourceExists != null) {
			return resourceExists;
		}

		resourceExists = ThemeHelper.resourceExists(
			servletContext, this, portletId, path);

		_resourceExistsMap.put(key, resourceExists);

		return resourceExists;
	}

	public void setCssPath(String cssPath) {
		_cssPath = cssPath;
	}

	public void setImagesPath(String imagesPath) {
		_imagesPath = imagesPath;
	}

	public void setJavaScriptPath(String javaScriptPath) {
		_javaScriptPath = javaScriptPath;
	}

	public void setLoadFromServletContext(boolean loadFromServletContext) {
		_loadFromServletContext = loadFromServletContext;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRootPath(String rootPath) {
		_rootPath = rootPath;
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;

		if (Validator.isNotNull(_servletContextName)) {
			_warFile = true;
		}
		else {
			_warFile = false;
		}
	}

	public void setSetting(String key, String value) {
		ThemeSetting themeSetting = _themeSettingsMap.get(key);

		if (themeSetting != null) {
			themeSetting.setValue(value);
		}
		else {
			addSetting(key, value, false, null, null, null);
		}
	}

	public void setSpriteImages(
		String spriteFileName, Properties spriteProperties) {

		for (Map.Entry<Object, Object> entry : spriteProperties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			int[] values = StringUtil.split(value, 0);

			int offset = values[0];
			int height = values[1];
			int width = values[2];

			SpriteImage spriteImage = new SpriteImage(
				spriteFileName, key, offset, height, width);

			_spriteImagesMap.put(key, spriteImage);
		}
	}

	public void setTemplateExtension(String templateExtension) {
		_templateExtension = templateExtension;
	}

	public void setTemplatesPath(String templatesPath) {
		_templatesPath = templatesPath;
	}

	public void setThemeCompanyLimit(ThemeCompanyLimit themeCompanyLimit) {
		_themeCompanyLimit = themeCompanyLimit;
	}

	public void setThemeGroupLimit(ThemeGroupLimit themeGroupLimit) {
		_themeGroupLimit = themeGroupLimit;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	public void setVirtualPath(String virtualPath) {
		if (_warFile && Validator.isNull(virtualPath)) {
			virtualPath = PropsValues.THEME_VIRTUAL_PATH;
		}

		_virtualPath = virtualPath;
	}

	public void setWapTheme(boolean wapTheme) {
		_wapTheme = wapTheme;
	}

	protected boolean isAvailable(ThemeCompanyLimit limit, long id) {
		boolean available = true;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Check if theme " + getThemeId() + " is available for " + id);
		}

		if (limit != null) {
			List<ThemeCompanyId> includes = limit.getIncludes();
			List<ThemeCompanyId> excludes = limit.getExcludes();

			if ((includes.size() != 0) && (excludes.size() != 0)) {

				// Since includes and excludes are specified, check to make sure
				// the current company id is included and also not excluded

				if (_log.isDebugEnabled()) {
					_log.debug("Check includes and excludes");
				}

				available = limit.isIncluded(id);

				if (available) {
					available = !limit.isExcluded(id);
				}
			}
			else if ((includes.size() == 0) && (excludes.size() != 0)) {

				// Since no includes are specified, check to make sure the
				// current company id is not excluded

				if (_log.isDebugEnabled()) {
					_log.debug("Check excludes");
				}

				available = !limit.isExcluded(id);
			}
			else if ((includes.size() != 0) && (excludes.size() == 0)) {

				// Since no excludes are specified, check to make sure the
				// current company id is included

				if (_log.isDebugEnabled()) {
					_log.debug("Check includes");
				}

				available = limit.isIncluded(id);
			}
			else {

				// Since no includes or excludes are specified, this theme is
				// available for every company

				if (_log.isDebugEnabled()) {
					_log.debug("No includes or excludes set");
				}

				available = true;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Theme " + getThemeId() + " is " +
					(!available ? "NOT " : "") + "available for " + id);
		}

		return available;
	}

	private static Log _log = LogFactoryUtil.getLog(ThemeImpl.class);

	private Map<String, ColorScheme> _colorSchemesMap =
		new HashMap<String, ColorScheme>();
	private String _cssPath = "${root-path}/css";
	private String _imagesPath = "${root-path}/images";
	private String _javaScriptPath = "${root-path}/js";
	private boolean _loadFromServletContext;
	private String _name;
	private Map<String, Boolean> _resourceExistsMap =
		new ConcurrentHashMap<String, Boolean>();
	private Map<String, String> _resourcePathsMap =
		new ConcurrentHashMap<String, String>();
	private String _rootPath = "/";
	private String _servletContextName = StringPool.BLANK;
	private Map<String, SpriteImage> _spriteImagesMap =
		new HashMap<String, SpriteImage>();
	private String _templateExtension = "vm";
	private String _templatesPath = "${root-path}/templates";
	private ThemeCompanyLimit _themeCompanyLimit;
	private ThemeGroupLimit _themeGroupLimit;
	private String _themeId;
	private Map<String, ThemeSetting> _themeSettingsMap =
		new LinkedHashMap<String, ThemeSetting>();
	private long _timestamp;
	private String _virtualPath = StringPool.BLANK;
	private boolean _wapTheme;
	private boolean _warFile;

}