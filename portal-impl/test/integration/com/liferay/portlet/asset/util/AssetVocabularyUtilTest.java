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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetVocabularyUtilTest {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(_LOCALE, _TITLE);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_vocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), _TITLE, titleMap, null, null,
			serviceContext);

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		_companyGroup = company.getGroup();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_companyGroup.getGroupId(), TestPropsValues.getUserId());

		_companyVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), _TITLE, titleMap, null, null,
			serviceContext);
	}

	@Test
	public void testGetUnambiguousVocabularyTitleWithAmbiguity()
		throws Exception {

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

		vocabularies.add(_companyVocabulary);
		vocabularies.add(_vocabulary);

		String unambiguousCompanyVocabularyTitle =
			_companyVocabulary.getUnambiguousTitle(
				vocabularies, _group.getGroupId(), _LOCALE);

		Assert.assertTrue(
			unambiguousCompanyVocabularyTitle.contains(
				_companyGroup.getDescriptiveName(_LOCALE)));

		String unambiguousVocabularyTitle = _vocabulary.getUnambiguousTitle(
			vocabularies, _group.getGroupId(), _LOCALE);

		Assert.assertEquals(_TITLE, unambiguousVocabularyTitle);
	}

	@Test
	public void testGetUnambiguousVocabularyTitleWithoutAmbiguity()
		throws Exception {

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

		vocabularies.add(_companyVocabulary);

		String unambiguousCompanyVocabularyTitle =
			_companyVocabulary.getUnambiguousTitle(
				vocabularies, _group.getGroupId(), _LOCALE);

		Assert.assertEquals(_TITLE, unambiguousCompanyVocabularyTitle);
	}

	private static final Locale _LOCALE = LocaleUtil.US;

	private static final String _TITLE = "Test Vocabulary";

	private Group _companyGroup;

	@DeleteAfterTestRun
	private AssetVocabulary _companyVocabulary;

	@DeleteAfterTestRun
	private Group _group;

	private AssetVocabulary _vocabulary;

}