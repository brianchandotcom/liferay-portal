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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.template.TemplateContextHelper;

import freemarker.core.ParseException;

import freemarker.template.Configuration;

import java.io.Reader;
import java.io.Writer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mika Koivisto
 * @author Tina Tian
 */
public class FreeMarkerTemplate implements Template {

	public FreeMarkerTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		Configuration configuration,
		TemplateContextHelper templateContextHelper) {

		_templateResource = templateResource;

		if (errorTemplateResource != null) {
			_errorTemplateResource = errorTemplateResource;
			_hasErrorTemplate = true;
		}

		_context = new ConcurrentHashMap<String, Object>();

		if (context != null) {
			for (Map.Entry<String, Object> entry : context.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		}

		_configuration = configuration;
		_templateContextHelper = templateContextHelper;
	}

	public Object get(String key) {
		return _context.get(key);
	}

	public void prepare(HttpServletRequest request) {
		_templateContextHelper.prepare(this, request);
	}

	public boolean processTemplate(Writer writer) throws TemplateException {
		if (!_hasErrorTemplate) {
			try {
				_processTemplate(_templateResource, writer);

				return true;
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process FreeMarker template " +
						_templateResource.getTemplateId(),
					e);
			}
		}

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			_processTemplate(_templateResource, unsyncStringWriter);

			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(writer);

			return true;
		}
		catch (Exception e1) {
			if ((e1 instanceof ParseException) ||
				(e1 instanceof freemarker.template.TemplateException)) {

				put("exception", e1.getMessage());

				if (_templateResource instanceof StringTemplateResource) {
					StringTemplateResource stringTemplateResource =
						(StringTemplateResource)_templateResource;

					put("script", stringTemplateResource.getContent());
				}

				if (e1 instanceof ParseException) {
					ParseException pe = (ParseException)e1;

					put("column", pe.getColumnNumber());
					put("line", pe.getLineNumber());
				}

				try {
					_processTemplate(_errorTemplateResource, writer);
				}
				catch (Exception e2) {
					throw new TemplateException(
						"Unable to process FreeMarker template " +
							_errorTemplateResource.getTemplateId(),
						e2);
				}
			}
			else {
				throw new TemplateException(
					"Unable to process FreeMarker template " +
						_templateResource.getTemplateId(),
					e1);
			}
		}

		return false;
	}

	public void put(String key, Object value) {
		if (value == null) {
			return;
		}

		_context.put(key, value);
	}

	private void _processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception {

		if (templateResource == null) {
			throw new Exception("Unable to find template resource");
		}

		Reader reader = templateResource.getReader();

		if (reader == null) {
			throw new Exception(
				"Unable to find template resource " + templateResource);
		}

		freemarker.template.Template template =
			new freemarker.template.Template(
				templateResource.getTemplateId(), reader, _configuration,
				TemplateResource.DEFAUT_ENCODING);

		template.process(_context, writer);
	}

	private Configuration _configuration;
	private Map<String, Object> _context;
	private TemplateResource _errorTemplateResource;
	private boolean _hasErrorTemplate;
	private TemplateContextHelper _templateContextHelper;
	private TemplateResource _templateResource;

}