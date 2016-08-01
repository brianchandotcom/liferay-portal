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

package com.liferay.portal.template.velocity.internal;

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.AbstractSingleResourceTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplateResourceThreadLocal;

import java.io.Writer;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;

/**
 * @author Tina Tian
 */
public class VelocityTemplate extends AbstractSingleResourceTemplate {

	public VelocityTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		VelocityEngine velocityEngine,
		TemplateContextHelper templateContextHelper,
		int resourceModificationCheckInterval, boolean privileged) {

		super(
			templateResource, errorTemplateResource, context,
			templateContextHelper, TemplateConstants.LANG_TYPE_VM,
			resourceModificationCheckInterval);

		_velocityContext = new VelocityContext(super.context);
		_velocityEngine = velocityEngine;
		_privileged = privileged;
	}

	@Override
	protected void handleException(Exception exception, Writer writer)
		throws TemplateException {

		put("exception", exception.getMessage());

		if (templateResource instanceof StringTemplateResource) {
			StringTemplateResource stringTemplateResource =
				(StringTemplateResource)templateResource;

			put("script", stringTemplateResource.getContent());
		}

		if (exception instanceof ParseErrorException) {
			ParseErrorException pee = (ParseErrorException)exception;

			put("column", pee.getColumnNumber());
			put("line", pee.getLineNumber());
		}

		try {
			processTemplate(errorTemplateResource, writer);
		}
		catch (Exception e) {
			throw new TemplateException(
				"Unable to process Velocity template " +
					errorTemplateResource.getTemplateId(),
				e);
		}
	}

	@Override
	protected void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception {

		TemplateResourceThreadLocal.setTemplateResource(
			TemplateConstants.LANG_TYPE_VM, templateResource);

		try {
			Template template = null;

			if (_privileged) {
				template = AccessController.doPrivileged(
					new TemplatePrivilegedExceptionAction(templateResource));
			}
			else {
				template = _velocityEngine.getTemplate(
					getTemplateResourceUUID(templateResource),
					TemplateConstants.DEFAUT_ENCODING);
			}

			template.merge(_velocityContext, writer);
		}
		catch (PrivilegedActionException pae) {
			throw pae.getException();
		}
		finally {
			TemplateResourceThreadLocal.setTemplateResource(
				TemplateConstants.LANG_TYPE_VM, null);
		}
	}

	private final boolean _privileged;
	private final VelocityContext _velocityContext;
	private final VelocityEngine _velocityEngine;

	private class TemplatePrivilegedExceptionAction
		implements PrivilegedExceptionAction<Template> {

		public TemplatePrivilegedExceptionAction(
			TemplateResource templateResource) {

			_templateResource = templateResource;
		}

		@Override
		public Template run() throws Exception {
			return _velocityEngine.getTemplate(
				getTemplateResourceUUID(_templateResource),
				TemplateConstants.DEFAUT_ENCODING);
		}

		private final TemplateResource _templateResource;

	}

}