/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.CollectionDisplayViewportSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class CollectionDisplayViewport implements Cloneable, Serializable {

	public static CollectionDisplayViewport toDTO(String json) {
		return CollectionDisplayViewportSerDes.toDTO(json);
	}

	public CollectionDisplayViewportDefinition
		getCollectionDisplayViewportDefinition() {

		return collectionDisplayViewportDefinition;
	}

	public void setCollectionDisplayViewportDefinition(
		CollectionDisplayViewportDefinition
			collectionDisplayViewportDefinition) {

		this.collectionDisplayViewportDefinition =
			collectionDisplayViewportDefinition;
	}

	public void setCollectionDisplayViewportDefinition(
		UnsafeSupplier<CollectionDisplayViewportDefinition, Exception>
			collectionDisplayViewportDefinitionUnsafeSupplier) {

		try {
			collectionDisplayViewportDefinition =
				collectionDisplayViewportDefinitionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionDisplayViewportDefinition
		collectionDisplayViewportDefinition;

	public Id getId() {
		return id;
	}

	public String getIdAsString() {
		if (id == null) {
			return null;
		}

		return id.toString();
	}

	public void setId(Id id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Id, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Id id;

	@Override
	public CollectionDisplayViewport clone() throws CloneNotSupportedException {
		return (CollectionDisplayViewport)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionDisplayViewport)) {
			return false;
		}

		CollectionDisplayViewport collectionDisplayViewport =
			(CollectionDisplayViewport)object;

		return Objects.equals(toString(), collectionDisplayViewport.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionDisplayViewportSerDes.toJSON(this);
	}

	public static enum Id {

		DESKTOP("Desktop"), LANDSCAPE_MOBILE("LandscapeMobile"),
		PORTRAIT_MOBILE("PortraitMobile"), TABLET("Tablet");

		public static Id create(String value) {
			for (Id id : values()) {
				if (Objects.equals(id.getValue(), value) ||
					Objects.equals(id.name(), value)) {

					return id;
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

		private Id(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:2105197281