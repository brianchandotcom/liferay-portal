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

package com.liferay.portal.vulcan.internal.graphql.servlet.test;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;

/**
 * @author Luis Miguel Barcos
 */
public class TestQuery {

	public String getField() {
		return _FIELD;
	}

	public long getId() {
		return _ID;
	}

	@GraphQLField
	public TestDTO testDTO() throws Exception {
		return new TestDTO(_ID);
	}

	public class TestDTO {

		public TestDTO(long id) {
			_id = id;
		}

		public long getId() {
			return _id;
		}

		@GraphQLField
		private long _id;

	}

	@GraphQLTypeExtension(TestDTO.class)
	public class TestGraphQLTypeExtension {

		public TestGraphQLTypeExtension(TestDTO testDTO) {
			_testDTO = testDTO;
		}

		@GraphQLField
		public String field() {
			return _FIELD;
		}

		private final TestDTO _testDTO;

	}

	private static final String _FIELD = RandomTestUtil.randomString();

	private static final long _ID = RandomTestUtil.randomLong();

}