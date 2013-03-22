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
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.xml.SAXReader;
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
	public void plugin1() throws Exception {
		try {
			PortletBeanLocatorUtil.getBeanLocator("a-test-hook");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void plugin2() throws Exception {
		try {
			PortletBeanLocatorUtil.getBeanLocator("chat-portlet");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void plugin3() throws Exception {
		try {
			PortletBeanLocatorUtil.getBeanLocator("flash-portlet");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void plugin4() throws Exception {
		try {
			PortletBeanLocatorUtil.locate("a-test-hook", "liferayDataSource");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void plugin5() throws Exception {
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
	public void plugin6() throws Exception {
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
	public void plugin7() throws Exception {
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
	public void portal1() throws Exception {
		try {
			PortalBeanLocatorUtil.getBeanLocator();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void portal2() throws Exception {
		try {
			PortalBeanLocatorUtil.locate(PortalUUID.class);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void portal3() throws Exception {
		try {
			PortalBeanLocatorUtil.locate(PortalUUID.class.getName());

			Assert.fail();
		}
		catch (SecurityException se) {

		}
	}

	@Test
	public void portal4() throws Exception {
		try {
			PortalBeanLocatorUtil.locate(SAXReader.class);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void portal5() throws Exception {
		try {
			PortalBeanLocatorUtil.locate(SAXReader.class.getName());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void portal6() throws Exception {
		try {
			PortalBeanLocatorUtil.reset();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void portal7() throws Exception {
		try {
			PortalBeanLocatorUtil.setBeanLocator(
				new BeanLocator() {

					public Object locate(String name)
						throws BeanLocatorException {

						return null;
					}

					public <T> Map<String, T> locate(Class<T> clazz)
						throws BeanLocatorException {

						return null;
					}

					public Class<?> getType(String name)
						throws BeanLocatorException {

						return null;
					}

					public String[] getNames() {
						return null;
					}

					public ClassLoader getClassLoader() {
						return null;
					}

				}
			);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}