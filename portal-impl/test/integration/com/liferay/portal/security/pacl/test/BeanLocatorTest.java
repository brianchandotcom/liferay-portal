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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class BeanLocatorTest {

	@Test
	public void testPlugin1() throws Exception {
		try {
			PortletBeanLocatorUtil.getBeanLocator("a-test-hook");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPlugin2() throws Exception {
		try {
			PortletBeanLocatorUtil.getBeanLocator("chat-portlet");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPlugin3() throws Exception {
		try {
			PortletBeanLocatorUtil.getBeanLocator("flash-portlet");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPlugin4() throws Exception {
		try {
			PortletBeanLocatorUtil.locate("a-test-hook", "liferayDataSource");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPlugin5() throws Exception {
		try {
			BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
				"a-test-hook");

			PortletBeanLocatorUtil.setBeanLocator("a-test-hook", beanLocator);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPlugin6() throws Exception {
		try {
			BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
				"a-test-hook");

			PortletBeanLocatorUtil.setBeanLocator("chat-portlet", beanLocator);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPlugin7() throws Exception {
		try {
			BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
				"a-test-hook");

			PortletBeanLocatorUtil.setBeanLocator("flash-portlet", beanLocator);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPortal1() throws Exception {
		try {
			PortalBeanLocatorUtil.getBeanLocator();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPortal2() throws Exception {
		try {
			PortalBeanLocatorUtil.locate(PortalUUID.class);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPortal3() throws Exception {
		try {
			PortalUUID portalUuid = (PortalUUID)PortalBeanLocatorUtil.locate(
				PortalUUID.class.getName());

			portalUuid.generate();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPortal4() throws Exception {
		try {
			PortalBeanLocatorUtil.locate(SAXReader.class);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPortal5() throws Exception {
		try {
			PortalBeanLocatorUtil.locate(SAXReader.class.getName());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testPortal6() throws Exception {
		try {
			PortalBeanLocatorUtil.reset();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPortal7() throws Exception {
		try {
			PortalBeanLocatorUtil.setBeanLocator(
				new BeanLocator() {

					@Override
					public void destroy() {
					}

					@Override
					public ClassLoader getClassLoader() {
						return null;
					}

					@Override
					public String[] getNames() {
						return null;
					}

					@Override
					public Class<?> getType(String name) {
						return null;
					}

					@Override
					public <T> Map<String, T> locate(Class<T> clazz)
						throws BeanLocatorException {

						return null;
					}

					@Override
					public Object locate(String name) {
						return null;
					}

				}
			);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPortal8() throws Exception {
		try {
			AuthTokenUtil authTokenUtil =
				(AuthTokenUtil)PortalBeanLocatorUtil.locate(
					AuthTokenUtil.class.getName());

			Assert.assertFalse(
				ProxyUtil.isProxyClass(authTokenUtil.getClass()));
		}
		catch (SecurityException se) {
		}
	}

}