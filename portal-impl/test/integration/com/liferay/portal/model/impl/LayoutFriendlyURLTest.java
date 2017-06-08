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

package com.liferay.portal.model.impl;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LayoutFriendlyURLTest {

	@Test
	@Transactional
	public void testDifferentFriendlyURLDifferentLocaleDifferentGroup()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Map<Locale, String> randomMap = ServiceTestUtil.randomLocaleStringMap();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/home");
		friendlyURLMap.put(new Locale("es", "ES"), "/casa");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}

		group = GroupTestUtil.addGroup();

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testDifferentFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Map<Locale, String> randomMap = ServiceTestUtil.randomLocaleStringMap();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/home");
		friendlyURLMap.put(new Locale("es", "ES"), "/casa");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}

		group = GroupTestUtil.addGroup();

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testDifferentFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Map<Locale, String> randomMap = ServiceTestUtil.randomLocaleStringMap();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/home");
		friendlyURLMap.put(new Locale("es", "ES"), "/casa");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLDifferentLocaleDifferentGroup()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Map<Locale, String> randomMap = ServiceTestUtil.randomLocaleStringMap();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/home");
		friendlyURLMap.put(new Locale("es", "ES"), "/home");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}

		group = GroupTestUtil.addGroup();

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLDifferentLocaleDifferentLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Map<Locale, String> randomMap = ServiceTestUtil.randomLocaleStringMap();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/home");
		friendlyURLMap.put(new Locale("es", "ES"), "/casa");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/welcome");
		friendlyURLMap.put(new Locale("es", "ES"), "/home");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);

			Assert.fail();
		}
		catch (LayoutFriendlyURLException lfurle) {
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Map<Locale, String> randomMap = ServiceTestUtil.randomLocaleStringMap();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/home");
		friendlyURLMap.put(new Locale("es", "ES"), "/home");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Map<Locale, String> randomMap = ServiceTestUtil.randomLocaleStringMap();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/home");
		friendlyURLMap.put(new Locale("es", "ES"), "/home");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLSameLocaleDifferentLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Map<Locale, String> randomMap = ServiceTestUtil.randomLocaleStringMap();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/home");
		friendlyURLMap.put(new Locale("es", "ES"), "/casa");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);
		}
		catch (LayoutFriendlyURLException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(new Locale("en", "US"), "/house");
		friendlyURLMap.put(new Locale("es", "ES"), "/casa");

		try {
			LayoutLocalServiceUtil.addLayout(
				TestPropsValues.getUserId(), group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, randomMap, randomMap,
				randomMap, randomMap, randomMap, LayoutConstants.TYPE_PORTLET,
				false, friendlyURLMap, serviceContext);

			Assert.fail();
		}
		catch (LayoutFriendlyURLException lfurle) {
		}
	}

}