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

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;

import java.security.AccessControlContext;
import java.security.PrivilegedAction;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public abstract class BaseTemplateManager implements TemplateManager {

	@Override
	public void addContextObjects(
		Map<String, Object> contextObjects,
		Map<String, Object> newContextObjects) {

		for (Entry<String, Object> entry : newContextObjects.entrySet()) {
			String variableName = entry.getKey();

			if (contextObjects.containsKey(variableName)) {
				continue;
			}

			Object object = entry.getValue();

			if (object instanceof Class) {
				addStaticClassSupport(
					contextObjects, variableName, (Class<?>)object);
			}
			else {
				contextObjects.put(variableName, object);
			}
		}
	}

	@Override
	public void addStaticClassSupport(
		Map<String, Object> contextObjects, String variableName,
		Class<?> variableClass) {
	}

	@Override
	public void addTaglibApplication(
		Map<String, Object> contextObjects, String applicationName,
		ServletContext servletContext) {
	}

	@Override
	public void addTaglibFactory(
		Map<String, Object> contextObjects, String taglibLiferayHash,
		ServletContext servletContext) {
	}

	@Override
	public void addTaglibRequest(
		Map<String, Object> contextObjects, String applicationName,
		HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public void addTaglibSupport(
		Map<String, Object> contextObjects, HttpServletRequest request,
		HttpServletResponse response) {
	}

	@Override
	public void addTaglibTheme(
		Map<String, Object> contextObjects, String themeName,
		HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public String[] getRestrictedVariables() {
		return new String[0];
	}

	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		this.templateContextHelper = templateContextHelper;
	}

	public void setTemplateResourceLoader(
		TemplateResourceLoader templateResourceLoader) {

		this.templateResourceLoader = templateResourceLoader;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected AccessControlContext getAccessControlContext() {
		TemplateControlContext templateControlContext =
			templateContextHelper.getTemplateControlContext();

		return templateControlContext.getAccessControlContext();
	}

	protected Map<String, Object> getHelperUtilities(boolean restricted) {
		return templateContextHelper.getHelperUtilities(
			getTemplateControlContextClassLoader(), restricted);
	}

	protected ClassLoader getTemplateControlContextClassLoader() {
		TemplateControlContext templateControlContext =
			templateContextHelper.getTemplateControlContext();

		return templateControlContext.getClassLoader();
	}

	protected TemplateContextHelper templateContextHelper;
	protected TemplateResourceLoader templateResourceLoader;

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected abstract class DoGetAbstractTemplatePrivilegedAction
		implements PrivilegedAction<Template> {

		public DoGetAbstractTemplatePrivilegedAction(
			TemplateResource errorTemplateResource, boolean restricted,
			Map<String, Object> helperUtilities) {

			this.errorTemplateResource = errorTemplateResource;
			this.restricted = restricted;
			this.helperUtilities = helperUtilities;
		}

		protected final TemplateResource errorTemplateResource;
		protected final Map<String, Object> helperUtilities;
		protected boolean restricted;

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected class DoGetHelperUtilitiesPrivilegedAction
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
		private final boolean _restricted;
		private final TemplateContextHelper _templateContextHelper;

	}

}