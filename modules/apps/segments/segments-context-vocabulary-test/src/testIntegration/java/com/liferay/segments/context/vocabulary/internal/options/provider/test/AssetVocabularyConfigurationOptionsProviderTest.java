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

package com.liferay.segments.context.vocabulary.internal.options.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class AssetVocabularyConfigurationOptionsProviderTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testGetOption() throws PortalException {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), company.getGroupId(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				null, null, ServiceContextTestUtil.getServiceContext());

		try {
			List<ConfigurationFieldOptionsProvider.Option> options =
				_configurationFieldOptionsProvider.getOptions();

			Stream<ConfigurationFieldOptionsProvider.Option> stream =
				options.stream();

			Assert.assertTrue(
				stream.filter(
					option -> Objects.equals(
						option.getLabel(LocaleUtil.getDefault()),
						assetVocabulary.getTitle(LocaleUtil.getDefault()))
				).findFirst(
				).isPresent());
		}
		finally {
			_assetVocabularyLocalService.deleteVocabulary(
				assetVocabulary.getVocabularyId());
		}
	}

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "(&(configuration.pid=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration)(configuration.field.name=assetVocabulary))"
	)
	private ConfigurationFieldOptionsProvider
		_configurationFieldOptionsProvider;

}