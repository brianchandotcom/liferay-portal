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

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class TablesDisplayContext
	extends BaseTableDisplayContext<TablesDisplayContext.Item> {

	public static class Item {

		public Item(
			String name, int calories, String color, boolean skinEdible) {

			_name = name;
			_calories = calories;
			_color = color;
			_skinEdible = skinEdible;
		}

		public int getCalories() {
			return _calories;
		}

		public String getColor() {
			return _color;
		}

		public String getName() {
			return _name;
		}

		public boolean isSkinEdible() {
			return _skinEdible;
		}

		private final int _calories;
		private final String _color;
		private final String _name;
		private final boolean _skinEdible;

	}

	@Override
	protected void configureSchema(Schema schema) {
		schema.addField(new Field("name", "Name"));
		schema.addField(new Field("calories", "Calories/portion", "number"));
		schema.addField(new Field("color", "Color"));
		schema.addField(new Field("skinEdible", "Skin Edible"));
	}

	@Override
	protected Collection<Item> doGetItems() {
		return Arrays.asList(
			new Item("Banana", 89, "yellow", false),
			new Item("Apple", 52, "red", true),
			new Item("Pear", 58, "green", true),
			new Item("Pomegranate", 68, "yellowish", false));
	}

}