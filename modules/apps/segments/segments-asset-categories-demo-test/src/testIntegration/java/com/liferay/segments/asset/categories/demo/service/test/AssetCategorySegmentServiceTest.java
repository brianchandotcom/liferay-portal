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

package com.liferay.segments.asset.categories.demo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.Segment;
import com.liferay.segments.service.SegmentService;

import java.util.Collection;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class AssetCategorySegmentServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				_ASSET_VOCABULARY_NAME, serviceContext);

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			RandomTestUtil.randomString(), vocabulary.getVocabularyId(),
			serviceContext);

		_user = UserTestUtil.addUser();

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			_user.getModelClassName(), _user.getUserId());

		AssetCategoryLocalServiceUtil.addAssetEntryAssetCategory(
			assetEntry.getEntryId(), assetCategory.getCategoryId());
	}

	@Test
	public void testMatches() throws Exception {
		Collection segments = _segmentService.getSegments(_group.getGroupId());

		Assert.assertFalse("Segments list is empty", segments.isEmpty());

		Segment segment = (Segment)segments.iterator().next();

		Assert.assertTrue(
			"User does not match the segment",
			_segmentService.matches(
				_user.getUserId(), segment, new HashMap<>()));
	}

	private static final String _ASSET_VOCABULARY_NAME = "Segments";

	@Inject
	private static SegmentService _segmentService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}