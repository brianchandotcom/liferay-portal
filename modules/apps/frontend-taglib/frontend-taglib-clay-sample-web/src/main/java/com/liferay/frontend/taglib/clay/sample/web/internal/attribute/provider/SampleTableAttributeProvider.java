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

package com.liferay.frontend.taglib.clay.sample.web.internal.attribute.provider;

import com.liferay.frontend.taglib.clay.servlet.taglib.attribute.provider.ClayComponentAttributeProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Field;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Schema;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true,
	property = {
		"clay.component.attribute.provider.key=SampleTableAttributeProvider",
		"service.ranking:Integer=2"
	},
	service = ClayComponentAttributeProvider.class
)
public class SampleTableAttributeProvider
	implements ClayComponentAttributeProvider {

	@Override
	public void getAttributes(Map<String, Object> context) {
		context.put("items", _getItems());
		context.put("schema", _getSchema().toMap());
		context.put("selectable", true);

		String currentTableClasses = GetterUtil.getString(
			context.get("tableClasses"));

		currentTableClasses += " service-ranking-2";

		context.put("tableClasses", currentTableClasses);
	}

	private Map<String, Object> _getItem(
		String name, int calories, int portion) {

		Map<String, Object> item = new HashMap<>();

		item.put("calories", calories);
		item.put("name", name);
		item.put("portion", portion);

		return item;
	}

	private Collection<?> _getItems() {
		return Arrays.asList(
			_getItem("Blueberry", 57, 100), _getItem("Strawberry", 33, 100),
			_getItem("Raspberry", 53, 100));
	}

	private Schema _getSchema() {
		Schema schema = new Schema();

		schema.addField(new Field("name", "Name"));
		schema.addField(new Field("calories", "Calories", "number"));
		schema.addField(new Field("portion", "Portion", "number"));

		return schema;
	}

}