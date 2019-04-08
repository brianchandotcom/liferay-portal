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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.headless.delivery.dto.v1_0.ContentStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.InputStream;

import java.util.Objects;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ContentStructureResourceTest
	extends BaseContentStructureResourceTestCase {

	@Override
	protected void assertValid(ContentStructure contentStructure) {
		boolean valid = false;

		if (Objects.equals(
				contentStructure.getContentSpaceId(), testGroup.getGroupId()) &&
			(contentStructure.getDateCreated() != null) &&
			(contentStructure.getDateModified() != null) &&
			(contentStructure.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		ContentStructure contentStructure1,
		ContentStructure contentStructure2) {

		if (Objects.equals(
				contentStructure1.getContentSpaceId(),
				contentStructure2.getContentSpaceId()) &&
			Objects.equals(
				contentStructure1.getName(), contentStructure2.getName())) {

			return true;
		}

		return false;
	}

	@Override
	protected ContentStructure
			testGetContentSpaceContentStructuresPage_addContentStructure(
				Long contentSpaceId, ContentStructure contentStructure)
		throws Exception {

		return _toContentStructure(
			_addDDMStructure(
				GroupLocalServiceUtil.getGroup(contentSpaceId),
				contentStructure.getName()));
	}

	@Override
	protected ContentStructure testGetContentStructure_addContentStructure()
		throws Exception {

		return _toContentStructure(
			_addDDMStructure(testGroup, RandomTestUtil.randomString()));
	}

	private DDMStructure _addDDMStructure(Group group, String name)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), name,
			_deserialize(_read("test-structured-content-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializer ddmFormDeserializer =
			_ddmFormDeserializerTracker.getDDMFormDeserializer("json");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private ContentStructure _toContentStructure(DDMStructure structure) {
		return new ContentStructure() {
			{
				contentSpaceId = structure.getGroupId();
				dateCreated = structure.getCreateDate();
				dateModified = structure.getModifiedDate();
				id = structure.getStructureId();
				name = structure.getName(LocaleUtil.getDefault());
			}
		};
	}

	@Inject
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

}