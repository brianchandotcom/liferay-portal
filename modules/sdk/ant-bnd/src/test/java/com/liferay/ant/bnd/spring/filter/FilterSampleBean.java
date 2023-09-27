/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.bnd.spring.filter;

import com.liferay.ant.bnd.spring.ServiceReference;

/**
 * @author Miguel Pastor
 */
public class FilterSampleBean {

	@ServiceReference(filterString = "(service.ranking=1)", type = String.class)
	protected String s;

}