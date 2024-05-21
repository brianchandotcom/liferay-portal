/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test.v1_0;

import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;

/**
 * @author Carlos Correa
 */
public class TestDTOPage {

	public TestDTOPage(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	@GraphQLField
	protected int page;

	@GraphQLField
	protected int pageSize;

}