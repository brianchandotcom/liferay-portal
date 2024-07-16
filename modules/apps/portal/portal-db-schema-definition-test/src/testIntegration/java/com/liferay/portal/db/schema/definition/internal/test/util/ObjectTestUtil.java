/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.test.util;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ObjectTestUtil {

	public static void createObjectsData() throws Exception {
		_objectDefinition1 = _addObjectDefinition();
		_objectDefinition2 = _addObjectDefinition();

		_objectRelationship = _addObjectRelationship(
			_objectDefinition1, _objectDefinition2);
	}

	public static void removeObjectsData() throws Exception {
		if (_objectRelationship != null) {
			ObjectRelationshipLocalServiceUtil.deleteObjectRelationship(
				_objectRelationship.getObjectRelationshipId());
		}

		if (_objectDefinition1 != null) {
			ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
				_objectDefinition1.getObjectDefinitionId());
		}

		if (_objectDefinition2 != null) {
			ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
				_objectDefinition2.getObjectDefinitionId());
		}
	}

	private static ObjectDefinition _addObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, true, true, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionTestUtil.getRandomName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).userId(
						TestPropsValues.getUserId()
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(StringUtil.randomId())
					).name(
						"able"
					).build()));

		ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		return objectDefinition;
	}

	private static ObjectRelationship _addObjectRelationship(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2)
		throws Exception {

		return ObjectRelationshipLocalServiceUtil.addObjectRelationship(
			null, TestPropsValues.getUserId(),
			objectDefinition1.getObjectDefinitionId(),
			objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY, null);
	}

	private static ObjectDefinition _objectDefinition1;
	private static ObjectDefinition _objectDefinition2;
	private static ObjectRelationship _objectRelationship;

}