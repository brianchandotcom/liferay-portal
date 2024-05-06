/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.test.util;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Guilherme Camacho
 */
public class ObjectDefinitionTestUtil {

	public static ObjectDefinition addCustomObjectDefinition(
			boolean enableLocalization,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			List<ObjectField> objectFields)
		throws Exception {

		return addCustomObjectDefinition(
			0, enableLocalization, objectDefinitionLocalService, objectFields);
	}

	public static ObjectDefinition addCustomObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		return ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
			TestPropsValues.getUserId(), 0, false, true, false, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			getRandomName(), null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			true, ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);
	}

	public static ObjectDefinition addCustomObjectDefinition(
			long objectFolderId, boolean enableLocalization,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			List<ObjectField> objectFields)
		throws Exception {

		return objectDefinitionLocalService.addCustomObjectDefinition(
			TestPropsValues.getUserId(), objectFolderId, false, true,
			enableLocalization, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			getRandomName(), null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			true, ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);
	}

	public static ObjectDefinition addCustomObjectDefinition(
			long objectFolderId,
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws Exception {

		return addCustomObjectDefinition(
			objectFolderId, false, objectDefinitionLocalService, null);
	}

	public static ObjectDefinition addCustomObjectDefinition(
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws Exception {

		return addCustomObjectDefinition(
			false, objectDefinitionLocalService, null);
	}

	public static ObjectDefinition addCustomObjectDefinition(
			String name,
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws PortalException {

		return objectDefinitionLocalService.addCustomObjectDefinition(
			TestPropsValues.getUserId(), 0, false, true, false, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			name, null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			false, ObjectDefinitionConstants.SCOPE_COMPANY,
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
	}

	public static ObjectDefinition addModifiableSystemObjectDefinition(
			long userId, String dbTableName, Map<Locale, String> labelMap,
			String name, String pkObjectFieldDBColumnName,
			String pkObjectFieldName, Map<Locale, String> pluralLabelMap,
			String scope, String titleObjectFieldName, int version,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			List<ObjectField> objectFields)
		throws Exception {

		return objectDefinitionLocalService.addSystemObjectDefinition(
			null, userId, 0, null, dbTableName, false, true, labelMap, true,
			name, null, null, pkObjectFieldDBColumnName, pkObjectFieldName,
			pluralLabelMap, false, scope, titleObjectFieldName, version,
			WorkflowConstants.STATUS_DRAFT, objectFields);
	}

	public static ObjectDefinition addUnmodifiableSystemObjectDefinition(
			String externalReferenceCode, long userId, String className,
			String dbTableName, Map<Locale, String> labelMap, String name,
			String pkObjectFieldDBColumnName, String pkObjectFieldName,
			Map<Locale, String> pluralLabelMap, String scope,
			String titleObjectFieldName, int version,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			List<ObjectField> objectFields)
		throws Exception {

		return objectDefinitionLocalService.addSystemObjectDefinition(
			externalReferenceCode, userId, 0, className, dbTableName, false,
			true, labelMap, false, name, null, null, pkObjectFieldDBColumnName,
			pkObjectFieldName, pluralLabelMap, false, scope,
			titleObjectFieldName, version, WorkflowConstants.STATUS_APPROVED,
			objectFields);
	}

	public static String getRandomName() {
		return "A" + RandomTestUtil.randomString();
	}

	public static ObjectDefinition publishObjectDefinition(
			boolean localized, String name, List<ObjectField> objectFields,
			String scope, long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				userId, 0, false, true, localized, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				name, null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				objectFields);

		return ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		return publishObjectDefinition(
			getRandomName(), objectFields,
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields, String scope)
		throws Exception {

		return publishObjectDefinition(
			getRandomName(), objectFields, scope, TestPropsValues.getUserId());
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields, String scope, long userId)
		throws Exception {

		return publishObjectDefinition(
			getRandomName(), objectFields, scope, userId);
	}

	public static ObjectDefinition publishObjectDefinition(
			String name, List<ObjectField> objectFields, String scope)
		throws Exception {

		return publishObjectDefinition(
			name, objectFields, scope, TestPropsValues.getUserId());
	}

	public static ObjectDefinition publishObjectDefinition(
			String name, List<ObjectField> objectFields, String scope,
			long userId)
		throws Exception {

		return publishObjectDefinition(
			false, name, objectFields, scope, userId);
	}

}