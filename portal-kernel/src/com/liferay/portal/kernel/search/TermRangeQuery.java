/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

/**
 * @author Raymond Augé
 */
public interface TermRangeQuery extends Query {

	public String getField();

	public String getLowerTerm();

	public String getUpperTerm();

	public boolean includesLower();

	public boolean includesUpper();

	@Override
	public String toString();

}