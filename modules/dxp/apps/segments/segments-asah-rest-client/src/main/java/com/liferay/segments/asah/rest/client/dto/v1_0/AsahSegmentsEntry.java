/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.client.dto.v1_0;

import com.liferay.segments.asah.rest.client.function.UnsafeSupplier;
import com.liferay.segments.asah.rest.client.serdes.v1_0.AsahSegmentsEntrySerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AsahSegmentsEntry implements Cloneable, Serializable {

	public static AsahSegmentsEntry toDTO(String json) {
		return AsahSegmentsEntrySerDes.toDTO(json);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	public Membership[] getMemberships() {
		return memberships;
	}

	public void setMemberships(Membership[] memberships) {
		this.memberships = memberships;
	}

	public void setMemberships(
		UnsafeSupplier<Membership[], Exception> membershipsUnsafeSupplier) {

		try {
			memberships = membershipsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Membership[] memberships;

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

	@Override
	public AsahSegmentsEntry clone() throws CloneNotSupportedException {
		return (AsahSegmentsEntry)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AsahSegmentsEntry)) {
			return false;
		}

		AsahSegmentsEntry asahSegmentsEntry = (AsahSegmentsEntry)object;

		return Objects.equals(toString(), asahSegmentsEntry.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AsahSegmentsEntrySerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-397489301