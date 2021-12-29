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
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectRelationshipSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectRelationship implements Cloneable, Serializable {

	public static ObjectRelationship toDTO(String json) {
		return ObjectRelationshipSerDes.toDTO(json);
	}

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

	public DeletionType getDeletionType() {
		return deletionType;
	}

	public String getDeletionTypeAsString() {
		if (deletionType == null) {
			return null;
		}

		return deletionType.toString();
	}

	public void setDeletionType(DeletionType deletionType) {
		this.deletionType = deletionType;
	}

	public void setDeletionType(
		UnsafeSupplier<DeletionType, Exception> deletionTypeUnsafeSupplier) {

		try {
			deletionType = deletionTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DeletionType deletionType;

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

	public Long getObjectDefinitionId1() {
		return objectDefinitionId1;
	}

	public void setObjectDefinitionId1(Long objectDefinitionId1) {
		this.objectDefinitionId1 = objectDefinitionId1;
	}

	public void setObjectDefinitionId1(
		UnsafeSupplier<Long, Exception> objectDefinitionId1UnsafeSupplier) {

		try {
			objectDefinitionId1 = objectDefinitionId1UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long objectDefinitionId1;

	public Long getObjectDefinitionId2() {
		return objectDefinitionId2;
	}

	public void setObjectDefinitionId2(Long objectDefinitionId2) {
		this.objectDefinitionId2 = objectDefinitionId2;
	}

	public void setObjectDefinitionId2(
		UnsafeSupplier<Long, Exception> objectDefinitionId2UnsafeSupplier) {

		try {
			objectDefinitionId2 = objectDefinitionId2UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long objectDefinitionId2;

	public String getObjectDefinitionName2() {
		return objectDefinitionName2;
	}

	public void setObjectDefinitionName2(String objectDefinitionName2) {
		this.objectDefinitionName2 = objectDefinitionName2;
	}

	public void setObjectDefinitionName2(
		UnsafeSupplier<String, Exception> objectDefinitionName2UnsafeSupplier) {

		try {
			objectDefinitionName2 = objectDefinitionName2UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String objectDefinitionName2;

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
	public ObjectRelationship clone() throws CloneNotSupportedException {
		return (ObjectRelationship)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectRelationship)) {
			return false;
		}

		ObjectRelationship objectRelationship = (ObjectRelationship)object;

		return Objects.equals(toString(), objectRelationship.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ObjectRelationshipSerDes.toJSON(this);
	}

	public static enum DeletionType {

		CASCADE("cascade"), DISASSOCIATE("disassociate"), PREVENT("prevent");

		public static DeletionType create(String value) {
			for (DeletionType deletionType : values()) {
				if (Objects.equals(deletionType.getValue(), value) ||
					Objects.equals(deletionType.name(), value)) {

					return deletionType;
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

		private DeletionType(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Type {

		ONE_TO_MANY("oneToMany"), ONE_TO_ONE("oneToOne"),
		MANY_TO_MANY("manyToMany");

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