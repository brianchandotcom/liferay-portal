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

package com.liferay.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.InvokerFilterContainer;
import com.liferay.portal.kernel.portlet.PortletInstanceFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
@RunWith(Arquillian.class)
public class InvokerFilterContainerImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortletException {
		Bundle bundle = FrameworkUtil.getBundle(
			InvokerFilterContainerImplTest.class);

		_bundleContext = bundle.getBundleContext();

		String servletContextName =
			ServletContextClassLoaderPool.getServletContextName(
				PortalClassLoaderUtil.getClassLoader());

		PortletAppImpl portletAppImpl = new PortletAppImpl(servletContextName);

		portletAppImpl.setWARFile(false);

		_portlet = new PortletImpl();

		_portlet.setPortletApp(portletAppImpl);
		_portlet.setPortletClass(MVCPortlet.class.getName());
		_portlet.setPortletId("InvokerFilterContainerImplTest");
		_portlet.setInitParams(
			Collections.singletonMap("template-path", "/META-INF/resources/"));

		_invokerFilterContainer =
			(InvokerFilterContainer)_portletInstanceFactory.create(
				_portlet, ServletContextPool.get(servletContextName), true);
	}

	@AfterClass
	public static void tearDownClass() {
		_portletInstanceFactory.destroy(_portlet);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetEventFilters() {
		EventFilter eventFilter = _registerPortletFilter(EventFilter.class);

		List<EventFilter> eventFilters =
			_invokerFilterContainer.getEventFilters();

		Assert.assertTrue(
			"Target not found in " + eventFilters,
			eventFilters.removeIf(filter -> eventFilter == filter));
	}

	@Test
	public void testGetRenderFilters() {
		RenderFilter renderFilter = _registerPortletFilter(RenderFilter.class);

		List<RenderFilter> renderFilters =
			_invokerFilterContainer.getRenderFilters();

		Assert.assertTrue(
			"Target not found in " + renderFilters,
			renderFilters.removeIf(filter -> renderFilter == filter));
	}

	@Test
	public void testGetResourceFilters() {
		ResourceFilter resourceFilter = _registerPortletFilter(
			ResourceFilter.class);

		List<ResourceFilter> resourceFilters =
			_invokerFilterContainer.getResourceFilters();

		Assert.assertTrue(
			"Target not found in " + resourceFilters,
			resourceFilters.removeIf(filter -> resourceFilter == filter));
	}

	@Test
	public void testInitAndGetActionFilters() {
		boolean[] calledInit = {false};

		ActionFilter actionFilter = (ActionFilter)ProxyUtil.newProxyInstance(
			ActionFilter.class.getClassLoader(),
			new Class<?>[] {ActionFilter.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "equals")) {
					return false;
				}

				if (Objects.equals(method.getName(), "hashcode")) {
					return 0;
				}

				if (Objects.equals(method.getName(), "init")) {
					calledInit[0] = true;
				}

				return null;
			});

		_registerPortletFilter(actionFilter, false);

		Assert.assertTrue(calledInit[0]);

		List<ActionFilter> actionFilters =
			_invokerFilterContainer.getActionFilters();

		Assert.assertTrue(
			"Target not found in " + actionFilters,
			actionFilters.removeIf(filter -> filter == actionFilter));
	}

	private <T> T _registerPortletFilter(Class<T> clazz) {
		T portletFilter = (T)ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {clazz},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "equals")) {
					return false;
				}

				if (Objects.equals(method.getName(), "hashcode")) {
					return 0;
				}

				return null;
			});

		_registerPortletFilter((PortletFilter)portletFilter, true);

		return portletFilter;
	}

	private void _registerPortletFilter(
		PortletFilter portletFilter, boolean preinitialized) {

		_serviceRegistration = _bundleContext.registerService(
			PortletFilter.class, portletFilter,
			new HashMapDictionary<String, Object>() {
				{
					put("javax.portlet.name", "InvokerFilterContainerImplTest");
					put(
						"preinitialized.filter",
						Boolean.valueOf(preinitialized));
					put("service.ranking", Integer.MAX_VALUE);
				}
			});
	}

	private static BundleContext _bundleContext;
	private static InvokerFilterContainer _invokerFilterContainer;
	private static Portlet _portlet;

	@Inject
	private static PortletInstanceFactory _portletInstanceFactory;

	private ServiceRegistration<?> _serviceRegistration;

}