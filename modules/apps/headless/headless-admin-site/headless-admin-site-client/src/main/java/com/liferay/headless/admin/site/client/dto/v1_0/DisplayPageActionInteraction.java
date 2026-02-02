/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.DisplayPageActionInteractionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class DisplayPageActionInteraction
	extends ActionInteraction implements Cloneable, Serializable {

	public static DisplayPageActionInteraction toDTO(String json) {
		return DisplayPageActionInteractionSerDes.toDTO(json);
	}

	public String getMappingFieldKey() {
		return mappingFieldKey;
	}

	public void setMappingFieldKey(String mappingFieldKey) {
		this.mappingFieldKey = mappingFieldKey;
	}

	public void setMappingFieldKey(
		UnsafeSupplier<String, Exception> mappingFieldKeyUnsafeSupplier) {

		try {
			mappingFieldKey = mappingFieldKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String mappingFieldKey;

	@Override
	public DisplayPageActionInteraction clone()
		throws CloneNotSupportedException {

		return (DisplayPageActionInteraction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageActionInteraction)) {
			return false;
		}

		DisplayPageActionInteraction displayPageActionInteraction =
			(DisplayPageActionInteraction)object;

		return Objects.equals(
			toString(), displayPageActionInteraction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DisplayPageActionInteractionSerDes.toJSON(this);
	}

}