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

package com.liferay.portal.remote.cors.client.test;

import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.remote.cors.test.internal.CORSTestApplication;
import com.liferay.portal.remote.cors.test.internal.activator.BaseTestPreparatorBundleActivator;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marta Medio
 */
@RunAsClient
@RunWith(Arquillian.class)
public class CORSAnnotationClientTest extends BaseCORSClientTestCase {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseCORSClientTestCase.getArchive(
			CORSAnnotationTestPreparatorBundleActivator.class);
	}

	@Test
	public void testCORSApplication() throws Exception {
		assertURL("/test/cors-app", true);
	}

	public static class CORSAnnotationTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		protected void prepareTest() {
			HashMapDictionary<String, Object> properties =
				new HashMapDictionary<>();

			properties.put("liferay.cors.annotation", true);

			registerJaxRsApplication(
				new CORSTestApplication(), "test", properties);
		}

	}

}