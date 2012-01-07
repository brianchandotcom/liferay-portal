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

package com.liferay.portal.model;

import java.io.IOException;
import java.io.Serializable;

import java.util.List;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public interface LayoutTemplate
	extends Comparable<LayoutTemplate>, Plugin, Serializable {

	public String getLayoutTemplateId();

	public boolean getStandard();

	public boolean isStandard();

	public void setStandard(boolean standard);

	public String getThemeId();

	public void setThemeId(String themeId);

	public String getName();

	public void setName(String name);

	public String getTemplatePath();

	public void setTemplatePath(String templatePath);

	public String getWapTemplatePath();

	public void setWapTemplatePath(String wapWapTemplatePath);

	public String getThumbnailPath();

	public void setThumbnailPath(String thumbnailPath);

	public String getContent();

	public void setContent(String content);

	public String getUncachedContent() throws IOException;

	public String getUncachedWapContent() throws IOException;

	public String getWapContent();

	public void setWapContent(String wapContent);

	public List<String> getColumns();

	public void setColumns(List<String> columns);

	public void setServletContext(ServletContext servletContext);

	public String getServletContextName();

	public void setServletContextName(String servletContextName);

	public boolean getWARFile();

	public String getContextPath();

	public String getStaticResourcePath();

	public boolean hasSetContent();

	public boolean hasSetWapContent();

	public boolean isWARFile();

}