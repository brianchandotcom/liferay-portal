/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.theme.ThemeCompanyLimit;
import com.liferay.portal.theme.ThemeGroupLimit;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public interface Theme extends Comparable<Theme>, Plugin, Serializable {

	public String getThemeId();

	public ThemeCompanyLimit getThemeCompanyLimit();

	public void setThemeCompanyLimit(ThemeCompanyLimit themeCompanyLimit);

	public boolean isCompanyAvailable(long companyId);

	public ThemeGroupLimit getThemeGroupLimit();

	public void setThemeGroupLimit(ThemeGroupLimit themeGroupLimit);

	public boolean isGroupAvailable(long groupId);

	public long getTimestamp();

	public void setTimestamp(long timestamp);

	public String getName();

	public void setName(String name);

	public String getRootPath();

	public void setRootPath(String rootPath);

	public String getTemplatesPath();

	public void setTemplatesPath(String templatesPath);

	public String getCssPath();

	public void setCssPath(String cssPath);

	public String getDevice();

	public String getImagesPath();

	public void setImagesPath(String imagesPath);

	public String getJavaScriptPath();

	public void setJavaScriptPath(String javaScriptPath);

	public String getVirtualPath();

	public void setVirtualPath(String virtualPath);

	public String getTemplateExtension();

	public void setTemplateExtension(String templateExtension);

	public Map<String, ThemeSetting> getConfigurableSettings();

	public void addSetting(
		 String key, String value, boolean configurable, String type,
		 String[] options);

	public Map<String, ThemeSetting> getSettings();

	public Properties getSettingsProperties();

	public String getSetting(String key);

	public String[] getSettingOptions(String key);

	public void setSetting(String key, String value);

	public boolean getWapTheme();

	public boolean isWapTheme();

	public void setWapTheme(boolean wapTheme);

	public List<ColorScheme> getColorSchemes();

	public Map<String, ColorScheme> getColorSchemesMap();

	public boolean hasColorSchemes();

	public SpriteImage getSpriteImage(String fileName);

	public void setSpriteImages(
		String spriteFileName, Properties spriteProperties);

	public String getServletContextName();

	public void setServletContextName(String servletContextName);

	public boolean getWARFile();

	public boolean isWARFile();

	public String getContextPath();

	public String getFreeMarkerTemplateLoader();

	public boolean getLoadFromServletContext();

	public String getStaticResourcePath();

	public boolean isLoadFromServletContext();

	public void setLoadFromServletContext(boolean loadFromServletContext);

	public String getVelocityResourceListener();

	public String getResourcePath(
		ServletContext servletContext, String portletId, String path);

	public boolean resourceExists(
			ServletContext servletContext, String portletId, String path)
		throws Exception;

}