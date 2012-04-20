/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.templateparser;

import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface TemplateParser {

	public String getLanguageId();

	public String getScript();

	public ThemeDisplay getThemeDisplay();

	public Map<String, String> getTokens();

	public String getViewMode();

	public String getXML();

	public void setContextObjects(Map<String, Object> contextObjects);

	public void setLanguageId(String languageId);

	public void setScript(String script);

	public void setThemeDisplay(ThemeDisplay themeDisplay);

	public void setTokens(Map<String, String> tokens);

	public void setViewMode(String viewMode);

	public void setXML(String xml);

	public String transform() throws TransformException;

}