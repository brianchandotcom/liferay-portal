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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONIncludesManagerUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.LocalizationImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class JSONSerializerTest {

	@Before
	public void setUp() throws Exception {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		JSONIncludesManagerUtil jsonIncludesManagerUtil =
			new JSONIncludesManagerUtil();

		jsonIncludesManagerUtil.setJSONIncludesManager(
			new JSONIncludesManagerImpl());

		LocalizationUtil localizationUtil = new LocalizationUtil();

		localizationUtil.setLocalization(new LocalizationImpl());
	}

	@Test
	public void testSerializeDDMStructureImpl() {
		DDMStructureImpl ddms = new DDMStructureImpl();

		ddms.setXsd("value");

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("*.class");

		String json = jsonSerializer.serialize(ddms);

		Assert.assertTrue(json.contains("\"xsd\":\"value\""));
	}

	@Test
	public void testSerializeHits() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		Hits hits = new HitsImpl();

		String json = jsonSerializer.serialize(hits);

		json = json.replace(StringPool.SPACE, StringPool.BLANK);

		Assert.assertTrue(json.contains("\"docs\":null"));
		Assert.assertFalse(json.contains("\"query\""));
		Assert.assertTrue(json.contains("\"queryTerms\":null"));
		Assert.assertTrue(json.contains("\"scores\":"));
		Assert.assertTrue(json.contains("\"snippets\":["));
		Assert.assertTrue(json.contains("\"start\":0"));
		Assert.assertTrue(json.contains("\"length\":0"));
	}

}