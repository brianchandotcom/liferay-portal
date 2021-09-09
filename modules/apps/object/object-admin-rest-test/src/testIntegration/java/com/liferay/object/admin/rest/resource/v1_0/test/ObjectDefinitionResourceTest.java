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

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionResourceTest
	extends BaseObjectDefinitionResourceTestCase {

	@After
	@Override
	public void tearDown() {
		if (_objectDefinition != null) {
			try {
				_objectDefinitionLocalService.deleteObjectDefinition(
					_objectDefinition.getId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}
	}

	@Override
	@Test
	public void testGetObjectDefinitionsPage() throws Exception {
		Page<ObjectDefinition> objectDefinitionsPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, Pagination.of(1, 10));

		long totalCount = objectDefinitionsPage.getTotalCount();

		_addObjectDefinition(randomObjectDefinition());

		objectDefinitionsPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, Pagination.of(1, 10));

		Assert.assertEquals(
			totalCount + 1, objectDefinitionsPage.getTotalCount());
	}

	@Override
	@Test
	public void testGetObjectDefinitionsPageWithPagination() throws Exception {
		Page<ObjectDefinition> objectDefinitionsPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, Pagination.of(1, 2));

		long totalCount = objectDefinitionsPage.getTotalCount();

		_addObjectDefinition(randomObjectDefinition());
		_addObjectDefinition(randomObjectDefinition());

		objectDefinitionsPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, Pagination.of(1, 2));

		List<ObjectDefinition> objectDefinitions =
			(List<ObjectDefinition>)objectDefinitionsPage.getItems();

		Assert.assertEquals(
			objectDefinitions.toString(), 2, objectDefinitions.size());

		objectDefinitionsPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, Pagination.of(2, 2));

		Assert.assertEquals(
			totalCount + 2, objectDefinitionsPage.getTotalCount());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectDefinitionNotFound() {
	}

	@Override
	@Test
	public void testGraphQLGetObjectDefinitionsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"objectDefinitions", new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject objectDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/objectDefinitions");

		int totalCount = (int)objectDefinitionsJSONObject.get("totalCount");

		testGraphQLObjectDefinition_addObjectDefinition();

		objectDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/objectDefinitions");

		Assert.assertEquals(
			totalCount + 1, objectDefinitionsJSONObject.get("totalCount"));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected ObjectDefinition randomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition = super.randomObjectDefinition();

		objectDefinition.setLabel(
			Collections.singletonMap(
				"en_US", "A" + objectDefinition.getName()));
		objectDefinition.setName("A" + objectDefinition.getName());
		objectDefinition.setPluralLabel(
			Collections.singletonMap(
				"en_US", "A" + objectDefinition.getName()));
		objectDefinition.setObjectFields(
			new ObjectField[] {
				new ObjectField() {
					{
						setLabel(Collections.singletonMap("en_US", "Column"));
						setName("column");
						setType(ObjectField.Type.create("String"));
					}
				}
			});
		objectDefinition.setScope(ObjectDefinitionConstants.SCOPE_COMPANY);

		return objectDefinition;
	}

	@Override
	protected ObjectDefinition testDeleteObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testGetObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testGetObjectDefinitionsPage_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected ObjectDefinition testGraphQLObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPatchObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPostObjectDefinition_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected ObjectDefinition
			testPostObjectDefinitionPublish_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPutObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	private ObjectDefinition _addObjectDefinition(
			ObjectDefinition randomObjectDefinition)
		throws Exception {

		_objectDefinition = objectDefinitionResource.postObjectDefinition(
			randomObjectDefinition);

		return _objectDefinition;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionResourceTest.class);

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}