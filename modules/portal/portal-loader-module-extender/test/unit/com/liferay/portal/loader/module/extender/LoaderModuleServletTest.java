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

package com.liferay.portal.loader.module.extender;

import aQute.bnd.osgi.Constants;

import aQute.lib.converter.Converter;

import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

import org.json.JSONException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Raymond Augé
 */
@RunWith(PowerMockRunner.class)
public class LoaderModuleServletTest extends PowerMockito {

	@Test
	public void testBasicOutput() throws Exception {
		LoaderModulesServlet loaderModulesServlet =
			_buildLoaderModulesServlet();

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		loaderModulesServlet.service(httpServletRequest, httpServletResponse);

		Assert.assertNotNull(httpServletResponse.getContentAsString());
		Assert.assertEquals(
			Details.CONTENT_TYPE, httpServletResponse.getContentType());
	}

	@Test
	public void testMultiModuleOutput() throws Exception {
		LoaderModulesServlet loaderModulesServlet =
			_buildLoaderModulesServlet();

		ServiceReference<ServletContext> serviceReference =
			_buildServiceReference(
				"test", new Version("1.0.0"), true, 0,
				_getResource("dependencies/config1.js"));

		loaderModulesServlet.addingService(serviceReference);

		serviceReference = _buildServiceReference(
			"foo", new Version("13.2.23"), true, 0,
			_getResource("dependencies/config2.js"));

		loaderModulesServlet.addingService(serviceReference);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		loaderModulesServlet.service(httpServletRequest, httpServletResponse);

		String content = httpServletResponse.getContentAsString();

		content = content.replace('"', '\'');

		assertContains("'test': '/test-1.0.0'", content);
		assertContains("'test@1.0.0': '/test-1.0.0'", content);
		assertContains(
			"'test/some.es':{'dependencies':['exports','test@1.0.0/other.es']}",
			content);
		assertContains(
			"'test@1.0.0/some.es':" +
				"{'dependencies':['exports','test@1.0.0/other.es']}", content);
		assertContains("'foo': '/foo-13.2.23'", content);
		assertContains("'foo@13.2.23': '/foo-13.2.23'", content);
		assertContains(
			"'foo/foo.es':{'dependencies':['exports','foo@13.2.23/fum.es'," +
				"'jquery@2.15.3/jquery.js']}", content);
		assertContains(
			"'foo@13.2.23/foo.es':{'dependencies':['exports'," +
				"'foo@13.2.23/fum.es','jquery@2.15.3/jquery.js']}", content);
	}

	@Test
	public void testMultiVersionModuleOutput() throws Exception {
		LoaderModulesServlet loaderModulesServlet =
			_buildLoaderModulesServlet();

		ServiceReference<ServletContext> serviceReference =
			_buildServiceReference(
				"test", new Version("1.0.0"), true, 0,
				_getResource("dependencies/config1.js"));

		loaderModulesServlet.addingService(serviceReference);

		serviceReference = _buildServiceReference(
			"test", new Version("1.2.0"), true, 0,
			_getResource("dependencies/config1.js"));

		loaderModulesServlet.addingService(serviceReference);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		loaderModulesServlet.service(httpServletRequest, httpServletResponse);

		String content = httpServletResponse.getContentAsString();

		content = content.replace('"', '\'');

		assertContains("'test': '/test-1.2.0'", content);
		assertContains("'test@1.2.0': '/test-1.2.0'", content);
		assertContains("'test@1.0.0': '/test-1.0.0'", content);
		assertContains(
			"'test/some.es':{'dependencies':['exports','test@1.2.0/other.es']}",
			content);
		assertNotContains(
			"'test/some.es':{'dependencies':['exports','test@1.0.0/other.es']}",
			content);
		assertContains(
			"'test@1.2.0/some.es':{'dependencies':['exports'," +
				"'test@1.2.0/other.es']}", content);
		assertContains(
			"'test@1.0.0/some.es':{'dependencies':['exports'," +
				"'test@1.0.0/other.es']}", content);
	}

	@Test
	public void testSingleModuleOutput() throws Exception {
		LoaderModulesServlet loaderModulesServlet =
			_buildLoaderModulesServlet();

		ServiceReference<ServletContext> serviceReference =
			_buildServiceReference(
				"test", new Version("1.0.0"), true, 0,
				_getResource("dependencies/config1.js"));

		loaderModulesServlet.addingService(serviceReference);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		loaderModulesServlet.service(httpServletRequest, httpServletResponse);

		String content = httpServletResponse.getContentAsString();

		content = content.replace('"', '\'');

		assertContains("'test': '/test-1.0.0'", content);
		assertContains("'test@1.0.0': '/test-1.0.0'", content);
		assertContains(
			"'test/some.es':{'dependencies':['exports','test@1.0.0/other.es']}",
			content);
		assertContains("'test/other.es':{'dependencies':['exports']}", content);
		assertContains(
			"'test@1.0.0/some.es':" +
				"{'dependencies':['exports','test@1.0.0/other.es']}",
			content);
		assertContains(
			"'test@1.0.0/other.es':{'dependencies':['exports']}",
			content);
	}

