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

package com.liferay.portlet.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.PortletBagFactory;

import javax.portlet.Portlet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockServletContext;

/**
 * @author Raymond Augé
 */
public class PortletBagFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test1() throws Exception {
		try {
			PortletBagFactory portletBagFactory = new PortletBagFactory();

			portletBagFactory.create(new PortletImpl());

			Assert.fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	@Test
	public void test2() throws Exception {
		try {
			PortletBagFactory portletBagFactory = new PortletBagFactory();

			Class<?> clazz = getClass();

			portletBagFactory.setClassLoader(clazz.getClassLoader());

			portletBagFactory.create(new PortletImpl());

			Assert.fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	@Test
	public void test3() throws Exception {
		try {
			PortletBagFactory portletBagFactory = new PortletBagFactory();

			Class<?> clazz = getClass();

			portletBagFactory.setClassLoader(clazz.getClassLoader());

			portletBagFactory.setServletContext(new MockServletContext());

			portletBagFactory.create(new PortletImpl());

			Assert.fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	@Test
	public void test4_initializedInstance() throws Exception {
		PortletImpl portletImpl = new PortletImpl();

		portletImpl.setPortletApp(new PortletAppImpl(StringPool.BLANK));
		portletImpl.setPortletClass(MVCPortlet.class.getName());

		PortletBagFactory portletBagFactory = new PortletBagFactory();

		Class<?> clazz = getClass();

		portletBagFactory.setClassLoader(clazz.getClassLoader());

		portletBagFactory.setServletContext(new MockServletContext());
		portletBagFactory.setWARFile(false);

		portletBagFactory.create(portletImpl);
	}

	@Test
	public void test5_concreteInstance() throws Exception {
		PortletImpl portletImpl = new PortletImpl();

		portletImpl.setPortletApp(new PortletAppImpl(StringPool.BLANK));

		final MVCPortlet mvcPortlet = new MVCPortlet();

		PortletBagFactory portletBagFactory = new PortletBagFactory() {

			@Override
			protected Portlet getPortletInstance(
				com.liferay.portal.kernel.model.Portlet portlet) {

				return mvcPortlet;
			}

		};

		Class<?> clazz = getClass();

		portletBagFactory.setClassLoader(clazz.getClassLoader());

		portletBagFactory.setServletContext(new MockServletContext());
		portletBagFactory.setWARFile(false);

		portletBagFactory.create(portletImpl);
	}

}