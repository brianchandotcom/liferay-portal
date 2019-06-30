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

package com.liferay.portal.osgi.web.servlet.context.helper.internal;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.osgi.web.servlet.JSPServletFactory;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperFactory;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.runtime.HttpServiceRuntime;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * @author Raymond Augé
 */
@Component(immediate = true, service = ServletContextHelperFactory.class)
public class ServletContextHelperFactoryImpl
	implements ServletContextHelperFactory {

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws Exception {

		_saxParserFactory.setNamespaceAware(false);
		_saxParserFactory.setValidating(false);
		_saxParserFactory.setXIncludeAware(false);

		try {
			_saxParserFactory.setFeature(_FEATURES_DISALLOW_DOCTYPE_DECL, true);
			_saxParserFactory.setFeature(
				_FEATURES_EXTERNAL_GENERAL_ENTITIES, false);
			_saxParserFactory.setFeature(
				_FEATURES_EXTERNAL_PARAMETER_ENTITIES, false);
			_saxParserFactory.setFeature(_FEATURES_LOAD_EXTERNAL_DTD, false);
		}
		catch (ParserConfigurationException | SAXNotRecognizedException |
			   SAXNotSupportedException e) {

			ReflectionUtil.throwException(e);
		}

		_executorService = _portalExecutorManager.getPortalExecutor(
			ServletContextHelperFactoryImpl.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ServletContextHelperRegistration.class.getName(),
			new ServletContextHelperRegistrationServiceFactory(
				_jspServletFactory, _saxParserFactory, properties,
				_executorService),
			null);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) throws Exception {
		_serviceRegistration.unregister();

		_executorService.shutdownNow();
	}

	private static final String _FEATURES_DISALLOW_DOCTYPE_DECL =
		"http://apache.org/xml/features/disallow-doctype-decl";

	private static final String _FEATURES_EXTERNAL_GENERAL_ENTITIES =
		"http://xml.org/sax/features/external-general-entities";

	private static final String _FEATURES_EXTERNAL_PARAMETER_ENTITIES =
		"http://xml.org/sax/features/external-parameter-entities";

	private static final String _FEATURES_LOAD_EXTERNAL_DTD =
		"http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private ExecutorService _executorService;

	@Reference
	private HttpServiceRuntime _httpServiceRuntime;

	@Reference
	private JSPServletFactory _jspServletFactory;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private SAXParserFactory _saxParserFactory;

	private ServiceRegistration<?> _serviceRegistration;

}