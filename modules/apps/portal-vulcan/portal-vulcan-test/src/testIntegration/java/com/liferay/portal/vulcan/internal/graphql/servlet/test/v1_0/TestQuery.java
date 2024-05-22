/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test.v1_0;

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;

import javax.ws.rs.NotFoundException;

/**
 * @author Carlos Correa
 */
public class TestQuery {

	public TestQuery() {
	}

	public TestQuery(TestDTO testDTO) {
		_testDTO = testDTO;
	}

	@GraphQLField
	public TestDTO testDTO() {
		return _testDTO;
	}

	@GraphQLField
	public TestDTOPage testDTOPage(
		@GraphQLName("page") int page, @GraphQLName("pageSize") int pageSize) {

		return new TestDTOPage(page, pageSize);
	}

	@GraphQLField
	public TestDTO testDTOV1() {
		return _testDTO;
	}

	@GraphQLField
	public TestDTO testNoPermissionOverDTO()
		throws PrincipalException.MustHavePermission {

		throw new PrincipalException.MustHavePermission(
			0L, StringUtil.randomString());
	}

	@GraphQLField
	public TestDTO testNotFoundDTO() {
		throw new NotFoundException();
	}

	@GraphQLField
	public TestDTO testUnauthorizedUser() throws SecurityException {
		throw new SecurityException();
	}

	@GraphQLTypeExtension(TestDTO.class)
	public class TestGraphQLTypeExtension {

		public TestGraphQLTypeExtension(TestDTO testDTO) {
			_testDTO = testDTO;
		}

		@GraphQLField
		public String extendedString() {
			return _testDTO.getExtendedString();
		}

		private final TestDTO _testDTO;

	}

	private static TestDTO _testDTO;

}