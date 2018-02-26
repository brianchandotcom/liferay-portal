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

package com.liferay.frontend.taglib.chart.model;

import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class TypedMultiValueColumn extends MultiValueColumn {

	public TypedMultiValueColumn(String id) {
		super(id);
	}

	public TypedMultiValueColumn(String id, Type type) {
		super(id);

		setType(type);
	}

	public TypedMultiValueColumn(
		String id, Type type, Collection<? extends Number> values) {

		super(id, values);

		setType(type);
	}

	public TypedMultiValueColumn(String id, Type type, Number... values) {
		super(id, values);

		setType(type);
	}

	public void setType(Type type) {
		set("type", type._value);
	}

	public enum Type {

		AREA("area"), AREA_SPLINE("area-spline"), AREA_STEP("area-step"),
		BAR("bar"), BUBBLE("bubble"), DONUT("donut"), GAUGE("gauge"),
		LINE("line"), PIE("pie"), SCATTER("scatter"), SPLINE("spline"),
		STEP("step");

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

}