/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.fields;

import java.util.Set;

/**
 * Instances of this interface represent the value of the {@code fields} query
 * param, which can be used to filter the fields returned in a request.
 *
 * @author Alejandro Hernández
 * @review
 */
public interface FieldsQueryParam {

	/**
	 * The list of fields. An empty set means that no fields should be returned.
	 * A {@code null} value means that no {@code fields} query param was sent.
	 *
	 * @review
	 */
	public Set<String> getFieldNames();

}