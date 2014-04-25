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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.util.AssetTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
@Transactional
public class AssetVocabularyServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_locale = LocaleThreadLocal.getSiteDefaultLocale();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);

		LocaleThreadLocal.setSiteDefaultLocale(_locale);
	}

	@Test
	public void testDeleteVocabularyWithCategoriesShouldDeleteOnCascade()
		throws Exception {

		int initialResourcesActionsCount =
			ResourceActionLocalServiceUtil.getResourceActions(
				AssetVocabulary.class.getName()).size();

		AssetVocabulary vocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory category = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary.getVocabularyId());

		AssetVocabularyLocalServiceUtil.deleteVocabulary(
			vocabulary.getVocabularyId());

		Assert.assertNull(
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
				vocabulary.getVocabularyId()));

		Assert.assertNull(
			AssetCategoryLocalServiceUtil.fetchAssetCategory(
				category.getCategoryId()));

		Assert.assertEquals(
			initialResourcesActionsCount,
			ResourceActionLocalServiceUtil.getResourceActions(
				AssetVocabulary.class.getName()).size());
	}

	@Test
	public void testLocalizedSiteAddDefaultVocabulary() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				_group.getGroupId());

		Assert.assertEquals(
			PropsValues.ASSET_VOCABULARY_DEFAULT,
			vocabulary.getTitle(LocaleUtil.US, true));
	}

	@Test
	public void testLocalizedSiteAddLocalizedVocabulary() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		String title = ServiceTestUtil.randomString();

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(LocaleUtil.US, title + "_US");
		titleMap.put(LocaleUtil.SPAIN, title + "_ES");

		String description = ServiceTestUtil.randomString();

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(LocaleUtil.SPAIN, description + "_ES");
		descriptionMap.put(LocaleUtil.US, description + "_US");

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), StringPool.BLANK, titleMap,
				descriptionMap, StringPool.BLANK, serviceContext);

		Assert.assertEquals(
			titleMap.get(LocaleUtil.SPAIN), vocabulary.getName());
		Assert.assertEquals(
			titleMap.get(LocaleUtil.SPAIN),
			vocabulary.getTitle(LocaleUtil.GERMANY, true));
		Assert.assertEquals(
			titleMap.get(LocaleUtil.SPAIN),
			vocabulary.getTitle(LocaleUtil.SPAIN, true));
		Assert.assertEquals(
			titleMap.get(LocaleUtil.US),
			vocabulary.getTitle(LocaleUtil.US, true));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.SPAIN),
			vocabulary.getDescription(LocaleUtil.GERMANY, true));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.SPAIN),
			vocabulary.getDescription(LocaleUtil.SPAIN, true));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.US),
			vocabulary.getDescription(LocaleUtil.US, true));
	}

	@Test
	public void testLocalizedSiteAddVocabulary() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		String title = ServiceTestUtil.randomString();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), title, serviceContext);

		Assert.assertEquals(title, vocabulary.getTitle(LocaleUtil.US, true));
		Assert.assertEquals(title, vocabulary.getName());
	}

	private Group _group;
	private Locale _locale;

}