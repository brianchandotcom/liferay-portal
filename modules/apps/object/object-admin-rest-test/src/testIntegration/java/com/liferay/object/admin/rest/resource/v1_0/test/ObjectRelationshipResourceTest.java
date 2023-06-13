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
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectRelationship;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 * @author Murilo Stodolni
 */
@RunWith(Arquillian.class)
public class ObjectRelationshipResourceTest
	extends BaseObjectRelationshipResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_objectDefinition1 = _addObjectDefinition();
		_objectDefinition2 = _addObjectDefinition();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_objectDefinition1 != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition1.getObjectDefinitionId());
		}

		if (_objectDefinition2 != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition2.getObjectDefinitionId());
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetObjectDefinitionByExternalReferenceCodeObjectRelationshipsPageWithFilterDateTimeEquals() {
	}

	@Ignore
	@Override
	@Test
	public void testGetObjectDefinitionObjectRelationshipsPageWithFilterDateTimeEquals() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectRelationship() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectRelationshipNotFound() {
	}

	@Override
	@Test
	public void testPostObjectDefinitionObjectRelationship() throws Exception {
		super.testPostObjectDefinitionObjectRelationship();

		ObjectRelationship randomObjectRelationship =
			randomObjectRelationship();

		randomObjectRelationship.setObjectDefinitionExternalReferenceCode2(
			RandomTestUtil.randomString());

		randomObjectRelationship.setObjectDefinitionId2(0L);
		randomObjectRelationship.setObjectDefinitionModifiable2(() -> null);
		randomObjectRelationship.setObjectDefinitionSystem2(() -> null);

		ObjectRelationship postObjectRelationship =
			testPostObjectDefinitionObjectRelationship_addObjectRelationship(
				randomObjectRelationship);

		Assert.assertNotNull(
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					randomObjectRelationship.
						getObjectDefinitionExternalReferenceCode2(),
					TestPropsValues.getCompanyId()));

		Assert.assertTrue(
			postObjectRelationship.getObjectDefinitionModifiable2());
		Assert.assertFalse(postObjectRelationship.getObjectDefinitionSystem2());
	}

	@Override
	protected ObjectRelationship randomObjectRelationship() throws Exception {
		ObjectRelationship objectRelationship =
			super.randomObjectRelationship();

		objectRelationship.setName("a" + RandomTestUtil.randomString());
		objectRelationship.setObjectDefinitionExternalReferenceCode1(
			_objectDefinition1.getExternalReferenceCode());
		objectRelationship.setObjectDefinitionExternalReferenceCode2(
			_objectDefinition2.getExternalReferenceCode());
		objectRelationship.setObjectDefinitionId1(
			_objectDefinition1.getObjectDefinitionId());
		objectRelationship.setObjectDefinitionId2(
			_objectDefinition2.getObjectDefinitionId());
		objectRelationship.setObjectDefinitionModifiable2(true);
		objectRelationship.setObjectDefinitionName2(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
		objectRelationship.setObjectDefinitionSystem2(false);
		objectRelationship.setParameterObjectFieldId(0L);
		objectRelationship.setParameterObjectFieldName(StringPool.BLANK);
		objectRelationship.setReverse(false);
		objectRelationship.setType(ObjectRelationship.Type.ONE_TO_MANY);

		return objectRelationship;
	}

	@Override
	protected ObjectRelationship
			testDeleteObjectRelationship_addObjectRelationship()
		throws Exception {

		return testPostObjectDefinitionObjectRelationship_addObjectRelationship(
			randomObjectRelationship());
	}

	@Override
	protected ObjectRelationship
			testGetObjectDefinitionByExternalReferenceCodeObjectRelationshipsPage_addObjectRelationship(
				String externalReferenceCode,
				ObjectRelationship objectRelationship)
		throws Exception {

		return testPostObjectDefinitionObjectRelationship_addObjectRelationship(
			randomObjectRelationship());
	}

	@Override
	protected String
		testGetObjectDefinitionByExternalReferenceCodeObjectRelationshipsPage_getExternalReferenceCode() {

		return _objectDefinition1.getExternalReferenceCode();
	}

	@Override
	protected Long
		testGetObjectDefinitionObjectRelationshipsPage_getObjectDefinitionId() {

		return _objectDefinition1.getObjectDefinitionId();
	}

	@Override
	protected ObjectRelationship
			testGetObjectRelationship_addObjectRelationship()
		throws Exception {

		return testPostObjectDefinitionObjectRelationship_addObjectRelationship(
			randomObjectRelationship());
	}

	@Override
	protected ObjectRelationship
			testGraphQLObjectRelationship_addObjectRelationship()
		throws Exception {

		return testPostObjectDefinitionObjectRelationship_addObjectRelationship(
			randomObjectRelationship());
	}

	@Override
	protected ObjectRelationship
			testPostObjectDefinitionByExternalReferenceCodeObjectRelationship_addObjectRelationship(
				ObjectRelationship objectRelationship)
		throws Exception {

		return objectRelationshipResource.
			postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				testGetObjectDefinitionByExternalReferenceCodeObjectRelationshipsPage_getExternalReferenceCode(),
				objectRelationship);
	}

	@Override
	protected ObjectRelationship
			testPutObjectRelationship_addObjectRelationship()
		throws Exception {

		return testPostObjectDefinitionObjectRelationship_addObjectRelationship(
			randomObjectRelationship());
	}

	private ObjectDefinition _addObjectDefinition() throws Exception {
		String value = "A" + RandomTestUtil.randomString();

		return _objectDefinitionLocalService.addCustomObjectDefinition(
			TestPropsValues.getUserId(), false, false,
			LocalizedMapUtil.getLocalizedMap(value), value, null, null,
			LocalizedMapUtil.getLocalizedMap(value), true,
			ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			Collections.emptyList());
	}

	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}