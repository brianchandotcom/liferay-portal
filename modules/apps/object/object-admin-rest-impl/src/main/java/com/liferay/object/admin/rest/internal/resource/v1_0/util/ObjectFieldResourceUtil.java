/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.resource.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectField;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldResourceUtil {

	/**
	 * See {@link
	 * com.liferay.object.admin.rest.internal.resource.v1_0.BaseObjectFieldResourceImpl#patchObjectField(
	 * Long, ObjectField)}
	 */
	public static ObjectField patchObjectField(
		ObjectField existingObjectField, ObjectField objectField) {

		if (existingObjectField == null) {
			return objectField;
		}

		if (objectField.getBusinessType() != null) {
			existingObjectField.setBusinessType(objectField::getBusinessType);
		}

		if (objectField.getDBType() != null) {
			existingObjectField.setDBType(objectField::getDBType);
		}

		if (objectField.getDefaultValue() != null) {
			existingObjectField.setDefaultValue(objectField::getDefaultValue);
		}

		if (objectField.getExternalReferenceCode() != null) {
			existingObjectField.setExternalReferenceCode(
				objectField::getExternalReferenceCode);
		}

		if (objectField.getIndexed() != null) {
			existingObjectField.setIndexed(objectField::getIndexed);
		}

		if (objectField.getIndexedAsKeyword() != null) {
			existingObjectField.setIndexedAsKeyword(
				objectField::getIndexedAsKeyword);
		}

		if (objectField.getIndexedLanguageId() != null) {
			existingObjectField.setIndexedLanguageId(
				objectField::getIndexedLanguageId);
		}

		if (objectField.getLabel() != null) {
			existingObjectField.setLabel(objectField::getLabel);
		}

		if (objectField.getListTypeDefinitionExternalReferenceCode() != null) {
			existingObjectField.setListTypeDefinitionExternalReferenceCode(
				objectField::getListTypeDefinitionExternalReferenceCode);
		}

		if (objectField.getListTypeDefinitionId() != null) {
			existingObjectField.setListTypeDefinitionId(
				objectField::getListTypeDefinitionId);
		}

		if (objectField.getLocalized() != null) {
			existingObjectField.setLocalized(objectField::getLocalized);
		}

		if (objectField.getName() != null) {
			existingObjectField.setName(objectField::getName);
		}

		if (objectField.getObjectDefinitionExternalReferenceCode1() != null) {
			existingObjectField.setObjectDefinitionExternalReferenceCode1(
				objectField::getObjectDefinitionExternalReferenceCode1);
		}

		if (objectField.getObjectFieldSettings() != null) {
			existingObjectField.setObjectFieldSettings(
				objectField::getObjectFieldSettings);
		}

		if (objectField.getObjectRelationshipExternalReferenceCode() != null) {
			existingObjectField.setObjectRelationshipExternalReferenceCode(
				objectField::getObjectRelationshipExternalReferenceCode);
		}

		if (objectField.getReadOnly() != null) {
			existingObjectField.setReadOnly(objectField::getReadOnly);
		}

		if (objectField.getReadOnlyConditionExpression() != null) {
			existingObjectField.setReadOnlyConditionExpression(
				objectField::getReadOnlyConditionExpression);
		}

		if (objectField.getRequired() != null) {
			existingObjectField.setRequired(objectField::getRequired);
		}

		if (objectField.getState() != null) {
			existingObjectField.setState(objectField::getState);
		}

		if (objectField.getSystem() != null) {
			existingObjectField.setSystem(objectField::getSystem);
		}

		if (objectField.getType() != null) {
			existingObjectField.setType(objectField::getType);
		}

		return existingObjectField;
	}

}