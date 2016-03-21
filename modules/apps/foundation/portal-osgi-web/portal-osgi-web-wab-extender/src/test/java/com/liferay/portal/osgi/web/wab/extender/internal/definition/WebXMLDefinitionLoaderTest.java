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

package com.liferay.portal.osgi.web.wab.extender.internal.definition;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.wab.extender.internal.definition.ordering.Ordering;
import com.liferay.portal.osgi.web.wab.extender.internal.definition.ordering.OrderingBeforeAndAfterException;
import com.liferay.portal.osgi.web.wab.extender.internal.definition.ordering.OrderingCircularDependencyException;
import com.liferay.portal.osgi.web.wab.extender.internal.definition.ordering.OrderingImpl;
import com.liferay.portal.osgi.web.wab.extender.internal.definition.ordering.WebFragmentOrderUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;

import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.osgi.framework.Bundle;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Miguel Pastor
 */
@RunWith(PowerMockRunner.class)
public class WebXMLDefinitionLoaderTest {

	@Test
	public void testCircularDependenciesException() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundle.getEntry());

		EntryLoaderMockBundle bundleCircular1 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment-circular1.xml");

		WebXMLDefinitionLoader fragmentCircular1Loader =
			new WebXMLDefinitionLoader(
				bundleCircular1, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragmentCircular1Definition =
			fragmentCircular1Loader.loadWebXML(bundleCircular1.getEntry());

		EntryLoaderMockBundle bundleCircular2 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment-circular2.xml");

		WebXMLDefinitionLoader fragmentCircular2Loader =
			new WebXMLDefinitionLoader(
				bundleCircular2, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragmentCircular2Definition =
			fragmentCircular2Loader.loadWebXML(bundleCircular2.getEntry());

		List<WebXMLDefinition> definitions = new ArrayList<>();
		definitions.add(fragmentCircular1Definition);
		definitions.add(fragmentCircular2Definition);

		boolean exceptionThrown = false;

		try {
			WebFragmentOrderUtil.getOrder(
				definitions, webXMLDefinition.getAbsoluteOrderNames());
		}
		catch (Exception e) {
			if (e instanceof OrderingCircularDependencyException) {
				exceptionThrown = true;
			}
		}

		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void testDuplicatedNameException() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundle.getEntry());

		EntryLoaderMockBundle bundleFragment5 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment5.xml");

		WebXMLDefinitionLoader fragment5DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment5, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment5Definition =
			fragment5DefinitionLoader.loadWebXML(bundleFragment5.getEntry());

		List<WebXMLDefinition> definitions = new ArrayList<>();
		definitions.add(fragment5Definition);

		boolean exceptionThrown = false;

		try {
			WebFragmentOrderUtil.getOrder(
				definitions, webXMLDefinition.getAbsoluteOrderNames());
		}
		catch (Exception e) {
			if (e instanceof OrderingBeforeAndAfterException) {
				exceptionThrown = true;
			}
		}

		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void testLoadCustomDependencies() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 1, bundle.getEntry());
	}

	@Test
	public void testLoadCustomDependenciesAbsoluteOrdering() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-absolute-ordering1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		List<String> absoluteOrdering = new ArrayList<>();

		absoluteOrdering.add("fragment2");
		absoluteOrdering.add("fragment1");
		absoluteOrdering.add(OrderingImpl.OTHERS);

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 1, bundle.getEntry(), null, null,
			absoluteOrdering);
	}

	@Test
	public void testLoadCustomWebFragment1Dependencies() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 0, bundle.getEntry(), "fragment1",
			null, null);
	}

	@Test
	public void testLoadCustomWebFragment2Dependencies() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment2.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		Ordering ordering = new OrderingImpl();
		EnumMap<Ordering.Path, String[]> routes = ordering.getRoutes();
		routes.put(Ordering.Path.AFTER, new String[] {"fragment1"});

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0, bundle.getEntry(), "fragment2",
			ordering, null);
	}

	@Test
	public void testLoadCustomWebFragment3Dependencies() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment4.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		Ordering ordering = new OrderingImpl();
		EnumMap<Ordering.Path, String[]> routes = ordering.getRoutes();
		routes.put(Ordering.Path.BEFORE, new String[] {OrderingImpl.OTHERS});

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0, bundle.getEntry(), "fragment4",
			ordering, null);
	}

	@Test
	public void testLoadDefaultDependencies() throws Exception {
		Bundle bundle = new MockBundle();

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0,
			bundle.getEntry("WEB-INF/web.xml"));
	}

	@Test
	public void testLoadMetadataComplete() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundle.getEntry());

		Assert.assertTrue(webXMLDefinition.isMetadataComplete());
	}

	@Test
	public void testLoadNotMetadataComplete() throws Exception {
		EntryLoaderMockBundle bundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-absolute-ordering1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundle.getEntry());

		Assert.assertFalse(webXMLDefinition.isMetadataComplete());
	}

	@Test
	public void testSortFragments1() throws Exception {
		EntryLoaderMockBundle bundleAbsoluteOrdering =
			new EntryLoaderMockBundle(
				"dependencies/custom-web-absolute-ordering1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleAbsoluteOrdering, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundleAbsoluteOrdering.getEntry());

		EntryLoaderMockBundle bundleFragment1 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment1.xml");

		WebXMLDefinitionLoader fragment1DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment1, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1Definition =
			fragment1DefinitionLoader.loadWebXML(bundleFragment1.getEntry());

		EntryLoaderMockBundle bundleFragment2 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment2.xml");

		WebXMLDefinitionLoader fragment2DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment2, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2Definition =
			fragment2DefinitionLoader.loadWebXML(bundleFragment2.getEntry());

		EntryLoaderMockBundle bundleFragment3 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment3.xml");

		WebXMLDefinitionLoader fragment3DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment3, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3Definition =
			fragment3DefinitionLoader.loadWebXML(bundleFragment3.getEntry());

		List<WebXMLDefinition> definitions = new ArrayList<>();
		definitions.add(fragment3Definition);
		definitions.add(fragment1Definition);
		definitions.add(fragment2Definition);

		List<WebXMLDefinition> sortedDefinitions =
			WebFragmentOrderUtil.getOrder(
				definitions, webXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(3, sortedDefinitions.size());

		WebXMLDefinition firstDefinition = sortedDefinitions.get(0);
		Assert.assertEquals("fragment2", firstDefinition.getFragmentName());

		WebXMLDefinition secondDefinition = sortedDefinitions.get(1);
		Assert.assertEquals("fragment1", secondDefinition.getFragmentName());

		WebXMLDefinition thirdDefinition = sortedDefinitions.get(2);
		Assert.assertEquals("fragment3", thirdDefinition.getFragmentName());
	}

	@Test
	public void testSortFragments2() throws Exception {
		EntryLoaderMockBundle bundleAbsoluteOrdering =
			new EntryLoaderMockBundle(
				"dependencies/custom-web-absolute-ordering2.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleAbsoluteOrdering, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundleAbsoluteOrdering.getEntry());

		EntryLoaderMockBundle bundleFragment1 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment1.xml");

		WebXMLDefinitionLoader fragment1DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment1, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1Definition =
			fragment1DefinitionLoader.loadWebXML(bundleFragment1.getEntry());

		EntryLoaderMockBundle bundleFragment2 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment2.xml");

		WebXMLDefinitionLoader fragment2DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment2, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2Definition =
			fragment2DefinitionLoader.loadWebXML(bundleFragment2.getEntry());

		EntryLoaderMockBundle bundleFragment3 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment3.xml");

		WebXMLDefinitionLoader fragment3DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment3, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3Definition =
			fragment3DefinitionLoader.loadWebXML(bundleFragment3.getEntry());

		List<WebXMLDefinition> definitions = new ArrayList<>();
		definitions.add(fragment3Definition);
		definitions.add(fragment2Definition);
		definitions.add(fragment1Definition);

		List<WebXMLDefinition> sortedDefinitions =
			WebFragmentOrderUtil.getOrder(
				definitions, webXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(2, sortedDefinitions.size());

		WebXMLDefinition firstDefinition = sortedDefinitions.get(0);
		Assert.assertEquals("fragment1", firstDefinition.getFragmentName());

		WebXMLDefinition secondDefinition = sortedDefinitions.get(1);
		Assert.assertEquals("fragment2", secondDefinition.getFragmentName());
	}

	@Test
	public void testSortFragments3() throws Exception {
		EntryLoaderMockBundle bundleWebXML = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleWebXML, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundleWebXML.getEntry());

		EntryLoaderMockBundle bundleFragment1 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment1.xml");

		WebXMLDefinitionLoader fragment1DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment1, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1Definition =
			fragment1DefinitionLoader.loadWebXML(bundleFragment1.getEntry());

		EntryLoaderMockBundle bundleFragment2 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment2.xml");

		WebXMLDefinitionLoader fragment2DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment2, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2Definition =
			fragment2DefinitionLoader.loadWebXML(bundleFragment2.getEntry());

		EntryLoaderMockBundle bundleFragment3 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment3.xml");

		WebXMLDefinitionLoader fragment3DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment3, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3Definition =
			fragment3DefinitionLoader.loadWebXML(bundleFragment3.getEntry());

		List<WebXMLDefinition> definitions = new ArrayList<>();
		definitions.add(fragment3Definition);
		definitions.add(fragment2Definition);
		definitions.add(fragment1Definition);

		List<WebXMLDefinition> sortedDefinitions =
			WebFragmentOrderUtil.getOrder(
				definitions, webXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(3, sortedDefinitions.size());

		WebXMLDefinition firstDefinition = sortedDefinitions.get(0);
		Assert.assertEquals("fragment1", firstDefinition.getFragmentName());

		WebXMLDefinition secondDefinition = sortedDefinitions.get(1);
		Assert.assertEquals("fragment3", secondDefinition.getFragmentName());

		WebXMLDefinition thirdDefinition = sortedDefinitions.get(2);
		Assert.assertEquals("fragment2", thirdDefinition.getFragmentName());
	}

	@Test
	public void testSortFragments4() throws Exception {
		EntryLoaderMockBundle bundleWebxml = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleWebxml, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundleWebxml.getEntry());

		EntryLoaderMockBundle bundleFragment1 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment1.xml");

		WebXMLDefinitionLoader fragment1DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment1, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1Definition =
			fragment1DefinitionLoader.loadWebXML(bundleFragment1.getEntry());

		EntryLoaderMockBundle bundleFragment2 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment2.xml");

		WebXMLDefinitionLoader fragment2DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment2, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2Definition =
			fragment2DefinitionLoader.loadWebXML(bundleFragment2.getEntry());

		EntryLoaderMockBundle bundleFragment4 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment4.xml");

		WebXMLDefinitionLoader fragment4DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment4, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment4Definition =
			fragment4DefinitionLoader.loadWebXML(bundleFragment4.getEntry());

		List<WebXMLDefinition> definitions = new ArrayList<>();
		definitions.add(fragment2Definition);
		definitions.add(fragment1Definition);
		definitions.add(fragment4Definition);

		List<WebXMLDefinition> sortedDefinitions =
			WebFragmentOrderUtil.getOrder(
				definitions, webXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(3, sortedDefinitions.size());

		WebXMLDefinition firstDefinition = sortedDefinitions.get(0);
		Assert.assertEquals("fragment4", firstDefinition.getFragmentName());

		WebXMLDefinition secondDefinition = sortedDefinitions.get(1);
		Assert.assertEquals("fragment1", secondDefinition.getFragmentName());

		WebXMLDefinition thirdDefinition = sortedDefinitions.get(2);
		Assert.assertEquals("fragment2", thirdDefinition.getFragmentName());
	}

	@Test
	public void testUnsortedFragments() throws Exception {
		EntryLoaderMockBundle bundleAbsoluteOrdering =
			new EntryLoaderMockBundle("dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleAbsoluteOrdering, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			bundleAbsoluteOrdering.getEntry());

		EntryLoaderMockBundle bundleFragment1 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment1.xml");

		WebXMLDefinitionLoader fragment1DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment1, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1Definition =
			fragment1DefinitionLoader.loadWebXML(bundleFragment1.getEntry());

		EntryLoaderMockBundle bundleFragment3 = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment3.xml");

		WebXMLDefinitionLoader fragment3DefinitionLoader =
			new WebXMLDefinitionLoader(
				bundleFragment3, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3Definition =
			fragment3DefinitionLoader.loadWebXML(bundleFragment3.getEntry());

		List<WebXMLDefinition> definitions = new ArrayList<>();
		definitions.add(fragment1Definition);
		definitions.add(fragment3Definition);

		List<WebXMLDefinition> sortedDefinitions =
			WebFragmentOrderUtil.getOrder(
				definitions, webXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(2, sortedDefinitions.size());

		WebXMLDefinition firstDefinition = sortedDefinitions.get(0);
		Assert.assertEquals("fragment1", firstDefinition.getFragmentName());

		WebXMLDefinition secondDefinition = sortedDefinitions.get(1);
		Assert.assertEquals("fragment3", secondDefinition.getFragmentName());
	}

	protected void testLoadDependencies(
			WebXMLDefinitionLoader webXMLDefinitionLoader, int numfOfListeners,
			int numOfFilters, int numfOfServlets, URL webXML)
		throws Exception {

		testLoadDependencies(
			webXMLDefinitionLoader, numfOfListeners, numOfFilters,
			numfOfServlets, webXML, null, null, null);
	}

	protected void testLoadDependencies(
			WebXMLDefinitionLoader webXMLDefinitionLoader, int numfOfListeners,
			int numOfFilters, int numfOfServlets, URL webXML,
			String fragmentName, Ordering ordering,
			List<String> absoluteOrdering)
		throws Exception {

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			webXML);

		if (Validator.isNotNull(fragmentName)) {
			Assert.assertEquals(
				fragmentName, webXMLDefinition.getFragmentName());
		}

		if (ordering != null) {
			Ordering definitionOrdering = webXMLDefinition.getOrdering();

			EnumMap<Ordering.Path, String[]> routes =
				definitionOrdering.getRoutes();

			EnumMap<Ordering.Path, String[]> expectedRoutes =
				ordering.getRoutes();

			Assert.assertArrayEquals(
				expectedRoutes.get(Ordering.Path.AFTER),
				routes.get(Ordering.Path.AFTER));

			Assert.assertArrayEquals(
				expectedRoutes.get(Ordering.Path.BEFORE),
				routes.get(Ordering.Path.BEFORE));
		}

		if (ListUtil.isNotEmpty(absoluteOrdering)) {
			String[] absoluteOrderingArray = absoluteOrdering.toArray(
				new String[0]);
			List<String> definitionAbsoluteOrdering =
				webXMLDefinition.getAbsoluteOrderNames();
			String[] definitionAbsoluteOrderingArray =
				definitionAbsoluteOrdering.toArray(new String[0]);
			Assert.assertArrayEquals(
				absoluteOrderingArray, definitionAbsoluteOrderingArray);
		}

		List<ListenerDefinition> listenerDefinitions =
			webXMLDefinition.getListenerDefinitions();

		Assert.assertEquals(numfOfListeners, listenerDefinitions.size());

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			EventListener eventListener = listenerDefinition.getEventListener();

			Assert.assertTrue(eventListener instanceof ServletContextListener);
		}

		Map<String, ServletDefinition> servletDefinitions =
			webXMLDefinition.getServletDefinitions();

		Assert.assertEquals(numfOfServlets, servletDefinitions.size());

		Map<String, FilterDefinition> filterDefinitions =
			webXMLDefinition.getFilterDefinitions();

		Assert.assertEquals(numOfFilters, filterDefinitions.size());
	}

	@Mock
	private Servlet _servlet;

	@Mock
	private ServletContextListener _servletContextListener;

	private static class EntryLoaderMockBundle extends MockBundle {

		public EntryLoaderMockBundle(String path) {
			_path = path;
		}

		public URL getEntry() {
			Class<?> clazz = getClass();

			return clazz.getResource(_path);
		}

		private final String _path;

	}

}