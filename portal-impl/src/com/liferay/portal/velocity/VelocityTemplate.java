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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.AbstractTemplate;
import com.liferay.portal.template.TemplateContextHelper;

import java.io.Reader;
import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;

/**
 * @author Tina Tian
 */
public class VelocityTemplate extends AbstractTemplate {

	public VelocityTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, VelocityContext velocityContext,
		VelocityEngine velocityEngine,
		TemplateContextHelper templateContextHelper) {

		super(templateResource, errorTemplateResource, templateContextHelper);

		if (velocityContext == null) {
			_velocityContext = new VelocityContext();
		}
		else {
			_velocityContext = new VelocityContext(velocityContext);
		}

		_velocityEngine = velocityEngine;
	}

	public Object get(String key) {
		return _velocityContext.get(key);
	}

	public void put(String key, Object value) {
		if (value == null) {
			return;
		}

		_velocityContext.put(key, value);
	}

	protected void handleException(
			TemplateResource templateResource,
			TemplateResource errorTemplateResource, Exception exception,
			Writer writer)
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

	protected void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception {

		Reader reader = null;

		try {
			reader = templateResource.getReader();

			_velocityEngine.evaluate(
				_velocityContext, writer, templateResource.getTemplateId(),
				reader);
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	private VelocityContext _velocityContext;
	private VelocityEngine _velocityEngine;

}