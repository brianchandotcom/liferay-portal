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
import com.liferay.blogs.uad.constants.BlogsUADConstants;
import com.liferay.blogs.uad.test.BlogsEntryUADEntityTestHelper;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@RunWith(Arquillian.class)
public class BlogsEntryUADAnonymizerTest extends BaseUADAnonymizerTestCase
	implements WhenHasStatusByUserIdField {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	public BaseModel<?> addBaseModelWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		BlogsEntry blogsEntry = _blogsEntryUADEntityTestHelper.addBlogsEntryWithStatusByUserId(userId,
				statusByUserId);

		_blogsEntries.add(blogsEntry);

		return blogsEntry;
	}

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected BaseModel<?> addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {
		BlogsEntry blogsEntry = _blogsEntryUADEntityTestHelper.addBlogsEntry(userId);

		if (deleteAfterTestRun) {
			_blogsEntries.add(blogsEntry);
		}

		return blogsEntry;
	}

	@Override
	protected UADAggregator getUADAggregator() {
		return _uadAggregator;
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		BlogsEntry blogsEntry = _blogsEntryLocalService.getBlogsEntry(baseModelPK);

		String userName = blogsEntry.getUserName();
		String statusByUserName = blogsEntry.getStatusByUserName();

		if ((blogsEntry.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName()) &&
				(blogsEntry.getStatusByUserId() != user.getUserId()) &&
				!statusByUserName.equals(user.getFullName())) {
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

	@After
	public void tearDown() throws Exception {
		_blogsEntryUADEntityTestHelper.cleanUpDependencies(_blogsEntries);
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<BlogsEntry>();
	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;
	@Inject
	private BlogsEntryUADEntityTestHelper _blogsEntryUADEntityTestHelper;
	@Inject(filter = "model.class.name=" +
	BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY)
	private UADAggregator _uadAggregator;
	@Inject(filter = "model.class.name=" +
	BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY)
	private UADAnonymizer _uadAnonymizer;
}