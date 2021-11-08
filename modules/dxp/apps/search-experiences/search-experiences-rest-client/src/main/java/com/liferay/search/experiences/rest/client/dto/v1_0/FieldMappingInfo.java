/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.FieldMappingInfoSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class FieldMappingInfo implements Cloneable, Serializable {

	public static FieldMappingInfo toDTO(String json) {
		return FieldMappingInfoSerDes.toDTO(json);
	}

	public Integer getLanguageIdPosition() {
		return languageIdPosition;
	}

	public void setLanguageIdPosition(Integer languageIdPosition) {
		this.languageIdPosition = languageIdPosition;
	}

	public void setLanguageIdPosition(
		UnsafeSupplier<Integer, Exception> languageIdPositionUnsafeSupplier) {

		try {
			languageIdPosition = languageIdPositionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer languageIdPosition;

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
	public FieldMappingInfo clone() throws CloneNotSupportedException {
		return (FieldMappingInfo)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FieldMappingInfo)) {
			return false;
		}

		FieldMappingInfo fieldMappingInfo = (FieldMappingInfo)object;

		return Objects.equals(toString(), fieldMappingInfo.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FieldMappingInfoSerDes.toJSON(this);
	}

}