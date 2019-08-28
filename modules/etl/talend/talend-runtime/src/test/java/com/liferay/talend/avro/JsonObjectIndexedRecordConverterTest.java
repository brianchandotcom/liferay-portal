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

package com.liferay.talend.avro;

import com.liferay.talend.BaseTest;
import com.liferay.talend.common.oas.constants.OASConstants;

import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class JsonObjectIndexedRecordConverterTest extends BaseTest {

	@Test
	public void testToIndexedRecordIfDateTimeStringPropertyPresent() {
		String endpoint = "/v1.0/attachments/{id}";

		Schema schema = getSchema(
			endpoint, OASConstants.OPERATION_GET, readObject("openapi.json"));

		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			new JsonObjectIndexedRecordConverter(schema);

		IndexedRecord indexedRecord =
			jsonObjectIndexedRecordConverter.toIndexedRecord(
				readObject("attachmentContent.json"));

		Assert.assertNotNull(
			"attachment is converted to indexed record", indexedRecord);

		Object displayDate = indexedRecord.get(1);

		Assert.assertEquals(
			"displayDate field type", Long.class, displayDate.getClass());

		Assert.assertEquals(
			"displayDate field value", 1320144300000L, displayDate);
	}

	@Test
	public void testToIndexedRecordWithSimpleTypeProperties() {
		String endpoint = "/v1.0/catalogs/{siteId}/products";

		Schema schema = getSchema(
			endpoint, OASConstants.OPERATION_GET, readObject("openapi.json"));

		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			new JsonObjectIndexedRecordConverter(schema);

		IndexedRecord indexedRecord =
			jsonObjectIndexedRecordConverter.toIndexedRecord(
				readObject("productContent.json"));

		Assert.assertNotNull(
			"product is converted to indexed record", indexedRecord);

		Assert.assertEquals("SKU is '3 pcs'", "3 pcs", indexedRecord.get(13));

		Object id = indexedRecord.get(17);

		Assert.assertEquals("ID class is Long", Long.class, id.getClass());

		Assert.assertEquals("ID value is 19770907", Long.valueOf(19770709), id);

		Object productId = indexedRecord.get(24);

		Assert.assertEquals(
			"productId class is long 20111101", Long.class,
			productId.getClass());

		Assert.assertEquals(
			"productId value is 20111101", Long.valueOf(20111101), productId);
	}

}