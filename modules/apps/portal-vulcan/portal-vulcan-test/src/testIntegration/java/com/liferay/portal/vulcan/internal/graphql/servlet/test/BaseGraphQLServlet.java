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

import org.junit.Assert;

/**
 * @author Luis Miguel Barcos
 */
public class BaseGraphQLServlet {

	public static class TestDTO {

		public TestDTO() {
			this(
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				HashMapBuilder.put(
					"a" + RandomTestUtil.randomString(),
					RandomTestUtil.randomString()
				).put(
					"a" + RandomTestUtil.randomString(),
					RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString());
		}

		public TestDTO(
			String extendedString, long id, Map<String, String> map,
			String string) {

			_extendedString = extendedString;

			this.id = id;
			this.map = map;
			this.string = string;
		}

		public String getExtendedString() {
			return _extendedString;
		}

		public long getId() {
			return id;
		}

		public Map<String, String> getMap() {
			return map;
		}

		public String getString() {
			return string;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		protected long id;

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		protected Map<String, String> map;

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		protected String string;

		private String _extendedString;

	}

	public static class TestMutation {

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		public BaseGraphQLServlet.TestDTO createTestDTO(
				@GraphQLName("testDTO") TestDTO testDTO)
			throws Exception {

			return testDTO;
		}

	}

	public static class TestPagination {

		public TestPagination() {
			this(0, 0);
		}

		public TestPagination(int page, int pageSize) {
			this.page = page;
			this.pageSize = pageSize;
		}

		public int getPage() {
			return page;
		}

		public int getPageSize() {
			return pageSize;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		protected int page;

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		protected int pageSize;

	}

	public static class TestQuery {

		public TestQuery() {
		}

		public TestQuery(TestDTO testDTO) {
			_testDTO = testDTO;
		}

		public TestQuery(TestPagination testPagination) {
			_testPagination = testPagination;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		public BaseGraphQLServlet.TestDTO testDTO() throws Exception {
			return _testDTO;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		public BaseGraphQLServlet.TestPagination testPagination(
				@GraphQLName("page") int page,
				@GraphQLName("pageSize") int pageSize)
			throws Exception {

			return new TestPagination(page, pageSize);
		}

		@GraphQLTypeExtension(TestDTO.class)
		public class TestGraphQLTypeExtension {

			public TestGraphQLTypeExtension(TestDTO testDTO) {
				_testDTO = testDTO;
			}

			@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
			public String extendedString() {
				return _testDTO.getExtendedString();
			}

			private final TestDTO _testDTO;

		}

		private static TestDTO _testDTO;
		private static TestPagination _testPagination;

	}

	public class TestServletData implements ServletData {

		public TestServletData(TestDTO testDTO) {
			_testQuery = new TestQuery(testDTO);
		}

		public TestServletData(TestPagination testPagination) {
			_testQuery = new TestQuery(testPagination);
		}

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
		private final TestQuery _testQuery;

	}

	protected void assertEquals(
		boolean assertExtendedProperties, TestDTO expectedTestDTO,
		JSONObject jsonObject) {

		if (assertExtendedProperties) {
			Assert.assertEquals(
				jsonObject.get("extendedString"),
				expectedTestDTO.getExtendedString());
		}

		Assert.assertEquals(jsonObject.get("id"), expectedTestDTO.getId());
		Assert.assertEquals(
			JSONUtil.toStringMap(jsonObject.getJSONObject("map")),
			expectedTestDTO.getMap());
		Assert.assertEquals(
			jsonObject.get("string"), expectedTestDTO.getString());
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

}