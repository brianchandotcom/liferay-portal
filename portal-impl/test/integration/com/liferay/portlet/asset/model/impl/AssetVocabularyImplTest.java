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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author José Manuel Navarro
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetVocabularyImplTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testHasMoreThanOneCategorySelected() throws Exception {
		AssetVocabulary vocabulary1 = addVocabulary(1, true);

		AssetCategory category11 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());
		AssetCategory category12 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());

		Assert.assertFalse(
			vocabulary1.hasMoreThanOneCategorySelected(new long[0]));
		Assert.assertFalse(
			vocabulary1.hasMoreThanOneCategorySelected(
				new long[] {category11.getCategoryId()}));
		Assert.assertTrue(
			vocabulary1.hasMoreThanOneCategorySelected(
				new long[] {
					category11.getCategoryId(), category12.getCategoryId()
				}));

		AssetVocabulary vocabulary2 = addVocabulary(2, true);

		AssetCategory category21 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());
		AssetCategory category22 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());

		Assert.assertFalse(
			vocabulary1.hasMoreThanOneCategorySelected(
				new long[] {
					category21.getCategoryId(), category22.getCategoryId()
				}));
	}

	@Test
	public void testIsAssociatedToAssetRendererFactory() throws Exception {
		AssetVocabulary vocabulary = addVocabulary(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(vocabulary.isAssociatedToAssetRendererFactory(1));

		vocabulary = addVocabulary(1, true);

		Assert.assertTrue(vocabulary.isAssociatedToAssetRendererFactory(1));
		Assert.assertFalse(vocabulary.isAssociatedToAssetRendererFactory(2));
	}

	@Test
	public void testIsMissingRequiredCategory() throws Exception {
		AssetVocabulary vocabulary = addVocabulary(1, false);

		AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary.getVocabularyId());

		Assert.assertFalse(
			vocabulary.isMissingRequiredCategory(1, new long[] {1}));

		vocabulary = addVocabulary(1, true);

		Assert.assertTrue(
			vocabulary.isMissingRequiredCategory(1, new long[] {1}));
		Assert.assertFalse(
			vocabulary.isMissingRequiredCategory(2, new long[0]));

		AssetCategory category = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary.getVocabularyId());

		Assert.assertTrue(
			vocabulary.isMissingRequiredCategory(1, new long[] {1}));
		Assert.assertFalse(
			vocabulary.isMissingRequiredCategory(
				1, new long[] {category.getCategoryId()}));
	}

	@Test
	public void testIsRequired() throws Exception {
		AssetVocabulary vocabulary = addVocabulary(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS, false);

		Assert.assertFalse(vocabulary.isRequired(1));
		Assert.assertFalse(vocabulary.isRequired(2));

		vocabulary = addVocabulary(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(vocabulary.isRequired(1));
		Assert.assertTrue(vocabulary.isRequired(2));

		vocabulary = addVocabulary(1, false);

		Assert.assertFalse(vocabulary.isRequired(1));
		Assert.assertFalse(vocabulary.isRequired(2));

		vocabulary = addVocabulary(1, true);

		Assert.assertTrue(vocabulary.isRequired(1));
		Assert.assertFalse(vocabulary.isRequired(2));
	}

	protected AssetVocabulary addVocabulary(long classNameId, boolean required)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(locale, RandomTestUtil.randomString());

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setClassNameIds(
			new long[] {classNameId}, new boolean[] {required});
		vocabularySettingsHelper.setMultiValued(true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary vocabulary = AssetVocabularyServiceUtil.addVocabulary(
			RandomTestUtil.randomString(), titleMap, descriptionMap,
			vocabularySettingsHelper.toString(), serviceContext);

		Assert.assertNotNull(vocabulary);

		return vocabulary;
	}

	private Group _group;

}