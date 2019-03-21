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

import com.liferay.frontend.taglib.clay.servlet.taglib.contributor.ClayTableTagSchemaContributor;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Field;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Schema;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = "clay.table.tag.schema.contributor.key=SampleTable",
	service = ClayTableTagSchemaContributor.class
)
public class SampleClayTableTagSchemaContributor
	implements ClayTableTagSchemaContributor {

	@Override
	public void populate(Schema schema) {
		schema.addField(new Field("name", "Name"));
		schema.addField(new Field("calories", "Calories", "number"));
		schema.addField(new Field("portion", "Portion", "number"));
	}

}