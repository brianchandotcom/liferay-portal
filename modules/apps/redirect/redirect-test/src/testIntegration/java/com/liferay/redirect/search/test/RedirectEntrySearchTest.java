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

package com.liferay.redirect.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class RedirectEntrySearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Ignore
	@Override
	@Test
	public void testBaseModelUserPermissions() {
	}

	@Ignore
	@Override
	@Test
	public void testLocalizedSearch() {
	}

	@Ignore
	@Override
	@Test
	public void testParentBaseModelUserPermissions() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchAttachments() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchBaseModelWithTrash() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchByDDMStructureField() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchByKeywordsInsideParentBaseModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchComments() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchCommentsByKeywords() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchExpireAllVersions() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchExpireLatestVersion() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchMyEntries() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchRecentEntries() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchStatus() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchVersions() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchWithinDDMStructure() {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return _redirectEntryLocalService.addRedirectEntry(
			serviceContext.getScopeGroupId(), keywords, keywords, true,
			serviceContext);
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		_redirectEntryLocalService.deleteRedirectEntry(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return RedirectEntry.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "sourceURL";
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		RedirectEntry redirectEntry = (RedirectEntry)baseModel;

		return _redirectEntryLocalService.updateRedirectEntry(
			redirectEntry.getRedirectEntryId(), keywords, keywords,
			RandomTestUtil.randomBoolean());
	}

	@Inject
	private RedirectEntryLocalService _redirectEntryLocalService;

}