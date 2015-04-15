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

package com.liferay.portal.model.impl;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutFriendlyURLsException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class LayoutFriendlyURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groups.add(_group);
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleDifferentGroup()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		try {
			addLayout(group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		try {
			addLayout(group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLLanguageId() throws Exception {
		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/es");

		addLayout(_group.getGroupId(), friendlyURLMap);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLLanguageIdAndCountryId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/es_ES");

		addLayout(_group.getGroupId(), friendlyURLMap);
	}

	@Test
	public void testInvalidFriendlyURLMapperURLInDefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/tags");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptionsMap =
				lfurlse.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(1, layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException lfurle =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(lfurle.getKeywordConflict(), "tags");
		}

		friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/home/tags");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptionsMap =
				lfurlse.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(1, layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException lfurle =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(lfurle.getKeywordConflict(), "tags");
		}

		friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/tags/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptionsMap =
				lfurlse.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(1, layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException lfurle =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(lfurle.getKeywordConflict(), "tags");
		}

		friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/blogs/-/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptionsMap =
				lfurlse.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(1, layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException lfurle =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(lfurle.getKeywordConflict(), "/-/");
		}
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLMapperURLInNonDefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/tags/two");
		friendlyURLMap.put(LocaleUtil.US, "/two");

		addLayout(_group.getGroupId(), friendlyURLMap);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLStartingWithLanguageId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/es/home");

		addLayout(_group.getGroupId(), friendlyURLMap);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLStartingWithLanguageIdAndCountryId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/es_ES/home");

		addLayout(_group.getGroupId(), friendlyURLMap);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void
			testInvalidFriendlyURLStartingWithLowerCaseLanguageIdAndCountryId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/es_es/home");

		addLayout(_group.getGroupId(), friendlyURLMap);
	}

	@Test
	public void testMultipleInvalidFriendlyURLMapperURL() throws Exception {
		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/tags/dos");
		friendlyURLMap.put(LocaleUtil.US, "/tags/two");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurlse) {
			Map<Locale, Exception> localizedExceptionsMap =
				lfurlse.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(2, layoutFriendlyURLExceptions.size());

			for (Exception e : layoutFriendlyURLExceptions) {
				String keywordsConflict =
					((LayoutFriendlyURLException)e).getKeywordConflict();

				Assert.assertEquals(keywordsConflict, "tags");
			}
		}
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleDifferentGroup()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/home");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		try {
			addLayout(group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleDifferentLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/home");
		friendlyURLMap.put(LocaleUtil.US, "/welcome");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurle) {
		}
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/home");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/home");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	public void testSameFriendlyURLSameLocaleDifferentLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/house");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException lfurle) {
		}
	}

	@Test
	public void testValidFriendlyURLMapperURLInDefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/blogs");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/home/blogs");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}

		friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/blogs/home");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	public void testValidFriendlyURLMapperURLInNonDefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/blogs/two");
		friendlyURLMap.put(LocaleUtil.US, "/two");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	@Test
	public void testValidFriendlyURLStartingWithLanguageId() throws Exception {
		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.US, "/eshome");

		try {
			addLayout(_group.getGroupId(), friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException lfurle) {
			Assert.fail();
		}
	}

	protected void addLayout(long groupId, Map<Locale, String> friendlyURLMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), groupId,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			LayoutConstants.TYPE_PORTLET, StringPool.BLANK, false,
			friendlyURLMap, serviceContext);
	}

	private Group _group;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

}