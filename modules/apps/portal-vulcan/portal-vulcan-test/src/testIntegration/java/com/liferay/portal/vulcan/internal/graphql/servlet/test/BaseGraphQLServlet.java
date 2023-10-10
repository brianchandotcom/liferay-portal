/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
public class BaseGraphQLServlet {

	public static class TestDTO {

		public TestDTO() {
			this(_EXTENDED_STRING_FIELD, _ID, _mapField, _STRING_FIELD);
		}

		public TestDTO(
			String extendedStringField, long id, Map<String, String> mapField,
			String stringField) {

			_extendedStringField = extendedStringField;
			this.id = id;
			this.mapField = mapField;
			this.stringField = stringField;
		}

		public long getId() {
			return id;
		}

		public Map<String, String> getMapField() {
			return mapField;
		}

		public String getStringField() {
			return stringField;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		protected long id;

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		protected Map<String, String> mapField;

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		protected String stringField;

		private final String _extendedStringField;

	}

	public static class TestMutation {

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		public BaseGraphQLServlet.TestDTO createTestDTO(
				@GraphQLName("testDTO") TestDTO testDTO)
			throws Exception {

			return testDTO;
		}

	}

	public static class TestQuery {

		public String getExtendedStringField() {
			return _EXTENDED_STRING_FIELD;
		}

		public long getId() {
			return _ID;
		}

		public Map<String, String> getMapField() {
			return _mapField;
		}

		public String getStringField() {
			return _STRING_FIELD;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		public BaseGraphQLServlet.TestDTO testDTO() throws Exception {
			return new TestDTO(
				_EXTENDED_STRING_FIELD, _ID, _mapField, _STRING_FIELD);
		}

		@GraphQLTypeExtension(TestDTO.class)
		public class TestGraphQLTypeExtension {

			public TestGraphQLTypeExtension(TestDTO testDTO) {
				_testDTO = testDTO;
			}

			@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
			public String extendedStringField() {
				return _EXTENDED_STRING_FIELD;
			}

			private final TestDTO _testDTO;

		}

	}

	public class TestServletData implements ServletData {

		@Override
		public Object getMutation() {
			return _testMutation;
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

		private final TestMutation _testMutation = new TestMutation();
		private final TestQuery _testQuery = new TestQuery();

	}

	protected JSONObject invoke(GraphQLField graphQLField, String type)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(type, graphQLField);

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"query", queryGraphQLField.toString()
			).toString(),
			"graphql", Http.Method.POST);
	}

	protected String toGraphQLString(TestDTO testDTO) throws Exception {
		StringBuilder sb = new StringBuilder("{");

		for (Field field : ReflectionUtil.getDeclaredFields(TestDTO.class)) {
			if (ArrayUtil.isEmpty(
					field.getAnnotationsByType(
						com.liferay.portal.vulcan.graphql.annotation.
							GraphQLField.class))) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			_appendGraphQLFieldValue(sb, field.get(testDTO));
		}

		sb.append("}");

		return sb.toString();
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

	private void _appendGraphQLFieldValue(StringBuilder sb, Object value)
		throws Exception {

		if (value instanceof Map) {
			Map<String, Object> map = (Map)value;

			sb.append("{");

			StringBuilder stringBuilder = new StringBuilder();

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (stringBuilder.length() > 1) {
					stringBuilder.append(", ");
				}

				stringBuilder.append(entry.getKey());
				stringBuilder.append(": ");

				_appendGraphQLFieldValue(stringBuilder, entry.getValue());
			}

			sb.append(stringBuilder.toString());
			sb.append("}");
		}
		else if (value instanceof String) {
			sb.append("\"");
			sb.append(value);
			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

	private static final String _EXTENDED_STRING_FIELD =
		RandomTestUtil.randomString();

	private static final long _ID = RandomTestUtil.randomLong();

	private static final String _STRING_FIELD = RandomTestUtil.randomString();

	private static final Map<String, String> _mapField = HashMapBuilder.put(
		RandomTestUtil.randomString(), RandomTestUtil.randomString()
	).put(
		RandomTestUtil.randomString(), RandomTestUtil.randomString()
	).build();

}