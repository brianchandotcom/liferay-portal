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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.RestrictedTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.util.PropsValues;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

import freemarker.template.Configuration;

import java.io.IOException;

import java.util.Map;

/**
 * @author Mika Koivisto
 * @author Tina Tina
 */
public class FreeMarkerManager implements TemplateManager {

	public void clearCache() {
		_stringTemplateLoader.removeTemplates();

		PortalCache portalCache = LiferayCacheStorage.getPortalCache();

		portalCache.removeAll();
	}

	public void clearCache(String templateId) {
		_stringTemplateLoader.removeTemplate(templateId);

		PortalCache portalCache = LiferayCacheStorage.getPortalCache();

		portalCache.remove(templateId);
	}

	public void destroy() throws TemplateException {
		if (_configuration == null) {
			return;
		}

		_configuration.clearEncodingMap();
		_configuration.clearSharedVariables();
		_configuration.clearTemplateCache();
		_configuration = null;

		_restrictedHelperUtilities.clear();
		_restrictedHelperUtilities = null;

		_standardHelperUtilities.clear();
		_standardHelperUtilities = null;

		_stringTemplateLoader.removeTemplates();
		_stringTemplateLoader = null;

		_templateContextHelper = null;
	}

	public String getManagerName() {
		return TemplateManager.FREE_MARKER;
	}

	public Template getTemplate(
		String templateId, String templateContent, String errorTemplateId,
		String errorTemplateContent, TemplateContextType templateContextType) {

		switch(templateContextType) {
			case EMPTY:
				return new FreeMarkerTemplate(
						templateId, templateContent, errorTemplateId,
						errorTemplateContent, null, _configuration,
						_templateContextHelper, _stringTemplateLoader);
			case RESTRICTED:
				return new RestrictedTemplate(
					new FreeMarkerTemplate(
						templateId, templateContent, errorTemplateId,
						errorTemplateContent, _restrictedHelperUtilities,
						_configuration, _templateContextHelper,
						_stringTemplateLoader),
					_templateContextHelper.getRestrictedVariables());
			case STANDARD:
				return new FreeMarkerTemplate(
						templateId, templateContent, errorTemplateId,
						errorTemplateContent, _standardHelperUtilities,
						_configuration, _templateContextHelper,
						_stringTemplateLoader);
		}

		return null;
	}

	public Template getTemplate(
		String templateId, String templateContent, String errorTemplateId,
		TemplateContextType templateContextType) {

		return getTemplate(
			templateId, templateContent, errorTemplateId, null,
			templateContextType);
	}

	public Template getTemplate(
		String templateId, String templateContent,
		TemplateContextType templateContextType) {

		return getTemplate(
			templateId, templateContent, null, null, templateContextType);
	}

	public Template getTemplate(
		String templateId, TemplateContextType templateContextType) {

		return getTemplate(templateId, null, null, null, templateContextType);
	}

	public boolean hasTemplate(String templateId) {
		try {
			freemarker.template.Template template = _configuration.getTemplate(
				templateId);

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

	public void init() throws TemplateException {
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
		_configuration.setTemplateLoader(multiTemplateLoader);
		_configuration.setTemplateUpdateDelay(
			PropsValues.FREEMARKER_ENGINE_MODIFICATION_CHECK_INTERVAL);

		try {
			_configuration.setSetting(
				"auto_import", PropsValues.FREEMARKER_ENGINE_MACRO_LIBRARY);
			_configuration.setSetting(
				"cache_storage", PropsValues.FREEMARKER_ENGINE_CACHE_STORAGE);
			_configuration.setSetting(
				"template_exception_handler",
				PropsValues.FREEMARKER_ENGINE_TEMPLATE_EXCEPTION_HANDLER);
		}
		catch (Exception e) {
			throw new TemplateException("Unable to init freemarker manager", e);
		}

		_standardHelperUtilities = _templateContextHelper.getHelperUtilities();
		_restrictedHelperUtilities =
			_templateContextHelper.getRestrictedHelperUtilities();
	}

	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		_templateContextHelper = templateContextHelper;
	}

	private static Log _log = LogFactoryUtil.getLog(FreeMarkerManager.class);

	private Configuration _configuration;
	private Map<String, Object> _restrictedHelperUtilities;
	private Map<String, Object> _standardHelperUtilities;
	private StringTemplateLoader _stringTemplateLoader;
	private TemplateContextHelper _templateContextHelper;

}