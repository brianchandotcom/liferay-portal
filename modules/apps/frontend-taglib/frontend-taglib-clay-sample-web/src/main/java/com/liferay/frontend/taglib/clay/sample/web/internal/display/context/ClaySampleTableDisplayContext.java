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

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.BaseTableDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Field;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Schema;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class ClaySampleTableDisplayContext
	extends BaseTableDisplayContext<SampleFruit> {

	@Override
	public Boolean isSelectable() {
		return true;
	}

	@Override
	protected void configureSchema(Schema schema) {
		schema.addField(new Field("main", "name", "Name"));
		schema.addField(new Field("number", "calories", "Calories/portion"));
		schema.addField(new Field(StringPool.BLANK, "color", "Color"));
		schema.addField(
			new Field(StringPool.BLANK, "skinEdible", "Skin Edible"));
	}

	@Override
	protected Collection<SampleFruit> doGetItems() {
		return Arrays.asList(
			new SampleFruit("Banana", 89, "yellow", false),
			new SampleFruit("Apple", 52, "red", true),
			new SampleFruit("Pear", 58, "green", true),
			new SampleFruit("Pomegranate", 68, "yellowish", false));
	}

}