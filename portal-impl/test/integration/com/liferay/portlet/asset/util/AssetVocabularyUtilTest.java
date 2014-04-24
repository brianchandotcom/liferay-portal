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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetVocabularyUtilTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId(), userId);

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(_LOCALE, _TITLE);

		_vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				userId, _TITLE, titleMap, null, null, serviceContext);

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		_globalGroup = company.getGroup();

		serviceContext = ServiceTestUtil.getServiceContext(
			_globalGroup.getGroupId(), userId);

		_globalVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				userId, _TITLE, titleMap, null, null, serviceContext);
	}

	@Test
	public void testGetUnambiguousVocabularyTitleWithAmbiguity()
		throws Exception {

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

		vocabularies.add(_globalVocabulary);
		vocabularies.add(_vocabulary);

		String unambiguousGlobalVocabularyTitle =
			AssetVocabularyUtil.getUnambiguousVocabularyTitle(
				vocabularies, _globalVocabulary, _group.getGroupId(), _LOCALE);

		Assert.assertTrue(
			unambiguousGlobalVocabularyTitle.contains(
				_globalGroup.getDescriptiveName(_LOCALE)));

		String unambiguousVocabularyTitle =
			AssetVocabularyUtil.getUnambiguousVocabularyTitle(
				vocabularies, _vocabulary, _group.getGroupId(), _LOCALE);

		Assert.assertEquals(_TITLE, unambiguousVocabularyTitle);
	}

	@Test
	public void testGetUnambiguousVocabularyTitleWithoutAmbiguity()
		throws Exception {

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

		vocabularies.add(_globalVocabulary);

		String unambiguousGlobalVocabularyTitle =
			AssetVocabularyUtil.getUnambiguousVocabularyTitle(
				vocabularies, _globalVocabulary, _group.getGroupId(), _LOCALE);

		Assert.assertEquals(_TITLE, unambiguousGlobalVocabularyTitle);
	}

	private static final Locale _LOCALE = LocaleUtil.US;

	private static final String _TITLE = "Test Vocabulary";

	private Group _globalGroup;
	private AssetVocabulary _globalVocabulary;
	private Group _group;
	private AssetVocabulary _vocabulary;

}