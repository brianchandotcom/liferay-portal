/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.sort;

import java.util.List;

/**
 * Models a parser from string to sort fields.
 *
 * @author Cristina González
 * @review
 */
public interface SortParser {

	/**
	 * Returns a {@link SortField} list from a string.
	 *
	 * @param  sortString the string to parse
	 * @return the {@link SortField} list
	 * @review
	 */
	public List<SortField> parse(String sortString);

}