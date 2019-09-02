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

import com.liferay.talend.avro.exception.ConverterException;
import com.liferay.talend.common.oas.constants.OASConstants;

import org.apache.avro.generic.IndexedRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class JsonObjectIndexedRecordConverterTest extends BaseConverterTest {

	@Test
	public void testToIndexedRecordIfDateTimeStringPropertyPresent() {
		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			getJsonObjectIndexedRecordConverter(
				"/v1.0/attachments/{id}", OASConstants.OPERATION_GET,
				readObject("openapi.json"));

		IndexedRecord indexedRecord =
			jsonObjectIndexedRecordConverter.toIndexedRecord(
				readObject("attachment_content.json"));

		Assert.assertNotNull(
			"Attachment is converted to indexed record", indexedRecord);

		Object displayDate = indexedRecord.get(1);

		Assert.assertEquals(
			"Display date field type", Long.class, displayDate.getClass());

		Assert.assertEquals(
			"Display date field value", 1320144300000L, displayDate);
	}

	@Test(expected = ConverterException.class)
	public void testToIndexedRecordWithInvalidDateProperty() {
		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			getJsonObjectIndexedRecordConverter(
				"/v1.0/catalogs/{siteId}/products", OASConstants.OPERATION_GET,
				readObject("openapi.json"));

		jsonObjectIndexedRecordConverter.toIndexedRecord(
			readObject("attachment_content_invalid_date_property.json"));
	}

	@Test(expected = ConverterException.class)
	public void testToIndexedRecordWithMissingRequiredProperty() {
		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			getJsonObjectIndexedRecordConverter(
				"/v1.0/catalogs/{siteId}/products", OASConstants.OPERATION_GET,
				readObject("openapi.json"));

		jsonObjectIndexedRecordConverter.toIndexedRecord(
			readObject("product_content_missing_required_property.json"));
	}

	@Test
	public void testToIndexedRecordWithSimpleTypeProperties() {
		JsonObjectIndexedRecordConverter jsonObjectIndexedRecordConverter =
			getJsonObjectIndexedRecordConverter(
				"/v1.0/catalogs/{siteId}/products", OASConstants.OPERATION_GET,
				readObject("openapi.json"));

		IndexedRecord indexedRecord =
			jsonObjectIndexedRecordConverter.toIndexedRecord(
				readObject("product_content.json"));

		Assert.assertNotNull(
			"Product is converted to indexed record", indexedRecord);

		Assert.assertEquals("SKU field value", "3 pcs", indexedRecord.get(13));

		Object id = indexedRecord.get(17);

		Assert.assertEquals("ID field type", Long.class, id.getClass());

		Assert.assertEquals("ID field value", Long.valueOf(19770709), id);

		Object productId = indexedRecord.get(24);

		Assert.assertEquals(
			"Product ID field type", Long.class, productId.getClass());

		Assert.assertEquals(
			"Product ID field value", Long.valueOf(20111101), productId);
	}

}