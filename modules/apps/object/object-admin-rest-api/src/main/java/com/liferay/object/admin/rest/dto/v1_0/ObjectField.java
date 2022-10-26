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

package com.liferay.object.admin.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("ObjectField")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ObjectField")
public class ObjectField implements Serializable {

	public static ObjectField toDTO(String json) {
		return ObjectMapperUtil.readValue(ObjectField.class, json);
	}

	public static ObjectField unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ObjectField.class, json);
	}

	@Schema
	@Valid
	public DBType getDBType() {
		return DBType;
	}

	@JsonIgnore
	public String getDBTypeAsString() {
		if (DBType == null) {
			return null;
		}

		return DBType.toString();
	}

	public void setDBType(DBType DBType) {
		this.DBType = DBType;
	}

	@JsonIgnore
	public void setDBType(
		UnsafeSupplier<DBType, Exception> DBTypeUnsafeSupplier) {

		try {
			DBType = DBTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DBType DBType;

	@Schema
	@Valid
	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	@JsonIgnore
	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, Map<String, String>> actions;

	@Schema
	@Valid
	public BusinessType getBusinessType() {
		return businessType;
	}

	@JsonIgnore
	public String getBusinessTypeAsString() {
		if (businessType == null) {
			return null;
		}

		return businessType.toString();
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	@JsonIgnore
	public void setBusinessType(
		UnsafeSupplier<BusinessType, Exception> businessTypeUnsafeSupplier) {

		try {
			businessType = businessTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BusinessType businessType;

	@Schema
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@JsonIgnore
	public void setDefaultValue(
		UnsafeSupplier<String, Exception> defaultValueUnsafeSupplier) {

		try {
			defaultValue = defaultValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String defaultValue;

	@Schema
	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String externalReferenceCode;

	@Schema
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@Schema
	public Boolean getIndexed() {
		return indexed;
	}

	public void setIndexed(Boolean indexed) {
		this.indexed = indexed;
	}

	@JsonIgnore
	public void setIndexed(
		UnsafeSupplier<Boolean, Exception> indexedUnsafeSupplier) {

		try {
			indexed = indexedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean indexed;

	@Schema
	public Boolean getIndexedAsKeyword() {
		return indexedAsKeyword;
	}

	public void setIndexedAsKeyword(Boolean indexedAsKeyword) {
		this.indexedAsKeyword = indexedAsKeyword;
	}

	@JsonIgnore
	public void setIndexedAsKeyword(
		UnsafeSupplier<Boolean, Exception> indexedAsKeywordUnsafeSupplier) {

		try {
			indexedAsKeyword = indexedAsKeywordUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean indexedAsKeyword;

	@Schema
	public String getIndexedLanguageId() {
		return indexedLanguageId;
	}

	public void setIndexedLanguageId(String indexedLanguageId) {
		this.indexedLanguageId = indexedLanguageId;
	}

	@JsonIgnore
	public void setIndexedLanguageId(
		UnsafeSupplier<String, Exception> indexedLanguageIdUnsafeSupplier) {

		try {
			indexedLanguageId = indexedLanguageIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String indexedLanguageId;

	@Schema
	@Valid
	public Map<String, String> getLabel() {
		return label;
	}

	public void setLabel(Map<String, String> label) {
		this.label = label;
	}

	@JsonIgnore
	public void setLabel(
		UnsafeSupplier<Map<String, String>, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> label;

	@Schema
	public String getListTypeDefinitionExternalReferenceCode() {
		return listTypeDefinitionExternalReferenceCode;
	}

	public void setListTypeDefinitionExternalReferenceCode(
		String listTypeDefinitionExternalReferenceCode) {

		this.listTypeDefinitionExternalReferenceCode =
			listTypeDefinitionExternalReferenceCode;
	}

	@JsonIgnore
	public void setListTypeDefinitionExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			listTypeDefinitionExternalReferenceCodeUnsafeSupplier) {

		try {
			listTypeDefinitionExternalReferenceCode =
				listTypeDefinitionExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String listTypeDefinitionExternalReferenceCode;

	@Schema
	public Long getListTypeDefinitionId() {
		return listTypeDefinitionId;
	}

	public void setListTypeDefinitionId(Long listTypeDefinitionId) {
		this.listTypeDefinitionId = listTypeDefinitionId;
	}

	@JsonIgnore
	public void setListTypeDefinitionId(
		UnsafeSupplier<Long, Exception> listTypeDefinitionIdUnsafeSupplier) {

		try {
			listTypeDefinitionId = listTypeDefinitionIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long listTypeDefinitionId;

	@Schema
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	@Schema
	@Valid
	public ObjectFieldSetting[] getObjectFieldSettings() {
		return objectFieldSettings;
	}

	public void setObjectFieldSettings(
		ObjectFieldSetting[] objectFieldSettings) {

		this.objectFieldSettings = objectFieldSettings;
	}

	@JsonIgnore
	public void setObjectFieldSettings(
		UnsafeSupplier<ObjectFieldSetting[], Exception>
			objectFieldSettingsUnsafeSupplier) {

		try {
			objectFieldSettings = objectFieldSettingsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ObjectFieldSetting[] objectFieldSettings;

	@Schema
	@Valid
	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	@JsonIgnore
	public String getRelationshipTypeAsString() {
		if (relationshipType == null) {
			return null;
		}

		return relationshipType.toString();
	}

	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	@JsonIgnore
	public void setRelationshipType(
		UnsafeSupplier<RelationshipType, Exception>
			relationshipTypeUnsafeSupplier) {

		try {
			relationshipType = relationshipTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected RelationshipType relationshipType;

	@Schema
	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@JsonIgnore
	public void setRequired(
		UnsafeSupplier<Boolean, Exception> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean required;

	@Schema
	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	@JsonIgnore
	public void setState(
		UnsafeSupplier<Boolean, Exception> stateUnsafeSupplier) {

		try {
			state = stateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean state;

	@Schema
	public Boolean getSystem() {
		return system;
	}

	public void setSystem(Boolean system) {
		this.system = system;
	}

	@JsonIgnore
	public void setSystem(
		UnsafeSupplier<Boolean, Exception> systemUnsafeSupplier) {

		try {
			system = systemUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean system;

	@Schema(deprecated = true)
	@Valid
	public Type getType() {
		return type;
	}

	@JsonIgnore
	public String getTypeAsString() {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Type type;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectField)) {
			return false;
		}

		ObjectField objectField = (ObjectField)object;

		return Objects.equals(toString(), objectField.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (DBType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"DBType\": ");

			sb.append("\"");

			sb.append(DBType);

			sb.append("\"");
		}

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		if (businessType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"businessType\": ");

			sb.append("\"");

			sb.append(businessType);

			sb.append("\"");
		}

		if (defaultValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValue\": ");

			sb.append("\"");

			sb.append(_escape(defaultValue));

			sb.append("\"");
		}

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (indexed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexed\": ");

			sb.append(indexed);
		}

		if (indexedAsKeyword != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexedAsKeyword\": ");

			sb.append(indexedAsKeyword);
		}

		if (indexedLanguageId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexedLanguageId\": ");

			sb.append("\"");

			sb.append(_escape(indexedLanguageId));

			sb.append("\"");
		}

		if (label != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append(_toJSON(label));
		}

		if (listTypeDefinitionExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listTypeDefinitionExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(listTypeDefinitionExternalReferenceCode));

			sb.append("\"");
		}

		if (listTypeDefinitionId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listTypeDefinitionId\": ");

			sb.append(listTypeDefinitionId);
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		if (objectFieldSettings != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFieldSettings\": ");

			sb.append("[");

			for (int i = 0; i < objectFieldSettings.length; i++) {
				sb.append(String.valueOf(objectFieldSettings[i]));

				if ((i + 1) < objectFieldSettings.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (relationshipType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relationshipType\": ");

			sb.append("\"");

			sb.append(relationshipType);

			sb.append("\"");
		}

		if (required != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(required);
		}

		if (state != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"state\": ");

			sb.append(state);
		}

		if (system != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"system\": ");

			sb.append(system);
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(type);

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.object.admin.rest.dto.v1_0.ObjectField",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("BusinessType")
	public static enum BusinessType {

		AGGREGATION("Aggregation"), ATTACHMENT("Attachment"),
		BOOLEAN("Boolean"), DATE("Date"), DECIMAL("Decimal"),
		FORMULA("Formula"), INTEGER("Integer"), LONG_INTEGER("LongInteger"),
		LONG_TEXT("LongText"), MULTISELECT_PICKLIST("MultiselectPicklist"),
		PICKLIST("Picklist"), PRECISION_DECIMAL("PrecisionDecimal"),
		RELATIONSHIP("Relationship"), RICH_TEXT("RichText"), TEXT("Text");

		@JsonCreator
		public static BusinessType create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (BusinessType businessType : values()) {
				if (Objects.equals(businessType.getValue(), value)) {
					return businessType;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private BusinessType(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("DBType")
	public static enum DBType {

		BIG_DECIMAL("BigDecimal"), BOOLEAN("Boolean"), CLOB("Clob"),
		DATE("Date"), DOUBLE("Double"), INTEGER("Integer"), LONG("Long"),
		STRING("String");

		@JsonCreator
		public static DBType create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (DBType dbType : values()) {
				if (Objects.equals(dbType.getValue(), value)) {
					return dbType;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private DBType(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("RelationshipType")
	public static enum RelationshipType {

		ONE_TO_MANY("oneToMany"), ONE_TO_ONE("oneToOne");

		@JsonCreator
		public static RelationshipType create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (RelationshipType relationshipType : values()) {
				if (Objects.equals(relationshipType.getValue(), value)) {
					return relationshipType;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private RelationshipType(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Type")
	public static enum Type {

		BIG_DECIMAL("BigDecimal"), BOOLEAN("Boolean"), CLOB("Clob"),
		DATE("Date"), DOUBLE("Double"), INTEGER("Integer"), LONG("Long"),
		STRING("String");

		@JsonCreator
		public static Type create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value)) {
					return type;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

}