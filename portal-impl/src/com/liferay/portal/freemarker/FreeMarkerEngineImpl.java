/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngine;
import com.liferay.portal.kernel.freemarker.FreeMarkerVariablesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Mika Koivisto
 */
public class FreeMarkerEngineImpl implements FreeMarkerEngine {

	public void flushTemplate(String freeMarkerTemplateId) {
		if (_stringTemplateLoader != null) {
			_stringTemplateLoader.removeTemplate(freeMarkerTemplateId);
		}

		PortalCache portalCache = LiferayCacheStorage.getPortalCache();

		portalCache.remove(freeMarkerTemplateId);
	}

	public FreeMarkerContext getWrappedRestrictedToolsContext() {
		return new FreeMarkerContextImpl(
			_restrictedToolsContext.getWrappedContext());
	}

	public FreeMarkerContext getWrappedStandardToolsContext() {
		return new FreeMarkerContextImpl(
			_standardToolsContext.getWrappedContext());
	}

	public void init() throws Exception {
		if (_configuration != null) {
			return;
		}

		LiferayTemplateLoader liferayTemplateLoader =
			new LiferayTemplateLoader();

		liferayTemplateLoader.setTemplateLoaders(
			PropsValues.FREEMARKER_ENGINE_TEMPLATE_LOADERS);

		_stringTemplateLoader = new StringTemplateLoader();

		MultiTemplateLoader multiTemplateLoader =
			new MultiTemplateLoader(
				new TemplateLoader[] {
					new ClassTemplateLoader(getClass(), StringPool.SLASH),
					_stringTemplateLoader, liferayTemplateLoader
				});

		_configuration = new Configuration();

		_configuration.setDefaultEncoding(StringPool.UTF8);
		_configuration.setLocalizedLookup(
			PropsValues.FREEMARKER_ENGINE_LOCALIZED_LOOKUP);
		_configuration.setObjectWrapper(new LiferayObjectWrapper());
		_configuration.setSetting(
			"auto_import", PropsValues.FREEMARKER_ENGINE_MACRO_LIBRARY);
		_configuration.setSetting(
			"cache_storage", PropsValues.FREEMARKER_ENGINE_CACHE_STORAGE);
		_configuration.setSetting(
			"template_exception_handler",
			PropsValues.FREEMARKER_ENGINE_TEMPLATE_EXCEPTION_HANDLER);
		_configuration.setTemplateLoader(multiTemplateLoader);
		_configuration.setTemplateUpdateDelay(
			PropsValues.FREEMARKER_ENGINE_MODIFICATION_CHECK_INTERVAL);

		_restrictedToolsContext = new FreeMarkerContextImpl();

		FreeMarkerVariablesUtil.insertHelperUtilities(
			_restrictedToolsContext,
			PropsValues.JOURNAL_TEMPLATE_FREEMARKER_RESTRICTED_VARIABLES);

		_standardToolsContext = new FreeMarkerContextImpl();

		FreeMarkerVariablesUtil.insertHelperUtilities(
			_standardToolsContext, null);
	}

	public boolean mergeTemplate(
			String freeMarkerTemplateId, FreeMarkerContext freeMarkerContext,
			Writer writer)
		throws Exception {

		return mergeTemplate(
			freeMarkerTemplateId, null, freeMarkerContext, writer);
	}

	public boolean mergeTemplate(
			String freeMarkerTemplateId, String freemarkerTemplateContent,
			FreeMarkerContext freeMarkerContext, Writer writer)
		throws Exception {

		if (Validator.isNotNull(freemarkerTemplateContent) &&
			(!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED ||
			 !stringTemplateExists(freeMarkerTemplateId))) {

			_stringTemplateLoader.putTemplate(
				freeMarkerTemplateId, freemarkerTemplateContent);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Added " + freeMarkerTemplateId +
						" to the string based FreeMarker template repository");
			}
		}

		FreeMarkerContextImpl freeMarkerContextImpl =
			(FreeMarkerContextImpl)freeMarkerContext;

		Template template = _configuration.getTemplate(
			freeMarkerTemplateId, StringPool.UTF8);

		template.process(freeMarkerContextImpl.getWrappedContext(), writer);

		return true;
	}

	public boolean resourceExists(String resource) {
		try {
			Template template = _configuration.getTemplate(resource);

			if (template != null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}

			return false;
		}
	}

	protected boolean stringTemplateExists(String freeMarkerTemplateId) {
		Object templateSource = _stringTemplateLoader.findTemplateSource(
			freeMarkerTemplateId);

		if (templateSource == null) {
			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(FreeMarkerEngineImpl.class);

	private Configuration _configuration;
	private FreeMarkerContextImpl _restrictedToolsContext;
	private FreeMarkerContextImpl _standardToolsContext;
	private StringTemplateLoader _stringTemplateLoader;

}