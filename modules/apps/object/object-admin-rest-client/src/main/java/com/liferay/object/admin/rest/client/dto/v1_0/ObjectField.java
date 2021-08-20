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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String type;

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

}