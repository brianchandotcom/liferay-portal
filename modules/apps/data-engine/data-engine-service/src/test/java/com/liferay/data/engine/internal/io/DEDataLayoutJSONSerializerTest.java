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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.exception.DEDataLayoutSerializerException;
import com.liferay.data.engine.io.DEDataLayoutSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataLayoutSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.IOException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

import org.json.JSONException;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutJSONSerializerTest extends BaseTestCase {

	@Before
	public void setUp() {
		_deDataLayoutJSONSerializer = new DEDataLayoutJSONSerializer();

		_deDataLayoutJSONSerializer.jsonFactory = new JSONFactoryImpl();
	}

	@Test
	public void testSerialize()
		throws DEDataLayoutSerializerException, IOException, JSONException {

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		DEDataLayoutSerializerApplyRequest deDataLayoutSerializerApplyRequest =
			DEDataLayoutSerializerApplyRequest.Builder.of(deDataLayout);

		DEDataLayoutSerializerApplyResponse
			deDataLayoutSerializerApplyResponse =
				_deDataLayoutJSONSerializer.apply(
					deDataLayoutSerializerApplyRequest);

		JSONAssert.assertEquals(
			read("data-layout-page-serializer.json"),
			deDataLayoutSerializerApplyResponse.getContent(), false);
	}

	@Test(
		expected = DEDataLayoutSerializerException.InvalidPaginationMode.class
	)
	public void testSerializeWithInvalidPaginationMode()
		throws DEDataLayoutSerializerException {

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "DummyPagination", "en_US");

		DEDataLayoutSerializerApplyRequest deDataLayoutSerializerApplyRequest =
			DEDataLayoutSerializerApplyRequest.Builder.of(deDataLayout);

		_deDataLayoutJSONSerializer.apply(deDataLayoutSerializerApplyRequest);
	}

	@Test(
		expected = DEDataLayoutSerializerException.InvalidDefaultLanguageId.class
	)
	public void testSerializeWithoutDefaultLanguageId()
		throws DEDataLayoutSerializerException {

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "wizard", StringPool.BLANK);

		DEDataLayoutSerializerApplyRequest deDataLayoutSerializerApplyRequest =
			DEDataLayoutSerializerApplyRequest.Builder.of(deDataLayout);

		_deDataLayoutJSONSerializer.apply(deDataLayoutSerializerApplyRequest);
	}

	@Test(expected = DEDataLayoutSerializerException.InvalidPageTitle.class)
	public void testSerializeWithoutPageTitle()
		throws DEDataLayoutSerializerException {

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "pagination", "en_US");

		Queue<DEDataLayoutPage> deDataLayoutPages =
			deDataLayout.getDEDataLayoutPages();

		DEDataLayoutPage deDataLayoutPage = deDataLayoutPages.element();

		deDataLayoutPage.setTitle(null);

		DEDataLayoutSerializerApplyRequest deDataLayoutSerializerApplyRequest =
			DEDataLayoutSerializerApplyRequest.Builder.of(deDataLayout);

		_deDataLayoutJSONSerializer.apply(deDataLayoutSerializerApplyRequest);
	}

	@Test(
		expected = DEDataLayoutSerializerException.InvalidPaginationMode.class
	)
	public void testSerializeWithoutPaginationMode()
		throws DEDataLayoutSerializerException {

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", StringPool.BLANK, "en_US");

		DEDataLayoutSerializerApplyRequest deDataLayoutSerializerApplyRequest =
			DEDataLayoutSerializerApplyRequest.Builder.of(deDataLayout);

		_deDataLayoutJSONSerializer.apply(deDataLayoutSerializerApplyRequest);
	}

	private DEDataLayout _createDEDataLayout(
		String name, String description, String paginationMode,
		String languageId) {

		DEDataLayoutColumn deDataLayoutColumn = _createDEDataLayoutColumn(
			12, "field");

		Queue<DEDataLayoutColumn> deDataLayoutColumns = new ArrayDeque<>();

		deDataLayoutColumns.add(deDataLayoutColumn);

		DEDataLayoutRow deDataLayoutRow = _createDEDataLayoutRow(
			deDataLayoutColumns);

		Queue<DEDataLayoutRow> deDataLayoutRows = new ArrayDeque<>();

		deDataLayoutRows.add(deDataLayoutRow);

		DEDataLayoutPage deDataLayoutPage = _createDEDataLayoutPage(
			StringPool.BLANK, "Page", deDataLayoutRows);

		Queue<DEDataLayoutPage> deDataLayoutPages = new ArrayDeque<>();

		deDataLayoutPages.add(deDataLayoutPage);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, description);

		DEDataLayout deDataLayout = new DEDataLayout();

		deDataLayout.setName(nameMap);
		deDataLayout.setDescription(descriptionMap);
		deDataLayout.setDEDataLayoutPages(deDataLayoutPages);
		deDataLayout.setPaginationMode(paginationMode);
		deDataLayout.setDefaultLanguageId(languageId);

		return deDataLayout;
	}

	private DEDataLayoutColumn _createDEDataLayoutColumn(
		int size, String fieldName) {

		DEDataLayoutColumn deDataLayoutColumn = new DEDataLayoutColumn();

		deDataLayoutColumn.setColumnSize(size);

		List<String> fieldsName = new ArrayList<>();

		fieldsName.add(fieldName);

		deDataLayoutColumn.setFieldsName(fieldsName);

		return deDataLayoutColumn;
	}

	private DEDataLayoutPage _createDEDataLayoutPage(
		String description, String title,
		Queue<DEDataLayoutRow> deDataLayoutRows) {

		DEDataLayoutPage deDataLayoutPage = new DEDataLayoutPage();

		Map<String, String> titleMap = new HashMap<>();

		titleMap.put("en_US", title);

		Map<String, String> descriptionMap = new HashMap<>();

		descriptionMap.put("en_US", description);

		deDataLayoutPage.setTitle(titleMap);
		deDataLayoutPage.setDescription(descriptionMap);
		deDataLayoutPage.setDEDataLayoutRows(deDataLayoutRows);

		return deDataLayoutPage;
	}

	private DEDataLayoutRow _createDEDataLayoutRow(
		Queue<DEDataLayoutColumn> deDataLayoutColumns) {

		DEDataLayoutRow deDataLayoutRow = new DEDataLayoutRow();

		deDataLayoutRow.setDEDataLayoutColumns(deDataLayoutColumns);

		return deDataLayoutRow;
	}

	private DEDataLayoutJSONSerializer _deDataLayoutJSONSerializer;

}