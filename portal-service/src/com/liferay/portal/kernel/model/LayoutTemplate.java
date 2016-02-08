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

package com.liferay.portal.kernel.model;

import java.io.IOException;
import java.io.Serializable;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public interface LayoutTemplate
	extends Comparable<LayoutTemplate>, Plugin, Serializable {

	public List<String> getColumns();

	public String getContent();

	public String getContextPath();

	public String getLayoutTemplateId();

	public String getName();

	public String getName(Locale locale);

	public String getServletContextName();

	public boolean getStandard();

	public String getStaticResourcePath();

	public String getTemplatePath();

	public String getThemeId();

	public String getThumbnailPath();

	public String getUncachedContent() throws IOException;

	public boolean getWARFile();

	public boolean hasSetContent();

	public boolean isStandard();

	public boolean isWARFile();

	public void setColumns(List<String> columns);

	public void setContent(String content);

	public void setName(String name);

	public void setServletContext(ServletContext servletContext);

	public void setServletContextName(String servletContextName);

	public void setStandard(boolean standard);

	public void setTemplatePath(String templatePath);

	public void setThemeId(String themeId);

	public void setThumbnailPath(String thumbnailPath);

}