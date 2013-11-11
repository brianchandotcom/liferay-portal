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
import com.liferay.portal.LayoutFriendlyURLsException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
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
import java.util.List;
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

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		group = GroupTestUtil.addGroup();

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testDifferentFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		group = GroupTestUtil.addGroup();

		try {
			addLayout(group.getGroupId(), true, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testDifferentFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testInvalidFriendlyURLMapperURLInDefaultLocale()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.US, "/tags");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptions =
				lfurlse.getLocalizedExceptions();

			List<Exception> layoutFriendlyURLExceptions =
				(List<Exception>)localizedExceptions.values();

			Assert.assertEquals(1, layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException lfurle =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(lfurle.getKeywordConflict(), "tags");
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.US, "/home/tags");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptions =
				lfurlse.getLocalizedExceptions();

			List<Exception> layoutFriendlyURLExceptions =
				(List<Exception>)localizedExceptions.values();

			Assert.assertEquals(1, layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException lfurle =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(lfurle.getKeywordConflict(), "tags");
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.US, "/tags/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptions =
				lfurlse.getLocalizedExceptions();

			List<Exception> layoutFriendlyURLExceptions =
				(List<Exception>)localizedExceptions.values();

			Assert.assertEquals(1, layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException lfurle =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(lfurle.getKeywordConflict(), "tags");
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.US, "/blogs/-/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptions =
				lfurlse.getLocalizedExceptions();

			List<Exception> layoutFriendlyURLExceptions =
				(List<Exception>)localizedExceptions.values();

			Assert.assertEquals(1, layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException lfurle =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(lfurle.getKeywordConflict(), "/-/");
		}
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	@Transactional
	public void testInvalidFriendlyURLMapperURLInNonDefaultLocale()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/tags/two");
		friendlyURLMap.put(LocaleUtil.US, "/two");

		addLayout(group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	@Transactional
	public void testMultipleInvalidFriendlyURLMapperURL() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/tags/dos");
		friendlyURLMap.put(LocaleUtil.US, "/tags/two");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptions =
				lfurlse.getLocalizedExceptions();

			List<Exception> layoutFriendlyURLExceptions =
				(List<Exception>)localizedExceptions.values();

			Assert.assertEquals(2, layoutFriendlyURLExceptions.size());

			for (Exception e : layoutFriendlyURLExceptions) {
				String keywordsConflict =
					((LayoutFriendlyURLException)e).getKeywordConflict();

				Assert.assertEquals(keywordsConflict, "tags");
			}
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLDifferentLocaleDifferentGroup()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/home");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		group = GroupTestUtil.addGroup();

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLDifferentLocaleDifferentLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/home");
		friendlyURLMap.put(LocaleUtil.US, "/welcome");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurle) {
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/home");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		try {
			addLayout(group.getGroupId(), true, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/home");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testSameFriendlyURLSameLocaleDifferentLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/house");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurle) {
		}
	}

	@Test
	@Transactional
	public void testValidFriendlyURLMapperURLInDefaultLocale()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.US, "/blogs");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.US, "/home/blogs");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.US, "/blogs/home");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void testValidFriendlyURLMapperURLInNonDefaultLocale()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/blogs/two");
		friendlyURLMap.put(LocaleUtil.US, "/two");

		try {
			addLayout(group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	protected void addLayout(
			long groupId, boolean privateLayout,
			Map<Locale, String> friendlyURLMap)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			ServiceTestUtil.randomLocaleStringMap(),
			ServiceTestUtil.randomLocaleStringMap(),
			ServiceTestUtil.randomLocaleStringMap(),
			ServiceTestUtil.randomLocaleStringMap(),
			ServiceTestUtil.randomLocaleStringMap(),
			LayoutConstants.TYPE_PORTLET, StringPool.BLANK, false,
			friendlyURLMap, serviceContext);
	}

}