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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.Language;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class LanguageResourceTest extends BaseLanguageResourceTestCase {

	@Override
	@Test
	public void testGetAssetLibraryLanguagesPage() throws Exception {
		Page<Language> page = languageResource.getAssetLibraryLanguagesPage(
			testGetAssetLibraryLanguagesPage_getAssetLibraryId());

		Assert.assertEquals(
			_getAvailableLocalesSize(
				testGetAssetLibraryLanguagesPage_getAssetLibraryId()),
			page.getTotalCount());
	}

	@Override
	@Test
	public void testGetSiteLanguagesPage() throws Exception {
		Page<Language> page = languageResource.getSiteLanguagesPage(
			testGetSiteLanguagesPage_getSiteId());

		Assert.assertEquals(
			_getAvailableLocalesSize(testGetSiteLanguagesPage_getSiteId()),
			page.getTotalCount());
	}

	@Override
	@Test
	public void testGraphQLGetSiteLanguagesPage() throws Exception {
		Long siteId = testGetSiteLanguagesPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"languages",
			HashMapBuilder.<String, Object>put(
				"siteKey", "\"" + siteId + "\""
			).build(),
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject languagesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/languages");

		Assert.assertEquals(
			_getAvailableLocalesSize(testGetSiteLanguagesPage_getSiteId()),
			languagesJSONObject.get("totalCount"));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	private int _getAvailableLocalesSize(Long siteId) {
		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(siteId);

		return availableLocales.size();
	}

}