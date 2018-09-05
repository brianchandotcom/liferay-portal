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

package com.liferay.portal.model.impl;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class LayoutTemplateImpl
	extends PluginBaseImpl implements LayoutTemplate {

	public LayoutTemplateImpl() {
		this(null, null);
	}

	public LayoutTemplateImpl(String layoutTemplateId) {
		this(layoutTemplateId, null);
	}

	public LayoutTemplateImpl(String layoutTemplateId, String name) {
		_layoutTemplateId = layoutTemplateId;
		_name = name;
	}

	@Override
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

		return false;
	}

	@Override
	public List<String> getColumns() {
		return _columns;
	}

	@Override
	public String getContent() {
		return _content;
	}

	@Override
	public String getContextPath() {
		if (!isWARFile()) {
			return PortalUtil.getPathContext();
		}

		String servletContextName = getServletContextName();

		if (ServletContextPool.containsKey(servletContextName)) {
			ServletContext servletContext = ServletContextPool.get(
				servletContextName);

			return servletContext.getContextPath();
		}

		return StringPool.SLASH.concat(servletContextName);
	}

	@Override
	public String getLayoutTemplateId() {
		return _layoutTemplateId;
	}

	@Override
	public String getName() {
		return getName(LocaleUtil.getDefault());
	}

	@Override
	public String getName(Locale locale) {
		if (Validator.isNotNull(_name)) {
			return _name;
		}

		String layoutTemplateId = StringUtil.replace(
			_layoutTemplateId, CharPool.UNDERLINE, CharPool.DASH);

		return LanguageUtil.get(locale, "layout-template-" + layoutTemplateId);
	}

	@Override
	public String getPluginId() {
		return getLayoutTemplateId();
	}

	@Override
	public String getPluginType() {
		return Plugin.TYPE_LAYOUT_TEMPLATE;
	}

	@Override
	public String getServletContextName() {
		return _servletContextName;
	}

	@Override
	public boolean getStandard() {
		return _standard;
	}

	@Override
	public String getStaticResourcePath() {
		String proxyPath = PortalUtil.getPathProxy();

		String contextPath = getContextPath();

		if (!isWARFile()) {
			return contextPath;
		}

		return proxyPath.concat(contextPath);
	}

	@Override
	public String getTemplatePath() {
		return _templatePath;
	}

	@Override
	public String getThemeId() {
		return _themeId;
	}

	@Override
	public String getThumbnailPath() {
		return _thumbnailPath;
	}

	@Override
	public String getUncachedContent() throws IOException {
		if (_servletContext == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Cannot get latest content for ", _servletContextName,
						" ", getTemplatePath(),
						" because the servlet context is null"));
			}

			return _content;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Getting latest content for ", _servletContextName, " ",
					getTemplatePath()));
		}

		String content = HttpUtil.URLtoString(
			_servletContext.getResource(getTemplatePath()));

		setContent(content);

		return content;
	}

	@Override
	public boolean getWARFile() {
		return _warFile;
	}

	@Override
	public boolean hasSetContent() {
		return _setContent;
	}

	@Override
	public boolean isStandard() {
		return _standard;
	}

	@Override
	public boolean isWARFile() {
		return _warFile;
	}

	@Override
	public void setColumns(List<String> columns) {
		_columns = columns;
	}

	@Override
	public void setContent(String content) {
		_setContent = true;

		_content = content;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;

		if (Validator.isNotNull(_servletContextName)) {
			_warFile = true;
		}
		else {
			_warFile = false;
		}
	}

	@Override
	public void setStandard(boolean standard) {
		_standard = standard;
	}

	@Override
	public void setTemplatePath(String templatePath) {
		_templatePath = templatePath;
	}

	@Override
	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	@Override
	public void setThumbnailPath(String thumbnailPath) {
		_thumbnailPath = thumbnailPath;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutTemplateImpl.class);

	private List<String> _columns = new ArrayList<>();
	private String _content;
	private final String _layoutTemplateId;
	private String _name;
	private transient ServletContext _servletContext;
	private String _servletContextName = StringPool.BLANK;
	private boolean _setContent;
	private boolean _standard;
	private String _templatePath;
	private String _themeId;
	private String _thumbnailPath;
	private boolean _warFile;

}