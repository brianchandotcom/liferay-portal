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

package com.liferay.dynamic.data.mapping.data.provider.internal.rest;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormInstanceFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rafael Praxedes
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class DDMRESTDataProviderTest {

	@Before
	public void setUp() throws Exception {
		setUpLanguageUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testBuildURL() {
		String url = _ddmRESTDataProvider.buildURL(
			createDDMDataProviderRequest(),
			createDDMRESTDataProviderSettings());

		Assert.assertEquals(
			"http://someservice.com/api/countries/1/regions", url);
	}

	@Test
	public void testGetPathParameters() {
		Map<String, String> pathParameters =
			_ddmRESTDataProvider.getPathParameters(
				createDDMDataProviderRequest(),
				createDDMRESTDataProviderSettings());

		Assert.assertEquals(
			pathParameters.toString(), 1, pathParameters.size());
		Assert.assertEquals("1", pathParameters.get("countryId"));
	}

	@Test
	public void testGetQueryParameters() {
		Map<String, String> queryParameters =
			_ddmRESTDataProvider.getQueryParameters(
				createDDMDataProviderRequest(),
				createDDMRESTDataProviderSettings());

		Assert.assertEquals(
			queryParameters.toString(), 1, queryParameters.size());
		Assert.assertEquals("Region", queryParameters.get("regionName"));
	}

	protected DDMDataProviderRequest createDDMDataProviderRequest() {
		DDMDataProviderRequest ddmDataProviderRequest =
			new DDMDataProviderRequest(StringPool.BLANK, null);

		ddmDataProviderRequest.queryString("countryId", "1");

		ddmDataProviderRequest.queryString("regionName", "Region");

		return ddmDataProviderRequest;
	}

	protected DDMRESTDataProviderSettings createDDMRESTDataProviderSettings() {
		DDMForm ddmForm = DDMFormFactory.create(
			DDMRESTDataProviderSettings.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", StringPool.BLANK));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "1234"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url",
				"http://someservice.com/api/countries/{countryId}/regions"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "Joe"));

		DDMFormFieldValue inputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(inputParameters);

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName", "Country Id"));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterPath", "countryId"));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType", "[\"number\"]"));

		inputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(inputParameters);

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName", "Region Name"));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterPath", "regionName"));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType", "[\"text\"]"));

		return DDMFormInstanceFactory.create(
			DDMRESTDataProviderSettings.class, ddmFormValues);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = PowerMockito.mock(Language.class);

		languageUtil.setLanguage(language);
	}

	protected void setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private final DDMRESTDataProvider _ddmRESTDataProvider =
		new DDMRESTDataProvider();

}