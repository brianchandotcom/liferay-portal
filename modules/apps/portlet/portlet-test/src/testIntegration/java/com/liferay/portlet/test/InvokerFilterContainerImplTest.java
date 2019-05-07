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
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
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
		PortletFilter portletFilter = new TestPortletFilter();

		_serviceRegistration = _bundleContext.registerService(
			PortletFilter.class, portletFilter,
			new HashMapDictionary<String, Object>() {
				{
					put("javax.portlet.name", "InvokerFilterContainerImplTest");
					put("preinitialized.filter", "true");
					put("service.ranking", Integer.MAX_VALUE);
				}
			});

		List<EventFilter> eventFilters =
			_invokerFilterContainer.getEventFilters();

		Assert.assertTrue(
			"Target not found in " + eventFilters,
			eventFilters.removeIf(eventFilter -> portletFilter == eventFilter));
	}

	@Test
	public void testGetRenderFilters() {
		PortletFilter portletFilter = new TestPortletFilter();

		_serviceRegistration = _bundleContext.registerService(
			PortletFilter.class, portletFilter,
			new HashMapDictionary<String, Object>() {
				{
					put("javax.portlet.name", "InvokerFilterContainerImplTest");
					put("preinitialized.filter", "true");
					put("service.ranking", Integer.MAX_VALUE);
				}
			});

		List<RenderFilter> renderFilters =
			_invokerFilterContainer.getRenderFilters();

		Assert.assertTrue(
			"Target not found in " + renderFilters,
			renderFilters.removeIf(
				renderFilter -> portletFilter == renderFilter));
	}

	@Test
	public void testGetResourceFilters() {
		PortletFilter portletFilter = new TestPortletFilter();

		_serviceRegistration = _bundleContext.registerService(
			PortletFilter.class, portletFilter,
			new HashMapDictionary<String, Object>() {
				{
					put("javax.portlet.name", "InvokerFilterContainerImplTest");
					put("preinitialized.filter", "true");
					put("service.ranking", Integer.MAX_VALUE);
				}
			});

		List<ResourceFilter> resourceFilters =
			_invokerFilterContainer.getResourceFilters();

		Assert.assertTrue(
			"Target not found in " + resourceFilters,
			resourceFilters.removeIf(
				resourceFilter -> portletFilter == resourceFilter));
	}

	@Test
	public void testInitAndGetActionFilters() {
		boolean[] calledInit = {false};

		PortletFilter portletFilter = new TestPortletFilter() {

			@Override
			public void init(FilterConfig filterConfig) {
				calledInit[0] = true;
			}

		};

		_serviceRegistration = _bundleContext.registerService(
			PortletFilter.class, portletFilter,
			new HashMapDictionary<String, Object>() {
				{
					put("javax.portlet.name", "InvokerFilterContainerImplTest");
					put("preinitialized.filter", "false");
					put("service.ranking", Integer.MAX_VALUE);
				}
			});

		Assert.assertTrue(calledInit[0]);

		List<ActionFilter> actionFilters =
			_invokerFilterContainer.getActionFilters();

		Assert.assertTrue(
			"Target not found in " + actionFilters,
			actionFilters.removeIf(
				actionFilter -> portletFilter == actionFilter));
	}

	private static BundleContext _bundleContext;
	private static InvokerFilterContainer _invokerFilterContainer;
	private static Portlet _portlet;

	@Inject
	private static PortletInstanceFactory _portletInstanceFactory;

	private ServiceRegistration<?> _serviceRegistration;

	private class TestPortletFilter
		implements ActionFilter, EventFilter, RenderFilter, ResourceFilter {

		@Override
		public void destroy() {
		}

		@Override
		public void doFilter(
			ActionRequest actionRequest, ActionResponse actionResponse,
			FilterChain filterChain) {
		}

		@Override
		public void doFilter(
			EventRequest eventRequest, EventResponse eventResponse,
			FilterChain filterChain) {
		}

		@Override
		public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain) {
		}

		@Override
		public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			FilterChain filterChain) {
		}

		@Override
		public void init(FilterConfig filterConfig) {
		}

	}

}