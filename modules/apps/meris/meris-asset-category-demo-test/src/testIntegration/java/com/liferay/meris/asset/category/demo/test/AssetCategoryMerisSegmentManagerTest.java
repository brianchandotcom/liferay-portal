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

package com.liferay.meris.asset.category.demo.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalServiceUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.meris.MerisRule;
import com.liferay.meris.MerisSegment;
import com.liferay.meris.MerisSegmentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class AssetCategoryMerisSegmentManagerTest {

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

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				"Sports Fan", serviceContext);

		AssetCategory soccerAssetCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				"Soccer", assetVocabulary.getVocabularyId(), serviceContext);
		AssetCategory tennisAssetCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				"Tennis", assetVocabulary.getVocabularyId(), serviceContext);

		_user = UserTestUtil.addUser();

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			_user.getModelClassName(), _user.getUserId());

		AssetEntryAssetCategoryRelLocalServiceUtil.
			addAssetEntryAssetCategoryRel(
				assetEntry.getEntryId(), soccerAssetCategory.getCategoryId());
		AssetEntryAssetCategoryRelLocalServiceUtil.
			addAssetEntryAssetCategoryRel(
				assetEntry.getEntryId(), tennisAssetCategory.getCategoryId());

		_merisProfileId = String.valueOf(_user.getUserId());
		_merisScopeId = String.valueOf(_group.getGroupId());
		_merisSegmentId = String.valueOf(assetVocabulary.getVocabularyId());
	}

	@Test
	public void testGetMerisProfile() {
		Assert.assertNotNull(
			"Meris profile was not found",
			_merisSegmentManager.getMerisProfile(_merisProfileId));
	}

	@Test
	public void testGetMerisProfileMerisSegments() throws Exception {
		Comparator<MerisSegment> merisSegmentNameComparator =
			Comparator.comparing(s -> s.getName(Locale.getDefault()));

		List<MerisSegment> merisSegments =
			_merisSegmentManager.getMerisSegments(
				_merisScopeId, _merisProfileId, _merisSegmentId,
				new HashMap<>(), 0, 1, merisSegmentNameComparator);

		Assert.assertFalse(
			"Meris profile does not contain a meris segment",
			merisSegments.isEmpty());
	}

	@Test
	public void testGetMerisProfiles() throws Exception {
		List merisProfiles = _merisSegmentManager.getMerisProfiles(
			_merisSegmentId, new HashMap<>(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertFalse(
			"Meris segment does not contain a meris profile",
			merisProfiles.isEmpty());
	}

	@Test
	public void testGetMerisSegment() {
		MerisSegment merisSegment = _merisSegmentManager.getMerisSegment(
			_merisSegmentId);

		Assert.assertNotNull("Meris segment was not found", merisSegment);

		Comparator<MerisRule> merisRuleNameComparator = Comparator.comparing(
			s -> s.getName(Locale.getDefault()));

		List<MerisRule> merisRules = merisSegment.getMerisRules(
			0, 1, merisRuleNameComparator);

		Assert.assertFalse(
			"Meris segment does not contain a meris rule",
			merisRules.isEmpty());
	}

	@Test
	public void testGetMerisSegments() {
		Comparator<MerisSegment> merisSegmentNameComparator =
			Comparator.comparing(s -> s.getName(Locale.getDefault()));

		List<MerisSegment> merisSegments =
			_merisSegmentManager.getMerisSegments(
				_merisScopeId, 0, 1, merisSegmentNameComparator);

		Assert.assertFalse(
			"No meris segments were found", merisSegments.isEmpty());
	}

	@Test
	public void testMatches() {
		Assert.assertTrue(
			"Meris profile does not match the meris segment",
			_merisSegmentManager.matches(
				_merisProfileId, _merisSegmentId, new HashMap<>()));
	}

	@Inject
	private static MerisSegmentManager _merisSegmentManager;

	@DeleteAfterTestRun
	private Group _group;

	private String _merisProfileId;
	private String _merisScopeId;
	private String _merisSegmentId;

	@DeleteAfterTestRun
	private User _user;

}