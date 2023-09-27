/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.constants;

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