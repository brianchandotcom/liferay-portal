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

package com.liferay.blogs.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.uad.test.BlogsEntryUADTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseHasAssetEntryUADAnonymizerTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class BlogsEntryUADAnonymizerTest
	extends BaseHasAssetEntryUADAnonymizerTestCase<BlogsEntry>
	implements WhenHasStatusByUserIdField {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public BlogsEntry addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		BlogsEntry blogsEntry =
			BlogsEntryUADTestUtil.addBlogsEntryWithStatusByUserId(
				_blogsEntryLocalService, userId, statusByUserId);

		_blogsEntries.add(blogsEntry);

		return blogsEntry;
	}

	@Override
	protected BlogsEntry addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected BlogsEntry addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		BlogsEntry blogsEntry = BlogsEntryUADTestUtil.addBlogsEntry(
			_blogsEntryLocalService, userId);

		if (deleteAfterTestRun) {
			_blogsEntries.add(blogsEntry);
		}

		return blogsEntry;
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		BlogsEntry blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			baseModelPK);

		String userName = blogsEntry.getUserName();
		String statusByUserName = blogsEntry.getStatusByUserName();

		if ((blogsEntry.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName()) &&
			(blogsEntry.getStatusByUserId() != user.getUserId()) &&
			!statusByUserName.equals(user.getFullName()) &&
			isAssetEntryAutoAnonymized(
				BlogsEntry.class.getName(), blogsEntry.getEntryId(), user)) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_blogsEntryLocalService.fetchBlogsEntry(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<>();

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject(filter = "component.name=*.BlogsEntryUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

}