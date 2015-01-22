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

package com.liferay.portal.template;

import com.liferay.portal.kernel.security.pacl.NotPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;

import java.io.Writer;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public abstract class BaseTemplateManager implements TemplateManager {

	@Override
	public void addStaticClassSupport(
		Map<String, Object> contextObjects, String variableName,
		Class<?> variableClass) {
	}

	@Override
	public void addStaticClassSupport(
		Template template, String variableName, Class<?> variableClass) {
	}

	@Override
	public void addTaglibApplication(
		Map<String, Object> contextObjects, String applicationName,
		ServletContext servletContext) {
	}

	@Override
	public void addTaglibApplication(
		Template template, String applicationName,
		ServletContext servletContext) {
	}

	@Override
	public void addTaglibFactory(
		Map<String, Object> contextObjects, String taglibLiferayHash,
		ServletContext servletContext) {
	}

	@Override
	public void addTaglibFactory(
		Template template, String taglibLiferayHash,
		ServletContext servletContext) {
	}

	@Override
	public void addTaglibRequest(
		Map<String, Object> contextObjects, String applicationName,
		HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public void addTaglibRequest(
		Template template, String applicationName, HttpServletRequest request,
		HttpServletResponse response) {
	}

	@Override
	public void addTaglibTheme(
		Template template, String string, HttpServletRequest request,
		HttpServletResponse response, Writer writer) {
	}

	@Override
	public String[] getRestrictedVariables() {
		return new String[0];
	}

	@NotPrivileged
	@Override
	public Template getTemplate(
		TemplateResource templateResource, boolean restricted) {

		return getTemplate(templateResource, null, restricted);
	}

	@NotPrivileged
	@Override
	public Template getTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted) {

		TemplateControlContext templateControlContext =
			templateContextHelper.getTemplateControlContext();

		AccessControlContext accessControlContext =
			templateControlContext.getAccessControlContext();

		ClassLoader classLoader = templateControlContext.getClassLoader();

		if (accessControlContext == null) {
			Map<String, Object> helperUtilities =
				templateContextHelper.getHelperUtilities(
					classLoader, restricted);

			return doGetTemplate(
				templateResource, errorTemplateResource, restricted,
				helperUtilities, false);
		}

		Map<String, Object> helperUtilities = AccessController.doPrivileged(
			new DoGetHelperUtilitiesPrivilegedAction(
				templateContextHelper, classLoader, restricted),
			accessControlContext);

		Template template = AccessController.doPrivileged(
			new DoGetTemplatePrivilegedAction(
				templateResource, errorTemplateResource, restricted,
				helperUtilities));

		return new PrivilegedTemplateWrapper(accessControlContext, template);
	}

	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		this.templateContextHelper = templateContextHelper;
	}

	public void setTemplateResourceLoader(
		TemplateResourceLoader templateResourceLoader) {

		this.templateResourceLoader = templateResourceLoader;
	}

	protected abstract Template doGetTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities, boolean privileged);

	protected TemplateContextHelper templateContextHelper;
	protected TemplateResourceLoader templateResourceLoader;

	private class DoGetHelperUtilitiesPrivilegedAction
		implements PrivilegedAction<Map<String, Object>> {

		public DoGetHelperUtilitiesPrivilegedAction(
			TemplateContextHelper templateContextHelper,
			ClassLoader classLoader, boolean restricted) {

			_templateContextHelper = templateContextHelper;
			_classLoader = classLoader;
			_restricted = restricted;
		}

		@Override
		public Map<String, Object> run() {
			return _templateContextHelper.getHelperUtilities(
				_classLoader, _restricted);
		}

		private final ClassLoader _classLoader;
		private boolean _restricted;
		private final TemplateContextHelper _templateContextHelper;

	}

	private class DoGetTemplatePrivilegedAction
		implements PrivilegedAction<Template> {

		public DoGetTemplatePrivilegedAction(
			TemplateResource templateResource,
			TemplateResource errorTemplateResource, boolean restricted,
			Map<String, Object> helperUtilities) {

			_templateResource = templateResource;
			_errorTemplateResource = errorTemplateResource;
			_restricted = restricted;
			_helperUtilities = helperUtilities;
		}

		@Override
		public Template run() {
			return doGetTemplate(
				_templateResource, _errorTemplateResource, _restricted,
				_helperUtilities, true);
		}

		private final TemplateResource _errorTemplateResource;
		private final Map<String, Object> _helperUtilities;
		private boolean _restricted;
		private final TemplateResource _templateResource;

	}

}