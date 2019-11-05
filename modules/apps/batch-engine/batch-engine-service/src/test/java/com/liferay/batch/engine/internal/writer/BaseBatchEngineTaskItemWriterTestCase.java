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

package com.liferay.batch.engine.internal.writer;

import com.fasterxml.jackson.annotation.JsonFilter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.lang.reflect.Field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;

/**
 * @author Ivica Cardic
 */
public abstract class BaseBatchEngineTaskItemWriterTestCase {

	@Before
	public void setUp() {
		_createDate = new Date();
	}

	public static class BaseItem {

		public Long getId() {
			return _id;
		}

		public void setId(Long id) {
			_id = id;
		}

		private Long _id;

	}

	@JsonFilter("Liferay.Vulcan")
	public static class Item extends BaseItem {

		public Date getCreateDate() {
			return _createDate;
		}

		public String getDescription() {
			return _description;
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

		public void setName(Map<String, String> name) {
			_name = name;
		}

		private Date _createDate;
		private String _description;
		private Map<String, String> _name;

	}

	protected Item[][] getItemGroups() {
		Item[][] itemBatches = new Item[3][];

		for (int i = 0; i < itemBatches.length; i++) {
			Item[] items = new Item[6];

			itemBatches[i] = items;

			for (int j = 0; j < 6; j++) {
				Item item = new Item();

				if (j != 1) {
					item.setCreateDate(_createDate);
				}

				if (j != 2) {
					item.setDescription("description" + i + j);
				}

				item.setId((long)(i + j));

				Map<String, String> name = HashMapBuilder.put(
					"en", "sample name" + i + j
				).build();

				if (j == 2) {
					name.put("en", null);
				}

				if (j != 3) {
					name.put("hr", "naziv" + i + j);
				}
				else {
					name.put("hr", null);
				}

				item.setName(name);

				items[j] = item;
			}
		}

		return itemBatches;
	}

	protected String getItemJSONContent(List<String> fieldNames, Item item) {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (fieldNames.contains("createDate")) {
			sb.append("\"createDate\": ");
			sb.append(_formatJSONValue(item.getCreateDate()));
			sb.append(StringPool.COMMA);
		}

		if (fieldNames.contains("description")) {
			sb.append("\"description\": ");
			sb.append(_formatJSONValue(item.getDescription()));
			sb.append(StringPool.COMMA);
		}

		if (fieldNames.contains("id")) {
			sb.append("\"id\": ");
			sb.append(_formatJSONValue(item.getId()));
			sb.append(StringPool.COMMA);
		}

		if (fieldNames.contains("name")) {
			Map<String, String> name = item.getName();

			sb.append("\"name\": {");

			for (Map.Entry<String, String> entry : name.entrySet()) {
				sb.append("\"");
				sb.append(entry.getKey());
				sb.append("\": ");
				sb.append(_formatJSONValue(entry.getValue()));
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			sb.append("}");
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}");

		return sb.toString();
	}

	protected List<Item> getItems() {
		List<Item> items = new ArrayList<>();

		for (Item[] itemGroup : getItemGroups()) {
			Collections.addAll(items, itemGroup);
		}

		return items;
	}

	protected static final List<String> columnFieldNames = Arrays.asList(
		"createDate", "description", "id", "name_en", "name_hr");
	protected static final DateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	protected static Map<String, Field> fieldMap = ItemClassIndexUtil.index(
		Item.class);
	protected static final List<String> jsonFieldNames = Arrays.asList(
		"createDate", "description", "id", "name");

	private String _formatJSONValue(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Date) {
			return "\"" + dateFormat.format(value) + "\"";
		}

		if (value instanceof String) {
			return "\"" + value + "\"";
		}

		return value.toString();
	}

	private static Date _createDate;

}