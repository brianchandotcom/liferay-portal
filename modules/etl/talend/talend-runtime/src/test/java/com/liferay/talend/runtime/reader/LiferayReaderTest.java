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

package com.liferay.talend.runtime.reader;

import com.liferay.talend.BaseTest;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.resource.LiferayInputResourceProperties;
import com.liferay.talend.runtime.LiferayFixedResponseContentSource;
import com.liferay.talend.tliferayinput.TLiferayInputProperties;
import com.liferay.talend.tliferayoutput.Action;

import java.util.Arrays;
import java.util.NoSuchElementException;

import javax.json.JsonValue;

import org.junit.Assert;
import org.junit.Test;

import org.talend.components.common.SchemaProperties;

/**
 * @author Igor Beslic
 */
public class LiferayReaderTest extends BaseTest {

	@Test(expected = NoSuchElementException.class)
	public void testNoSuchElementException() throws Exception {
		String endpoint =
			"/v1.0/taxonomy-vocabularies/{id}/taxonomy-categories";

		LiferayInputReader liferayInputReader = new LiferayInputReader(
			null,
			new LiferayFixedResponseContentSource(
				readObject("page_content.json")),
			_getTLiferayInputProperties(
				Action.Unavailable, _OAS_URL, endpoint));

		liferayInputReader.start();

		liferayInputReader.advance();

		liferayInputReader.getCurrentJsonValue();
	}

	@Test
	public void testPaging() throws Exception {
		String endpoint = "/v1.0/page-content/{id}/taxonomy-categories";

		LiferayInputReader liferayInputReader = new LiferayInputReader(
			null, new LiferayFixedResponseContentSource(),
			_getTLiferayInputProperties(
				Action.Unavailable, _OAS_URL, endpoint));

		liferayInputReader.start();

		Assert.assertTrue(
			"Liferay input reader advanced to next page",
			liferayInputReader.advance());

		Assert.assertNotNull(
			"Current JSON value", liferayInputReader.getCurrentJsonValue());

		Assert.assertFalse(
			"Liferay input reader advanced to next page",
			liferayInputReader.advance());
	}

	@Test
	public void testStartIfEmptyPageReturned() throws Exception {
		String endpoint =
			"/v1.0/taxonomy-vocabularies/{id}/taxonomy-categories";

		LiferayInputReader liferayInputReader = new LiferayInputReader(
			null,
			new LiferayFixedResponseContentSource(
				readObject("page_no_content.json")),
			_getTLiferayInputProperties(
				Action.Unavailable, _OAS_URL, endpoint));

		Assert.assertFalse(
			"Liferay input reader must not be initialized and advanced",
			liferayInputReader.start());
	}

	@Test
	public void testStartIfPageReturned() throws Exception {
		String endpoint =
			"/v1.0/taxonomy-vocabularies/{id}/taxonomy-categories";

		LiferayInputReader liferayInputReader = new LiferayInputReader(
			null,
			new LiferayFixedResponseContentSource(
				readObject("page_content.json")),
			_getTLiferayInputProperties(
				Action.Unavailable, _OAS_URL, endpoint));

		Assert.assertTrue(
			"Liferay input reader must be initialized and advanced",
			liferayInputReader.start());

		JsonValue currentJsonValue = liferayInputReader.getCurrentJsonValue();

		Assert.assertNotNull("Current JSON value", currentJsonValue);

		Assert.assertEquals(
			"Subsequent reads with no advancing must return same JSON value",
			currentJsonValue, liferayInputReader.getCurrentJsonValue());

		Assert.assertFalse(
			"Advance must not advance reader to next record",
			liferayInputReader.advance());
	}

	private TLiferayInputProperties _getTLiferayInputProperties(
		Action action, String apiSpecURL, String endpoint) {

		TLiferayInputProperties testLiferayInputProperties =
			new TLiferayInputProperties("testLiferayInputProperties");

		testLiferayInputProperties.init();

		LiferayConnectionProperties liferayConnectionProperties =
			new LiferayConnectionProperties("connection");

		liferayConnectionProperties.apiSpecURL.setValue(apiSpecURL);

		LiferayInputResourceProperties testLiferayInputResourceProperties =
			new LiferayInputResourceProperties("resource");

		testLiferayInputResourceProperties.connection =
			liferayConnectionProperties;
		testLiferayInputResourceProperties.endpoint.setValue(endpoint);
		testLiferayInputResourceProperties.operations.setValue(action);
		testLiferayInputResourceProperties.parametersTable.columnName.setValue(
			Arrays.asList("id"));
		testLiferayInputResourceProperties.parametersTable.typeColumnName.
			setValue(Arrays.asList("path"));
		testLiferayInputResourceProperties.parametersTable.valueColumnName.
			setValue(Arrays.asList("1234"));

		testLiferayInputProperties.connection = liferayConnectionProperties;

		testLiferayInputProperties.resource =
			testLiferayInputResourceProperties;
		testLiferayInputProperties.setSchema(SchemaProperties.EMPTY_SCHEMA);

		return testLiferayInputProperties;
	}

	private static final String _OAS_URL =
		"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0" +
			"/openapi.json";

}