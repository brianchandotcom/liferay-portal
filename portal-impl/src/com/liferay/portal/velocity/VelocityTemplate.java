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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.template.TemplateContextHelper;

import java.io.Reader;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;

/**
 * @author Tina Tian
 */
public class VelocityTemplate implements Template {

	public VelocityTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, VelocityContext velocityContext,
		VelocityEngine velocityEngine,
		TemplateContextHelper templateContextHelper) {

		_templateResource = templateResource;

		if (errorTemplateResource != null) {
			_errorTemplateResource = errorTemplateResource;
			_hasErrorTemplate = true;
		}

		if (velocityContext == null) {
			_velocityContext = new VelocityContext();
		}
		else {
			_velocityContext = new VelocityContext(velocityContext);
		}

		_velocityEngine = velocityEngine;
		_templateContextHelper = templateContextHelper;
	}

	public Object get(String key) {
		return _velocityContext.get(key);
	}

	public void prepare(HttpServletRequest request) {
		_templateContextHelper.prepare(this, request);
	}

	public boolean processTemplate(Writer writer) throws TemplateException {
		if (!_hasErrorTemplate) {
			try {
				return _processTemplate(_templateResource, writer);
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process Velocity template " +
						_templateResource.getTemplateId(),
					e);
			}
		}

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			boolean result = _processTemplate(
				_templateResource, unsyncStringWriter);

			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(writer);

			return result;
		}
		catch (Exception e1) {
			put("exception", e1.getMessage());

			if (_templateResource instanceof StringTemplateResource) {
				StringTemplateResource stringTemplateResource =
					(StringTemplateResource)_templateResource;

				put("script", stringTemplateResource.getContent());
			}

			if (e1 instanceof ParseErrorException) {
				ParseErrorException pee = (ParseErrorException)e1;

				put("column", pee.getColumnNumber());
				put("line", pee.getLineNumber());
			}

			try {
				_processTemplate(_errorTemplateResource, writer);

				return false;
			}
			catch (Exception e2) {
				throw new TemplateException(
					"Unable to process Velocity template " +
						_errorTemplateResource.getTemplateId(),
					e2);
			}
		}
	}

	public void put(String key, Object value) {
		if (value == null) {
			return;
		}

		_velocityContext.put(key, value);
	}

	private boolean _processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception {

		Reader reader = null;

		try {
			if (templateResource == null) {
				throw new Exception("Unable to find template resource");
			}

			reader = templateResource.getReader();

			if (reader == null) {
				throw new Exception(
					"Unable to find template resource " + templateResource);
			}

			return _velocityEngine.evaluate(
				_velocityContext, writer, templateResource.getTemplateId(),
				reader);
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	private TemplateResource _errorTemplateResource;
	private boolean _hasErrorTemplate;
	private TemplateContextHelper _templateContextHelper;
	private TemplateResource _templateResource;
	private VelocityContext _velocityContext;
	private VelocityEngine _velocityEngine;

}