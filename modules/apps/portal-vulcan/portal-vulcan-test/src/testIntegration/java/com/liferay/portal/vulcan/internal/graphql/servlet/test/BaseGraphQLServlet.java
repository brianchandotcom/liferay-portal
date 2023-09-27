/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
public class BaseGraphQLServlet {

	public static class TestDTO {

		public TestDTO(long id) {
			_id = id;
		}

		public long getId() {
			return _id;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		private long _id;

	}

	public static class TestQuery {

		public String getField() {
			return _FIELD;
		}

		public long getId() {
			return _ID;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		public BaseGraphQLServlet.TestDTO testDTO() throws Exception {
			return new TestDTO(_ID);
		}

		@GraphQLTypeExtension(TestDTO.class)
		public class TestGraphQLTypeExtension {

			public TestGraphQLTypeExtension(TestDTO testDTO) {
				_testDTO = testDTO;
			}

			@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
			public String field() {
				return _FIELD;
			}

			private final TestDTO _testDTO;

		}

	}

	public class TestServletData implements ServletData {

		@Override
		public Object getMutation() {
			return null;
		}

		@Override
		public String getPath() {
			return "/test-path-graphql/v1.0";
		}

		@Override
		public TestQuery getQuery() {
			return _testQuery;
		}

		@Override
		public boolean isJaxRsResourceInvocation() {
			return false;
		}

		private final TestQuery _testQuery = new TestQuery();

	}

	protected JSONObject invoke(GraphQLField graphQLField) throws Exception {
		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"query", queryGraphQLField.toString()
			).toString(),
			"graphql", Http.Method.POST);
	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final String _FIELD = RandomTestUtil.randomString();

	private static final long _ID = RandomTestUtil.randomLong();

}