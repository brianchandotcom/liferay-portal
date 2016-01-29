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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class FriendlyURLMapperTrackerImpl implements FriendlyURLMapperTracker {

	public FriendlyURLMapperTrackerImpl(Portlet portlet) {
		_portlet = portlet;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=" + portlet.getPortletId() +
				")(objectClass=" + FriendlyURLMapper.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new FriendlyURLMapperServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public void close() {
		for (Map.Entry<FriendlyURLMapper, ServiceRegistration<?>> entry :
				_serviceRegistrations.entrySet()) {

			ServiceRegistration<?> serviceRegistration = entry.getValue();

			serviceRegistration.unregister();
		}

		_serviceTracker.close();
	}

	@Override
	public FriendlyURLMapper getFriendlyURLMapper() {
		return _serviceTracker.getService();
	}

	@Override
	public void register(FriendlyURLMapper friendlyURLMapper) {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("javax.portlet.name", _portlet.getPortletId());

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			FriendlyURLMapper.class, friendlyURLMapper, properties);

		_serviceRegistrations.put(friendlyURLMapper, serviceRegistration);
	}

	@Override
	public void unregister(FriendlyURLMapper friendlyURLMapper) {
		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(friendlyURLMapper);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLMapperTrackerImpl.class);

	private final Portlet _portlet;
	private final Map<FriendlyURLMapper, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private final ServiceTracker<FriendlyURLMapper, FriendlyURLMapper>
		_serviceTracker;

	private class FriendlyURLMapperServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer<FriendlyURLMapper, FriendlyURLMapper> {

		@Override
		public FriendlyURLMapper addingService(
			ServiceReference<FriendlyURLMapper> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			FriendlyURLMapper friendlyURLMapper = registry.getService(
				serviceReference);

			try {
				if (Validator.isNotNull(_portlet.getFriendlyURLMapping())) {
					friendlyURLMapper.setMapping(
						_portlet.getFriendlyURLMapping());
				}

				friendlyURLMapper.setPortletId(_portlet.getPortletId());
				friendlyURLMapper.setPortletInstanceable(
					_portlet.isInstanceable());

				String friendlyURLRoutes = (String)serviceReference.getProperty(
					"com.liferay.portlet.friendly-url-routes");

				if (Validator.isNotNull(_portlet.getFriendlyURLRoutes())) {
					friendlyURLRoutes = _portlet.getFriendlyURLRoutes();
				}

				String xml = null;

				if (Validator.isNotNull(friendlyURLRoutes)) {
					Class<?> clazz = friendlyURLMapper.getClass();

					ClassLoader classLoader = clazz.getClassLoader();

					xml = StringUtil.read(classLoader, friendlyURLRoutes);
				}

				friendlyURLMapper.setRouter(newFriendlyURLRouter(xml));
			}
			catch (Exception e) {
				_log.error(e, e);

				return null;
			}

			return friendlyURLMapper;
		}

		@Override
		public void modifiedService(
			ServiceReference<FriendlyURLMapper> serviceReference,
			FriendlyURLMapper service) {
		}

		@Override
		public void removedService(
			ServiceReference<FriendlyURLMapper> serviceReference,
			FriendlyURLMapper service) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

		protected Router newFriendlyURLRouter(String xml) throws Exception {
			if (Validator.isNull(xml)) {
				return null;
			}

			Document document = UnsecureSAXReaderUtil.read(xml, true);

			Element rootElement = document.getRootElement();

			List<Element> routeElements = rootElement.elements("route");

			Router router = new RouterImpl(routeElements.size());

			for (Element routeElement : routeElements) {
				String pattern = routeElement.elementText("pattern");

				Route route = router.addRoute(pattern);

				for (Element generatedParameterElement :
						routeElement.elements("generated-parameter")) {

					String name = generatedParameterElement.attributeValue(
						"name");
					String value = generatedParameterElement.getText();

					route.addGeneratedParameter(name, value);
				}

				for (Element ignoredParameterElement :
						routeElement.elements("ignored-parameter")) {

					String name = ignoredParameterElement.attributeValue(
						"name");

					route.addIgnoredParameter(name);
				}

				for (Element implicitParameterElement :
						routeElement.elements("implicit-parameter")) {

					String name = implicitParameterElement.attributeValue(
						"name");
					String value = implicitParameterElement.getText();

					route.addImplicitParameter(name, value);
				}

				for (Element overriddenParameterElement :
						routeElement.elements("overridden-parameter")) {

					String name = overriddenParameterElement.attributeValue(
						"name");
					String value = overriddenParameterElement.getText();

					route.addOverriddenParameter(name, value);
				}
			}

			return router;
		}

	}

}