	@Test(expected = JSONException.class)
	public void testSingleModuleOutputEmptyConfig() throws Exception {
		LoaderModulesServlet loaderModulesServlet =
			_buildLoaderModulesServlet();

		ServiceReference<ServletContext> serviceReference =
			_buildServiceReference(
				"test", new Version("1.0.0"), true, 0,
				_getResource("dependencies/empty.js"));

		loaderModulesServlet.addingService(serviceReference);
	}

	@Test
	public void testSingleModuleOutputIdempotent() throws Exception {
		LoaderModulesServlet loaderModulesServlet =
			_buildLoaderModulesServlet();

		ServiceReference<ServletContext> serviceReference =
			_buildServiceReference(
				"test", new Version("1.0.0"), true, 0,
				_getResource("dependencies/config1.js"));

		loaderModulesServlet.addingService(serviceReference);
		loaderModulesServlet.addingService(serviceReference);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		loaderModulesServlet.service(httpServletRequest, httpServletResponse);

		String content = httpServletResponse.getContentAsString();

		content = content.replace('"', '\'');

		assertOccurrences("'test': '/test-1.0.0'", content, 1);
		assertOccurrences("'test@1.0.0': '/test-1.0.0'", content, 1);
		assertOccurrences(
			"'test/some.es':{'dependencies':['exports','test@1.0.0/other.es']}",
			content, 1);
		assertOccurrences(
			"'test/other.es':{'dependencies':['exports']}", content, 1);
		assertOccurrences(
			"'test@1.0.0/some.es':{'dependencies':" +
				"['exports','test@1.0.0/other.es']}", content, 1);
		assertOccurrences(
			"'test@1.0.0/other.es':{'dependencies':['exports']}", content, 1);
	}

	@Test(expected = JSONException.class)
	public void testSingleModuleOutputMalformedConfig() throws Exception {
		LoaderModulesServlet loaderModulesServlet =
			_buildLoaderModulesServlet();

		ServiceReference<ServletContext> serviceReference =
			_buildServiceReference(
				"test", new Version("1.0.0"), true, 0,
				_getResource("dependencies/malformed.js"));

		loaderModulesServlet.addingService(serviceReference);
	}

	@Test
	public void testSingleModuleOutputNoConfig() throws Exception {
		LoaderModulesServlet loaderModulesServlet =
			_buildLoaderModulesServlet();

		ServiceReference<ServletContext> serviceReference =
			_buildServiceReference("test", new Version("1.0.0"), true, 0, null);

		loaderModulesServlet.addingService(serviceReference);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		loaderModulesServlet.service(httpServletRequest, httpServletResponse);

		String content = httpServletResponse.getContentAsString();

		content = content.replace('"', '\'');

		assertContains("'test': '/test-1.0.0'", content);
		assertContains("'test@1.0.0': '/test-1.0.0'", content);
		assertNotContains("':{'dependencies':['", content);
	}

	@Test
	public void testUnversionedModuleOutput() throws Exception {
		LoaderModulesServlet loaderModulesServlet = _buildLoaderModulesServlet(
			Collections.<String, Object>singletonMap(
				"loader.modules.apply.versioning", Boolean.FALSE));

		ServiceReference<ServletContext> serviceReference =
			_buildServiceReference(
				"test", new Version("1.0.0"), true, 0,
				_getResource("dependencies/config1.js"));

		loaderModulesServlet.addingService(serviceReference);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		loaderModulesServlet.service(httpServletRequest, httpServletResponse);

		String content = httpServletResponse.getContentAsString();

		content = content.replace('"', '\'');

		assertContains("'test': '/test-1.0.0'", content);
		assertContains("'test@1.0.0': '/test-1.0.0'", content);
		assertContains(
			"'test/some.es':{'dependencies':['exports','test/other.es']}",
			content);
		assertNotContains(
			"'test/some.es':{'dependencies':['exports','test@1.0.0/other.es']}",
			content);
		assertContains("'test/other.es':{'dependencies':['exports']}", content);
		assertNotContains(
			"'test@1.0.0/some.es':" +
				"{'dependencies':['exports','test@1.0.0/other.es']}",
			content);
		assertNotContains(
			"'test@1.0.0/other.es':{'dependencies':['exports']}",
			content);
	}

	protected void assertContains(String expected, String content) {
		Assert.assertTrue(
			"Does not contain <" + expected + ">", content.contains(expected));
	}

	protected void assertNotContains(String expected, String content) {
		Assert.assertFalse(
			"Contains <" + expected + ">", content.contains(expected));
	}

	protected void assertOccurrences(
		String expected, String content, int occurences) {

		int count = 0;

		for (int i = content.indexOf(expected); i != -1;
			 i = content.indexOf(expected, i + 1)) {

			count++;
		}

		Assert.assertEquals(occurences, count);
	}

