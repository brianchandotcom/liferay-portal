/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class AssetVocabularyUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Map<Locale, String> titleMap = HashMapBuilder.put(
			_LOCALE, _TITLE
		).build();

		_vocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(), _TITLE, titleMap,
			null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		_companyGroup = company.getGroup();

		_companyVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), _companyGroup.getGroupId(), _TITLE,
			titleMap, null, null,
			ServiceContextTestUtil.getServiceContext(
				_companyGroup.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testGetUnambiguousVocabularyTitleWithAmbiguity()
		throws Exception {

		List<AssetVocabulary> vocabularies = new ArrayList<>();

		vocabularies.add(_companyVocabulary);
		vocabularies.add(_vocabulary);

		String unambiguousCompanyVocabularyTitle =
			_companyVocabulary.getUnambiguousTitle(
				vocabularies, _group.getGroupId(), _LOCALE);

		Assert.assertTrue(
			unambiguousCompanyVocabularyTitle,
			unambiguousCompanyVocabularyTitle.contains(
				_companyGroup.getDescriptiveName(_LOCALE)));

		String unambiguousVocabularyTitle = _vocabulary.getUnambiguousTitle(
			vocabularies, _group.getGroupId(), _LOCALE);

		Assert.assertEquals(_TITLE, unambiguousVocabularyTitle);
	}

	@Test
	public void testGetUnambiguousVocabularyTitleWithoutAmbiguity()
		throws Exception {

		String unambiguousCompanyVocabularyTitle =
			_companyVocabulary.getUnambiguousTitle(
				ListUtil.fromArray(_companyVocabulary), _group.getGroupId(),
				_LOCALE);

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