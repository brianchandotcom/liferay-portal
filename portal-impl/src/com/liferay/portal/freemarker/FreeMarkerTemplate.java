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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.util.PropsValues;

import freemarker.core.ParseException;

import freemarker.template.Configuration;

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
		String templateId, String templateContent, String errorTemplateId,
		String errorTemplateContent, Map<String, Object> context,
		Configuration configuration,
		TemplateContextHelper templateContextHelper,
		StringTemplateLoader stringTemplateLoader) {

		_context = new ConcurrentHashMap<String, Object>();

		if (context != null) {
			for (Map.Entry<String, Object> entry : context.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		}

		_templateId = templateId;
		_templateContent = templateContent;

		if (errorTemplateId != null) {
			_errorTemplateId = errorTemplateId;
			_errorTemplateContent = errorTemplateContent;
			_hasErrorTemplate = true;
		}

		_configuration = configuration;
		_templateContextHelper = templateContextHelper;
		_stringTemplateLoader = stringTemplateLoader;
	}

	public Object get(String key) {
		return _context.get(key);
	}

	public void prepare(HttpServletRequest request) throws TemplateException {
		_templateContextHelper.prepare(this, request);
	}

	public boolean processTemplate(Writer writer) throws TemplateException {
		handleTemplateContent(_templateId, _templateContent);

		freemarker.template.Template template = null;

		if (!_hasErrorTemplate) {
			try {
				template = _configuration.getTemplate(
					_templateId, StringPool.UTF8);

				template.process(_context, writer);

				return true;
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process freemarker template " + _templateId, e);
			}
		}

		try {
			template = _configuration.getTemplate(_templateId, StringPool.UTF8);

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			template.process(_context, unsyncStringWriter);

			unsyncStringWriter.getStringBundler().writeTo(writer);

			return true;
		}
		catch (Exception e) {
			if (e instanceof ParseException ||
				e instanceof freemarker.template.TemplateException) {

				put("exception", e.getMessage());
				put("script", _templateContent);

				if (e instanceof ParseException) {
					ParseException pe = (ParseException)e;

					put("column", pe.getColumnNumber());
					put("line", pe.getLineNumber());
				}

				handleTemplateContent(_errorTemplateId, _errorTemplateContent);

				try {
					template = _configuration.getTemplate(
						_errorTemplateId, StringPool.UTF8);

					template.process(_context, writer);
				}
				catch (Exception ex) {
					throw new TemplateException(
						"Unable to process freemarker template " +
							_errorTemplateId,
						ex);
				}
			}
			else {
				throw new TemplateException(
					"Unable to process freemarker template " + _templateId, e);
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

	protected void handleTemplateContent(
		String templateId, String templateContent) {

		if (Validator.isNotNull(templateContent) &&
			(!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED ||
			 !stringTemplateExists(templateId))) {

			_stringTemplateLoader.putTemplate(templateId, templateContent);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Added " + templateId + " to the string based FreeMarker " +
						"template repository");
			}
		}
	}

	protected boolean stringTemplateExists(String templateId) {
		Object templateSource = _stringTemplateLoader.findTemplateSource(
			templateId);

		if (templateSource == null) {
			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(FreeMarkerTemplate.class);

	private Configuration _configuration;
	private Map<String, Object> _context;
	private String _errorTemplateContent;
	private String _errorTemplateId;
	private boolean _hasErrorTemplate;
	private StringTemplateLoader _stringTemplateLoader;
	private String _templateContent;
	private TemplateContextHelper _templateContextHelper;
	private String _templateId;

}