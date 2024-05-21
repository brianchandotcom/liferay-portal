/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test.v1_0;

import com.liferay.portal.vulcan.graphql.servlet.ServletData;

/**
 * @author Carlos Correa
 */
public class TestServletData implements ServletData {

	public TestServletData(TestDTO testDTO) {
		_testQuery = new TestQuery(testDTO);
	}

	@Override
	public String getApplicationName() {
		return "test";
	}

	@Override
	public Object getMutation() {
		return _testMutation;
	}

	@Override
	public String getPath() {
		return "/test-path-graphql/v1_0";
	}

	@Override
	public TestQuery getQuery() {
		return _testQuery;
	}

	@Override
	public boolean isJaxRsResourceInvocation() {
		return false;
	}

	private final TestMutation _testMutation = new TestMutation();
	private final TestQuery _testQuery;

}