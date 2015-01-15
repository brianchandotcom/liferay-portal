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

package com.liferay.portal.template.freemarker;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.BaseTemplateManager;
import com.liferay.portal.template.RestrictedTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.freemarker.configuration.FreemarkerEngineConfiguration;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.taglib.util.VelocityTaglib;
import com.liferay.taglib.util.VelocityTaglibImpl;

import freemarker.cache.TemplateCache;
import freemarker.debug.impl.DebuggerService;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.Writer;

import java.lang.reflect.Field;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Tina Tina
 */
@Component(
	configurationPid = "com.liferay.portal.template.freemarker",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = TemplateManager.class
)
public class FreeMarkerManager extends BaseTemplateManager {

	@Override
	public void addStaticClassSupport(
		Map<String, Object> contextObjects, String variableName,
		Class<?> variableClass) {

		try {
			BeansWrapper beansWrapper = BeansWrapper.getDefaultInstance();

			TemplateHashModel templateHashModel =
				beansWrapper.getStaticModels();

			TemplateModel templateModel = templateHashModel.get(
				variableClass.getCanonicalName());

			contextObjects.put(variableName, templateModel);
		}
		catch (TemplateModelException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Variable " + variableName + " registration fail", e);
			}
		}
	}

	@Override
	public void addStaticClassSupport(
		Template template, String variableName, Class<?> variableClass) {

		try {
			BeansWrapper beansWrapper = BeansWrapper.getDefaultInstance();

			TemplateHashModel templateHashModel =
				beansWrapper.getStaticModels();

			TemplateModel templateModel = templateHashModel.get(
				variableClass.getCanonicalName());

			template.put(variableName, templateModel);
		}
		catch (TemplateModelException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Variable " + variableName + " registration fail", e);
			}
		}
	}

	@Override
	public void addTaglibApplication(
		Map<String, Object> contextObjects, String applicationName,
		ServletContext servletContext) {

		contextObjects.put(
			applicationName, getServletContextHashModel(servletContext));
	}

	@Override
	public void addTaglibApplication(
		Template template, String applicationName,
		ServletContext servletContext) {

		template.put(
			applicationName, getServletContextHashModel(servletContext));
	}

	@Override
	public void addTaglibFactory(
		Template template, String taglibFactoryName,
		ServletContext servletContext) {

		template.put(
			taglibFactoryName, new TaglibFactoryWrapper(servletContext));
	}

	@Override
	public void addTaglibFactory(
		Map<String, Object> contextObjects, String taglibFactoryName,
		ServletContext servletContext) {

		contextObjects.put(
			taglibFactoryName, new TaglibFactoryWrapper(servletContext));
	}

	@Override
	public void addTaglibRequest(
		Map<String, Object> contextObjects, String applicationName,
		HttpServletRequest request, HttpServletResponse response) {

		contextObjects.put(
			applicationName, new HttpRequestHashModel(
				request, response, ObjectWrapper.DEFAULT_WRAPPER));
	}

	@Override
	public void addTaglibRequest(
		Template template, String applicationName, HttpServletRequest request,
		HttpServletResponse response) {

		template.put(
			applicationName, new HttpRequestHashModel(
				request, response, ObjectWrapper.DEFAULT_WRAPPER));
	}

	@Override
	public void addTaglibTheme(
		Template template, String themeName, HttpServletRequest request,
		HttpServletResponse response, Writer writer) {

		VelocityTaglib velocityTaglib = new VelocityTaglibImpl(
			request.getServletContext(), request,
			new PipingServletResponse(response, writer), template);

		template.put(themeName, velocityTaglib);

		// Legacy support

		template.put("theme", velocityTaglib);
	}

	@Override
	public void destroy() {
		if (_configuration == null) {
			return;
		}

		_configuration.clearEncodingMap();
		_configuration.clearSharedVariables();
		_configuration.clearTemplateCache();

		_configuration = null;

		templateContextHelper.removeAllHelperUtilities();

		templateContextHelper = null;

		if (isEnableDebuggerService()) {
			//DebuggerService.shutdown();
		}
	}

	@Override
	public void destroy(ClassLoader classLoader) {
		templateContextHelper.removeHelperUtilities(classLoader);
	}

	@Override
	public String getName() {
		return TemplateConstants.LANG_TYPE_FTL;
	}

	@Override
	public String[] getRestrictedVariables() {
		return _freemarkerEngineConfiguration.getRestrictedVariables();
	}

	@Override
	public void init() throws TemplateException {
		if (_configuration != null) {
			return;
		}

		_configuration = new Configuration();

		try {
			Field field = ReflectionUtil.getDeclaredField(
				Configuration.class, "cache");

			TemplateCache templateCache = new LiferayTemplateCache(
				_configuration, _freemarkerEngineConfiguration);

			field.set(_configuration, templateCache);
		}
		catch (Exception e) {
			throw new TemplateException(
				"Unable to Initialize Freemarker manager");
		}

		_configuration.setDefaultEncoding(StringPool.UTF8);
		_configuration.setLocalizedLookup(
			_freemarkerEngineConfiguration.getLocalizedLookup());
		_configuration.setNewBuiltinClassResolver(
			new LiferayTemplateClassResolver());
		_configuration.setObjectWrapper(new LiferayObjectWrapper());

		try {
			_configuration.setSetting(
				"auto_import",
				_freemarkerEngineConfiguration.getMacroLibrary());
			_configuration.setSetting(
				"template_exception_handler",
				_freemarkerEngineConfiguration.getTemplateExceptionHandler());
		}
		catch (Exception e) {
			throw new TemplateException("Unable to init freemarker manager", e);
		}

		if (isEnableDebuggerService()) {
			DebuggerService.getBreakpoints("*");
		}
	}

	@Reference(unbind = "-")
	public void setFreeMarkerTemplateContextHelper(
		TemplateContextHelper freeMarkerTemplateContextHelper) {

		templateContextHelper = freeMarkerTemplateContextHelper;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_freemarkerEngineConfiguration = Configurable.createConfigurable(
			FreemarkerEngineConfiguration.class, properties);
	}

	@Override
	protected Template doGetTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities, boolean privileged) {

		Template template = new FreeMarkerTemplate(
			templateResource, errorTemplateResource, helperUtilities,
			_configuration, templateContextHelper, privileged,
			_freemarkerEngineConfiguration.getResourceModificationCheck());

		if (restricted) {
			template = new RestrictedTemplate(
				template, templateContextHelper.getRestrictedVariables());
		}

		return template;
	}

	protected ServletContextHashModel getServletContextHashModel(
		ServletContext servletContext) {

		GenericServlet genericServlet = new JSPSupportServlet(servletContext);

		return new ServletContextHashModel(
			genericServlet, ObjectWrapper.DEFAULT_WRAPPER);
	}

	protected boolean isEnableDebuggerService() {
		if ((System.getProperty("freemarker.debug.password") != null) &&
			(System.getProperty("freemarker.debug.port") != null)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FreeMarkerManager.class);

	private Configuration _configuration;
	private volatile FreemarkerEngineConfiguration
		_freemarkerEngineConfiguration;
	private final Map<String, TemplateModel> _templateModels =
		new ConcurrentHashMap<>();

	private class TaglibFactoryWrapper implements TemplateHashModel {

		public TaglibFactoryWrapper(ServletContext servletContext) {
			_taglibFactory = new TaglibFactory(servletContext);
		}

		@Override
		public TemplateModel get(String uri) throws TemplateModelException {
			TemplateModel templateModel = _templateModels.get(uri);

			if (templateModel == null) {
				templateModel = _taglibFactory.get(uri);

				_templateModels.put(uri, templateModel);
			}

			return templateModel;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		private final TaglibFactory _taglibFactory;

	}

}