/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetVocabulary;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
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

	@Test
	public void testLocalizedSiteAddDefaultVocabulary() throws Exception {
		Group group = GroupTestUtil.addGroup();

		LocaleThreadLocal.setSiteDefaultLocale(new Locale("es", "ES"));

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				group.getGroupId());

		Assert.assertEquals(
			PropsValues.ASSET_VOCABULARY_DEFAULT,
			assetVocabulary.getTitle(Locale.US, true));
	}

	@Test
	public void testLocalizedSiteAddLocalizedVocabulary() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Locale enLocale = Locale.US;
		Locale esLocale = new Locale("es", "ES");
		Locale deLocale = new Locale("de", "DE");

		LocaleThreadLocal.setSiteDefaultLocale(esLocale);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		String title = ServiceTestUtil.randomString();

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		String englishTitle = title + "_US";
		String spanishTitle = title + "_ES";

		titleMap.put(enLocale, englishTitle);
		titleMap.put(esLocale, spanishTitle);

		String description = ServiceTestUtil.randomString();

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		String englishDescription = description + "_US";
		String spanishDescription = description + "_ES";

		descriptionMap.put(enLocale, englishDescription);
		descriptionMap.put(esLocale, spanishDescription);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), StringPool.BLANK, titleMap,
				descriptionMap, StringPool.BLANK, serviceContext);

		Assert.assertEquals(spanishTitle, assetVocabulary.getName());

		Assert.assertEquals(
			englishTitle, assetVocabulary.getTitle(enLocale, true));
		Assert.assertEquals(
			spanishTitle, assetVocabulary.getTitle(esLocale, true));
		Assert.assertEquals(
			spanishTitle, assetVocabulary.getTitle(deLocale, true));

		Assert.assertEquals(
			englishDescription, assetVocabulary.getDescription(enLocale, true));
		Assert.assertEquals(
			spanishDescription, assetVocabulary.getDescription(esLocale, true));
		Assert.assertEquals(
			spanishDescription, assetVocabulary.getDescription(deLocale, true));
	}

	@Test
	public void testLocalizedSiteAddVocabulary() throws Exception {
		Group group = GroupTestUtil.addGroup();

		LocaleThreadLocal.setSiteDefaultLocale(new Locale("es", "ES"));

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		String title = ServiceTestUtil.randomString();

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), title, serviceContext);

		Assert.assertEquals(title, assetVocabulary.getTitle(Locale.US, true));

		Assert.assertEquals(title, assetVocabulary.getName());
	}

}