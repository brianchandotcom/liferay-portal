/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.client.serdes.v1_0;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectValidationRule;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectDefinitionSerDes {

	public static ObjectDefinition toDTO(String json) {
		ObjectDefinitionJSONParser objectDefinitionJSONParser =
			new ObjectDefinitionJSONParser();

		return objectDefinitionJSONParser.parseToDTO(json);
	}

	public static ObjectDefinition[] toDTOs(String json) {
		ObjectDefinitionJSONParser objectDefinitionJSONParser =
			new ObjectDefinitionJSONParser();

		return objectDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectDefinition objectDefinition) {
		if (objectDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectDefinition.getAccountEntryRestricted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryRestricted\": ");

			sb.append(objectDefinition.getAccountEntryRestricted());
		}

		if (objectDefinition.getAccountEntryRestrictedObjectFieldName() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryRestrictedObjectFieldName\": ");

			sb.append("\"");

			sb.append(
				_escape(
					objectDefinition.
						getAccountEntryRestrictedObjectFieldName()));

			sb.append("\"");
		}

		if (objectDefinition.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(objectDefinition.getActions()));
		}

		if (objectDefinition.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(objectDefinition.getActive());
		}

		if (objectDefinition.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					objectDefinition.getDateCreated()));

			sb.append("\"");
		}

		if (objectDefinition.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					objectDefinition.getDateModified()));

			sb.append("\"");
		}

		if (objectDefinition.getDefaultLanguageId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultLanguageId\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getDefaultLanguageId()));

			sb.append("\"");
		}

		if (objectDefinition.getEnableCategorization() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableCategorization\": ");

			sb.append(objectDefinition.getEnableCategorization());
		}

		if (objectDefinition.getEnableComments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableComments\": ");

			sb.append(objectDefinition.getEnableComments());
		}

		if (objectDefinition.getEnableLocalization() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableLocalization\": ");

			sb.append(objectDefinition.getEnableLocalization());
		}

		if (objectDefinition.getEnableObjectEntryDraft() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableObjectEntryDraft\": ");

			sb.append(objectDefinition.getEnableObjectEntryDraft());
		}

		if (objectDefinition.getEnableObjectEntryHistory() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableObjectEntryHistory\": ");

			sb.append(objectDefinition.getEnableObjectEntryHistory());
		}

		if (objectDefinition.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (objectDefinition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectDefinition.getId());
		}

		if (objectDefinition.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append(_toJSON(objectDefinition.getLabel()));
		}

		if (objectDefinition.getModifiable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiable\": ");

			sb.append(objectDefinition.getModifiable());
		}

		if (objectDefinition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getName()));

			sb.append("\"");
		}

		if (objectDefinition.getObjectActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectActions\": ");

			sb.append("[");

			for (int i = 0; i < objectDefinition.getObjectActions().length;
				 i++) {

				sb.append(
					String.valueOf(objectDefinition.getObjectActions()[i]));

				if ((i + 1) < objectDefinition.getObjectActions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFields\": ");

			sb.append("[");

			for (int i = 0; i < objectDefinition.getObjectFields().length;
				 i++) {

				sb.append(
					String.valueOf(objectDefinition.getObjectFields()[i]));

				if ((i + 1) < objectDefinition.getObjectFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectFolderExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFolderExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					objectDefinition.getObjectFolderExternalReferenceCode()));

			sb.append("\"");
		}

		if (objectDefinition.getObjectLayouts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectLayouts\": ");

			sb.append("[");

			for (int i = 0; i < objectDefinition.getObjectLayouts().length;
				 i++) {

				sb.append(
					String.valueOf(objectDefinition.getObjectLayouts()[i]));

				if ((i + 1) < objectDefinition.getObjectLayouts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectRelationships() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectRelationships\": ");

			sb.append("[");

			for (int i = 0;
				 i < objectDefinition.getObjectRelationships().length; i++) {

				sb.append(
					String.valueOf(
						objectDefinition.getObjectRelationships()[i]));

				if ((i + 1) <
						objectDefinition.getObjectRelationships().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectValidationRules() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectValidationRules\": ");

			sb.append("[");

			for (int i = 0;
				 i < objectDefinition.getObjectValidationRules().length; i++) {

				sb.append(
					String.valueOf(
						objectDefinition.getObjectValidationRules()[i]));

				if ((i + 1) <
						objectDefinition.getObjectValidationRules().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectViews() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectViews\": ");

			sb.append("[");

			for (int i = 0; i < objectDefinition.getObjectViews().length; i++) {
				sb.append(String.valueOf(objectDefinition.getObjectViews()[i]));

				if ((i + 1) < objectDefinition.getObjectViews().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getPanelAppOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"panelAppOrder\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getPanelAppOrder()));

			sb.append("\"");
		}

		if (objectDefinition.getPanelCategoryKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"panelCategoryKey\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getPanelCategoryKey()));

			sb.append("\"");
		}

		if (objectDefinition.getParameterRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameterRequired\": ");

			sb.append(objectDefinition.getParameterRequired());
		}

		if (objectDefinition.getPluralLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pluralLabel\": ");

			sb.append(_toJSON(objectDefinition.getPluralLabel()));
		}

		if (objectDefinition.getPortlet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portlet\": ");

			sb.append(objectDefinition.getPortlet());
		}

		if (objectDefinition.getRestContextPath() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"restContextPath\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getRestContextPath()));

			sb.append("\"");
		}

		if (objectDefinition.getRootObjectDefinitionExternalReferenceCode() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rootObjectDefinitionExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					objectDefinition.
						getRootObjectDefinitionExternalReferenceCode()));

			sb.append("\"");
		}

		if (objectDefinition.getScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scope\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getScope()));

			sb.append("\"");
		}

		if (objectDefinition.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(String.valueOf(objectDefinition.getStatus()));
		}

		if (objectDefinition.getStorageType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"storageType\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getStorageType()));

			sb.append("\"");
		}

		if (objectDefinition.getSystem() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"system\": ");

			sb.append(objectDefinition.getSystem());
		}

		if (objectDefinition.getTitleObjectFieldName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"titleObjectFieldName\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getTitleObjectFieldName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectDefinitionJSONParser objectDefinitionJSONParser =
			new ObjectDefinitionJSONParser();

		return objectDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectDefinition objectDefinition) {
		if (objectDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectDefinition.getAccountEntryRestricted() == null) {
			map.put("accountEntryRestricted", null);
		}
		else {
			map.put(
				"accountEntryRestricted",
				String.valueOf(objectDefinition.getAccountEntryRestricted()));
		}

		if (objectDefinition.getAccountEntryRestrictedObjectFieldName() ==
				null) {

			map.put("accountEntryRestrictedObjectFieldName", null);
		}
		else {
			map.put(
				"accountEntryRestrictedObjectFieldName",
				String.valueOf(
					objectDefinition.
						getAccountEntryRestrictedObjectFieldName()));
		}

		if (objectDefinition.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(objectDefinition.getActions()));
		}

		if (objectDefinition.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(objectDefinition.getActive()));
		}

		if (objectDefinition.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					objectDefinition.getDateCreated()));
		}

		if (objectDefinition.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					objectDefinition.getDateModified()));
		}

		if (objectDefinition.getDefaultLanguageId() == null) {
			map.put("defaultLanguageId", null);
		}
		else {
			map.put(
				"defaultLanguageId",
				String.valueOf(objectDefinition.getDefaultLanguageId()));
		}

		if (objectDefinition.getEnableCategorization() == null) {
			map.put("enableCategorization", null);
		}
		else {
			map.put(
				"enableCategorization",
				String.valueOf(objectDefinition.getEnableCategorization()));
		}

		if (objectDefinition.getEnableComments() == null) {
			map.put("enableComments", null);
		}
		else {
			map.put(
				"enableComments",
				String.valueOf(objectDefinition.getEnableComments()));
		}

		if (objectDefinition.getEnableLocalization() == null) {
			map.put("enableLocalization", null);
		}
		else {
			map.put(
				"enableLocalization",
				String.valueOf(objectDefinition.getEnableLocalization()));
		}

		if (objectDefinition.getEnableObjectEntryDraft() == null) {
			map.put("enableObjectEntryDraft", null);
		}
		else {
			map.put(
				"enableObjectEntryDraft",
				String.valueOf(objectDefinition.getEnableObjectEntryDraft()));
		}

		if (objectDefinition.getEnableObjectEntryHistory() == null) {
			map.put("enableObjectEntryHistory", null);
		}
		else {
			map.put(
				"enableObjectEntryHistory",
				String.valueOf(objectDefinition.getEnableObjectEntryHistory()));
		}

		if (objectDefinition.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(objectDefinition.getExternalReferenceCode()));
		}

		if (objectDefinition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectDefinition.getId()));
		}

		if (objectDefinition.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(objectDefinition.getLabel()));
		}

		if (objectDefinition.getModifiable() == null) {
			map.put("modifiable", null);
		}
		else {
			map.put(
				"modifiable", String.valueOf(objectDefinition.getModifiable()));
		}

		if (objectDefinition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectDefinition.getName()));
		}

		if (objectDefinition.getObjectActions() == null) {
			map.put("objectActions", null);
		}
		else {
			map.put(
				"objectActions",
				String.valueOf(objectDefinition.getObjectActions()));
		}

		if (objectDefinition.getObjectFields() == null) {
			map.put("objectFields", null);
		}
		else {
			map.put(
				"objectFields",
				String.valueOf(objectDefinition.getObjectFields()));
		}

		if (objectDefinition.getObjectFolderExternalReferenceCode() == null) {
			map.put("objectFolderExternalReferenceCode", null);
		}
		else {
			map.put(
				"objectFolderExternalReferenceCode",
				String.valueOf(
					objectDefinition.getObjectFolderExternalReferenceCode()));
		}

		if (objectDefinition.getObjectLayouts() == null) {
			map.put("objectLayouts", null);
		}
		else {
			map.put(
				"objectLayouts",
				String.valueOf(objectDefinition.getObjectLayouts()));
		}

		if (objectDefinition.getObjectRelationships() == null) {
			map.put("objectRelationships", null);
		}
		else {
			map.put(
				"objectRelationships",
				String.valueOf(objectDefinition.getObjectRelationships()));
		}

		if (objectDefinition.getObjectValidationRules() == null) {
			map.put("objectValidationRules", null);
		}
		else {
			map.put(
				"objectValidationRules",
				String.valueOf(objectDefinition.getObjectValidationRules()));
		}

		if (objectDefinition.getObjectViews() == null) {
			map.put("objectViews", null);
		}
		else {
			map.put(
				"objectViews",
				String.valueOf(objectDefinition.getObjectViews()));
		}

		if (objectDefinition.getPanelAppOrder() == null) {
			map.put("panelAppOrder", null);
		}
		else {
			map.put(
				"panelAppOrder",
				String.valueOf(objectDefinition.getPanelAppOrder()));
		}

		if (objectDefinition.getPanelCategoryKey() == null) {
			map.put("panelCategoryKey", null);
		}
		else {
			map.put(
				"panelCategoryKey",
				String.valueOf(objectDefinition.getPanelCategoryKey()));
		}

		if (objectDefinition.getParameterRequired() == null) {
			map.put("parameterRequired", null);
		}
		else {
			map.put(
				"parameterRequired",
				String.valueOf(objectDefinition.getParameterRequired()));
		}

		if (objectDefinition.getPluralLabel() == null) {
			map.put("pluralLabel", null);
		}
		else {
			map.put(
				"pluralLabel",
				String.valueOf(objectDefinition.getPluralLabel()));
		}

		if (objectDefinition.getPortlet() == null) {
			map.put("portlet", null);
		}
		else {
			map.put("portlet", String.valueOf(objectDefinition.getPortlet()));
		}

		if (objectDefinition.getRestContextPath() == null) {
			map.put("restContextPath", null);
		}
		else {
			map.put(
				"restContextPath",
				String.valueOf(objectDefinition.getRestContextPath()));
		}

		if (objectDefinition.getRootObjectDefinitionExternalReferenceCode() ==
				null) {

			map.put("rootObjectDefinitionExternalReferenceCode", null);
		}
		else {
			map.put(
				"rootObjectDefinitionExternalReferenceCode",
				String.valueOf(
					objectDefinition.
						getRootObjectDefinitionExternalReferenceCode()));
		}

		if (objectDefinition.getScope() == null) {
			map.put("scope", null);
		}
		else {
			map.put("scope", String.valueOf(objectDefinition.getScope()));
		}

		if (objectDefinition.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(objectDefinition.getStatus()));
		}

		if (objectDefinition.getStorageType() == null) {
			map.put("storageType", null);
		}
		else {
			map.put(
				"storageType",
				String.valueOf(objectDefinition.getStorageType()));
		}

		if (objectDefinition.getSystem() == null) {
			map.put("system", null);
		}
		else {
			map.put("system", String.valueOf(objectDefinition.getSystem()));
		}

		if (objectDefinition.getTitleObjectFieldName() == null) {
			map.put("titleObjectFieldName", null);
		}
		else {
			map.put(
				"titleObjectFieldName",
				String.valueOf(objectDefinition.getTitleObjectFieldName()));
		}

		return map;
	}

	public static class ObjectDefinitionJSONParser
		extends BaseJSONParser<ObjectDefinition> {

		@Override
		protected ObjectDefinition createDTO() {
			return new ObjectDefinition();
		}

		@Override
		protected ObjectDefinition[] createDTOArray(int size) {
			return new ObjectDefinition[size];
		}

		@Override
		protected void setField(
			ObjectDefinition objectDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accountEntryRestricted")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setAccountEntryRestricted(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"accountEntryRestrictedObjectFieldName")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setAccountEntryRestrictedObjectFieldName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setActions(
						(Map)ObjectDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultLanguageId")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setDefaultLanguageId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "enableCategorization")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setEnableCategorization(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "enableComments")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setEnableComments(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "enableLocalization")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setEnableLocalization(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "enableObjectEntryDraft")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setEnableObjectEntryDraft(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "enableObjectEntryHistory")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setEnableObjectEntryHistory(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setLabel(
						(Map)ObjectDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiable")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setModifiable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectActions")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ObjectAction[] objectActionsArray =
						new ObjectAction[jsonParserFieldValues.length];

					for (int i = 0; i < objectActionsArray.length; i++) {
						objectActionsArray[i] = ObjectActionSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					objectDefinition.setObjectActions(objectActionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectFields")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ObjectField[] objectFieldsArray =
						new ObjectField[jsonParserFieldValues.length];

					for (int i = 0; i < objectFieldsArray.length; i++) {
						objectFieldsArray[i] = ObjectFieldSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					objectDefinition.setObjectFields(objectFieldsArray);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"objectFolderExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setObjectFolderExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectLayouts")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ObjectLayout[] objectLayoutsArray =
						new ObjectLayout[jsonParserFieldValues.length];

					for (int i = 0; i < objectLayoutsArray.length; i++) {
						objectLayoutsArray[i] = ObjectLayoutSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					objectDefinition.setObjectLayouts(objectLayoutsArray);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectRelationships")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ObjectRelationship[] objectRelationshipsArray =
						new ObjectRelationship[jsonParserFieldValues.length];

					for (int i = 0; i < objectRelationshipsArray.length; i++) {
						objectRelationshipsArray[i] =
							ObjectRelationshipSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					objectDefinition.setObjectRelationships(
						objectRelationshipsArray);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectValidationRules")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ObjectValidationRule[] objectValidationRulesArray =
						new ObjectValidationRule[jsonParserFieldValues.length];

					for (int i = 0; i < objectValidationRulesArray.length;
						 i++) {

						objectValidationRulesArray[i] =
							ObjectValidationRuleSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					objectDefinition.setObjectValidationRules(
						objectValidationRulesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectViews")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ObjectView[] objectViewsArray =
						new ObjectView[jsonParserFieldValues.length];

					for (int i = 0; i < objectViewsArray.length; i++) {
						objectViewsArray[i] = ObjectViewSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					objectDefinition.setObjectViews(objectViewsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "panelAppOrder")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setPanelAppOrder(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "panelCategoryKey")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setPanelCategoryKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parameterRequired")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setParameterRequired(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pluralLabel")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setPluralLabel(
						(Map)ObjectDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "portlet")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setPortlet((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "restContextPath")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setRestContextPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"rootObjectDefinitionExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.
						setRootObjectDefinitionExternalReferenceCode(
							(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "scope")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setScope((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setStatus(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "storageType")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setStorageType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "system")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setSystem((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "titleObjectFieldName")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setTitleObjectFieldName(
						(String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}