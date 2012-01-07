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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class LayoutTemplateImpl
	 extends PluginBaseImpl implements LayoutTemplate {

	public LayoutTemplateImpl() {
	}

	public LayoutTemplateImpl(String layoutTemplateId) {
		_layoutTemplateId = layoutTemplateId;
	}

	public LayoutTemplateImpl(String layoutTemplateId, String name) {
		_layoutTemplateId = layoutTemplateId;
		_name = name;
	}

	public String getLayoutTemplateId() {
		return _layoutTemplateId;
	}

	public String getPluginId() {
		return getLayoutTemplateId();
	}

	public String getPluginType() {
		return Plugin.TYPE_LAYOUT_TEMPLATE;
	}

	public boolean getStandard() {
		return _standard;
	}

	public boolean isStandard() {
		return _standard;
	}

	public void setStandard(boolean standard) {
		_standard = standard;
	}

	public String getThemeId() {
		return _themeId;
	}

	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	public String getName() {
		if (Validator.isNull(_name)) {
			return _layoutTemplateId;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	public String getTemplatePath() {
		return _templatePath;
	}

	public void setTemplatePath(String templatePath) {
		_templatePath = templatePath;
	}

	public String getWapTemplatePath() {
		return _wapTemplatePath;
	}

	public void setWapTemplatePath(String wapTemplatePath) {
		_wapTemplatePath = wapTemplatePath;
	}

	public String getThumbnailPath() {
		return _thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		_thumbnailPath = thumbnailPath;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_setContent = true;

		_content = content;
	}

	public boolean hasSetContent() {
		return _setContent;
	}

	public String getUncachedContent() throws IOException {
		if (_servletContext == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Cannot get latest content for " + _servletContextName +
						" " + getTemplatePath() +
							" because the servlet context is null");
			}

			return _content;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Getting latest content for " + _servletContextName + " " +
					getTemplatePath());
		}

		String content = HttpUtil.URLtoString(
			_servletContext.getResource(getTemplatePath()));

		setContent(content);

		return content;
	}

	public String getUncachedWapContent() {
		if (_servletContext == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Cannot get latest WAP content for " + _servletContextName +
						" " + getWapTemplatePath() +
							" because the servlet context is null");
			}

			return _wapContent;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Getting latest WAP content for " + _servletContextName + " " +
					getWapTemplatePath());
		}

		String wapContent = null;

		try {
			wapContent = HttpUtil.URLtoString(
				_servletContext.getResource(getWapTemplatePath()));
		}
		catch (Exception e) {
			_log.error(
				"Unable to get content at WAP template path " +
					getWapTemplatePath() + ": " + e.getMessage());
		}

		setWapContent(wapContent);

		return wapContent;
	}

	public String getWapContent() {
		return _wapContent;
	}

	public void setWapContent(String wapContent) {
		_setWapContent = true;

		_wapContent = wapContent;
	}

	public boolean hasSetWapContent() {
		return _setWapContent;
	}

	public List<String> getColumns() {
		return _columns;
	}

	public void setColumns(List<String> columns) {
		_columns = columns;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public String getServletContextName() {
		return _servletContextName;
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

	public boolean getWARFile() {
		return _warFile;
	}

	public boolean isWARFile() {
		return _warFile;
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

	public String getStaticResourcePath() {
		String proxyPath = PortalUtil.getPathProxy();

		String contextPath = getContextPath();

		if (!isWARFile()) {
			return contextPath;
		}

		return proxyPath.concat(contextPath);
	}

	public int compareTo(LayoutTemplate layoutTemplate) {
		if (layoutTemplate == null) {
			return -1;
		}

		return getName().compareTo(layoutTemplate.getName());
	}

	public boolean equals(LayoutTemplate layoutTemplate) {
		if (layoutTemplate == null) {
			return false;
		}

		String layoutTemplateId = layoutTemplate.getLayoutTemplateId();

		if (getLayoutTemplateId().equals(layoutTemplateId)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutTemplateImpl.class);

	private String _layoutTemplateId;
	private boolean _standard;
	private String _themeId;
	private String _name;
	private String _templatePath;
	private String _wapTemplatePath;
	private String _thumbnailPath;
	private String _content;
	private boolean _setContent;
	private String _wapContent;
	private boolean _setWapContent;
	private List<String> _columns = new ArrayList<String>();
	private transient ServletContext _servletContext;
	private String _servletContextName = StringPool.BLANK;
	private boolean _warFile;

}