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

package com.liferay.batch.engine.internal.reader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;

/**
 * @author Ivica Cardic
 */
public abstract class BaseBatchEngineTaskItemReaderTestCase {

	@Before
	public void setUp() {
		createDate = new Date();

		createDateString = _dateFormat.format(createDate);
	}

	public static class Item {

		public Date getCreateDate() {
			return _createDate;
		}

		public String getDescription() {
			return _description;
		}

		public Long getId() {
			return _id;
		}

		public Map<String, String> getName() {
			return _name;
		}

		public void setCreateDate(Date createDate) {
			_createDate = createDate;
		}

		public void setDescription(String description) {
			_description = description;
		}

		public void setId(Long id) {
			_id = id;
		}

		public void setName(Map<String, String> name) {
			_name = name;
		}

		private Date _createDate;
		private String _description;
		private Long _id;
		private Map<String, String> _name;

	}

	protected void validate(
			String createDateString, String description, Long id,
			Map<String, String> fieldNameMappingMap,
			Map<String, Object> fieldNameValueMap, Map<String, String> nameMap)
		throws ReflectiveOperationException {

		Item item = BatchEngineTaskItemReaderUtil.convertValue(
			Item.class,
			BatchEngineTaskItemReaderUtil.mapFieldNames(
				fieldNameMappingMap, fieldNameValueMap));

		if (createDateString == null) {
			Assert.assertNull(item.getCreateDate());
		}
		else {
			Assert.assertEquals(
				createDateString, _dateFormat.format(item.getCreateDate()));
		}

		Assert.assertEquals(description, item.getDescription());
		Assert.assertEquals(id, item.getId());
		Assert.assertEquals(nameMap, item.getName());
	}

	protected static final String[] FIELD_NAMES = {
		"createDate", "description", "id", "name_en", "name_hr"
	};

	protected Date createDate;
	protected String createDateString;

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

}