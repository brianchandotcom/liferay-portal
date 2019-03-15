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

package com.liferay.frontend.taglib.clay.sample.web.internal.contributor;

import com.liferay.frontend.taglib.clay.servlet.taglib.contributor.ClayTagContextContributor;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Field;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Schema;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true,
	property = {
		"clay.tag.context.contributor.key=SampleTable",
		"service.ranking:Integer=2"
	},
	service = ClayTagContextContributor.class
)
public class SampleTableClayTagContextContributor
	implements ClayTagContextContributor {

	@Override
	public void populate(Map<String, Object> context) {
		Schema schema = _getSchema();

		context.put("schema", schema.toMap());

		context.put("selectable", true);
		context.put("tableClasses", "sample-table");
	}

	private Schema _getSchema() {
		Schema schema = new Schema();

		schema.addField(new Field("name", "Name"));
		schema.addField(new Field("calories", "Calories", "number"));
		schema.addField(new Field("portion", "Portion", "number"));

		return schema;
	}

}