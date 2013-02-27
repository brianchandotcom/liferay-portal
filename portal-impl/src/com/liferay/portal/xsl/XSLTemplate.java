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

package com.liferay.portal.xsl;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.template.TemplateContextHelper;

import java.io.Writer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Tina Tian
 */
public class XSLTemplate implements Template {

	public XSLTemplate(
		XSLTemplateResource xslTemplateResource,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		TemplateContextHelper templateContextHelper) {

		if (xslTemplateResource == null) {
			throw new IllegalArgumentException("XSL template resource is null");
		}

		if (templateContextHelper == null) {
			throw new IllegalArgumentException(
				"Template context helper is null");
		}

		_xslTemplateResource = xslTemplateResource;
		_errorTemplateResource = errorTemplateResource;
		_templateContextHelper = templateContextHelper;

		_context = new HashMap<String, Object>();

		if (context != null) {
			for (Map.Entry<String, Object> entry : context.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		}
	}

	public Object get(String key) {
		return _context.get(key);
	}

	public String[] getKeys() {
		Set<String> keySet = _context.keySet();

		return keySet.toArray(new String[keySet.size()]);
	}

	public void prepare(HttpServletRequest request) {
		_templateContextHelper.prepare(this, request);
	}

	public boolean processTemplate(Writer writer) throws TemplateException {
		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		String languageId = null;

		XSLURIResolver xslURIResolver =
			_xslTemplateResource.getXSLURIResolver();

		if (xslURIResolver != null) {
			languageId = xslURIResolver.getLanguageId();
		}

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		XSLErrorListener xslErrorListener = new XSLErrorListener(locale);

		transformerFactory.setErrorListener(xslErrorListener);

		transformerFactory.setURIResolver(xslURIResolver);

		StreamSource xmlSource = new StreamSource(
			_xslTemplateResource.getXMLReader());

		Transformer transformer = _getTransformer(
			transformerFactory, _xslTemplateResource);

		if (_errorTemplateResource == null) {
			try {
				transformer.transform(xmlSource, new StreamResult(writer));

				return true;
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process XSL template " +
						_xslTemplateResource.getTemplateId(),
					e);
			}
		}

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			transformer.setParameter(
				TemplateConstants.WRITER, unsyncStringWriter);

			transformer.transform(
				xmlSource, new StreamResult(unsyncStringWriter));

			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(writer);

			return true;
		}
		catch (Exception e1) {
			Transformer errorTransformer = _getTransformer(
				transformerFactory, _errorTemplateResource);

			errorTransformer.setParameter(TemplateConstants.WRITER, writer);
			errorTransformer.setParameter(
				"exception", xslErrorListener.getMessageAndLocation());

			if (_errorTemplateResource instanceof StringTemplateResource) {
				StringTemplateResource stringTemplateResource =
					(StringTemplateResource)_errorTemplateResource;

				errorTransformer.setParameter(
					"script", stringTemplateResource.getContent());
			}

			if (xslErrorListener.getLocation() != null) {
				errorTransformer.setParameter(
					"column", new Integer(xslErrorListener.getColumnNumber()));
				errorTransformer.setParameter(
					"line", new Integer(xslErrorListener.getLineNumber()));
			}

			try {
				errorTransformer.transform(xmlSource, new StreamResult(writer));
			}
			catch (Exception e2) {
				throw new TemplateException(
					"Unable to process XSL template " +
						_errorTemplateResource.getTemplateId(),
					e2);
			}

			return false;
		}
	}

	public void put(String key, Object value) {
		if (value == null) {
			return;
		}

		_context.put(key, value);
	}

	private Transformer _getTransformer(
			TransformerFactory transformerFactory,
			TemplateResource templateResource)
		throws TemplateException {

		try {
			StreamSource scriptSource = new StreamSource(
				templateResource.getReader());

			Transformer transformer = transformerFactory.newTransformer(
				scriptSource);

			for (Map.Entry<String, Object> entry : _context.entrySet()) {
				transformer.setParameter(entry.getKey(), entry.getValue());
			}

			return transformer;
		}
		catch (Exception e) {
			throw new TemplateException(
				"Unable to get Transformer for template " +
					templateResource.getTemplateId(),
				e);
		}
	}

	private Map<String, Object> _context;
	private TemplateResource _errorTemplateResource;
	private TemplateContextHelper _templateContextHelper;
	private XSLTemplateResource _xslTemplateResource;

}