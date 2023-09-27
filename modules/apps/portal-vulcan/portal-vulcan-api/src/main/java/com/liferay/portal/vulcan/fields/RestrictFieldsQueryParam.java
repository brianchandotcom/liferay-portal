/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.fields;

import java.util.Set;

/**
 * Instances of this interface represent the value of the {@code restrict
 * fields} query param, which can be used to restrict the fields returned in a
 * request.
 *
 * @author Javier Gamarra
 * @review
 */
public interface RestrictFieldsQueryParam {

	/**
	 * The list of restrict fields. An empty set means that all fields should be
	 * returned. A {@code null} value means that no {@code restrict fields}
	 * query param was sent.
	 *
	 * @review
	 */
	public Set<String> getRestrictFieldNames();

}