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

package com.liferay.portal.template.xsl.internal;

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.template.BaseTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.xsl.configuration.XSLEngineConfiguration;
import com.liferay.portal.xsl.XSLTemplateResource;
import com.liferay.portal.xsl.XSLURIResolver;

import java.io.Writer;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xalan.processor.TransformerFactoryImpl;

/**
 * @author Tina Tian
 * @author Peter Fellwock
 */
public class XSLTemplate extends BaseTemplate {

	public XSLTemplate(
		XSLTemplateResource xslTemplateResource,
		TemplateContextHelper templateContextHelper,
		XSLEngineConfiguration xslEngineConfiguration, boolean restricted) {

		super(
			xslTemplateResource, Collections.emptyMap(), templateContextHelper,
			restricted);

		_preventLocalConnections =
			xslEngineConfiguration.preventLocalConnections();

		_transformerFactory = TransformerFactory.newInstance(
			_TRANSFORMER_FACTORY_CLASS_NAME, _TRANSFORMER_FACTORY_CLASS_LOADER);

		try {
			_transformerFactory.setFeature(
				XMLConstants.FEATURE_SECURE_PROCESSING,
				xslEngineConfiguration.secureProcessingEnabled());
		}
		catch (TransformerConfigurationException tce) {
		}
	}

	@Override
	protected void handleException(
			TemplateResource templateResource,
			TemplateResource errorTemplateResource, Exception exception,
			Writer writer)
		throws TemplateException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			AggregateClassLoader.getAggregateClassLoader(
				contextClassLoader, _TRANSFORMER_FACTORY_CLASS_LOADER));

		try {
			Transformer errorTransformer = _getTransformer(
				errorTemplateResource);

			errorTransformer.setParameter(TemplateConstants.WRITER, writer);

			XSLErrorListener xslErrorListener =
				(XSLErrorListener)_transformerFactory.getErrorListener();

			errorTransformer.setParameter(
				"exception", xslErrorListener.getMessageAndLocation());

			if (errorTemplateResource instanceof StringTemplateResource) {
				StringTemplateResource stringTemplateResource =
					(StringTemplateResource)errorTemplateResource;

				errorTransformer.setParameter(
					"script", stringTemplateResource.getContent());
			}

			if (xslErrorListener.getLocation() != null) {
				errorTransformer.setParameter(
					"column",
					Integer.valueOf(xslErrorListener.getColumnNumber()));
				errorTransformer.setParameter(
					"line", Integer.valueOf(xslErrorListener.getLineNumber()));
			}

			try {
				errorTransformer.transform(
					_xmlStreamSource, new StreamResult(writer));
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process XSL template " +
						errorTemplateResource.getTemplateId(),
					e);
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	protected void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			AggregateClassLoader.getAggregateClassLoader(
				contextClassLoader, _TRANSFORMER_FACTORY_CLASS_LOADER));

		XSLTemplateResource xslTemplateResource =
			(XSLTemplateResource)templateResource;

		try {
			String languageId = null;

			XSLURIResolver xslURIResolver =
				xslTemplateResource.getXSLURIResolver();

			if (xslURIResolver != null) {
				languageId = xslURIResolver.getLanguageId();
			}

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			XSLErrorListener xslErrorListener = new XSLErrorListener(locale);

			_transformerFactory.setErrorListener(xslErrorListener);

			if (_preventLocalConnections) {
				xslURIResolver = new XSLSecureURIResolver(xslURIResolver);
			}

			_transformerFactory.setURIResolver(xslURIResolver);

			_xmlStreamSource = new StreamSource(
				xslTemplateResource.getXMLReader());

			Transformer transformer = _getTransformer(xslTemplateResource);

			transformer.transform(_xmlStreamSource, new StreamResult(writer));
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private Transformer _getTransformer(TemplateResource templateResource)
		throws TemplateException {

		try {
			StreamSource scriptSource = new StreamSource(
				templateResource.getReader());

			Transformer transformer = _transformerFactory.newTransformer(
				scriptSource);

			for (Map.Entry<String, Object> entry : entrySet()) {
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

	private static final ClassLoader _TRANSFORMER_FACTORY_CLASS_LOADER;

	private static final String _TRANSFORMER_FACTORY_CLASS_NAME;

	static {
		Class<?> transformerFactoryClass = TransformerFactoryImpl.class;

		_TRANSFORMER_FACTORY_CLASS_NAME = transformerFactoryClass.getName();

		_TRANSFORMER_FACTORY_CLASS_LOADER =
			transformerFactoryClass.getClassLoader();
	}

	private final boolean _preventLocalConnections;
	private final TransformerFactory _transformerFactory;
	private StreamSource _xmlStreamSource;

}