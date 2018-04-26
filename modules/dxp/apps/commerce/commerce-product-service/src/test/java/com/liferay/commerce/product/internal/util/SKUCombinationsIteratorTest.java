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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionRelImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelImpl;
import com.liferay.portal.configuration.ConfigurationFactoryImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Luca Pellizzon
 */
public class SKUCombinationsIteratorTest {

	@Before
	public void setUp() {
		ConfigurationFactoryUtil.setConfigurationFactory(
			new ConfigurationFactoryImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testSKUCombinationsIterator() throws Exception {
		int optionsAndValuesCount = 2;

		Map<CPDefinitionOptionRel, CPDefinitionOptionValueRel[]>
			combinationGeneratorMap = new HashMap<>();

		CPDefinition cpDefinition = _createCPDefinition();

		_prepareData(
			optionsAndValuesCount, combinationGeneratorMap, cpDefinition);

		Assert.assertEquals(
			combinationGeneratorMap.toString(), optionsAndValuesCount,
			combinationGeneratorMap.size());

		Iterator<CPDefinitionOptionValueRel[]> iterator =
			new SKUCombinationsIterator(combinationGeneratorMap);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		while (iterator.hasNext()) {
			CPDefinitionOptionValueRel[]
				cpDefinitionOptionValueRels = iterator.next();

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionValueRels) {

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				JSONArray valueJSONArray = JSONFactoryUtil.createJSONArray();

				valueJSONArray.put(String.valueOf(cpDefinitionOptionValueRel.
					getCPDefinitionOptionValueRelId()));

				jsonObject.put(
					"key",
					cpDefinitionOptionValueRel.getCPDefinitionOptionRelId());
				jsonObject.put("value", valueJSONArray);

				jsonArray.put(jsonObject);
			}
		}

		double expectedResult = Math.pow(
			optionsAndValuesCount, optionsAndValuesCount + 1);

		Assert.assertEquals(expectedResult, jsonArray.length(), 0.01);
	}

	private CPDefinition _createCPDefinition() {
		CPDefinition cpDefinition = new CPDefinitionImpl();

		cpDefinition.setCPDefinitionId(111);

		Map<Locale, String> map = Collections.singletonMap(
			Locale.US, "Title-Description");

		cpDefinition.setTitleMap(map);
		cpDefinition.setDescriptionMap(map);

		return cpDefinition;
	}

	private CPDefinitionOptionRel _createCPDefinitionOptionRel(
		CPDefinition cpDefinition) {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			new CPDefinitionOptionRelImpl();

		Date now = new Date();

		cpDefinitionOptionRel.setCPDefinitionOptionRelId(
			RandomTestUtil.randomLong());
		cpDefinitionOptionRel.setGroupId(_GROUP_ID);
		cpDefinitionOptionRel.setCompanyId(_COMPANY_ID);
		cpDefinitionOptionRel.setUserId(_USER_ID);
		cpDefinitionOptionRel.setUserName(_USER_NAME);
		cpDefinitionOptionRel.setCreateDate(now);
		cpDefinitionOptionRel.setModifiedDate(now);
		cpDefinitionOptionRel.setCPDefinitionId(
			cpDefinition.getCPDefinitionId());
		cpDefinitionOptionRel.setTitle(RandomTestUtil.randomString());
		cpDefinitionOptionRel.setDescription(RandomTestUtil.randomString());
		cpDefinitionOptionRel.setSkuContributor(true);

		return cpDefinitionOptionRel;
	}

	private CPDefinitionOptionValueRel _createCPDefinitionOptionValueRel(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			new CPDefinitionOptionValueRelImpl();

		Date now = new Date();

		cpDefinitionOptionValueRel.setCPDefinitionOptionValueRelId(
			RandomTestUtil.randomLong());
		cpDefinitionOptionValueRel.setGroupId(_GROUP_ID);
		cpDefinitionOptionValueRel.setCompanyId(_COMPANY_ID);
		cpDefinitionOptionValueRel.setUserId(_USER_ID);
		cpDefinitionOptionValueRel.setUserName(_USER_NAME);
		cpDefinitionOptionValueRel.setCreateDate(now);
		cpDefinitionOptionValueRel.setModifiedDate(now);
		cpDefinitionOptionValueRel.setCPDefinitionOptionRelId(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId());
		cpDefinitionOptionValueRel.setTitle(RandomTestUtil.randomString());
		cpDefinitionOptionValueRel.setKey(RandomTestUtil.randomString());

		return cpDefinitionOptionValueRel;
	}

	private void _prepareData(
		int optionsAndValuesCount, Map<CPDefinitionOptionRel,
			CPDefinitionOptionValueRel[]> combinationGeneratorMap,
		CPDefinition cpDefinition) {

		for (int i = 0; i < optionsAndValuesCount; i++) {
			CPDefinitionOptionValueRel[] cpDefinitionOptionValueRelArray =
				new CPDefinitionOptionValueRelImpl[optionsAndValuesCount];

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_createCPDefinitionOptionRel(cpDefinition);

			for (int j = 0; j < optionsAndValuesCount; j++) {
				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_createCPDefinitionOptionValueRel(cpDefinitionOptionRel);

				cpDefinitionOptionValueRelArray[j] = cpDefinitionOptionValueRel;
			}

			combinationGeneratorMap.put(
				cpDefinitionOptionRel, cpDefinitionOptionValueRelArray);
		}
	}

	private static final long _COMPANY_ID = 222;

	private static final long _GROUP_ID = 111;

	private static final long _USER_ID = 123456789;

	private static final String _USER_NAME = "UserName";

}