	private LoaderModulesServlet _buildLoaderModulesServlet() throws Exception {
		return _buildLoaderModulesServlet(
			Collections.<String, Object>emptyMap());
	}

	private LoaderModulesServlet _buildLoaderModulesServlet(
			Map<String, Object> properties)
		throws Exception {

		LoaderModulesServlet loaderModulesServlet = new LoaderModulesServlet();

		MockServletContext mockServletContext = new MockServletContext();

		mockServletContext.setContextPath("/loader");

		loaderModulesServlet.setDetails(
			Converter.cnv(Details.class, properties));
		loaderModulesServlet.init(new MockServletConfig(mockServletContext));

		return loaderModulesServlet;
	}

	private ServiceReference<ServletContext> _buildServiceReference(
		String bsn, Version version, boolean capability, int ranking, URL url) {

		Bundle bundle = mock(Bundle.class);

		_mockBundle(bundle, bsn, version, url, capability);

		MockServiceReference mockServiceReference = new MockServiceReference(
			bundle, new String[] {ServletContext.class.getName()},
			_counter.incrementAndGet(), ranking);

		mockServiceReference.setProperties(
			Collections.<String, Object>singletonMap(
				"osgi.web.contextpath", "/" + bsn + "-" + version.toString()));

		return mockServiceReference;
	}

	private URL _getResource(String resourceName) {
		Class<?> clazz = getClass();

		return clazz.getResource(resourceName);
	}

	private void _mockBundle(
		Bundle bundle, String bsn, Version version, URL url,
		boolean capability) {

		BundleWiring bundleWiring = mock(BundleWiring.class);
		BundleCapability bundleCapability = mock(BundleCapability.class);
		BundleWire bundleWire = mock(BundleWire.class);
		BundleCapability bundleCapabilityJquery = mock(BundleCapability.class);

		doReturn(
			Collections.<String, Object>singletonMap(
				Details.OSGI_WEBRESOURCE, bsn)
		).when(bundleCapability).getAttributes();

		Map<String, Object> jQueryProperties = new HashMap<>();

		jQueryProperties.put(Details.OSGI_WEBRESOURCE, "jquery");
		jQueryProperties.put(
			Constants.VERSION_ATTRIBUTE, new Version("2.15.3"));

		doReturn(
			jQueryProperties
		).when(bundleCapabilityJquery).getAttributes();

		doReturn(
			capability ? Arrays.asList(bundleCapability) :
				Collections.emptyList()
		).when(bundleWiring).getCapabilities(Details.OSGI_WEBRESOURCE);

		doReturn(
			capability ? Arrays.asList(bundleWire) : Collections.emptyList()
		).when(bundleWiring).getRequiredWires(Details.OSGI_WEBRESOURCE);

		doReturn(
			bundleCapabilityJquery
		).when(bundleWire).getCapability();

		doReturn(
			bundleWiring
		).when(bundle).adapt(BundleWiring.class);

		doReturn(
			url
		).when(bundle).getEntry(Details.CONFIG_JSON);

		doReturn(
			bsn
		).when(bundle).getSymbolicName();

		doReturn(
			version
		).when(bundle).getVersion();
	}

	private final AtomicInteger _counter = new AtomicInteger(0);

	private class MockServiceReference
		implements ServiceReference<ServletContext> {

		public MockServiceReference(
			Bundle bundle, String[] objectClasses, int id, int ranking) {

			_bundle = bundle;
			_objectClasses = objectClasses;
			_id = id;
			_ranking = ranking;

			_properties.put("objectClass", objectClasses);
			_properties.put("service.id", _id);
			_properties.put("service.ranking", _ranking);
		}

		@Override
		public int compareTo(Object object) {
			MockServiceReference other = (MockServiceReference)object;

			if (_ranking != other._ranking) {
				if (_ranking < other._ranking) {
					return -1;
				}

				return 1;
			}

			if (_id == other._id) {
				return 0;
			}

			if (_id < other._id) {
				return 1;
			}

			return -1;
		}

		@Override
		public Bundle getBundle() {
			return _bundle;
		}

		@Override
		public Object getProperty(String key) {
			return _properties.get(key);
		}

		@Override
		public String[] getPropertyKeys() {
			return _properties.keySet().toArray(new String[0]);
		}

		@Override
		public Bundle[] getUsingBundles() {
			return null;
		}

		@Override
		public boolean isAssignableTo(Bundle bundle, String className) {
			return false;
		}

		public void setProperties(Map<String, Object> properties) {
			_properties.putAll(properties);
			_properties.put("objectClass", _objectClasses);
			_properties.put("service.id", _id);
			_properties.put("service.ranking", _ranking);
		}

		private final Bundle _bundle;
		private int _id;
		private final String[] _objectClasses;
		private final Map<String, Object> _properties = new HashMap<>();
		private final int _ranking;

	}

}