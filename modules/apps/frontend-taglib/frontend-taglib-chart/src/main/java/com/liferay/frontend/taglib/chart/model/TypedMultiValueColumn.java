/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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