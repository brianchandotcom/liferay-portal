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

package com.liferay.object.admin.rest.client.dto.v1_0;

import com.liferay.object.admin.rest.client.function.UnsafeSupplier;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectFieldSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectField implements Cloneable, Serializable {

	public static ObjectField toDTO(String json) {
		return ObjectFieldSerDes.toDTO(json);
	}

	public DBType getDBType() {
		return DBType;
	}

	public String getDBTypeAsString() {
		if (DBType == null) {
			return null;
		}

		return DBType.toString();
	}

	public void setDBType(DBType DBType) {
		this.DBType = DBType;
	}

	public void setDBType(
		UnsafeSupplier<DBType, Exception> DBTypeUnsafeSupplier) {

		try {
			DBType = DBTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DBType DBType;

	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Map<String, String>> actions;

	public BusinessType getBusinessType() {
		return businessType;
	}

	public String getBusinessTypeAsString() {
		if (businessType == null) {
			return null;
		}

		return businessType.toString();
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public void setBusinessType(
		UnsafeSupplier<BusinessType, Exception> businessTypeUnsafeSupplier) {

		try {
			businessType = businessTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BusinessType businessType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public Boolean getIndexed() {
		return indexed;
	}

	public void setIndexed(Boolean indexed) {
		this.indexed = indexed;
	}

	public void setIndexed(
		UnsafeSupplier<Boolean, Exception> indexedUnsafeSupplier) {

		try {
			indexed = indexedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean indexed;

	public Boolean getIndexedAsKeyword() {
		return indexedAsKeyword;
	}

	public void setIndexedAsKeyword(Boolean indexedAsKeyword) {
		this.indexedAsKeyword = indexedAsKeyword;
	}

	public void setIndexedAsKeyword(
		UnsafeSupplier<Boolean, Exception> indexedAsKeywordUnsafeSupplier) {

		try {
			indexedAsKeyword = indexedAsKeywordUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean indexedAsKeyword;

	public String getIndexedLanguageId() {
		return indexedLanguageId;
	}

	public void setIndexedLanguageId(String indexedLanguageId) {
		this.indexedLanguageId = indexedLanguageId;
	}

	public void setIndexedLanguageId(
		UnsafeSupplier<String, Exception> indexedLanguageIdUnsafeSupplier) {

		try {
			indexedLanguageId = indexedLanguageIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String indexedLanguageId;

	public Map<String, String> getLabel() {
		return label;
	}

	public void setLabel(Map<String, String> label) {
		this.label = label;
	}

	public void setLabel(
		UnsafeSupplier<Map<String, String>, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> label;

	public Long getListTypeDefinitionId() {
		return listTypeDefinitionId;
	}

	public void setListTypeDefinitionId(Long listTypeDefinitionId) {
		this.listTypeDefinitionId = listTypeDefinitionId;
	}

	public void setListTypeDefinitionId(
		UnsafeSupplier<Long, Exception> listTypeDefinitionIdUnsafeSupplier) {

		try {
			listTypeDefinitionId = listTypeDefinitionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long listTypeDefinitionId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	public String getRelationshipTypeAsString() {
		if (relationshipType == null) {
			return null;
		}

		return relationshipType.toString();
	}

	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	public void setRelationshipType(
		UnsafeSupplier<RelationshipType, Exception>
			relationshipTypeUnsafeSupplier) {

		try {
			relationshipType = relationshipTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RelationshipType relationshipType;

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setRequired(
		UnsafeSupplier<Boolean, Exception> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean required;

	public Type getType() {
		return type;
	}

	public String getTypeAsString() {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Type type;

	@Override
	public ObjectField clone() throws CloneNotSupportedException {
		return (ObjectField)super.clone();
	}

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
		return ObjectFieldSerDes.toJSON(this);
	}

	public static enum BusinessType {

		BOOLEAN("Boolean"), DATE("Date"), DECIMAL("Decimal"),
		INTEGER("Integer"), LONG_INTEGER("LongInteger"), LONG_TEXT("LongText"),
		PICKLIST("Picklist"), PRECISION_DECIMAL("PrecisionDecimal"),
		RELATIONSHIP("Relationship"), TEXT("Text");

		public static BusinessType create(String value) {
			for (BusinessType businessType : values()) {
				if (Objects.equals(businessType.getValue(), value) ||
					Objects.equals(businessType.name(), value)) {

					return businessType;
				}
			}

			return null;
		}

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

	public static enum DBType {

		BIG_DECIMAL("BigDecimal"), BOOLEAN("Boolean"), CLOB("Clob"),
		DATE("Date"), DOUBLE("Double"), INTEGER("Integer"), LONG("Long"),
		STRING("String");

		public static DBType create(String value) {
			for (DBType dbType : values()) {
				if (Objects.equals(dbType.getValue(), value) ||
					Objects.equals(dbType.name(), value)) {

					return dbType;
				}
			}

			return null;
		}

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

	public static enum RelationshipType {

		ONE_TO_MANY("oneToMany"), ONE_TO_ONE("oneToOne");

		public static RelationshipType create(String value) {
			for (RelationshipType relationshipType : values()) {
				if (Objects.equals(relationshipType.getValue(), value) ||
					Objects.equals(relationshipType.name(), value)) {

					return relationshipType;
				}
			}

			return null;
		}

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

	public static enum Type {

		BIG_DECIMAL("BigDecimal"), BOOLEAN("Boolean"), CLOB("Clob"),
		DATE("Date"), DOUBLE("Double"), INTEGER("Integer"), LONG("Long"),
		STRING("String");

		public static Type create(String value) {
			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value) ||
					Objects.equals(type.name(), value)) {

					return type;
				}
			}

			return null;
		}

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

}