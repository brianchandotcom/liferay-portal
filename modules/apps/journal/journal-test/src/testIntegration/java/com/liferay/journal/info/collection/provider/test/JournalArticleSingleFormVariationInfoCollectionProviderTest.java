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

package com.liferay.journal.info.collection.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class JournalArticleSingleFormVariationInfoCollectionProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		Bundle bundle = FrameworkUtil.getBundle(
			JournalArticleSingleFormVariationInfoCollectionProviderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			(Class<InfoCollectionProvider<?>>)
				(Class<?>)InfoCollectionProvider.class,
			new TestSingleFormVariationInfoCollectionProvider(
				_ddmStructure, _group, _journalArticleLocalService),
			null);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetInfoItemFormVariation() {
		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				JournalArticle.class.getName());

		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoCollectionProvider.class, JournalArticle.class.getName());

		Assert.assertTrue(
			infoCollectionProvider instanceof
				SingleFormVariationInfoCollectionProvider);

		SingleFormVariationInfoCollectionProvider
			singleFormVariationInfoCollectionProvider =
				(SingleFormVariationInfoCollectionProvider)
					infoCollectionProvider;

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				_group.getGroupId(),
				singleFormVariationInfoCollectionProvider.
					getFormVariationKey());

		Assert.assertNotNull(infoItemFormVariation);
		Assert.assertNotNull(
			String.valueOf(_ddmStructure.getStructureId()),
			infoItemFormVariation.getKey());
		Assert.assertEquals(
			_ddmStructure.getName(LocaleUtil.getSiteDefault()),
			infoItemFormVariation.getLabel(LocaleUtil.getSiteDefault()));
	}

	@Test
	public void testGetInfoPage() throws Exception {
		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoCollectionProvider.class, JournalArticle.class.getName());

		InfoPage<?> infoPage = infoCollectionProvider.getCollectionInfoPage(
			new CollectionQuery());

		Assert.assertEquals(0, infoPage.getTotalCount());

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortalUtil.getClassNameId(DDMStructure.class),
			_ddmStructure.getStructureId(),
			DDMStructureTestUtil.getSampleStructuredContent(),
			_ddmStructure.getStructureKey(), null, LocaleUtil.getSiteDefault());

		infoPage = infoCollectionProvider.getCollectionInfoPage(
			new CollectionQuery());

		Assert.assertEquals(1, infoPage.getTotalCount());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		infoPage = infoCollectionProvider.getCollectionInfoPage(
			new CollectionQuery());

		Assert.assertEquals(1, infoPage.getTotalCount());
	}

	private DDMStructure _ddmStructure;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	private ServiceRegistration<InfoCollectionProvider<?>> _serviceRegistration;

	private static class TestSingleFormVariationInfoCollectionProvider
		implements SingleFormVariationInfoCollectionProvider<JournalArticle> {

		public TestSingleFormVariationInfoCollectionProvider(
			DDMStructure ddmStructure, Group group,
			JournalArticleLocalService journalArticleLocalService) {

			_ddmStructure = ddmStructure;
			_group = group;
			_journalArticleLocalService = journalArticleLocalService;
		}

		@Override
		public InfoPage<JournalArticle> getCollectionInfoPage(
			CollectionQuery collectionQuery) {

			return InfoPage.of(
				_journalArticleLocalService.getStructureArticles(
					_group.getGroupId(), _ddmStructure.getStructureKey()),
				collectionQuery.getPagination(),
				_journalArticleLocalService.getStructureArticlesCount(
					_group.getGroupId(), _ddmStructure.getStructureKey()));
		}

		@Override
		public String getFormVariationKey() {
			return String.valueOf(_ddmStructure.getStructureId());
		}

		@Override
		public String getLabel(Locale locale) {
			return StringPool.BLANK;
		}

		private final DDMStructure _ddmStructure;
		private final Group _group;
		private final JournalArticleLocalService _journalArticleLocalService;

	}

}