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

package com.liferay.segments.asah.connector.internal.client.util;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class FilterConstants {

	public static final String COMPARISON_OPERATOR_EQUALS = " eq ";

	public static final String COMPARISON_OPERATOR_GREATER_THAN_OR_EQUAL =
		" ge ";

	public static final String COMPARISON_OPERATOR_NOT_EQUALS = " ne ";

	public static final String FIELD_NAME_CONTEXT_INDIVIDUAL =
		"demographics/?/value";

	public static final String FIELD_NAME_CONTEXT_INDIVIDUAL_SEGMENT =
		"fields/?/value";

	public static final String LOGICAL_OPERATOR_AND = " and ";

	public static final String LOGICAL_OPERATOR_OR = " or ";

	private FilterConstants() {
	}

}