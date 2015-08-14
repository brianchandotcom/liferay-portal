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

package com.liferay.portlet.blogs.asset;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.search.test.BaseAssetSearchTestCase;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.util.Locale;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
@Sync
public class BlogsEntryAssetSearchTest extends BaseAssetSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Ignore
	@Override
	@Test
	public void testClassTypeIds1() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testClassTypeIds2() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testOrderByExpirationDateAsc() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testOrderByExpirationDateDesc() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws Exception {

		return BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), titleMap.get(LocaleUtil.getDefault()),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), keywords,
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BlogsEntry.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "title";
	}

	@Override
	protected boolean isLocalizableTitle() {
		return false;
	}

}