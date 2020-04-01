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
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class RedirectNotFoundEntrySearchTest extends BaseSearchTestCase {

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

	@Override
	public void testBaseModelUserPermissions() {
	}

	@Override
	public void testLocalizedSearch() {
	}

	@Override
	public void testParentBaseModelUserPermissions() {
	}

	@Override
	public void testSearchAttachments() {
	}

	@Override
	public void testSearchBaseModelWithTrash() {
	}

	@Override
	public void testSearchByDDMStructureField() {
	}

	@Override
	public void testSearchByKeywordsInsideParentBaseModel() {
	}

	@Override
	public void testSearchComments() {
	}

	@Override
	public void testSearchCommentsByKeywords() {
	}

	@Override
	public void testSearchExpireAllVersions() {
	}

	@Override
	public void testSearchExpireLatestVersion() {
	}

	@Override
	public void testSearchMyEntries() {
	}

	@Override
	public void testSearchRecentEntries() {
	}

	@Override
	public void testSearchStatus() {
	}

	@Override
	public void testSearchVersions() {
	}

	@Override
	public void testSearchWithinDDMStructure() {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		AtomicReference<RedirectNotFoundEntry>
			redirectNotFoundEntryAtomicReference = new AtomicReference<>();

		redirectNotFoundEntryAtomicReference.set(
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				serviceContext.getScopeGroup(), keywords));

		return redirectNotFoundEntryAtomicReference.get();
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		_redirectNotFoundEntryLocalService.deleteRedirectNotFoundEntry(
			primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return RedirectNotFoundEntry.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "url";
	}

	@Override
	protected BaseModel<?> updateBaseModel(
		BaseModel<?> baseModel, String keywords,
		ServiceContext serviceContext) {

		RedirectNotFoundEntry redirectNotFoundEntry =
			(RedirectNotFoundEntry)baseModel;

		redirectNotFoundEntry.setUrl(keywords);

		return _redirectNotFoundEntryLocalService.updateRedirectNotFoundEntry(
			redirectNotFoundEntry);
	}

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}