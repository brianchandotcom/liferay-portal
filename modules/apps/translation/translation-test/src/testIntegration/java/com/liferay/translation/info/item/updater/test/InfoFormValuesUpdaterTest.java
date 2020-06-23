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

package com.liferay.translation.info.item.updater.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.translation.exporter.TranslationInfoFormValuesExporter;
import com.liferay.translation.info.item.updater.InfoFormValuesUpdater;
import com.liferay.translation.test.util.TranslationTestUtil;

import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class InfoFormValuesUpdaterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule testRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		User user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpdateArticleFromInfoFormValues() throws Exception {
		Map<Locale, String> contentMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), titleMap,
			descriptionMap, contentMap, LocaleUtil.getSiteDefault(), false,
			true, _serviceContext);

		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoFormValuesImporter.importXLIFF(
				_group.getGroupId(),
				new InfoItemClassPKReference(
					JournalArticle.class.getName(), 122),
				TranslationTestUtil.readFileToInputStream(
					"test-journal-article_122.xlf"));

		JournalArticle journalArticle =
			_infoFormValuesUpdater.updateFromInfoFormValues(
				article, infoItemFieldValues);

		Assert.assertEquals(
			"Este es el titulo", journalArticle.getTitle(LocaleUtil.SPAIN));

		Assert.assertEquals(
			"Este es el resumen",
			journalArticle.getDescription(LocaleUtil.SPAIN));
	}

	@Test
	public void testUpdateArticleFromInfoFormValuesOnlyTitle()
		throws Exception {

		Map<Locale, String> contentMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "Descripcion"
		).put(
			LocaleUtil.US, "description"
		).build();

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), titleMap,
			descriptionMap, contentMap, LocaleUtil.getSiteDefault(), false,
			true, _serviceContext);

		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoFormValuesImporter.importXLIFF(
				_group.getGroupId(),
				new InfoItemClassPKReference(
					JournalArticle.class.getName(), 122),
				TranslationTestUtil.readFileToInputStream(
					"test-journal-article_122_only_title.xlf"));

		JournalArticle journalArticle =
			_infoFormValuesUpdater.updateFromInfoFormValues(
				article, infoItemFieldValues);

		Assert.assertEquals(
			"Este es el titulo", journalArticle.getTitle(LocaleUtil.SPAIN));

		Assert.assertEquals(
			"Descripcion", journalArticle.getDescription(LocaleUtil.SPAIN));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoFormValuesUpdater _infoFormValuesUpdater;

	private PermissionChecker _originalPermissionChecker;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	private ServiceContext _serviceContext;

	@Inject(filter = "content.type=application/xliff+xml")
	private TranslationInfoFormValuesExporter
		_xliffTranslationInfoFormValuesImporter;

}