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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerFieldUpdaterCustomizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class FriendlyURLMapperTrackerImpl implements FriendlyURLMapperTracker {

	public FriendlyURLMapperTrackerImpl(Portlet portlet) throws Exception {
		_portlet = portlet;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			StringBundler.concat(
				"(&(|(javax.portlet.name=", portlet.getPortletId(),
				")(javax.portlet.name=", portlet.getPortletName(),
				"))(objectClass=", FriendlyURLMapper.class.getName(), "))"));

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
		return _friendlyURLMapper;
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

	/**
	 * @see PortletBagFactory#getContent(String)
	 */
	protected String getContent(ClassLoader classLoader, String fileName)
		throws Exception {

		String queryString = HttpUtil.getQueryString(fileName);

		if (Validator.isNull(queryString)) {
			return StringUtil.read(classLoader, fileName);
		}

		int pos = fileName.indexOf(StringPool.QUESTION);

		String xml = StringUtil.read(classLoader, fileName.substring(0, pos));

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			queryString);

		if (parameterMap == null) {
			return xml;
		}

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (values.length == 0) {
				continue;
			}

			String value = values[0];

			xml = StringUtil.replace(xml, "@" + name + "@", value);
		}

		return xml;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLMapperTrackerImpl.class);

	private volatile FriendlyURLMapper _friendlyURLMapper;
	private final Portlet _portlet;
	private final Map<FriendlyURLMapper, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private final ServiceTracker<FriendlyURLMapper, FriendlyURLMapper>
		_serviceTracker;

	private class FriendlyURLMapperServiceTrackerCustomizer
		extends
			ServiceTrackerFieldUpdaterCustomizer
				<FriendlyURLMapper, FriendlyURLMapper> {

		@Override
		protected FriendlyURLMapper doAddingService(
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

					xml = getContent(clazz.getClassLoader(), friendlyURLRoutes);
				}

				friendlyURLMapper.setRouter(newFriendlyURLRouter(xml));
			}
			catch (Exception e) {
				_log.error(e, e);

				return null;
			}

			return friendlyURLMapper;
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

		private FriendlyURLMapperServiceTrackerCustomizer() throws Exception {
			super(
				ReflectionUtil.getDeclaredField(
					FriendlyURLMapperTrackerImpl.class, "_friendlyURLMapper"),
				FriendlyURLMapperTrackerImpl.this, null);
		}

	}

}