/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Sergio Jiménez del Coso
 */
public class OpenAPIUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetOperationIdWithCollection() {
		Assert.assertEquals(
			"getCamelSchemasPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/camelschemas",
				APIApplication.Endpoint.RetrieveType.COLLECTION,
				"CamelSchema"));
		Assert.assertEquals(
			"getPathNamePage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/path-name",
				APIApplication.Endpoint.RetrieveType.COLLECTION, null));
		Assert.assertEquals(
			"getPathNamePage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/path-name",
				APIApplication.Endpoint.RetrieveType.COLLECTION, "Schema"));
		Assert.assertEquals(
			"getSchemasWhateverPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/schema/whatever",
				APIApplication.Endpoint.RetrieveType.COLLECTION, "Schema"));
		Assert.assertEquals(
			"getScopeScopeKeyNoSchemaPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/scopes/{scopeKey}/no-schema",
				APIApplication.Endpoint.RetrieveType.COLLECTION, null));
		Assert.assertEquals(
			"getScopeScopeKeySiteScopedPathPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/scopes/{scopeKey}/site-scoped-path",
				APIApplication.Endpoint.RetrieveType.COLLECTION, "Schema"));
		Assert.assertEquals(
			"getSegmentASegmentBPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/segment-a/segment-b",
				APIApplication.Endpoint.RetrieveType.COLLECTION, "Schema"));
		Assert.assertEquals(
			"getWhateverPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/whatever",
				APIApplication.Endpoint.RetrieveType.COLLECTION,
				"CamelSchema"));
	}

	@Test
	public void testGetOperationIdWithScopedSingleElementByExternalReferenceCode() {
		Assert.assertEquals(
			"getScopeScopeKeyByExternalReferenceCodeSchemaERC",
			OpenAPIUtil.getOperationId(
				Http.Method.GET,
				"/scopes/{scopeKey}/by-external-reference-code/{schemaERC}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getScopeScopeKeyByExternalReferenceCodeSchemaExternal" +
				"ReferenceCode",
			OpenAPIUtil.getOperationId(
				Http.Method.GET,
				"/scopes/{scopeKey}/by-external-reference-code " +
					"/{schemaExternalReferenceCode}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getScopeScopeKeyPathNameByExternalReferenceCodePathNameERC",
			OpenAPIUtil.getOperationId(
				Http.Method.GET,
				"/scopes/{scopeKey}/path-names/by-external-reference-code" +
					"/{pathNameERC}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getScopeScopeKeySchemaByExternalReferenceCodeSchemaERC",
			OpenAPIUtil.getOperationId(
				Http.Method.GET,
				"/scopes/{scopeKey}/schemas/by-external-reference-code" +
					"/{schemaERC}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, null));
		Assert.assertEquals(
			"getScopeScopeKeySchemaByExternalReferenceCodeSchemaERC",
			OpenAPIUtil.getOperationId(
				Http.Method.GET,
				"/scopes/{scopeKey}/schemas/by-external-reference-code" +
					"/{schemaERC}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
	}

	@Test
	public void testGetOperationIdWithSingleElementByExternalReferenceCode() {
		Assert.assertEquals(
			"getByExternalReferenceCode",
			OpenAPIUtil.getOperationId(
				Http.Method.GET,
				"/by-external-reference-code/{externalReferenceCode}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getByExternalReferenceCodeExternalReferenceCode",
			OpenAPIUtil.getOperationId(
				Http.Method.GET,
				"/by-external-reference-code/{externalReferenceCode}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, null));
		Assert.assertEquals(
			"getPathNamePathNameERC",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/path-names/{pathNameERC}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getSchemaByExternalReferenceCodeSchemaERC",
			OpenAPIUtil.getOperationId(
				Http.Method.GET,
				"/schemas/by-external-reference-code/{schemaERC}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getSchemaWhateverWhateverERC",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/schema/whatever/{whateverERC}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getSegmentASegmentBSegmentBERC",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/segment-a/segment-b/{segmentBERC}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
	}

	@Test
	public void testGetOperationIdWithSingleElementById() {
		Assert.assertEquals(
			"getCamelSchema",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/camelschemas/{camelSchemaId}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT,
				"CamelSchema"));
		Assert.assertEquals(
			"getPathNamePathName",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/path-names/{pathNameId}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, null));
		Assert.assertEquals(
			"getPathName",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/path-names/{pathNameId}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getSchemaWhatever",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/schema/whatever/{whateverId}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getSegmentASegmentB",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/segment-a/segment-b/{segmentBId}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT, "Schema"));
		Assert.assertEquals(
			"getWhatever",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/whatever/{whateverId}",
				APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT,
				"CamelSchema"));
	}

	@Test
	public void testToOpenAPISchemas() throws Exception {
		JSONAssert.assertEquals(
			"{\"singleContainer\":{\"name\":\"singleContainer\"," +
				"\"type\":\"object\"},\"property\":{\"name\":\"property\"," +
					"\"type\":\"string\"}}",
			_getSchemaString(
				Collections.emptyMap(),
				HashMapBuilder.put(
					"property", APIApplication.Property.PropertyType.NORMAL
				).put(
					"singleContainer",
					APIApplication.Property.PropertyType.SINGLE_CONTAINER
				).build()),
			JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			StringBundler.concat(
				"{\"singleContainer\":{\"name\":",
				"\"singleContainer\",\"type\":\"object\",\"properties\":",
				"{\"property\":{\"name\":\"property\",\"type\":\"string",
				"\"}}}}"),
			_getSchemaString(
				HashMapBuilder.put(
					"property", "singleContainer"
				).build(),
				HashMapBuilder.put(
					"property", APIApplication.Property.PropertyType.NORMAL
				).put(
					"singleContainer",
					APIApplication.Property.PropertyType.SINGLE_CONTAINER
				).build()),
			JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			StringBundler.concat(
				"{\"singleContainer1\":{\"name\":\"singleContainer1\",",
				"\"type\":\"object\",\"properties\":{\"property\":{\"name\":",
				"\"property\",\"type\":\"string\"},\"singleContainer2\":{\"",
				"name\":\"singleContainer2\",\"type\":\"object\"}}}}"),
			_getSchemaString(
				HashMapBuilder.put(
					"property", "singleContainer1"
				).put(
					"singleContainer2", "singleContainer1"
				).build(),
				HashMapBuilder.put(
					"property", APIApplication.Property.PropertyType.NORMAL
				).put(
					"singleContainer1",
					APIApplication.Property.PropertyType.SINGLE_CONTAINER
				).put(
					"singleContainer2",
					APIApplication.Property.PropertyType.SINGLE_CONTAINER
				).build()),
			JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			StringBundler.concat(
				"{\"singleContainer1\":{\"name\":",
				"\"singleContainer1\",\"type\":\"object\",",
				"\"properties\":{\"singleContainer2\":{\"",
				"name\":\"singleContainer2\",\"type\":\"",
				"object\",\"properties\":{\"property\":{\"",
				"name\":\"property\",\"type\":\"string\"}}}}}}"),
			_getSchemaString(
				HashMapBuilder.put(
					"property", "singleContainer2"
				).put(
					"singleContainer2", "singleContainer1"
				).build(),
				HashMapBuilder.put(
					"property", APIApplication.Property.PropertyType.NORMAL
				).put(
					"singleContainer1",
					APIApplication.Property.PropertyType.SINGLE_CONTAINER
				).put(
					"singleContainer2",
					APIApplication.Property.PropertyType.SINGLE_CONTAINER
				).build()),
			JSONCompareMode.LENIENT);
	}

	private JSONObject _getOpenAPIJSONObject(
		Map<String, Schema> openAPISchemas) {

		Schema schema = openAPISchemas.get(_SCHEMA_NAME);

		return JSONFactoryUtil.createJSONObject(schema.getProperties());
	}

	private APIApplication.Schema _getSchema(
		Map<String, String> fieldMapping,
		Map<String, APIApplication.Property.PropertyType> propertyTypeMap) {

		List<APIApplication.Property> properties = new ArrayList<>();

		for (Map.Entry<String, APIApplication.Property.PropertyType> entry :
				propertyTypeMap.entrySet()) {

			properties.add(
				new APIApplication.Property() {

					@Override
					public String getDescription() {
						return null;
					}

					@Override
					public String getExternalReferenceCode() {
						return entry.getKey();
					}

					@Override
					public String getName() {
						return entry.getKey();
					}

					@Override
					public List<String> getObjectRelationshipNames() {
						return Collections.emptyList();
					}

					@Override
					public PropertyType getPropertyType() {
						return entry.getValue();
					}

					@Override
					public String getRelatedPropertyERC() {
						if (!fieldMapping.isEmpty()) {
							return fieldMapping.get(entry.getKey());
						}

						return "";
					}

					@Override
					public String getSourceFieldName() {
						return null;
					}

					@Override
					public Type getType() {
						if (Objects.equals(
								entry.getValue(), PropertyType.NORMAL)) {

							return Type.TEXT;
						}

						return Type.SINGLE_CONTAINER;
					}

				});
		}

		return new APIApplication.Schema() {

			@Override
			public String getDescription() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getExternalReferenceCode() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getMainObjectDefinitionExternalReferenceCode() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getName() {
				return _SCHEMA_NAME;
			}

			@Override
			public List<APIApplication.Property> getProperties() {
				return properties;
			}

		};
	}

	private String _getSchemaString(
			Map<String, String> fieldMapping,
			Map<String, APIApplication.Property.PropertyType> propertyTypeMap)
		throws Exception {

		OpenAPIResource openAPIResource = Mockito.mock(OpenAPIResource.class);

		Mockito.when(
			openAPIResource.getSchemas(Page.class)
		).thenReturn(
			HashMapBuilder.<String, Schema>put(
				"Page",
				new Schema() {
					{
						setName("Page");
						setProperties(
							HashMapBuilder.put(
								"items",
								new ArraySchema() {
									{
										setName("items");
									}
								}
							).build());
					}
				}
			).build()
		);

		Map<String, Schema> openAPISchemas = OpenAPIUtil.toOpenAPISchemas(
			openAPIResource, _getSchema(fieldMapping, propertyTypeMap));

		JSONObject jsonObject = _getOpenAPIJSONObject(openAPISchemas);

		return jsonObject.toString();
	}

	private static final String _SCHEMA_NAME = RandomTestUtil.randomString();

}