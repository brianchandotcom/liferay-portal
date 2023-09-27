/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.reader;

import com.liferay.batch.engine.model.impl.BatchEngineImportTaskImpl;
import com.liferay.portal.kernel.util.MapUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;

/**
 * @author Ivica Cardic
 */
public abstract class BaseBatchEngineImportTaskItemReaderImplTestCase {

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

		Item item = BatchEngineImportTaskItemReaderUtil.convertValue(
			new BatchEngineImportTaskImpl(), Item.class,
			BatchEngineImportTaskItemReaderUtil.mapFieldNames(
				fieldNameMappingMap, fieldNameValueMap),
			Arrays.asList(
				(batchEngineImportTask, extendedProperties, item1) -> {
					if (MapUtil.isNotEmpty(extendedProperties)) {
						throw new NoSuchFieldException(
							String.valueOf(extendedProperties.keySet()));
					}
				}));

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
		"createDate", "description", "id", "name_i18n_en", "name_i18n_hr"
	};

	protected Date createDate;
	protected String createDateString;

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

}