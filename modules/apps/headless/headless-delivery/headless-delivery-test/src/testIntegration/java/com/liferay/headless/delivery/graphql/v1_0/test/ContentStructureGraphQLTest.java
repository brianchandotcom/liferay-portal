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

package com.liferay.headless.delivery.graphql.v1_0.test;

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
import com.liferay.headless.delivery.client.dto.v1_0.ContentStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.InputStream;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ContentStructureGraphQLTest
	extends BaseContentStructureGraphQLTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected ContentStructure testContentStructure_addContentStructure()
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), testGroup);

		return _toContentStructure(
			ddmStructureTestHelper.addStructure(
				PortalUtil.getClassNameId(JournalArticle.class),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_deserialize(_read("test-structured-content-structure.json")),
				StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT));
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
				dateCreated = structure.getCreateDate();
				dateModified = structure.getModifiedDate();
				id = structure.getStructureId();
				name = structure.getName(LocaleUtil.getDefault());
				siteId = structure.getGroupId();
			}
		};
	}

	@Inject
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

}