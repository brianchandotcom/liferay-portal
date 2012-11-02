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

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.AbstractTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplateResourceThreadLocal;
import com.liferay.portal.util.PropsValues;

import freemarker.core.ParseException;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mika Koivisto
 * @author Tina Tian
 */
public class FreeMarkerTemplate extends AbstractTemplate {

	public FreeMarkerTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		Configuration configuration,
		TemplateContextHelper templateContextHelper) {

		super(
			templateResource, errorTemplateResource, templateContextHelper,
			TemplateConstants.LANG_TYPE_FTL,
			PropsValues.FREEMARKER_ENGINE_RESOURCE_MODIFICATION_CHECK_INTERVAL);

		_context = new HashMap<String, Object>();

		if (context != null) {
			for (Map.Entry<String, Object> entry : context.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		}

		_configuration = configuration;
	}

	public Object get(String key) {
		return _context.get(key);
	}

	public void put(String key, Object value) {
		if (value == null) {
			return;
		}

		_context.put(key, value);
	}

	@Override
	protected void handleException(Exception exception, Writer writer)
		throws TemplateException {

		if ((exception instanceof ParseException) ||
			(exception instanceof freemarker.template.TemplateException)) {

			put("exception", exception.getMessage());

			if (templateResource instanceof StringTemplateResource) {
				StringTemplateResource stringTemplateResource =
					(StringTemplateResource)templateResource;

				put("script", stringTemplateResource.getContent());
			}

			if (exception instanceof ParseException) {
				ParseException pe = (ParseException)exception;

				put("column", pe.getColumnNumber());
				put("line", pe.getLineNumber());
			}

			try {
				processTemplate(errorTemplateResource, writer);
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process FreeMarker template " +
						errorTemplateResource.getTemplateId(),
					e);
			}
		}
		else {
			throw new TemplateException(
				"Unable to process FreeMarker template " +
					templateResource.getTemplateId(),
				exception);
		}
	}

	@Override
	protected void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception {

		TemplateResourceThreadLocal.setTemplateResource(
			TemplateConstants.LANG_TYPE_FTL, templateResource);

		try {
			Template template = _configuration.getTemplate(
				getTemplateResourceUUID(templateResource),
				TemplateConstants.DEFAUT_ENCODING);

			template.process(_context, writer);
		}
		finally {
			TemplateResourceThreadLocal.setTemplateResource(
				TemplateConstants.LANG_TYPE_FTL, null);
		}
	}

	private Configuration _configuration;
	private Map<String, Object> _context;

}