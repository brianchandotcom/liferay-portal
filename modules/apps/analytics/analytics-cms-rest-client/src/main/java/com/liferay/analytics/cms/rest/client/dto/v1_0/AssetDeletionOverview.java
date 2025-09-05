/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.AssetDeletionOverviewSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class AssetDeletionOverview implements Cloneable, Serializable {

	public static AssetDeletionOverview toDTO(String json) {
		return AssetDeletionOverviewSerDes.toDTO(json);
	}

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

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setMimeType(
		UnsafeSupplier<String, Exception> mimeTypeUnsafeSupplier) {

		try {
			mimeType = mimeTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String mimeType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String title;

	public Integer getUsages() {
		return usages;
	}

	public void setUsages(Integer usages) {
		this.usages = usages;
	}

	public void setUsages(
		UnsafeSupplier<Integer, Exception> usagesUnsafeSupplier) {

		try {
			usages = usagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer usages;

	@Override
	public AssetDeletionOverview clone() throws CloneNotSupportedException {
		return (AssetDeletionOverview)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetDeletionOverview)) {
			return false;
		}

		AssetDeletionOverview assetDeletionOverview =
			(AssetDeletionOverview)object;

		return Objects.equals(toString(), assetDeletionOverview.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AssetDeletionOverviewSerDes.toJSON(this);
	}

	public static enum DeletionType {

		PERMANENT_DELETION("PERMANENT_DELETION"), RECYCLE_BIN("RECYCLE_BIN");

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

}