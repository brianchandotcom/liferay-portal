/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage.query;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Marcellus Tavares
 */
public class ConditionFactoryUtil {

	public static Junction conjunction() {
		return getConditionFactory().conjunction();
	}

	public static Junction disjunction() {
		return getConditionFactory().disjunction();
	}

	public static Condition eq(String name, Object value) {
		return getConditionFactory().eq(name, value);
	}

	public static ConditionFactory getConditionFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ConditionFactoryUtil.class);

		return _conditionFactory;
	}

	public static Condition gt(String name, Object value) {
		return getConditionFactory().gt(name, value);
	}

	public static Condition gte(String name, Object value) {
		return getConditionFactory().gte(name, value);
	}

	public static Condition in(String name, Object value) {
		return getConditionFactory().in(name, value);
	}

	public static Condition like(String name, Object value) {
		return getConditionFactory().like(name, value);
	}

	public static Condition lt(String name, Object value) {
		return getConditionFactory().lt(name, value);
	}

	public static Condition lte(String name, Object value) {
		return getConditionFactory().lte(name, value);
	}

	public static Condition ne(String name, Object value) {
		return getConditionFactory().ne(name, value);
	}

	public static Condition notIn(String name, Object value) {
		return getConditionFactory().notIn(name, value);
	}

	public void setConditionFactory(ConditionFactory conditionFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_conditionFactory = conditionFactory;
	}

	private static ConditionFactory _conditionFactory;

}