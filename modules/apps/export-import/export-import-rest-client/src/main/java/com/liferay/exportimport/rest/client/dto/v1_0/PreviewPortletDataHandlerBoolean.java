/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.PreviewPortletDataHandlerBooleanSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class PreviewPortletDataHandlerBoolean
	extends PreviewPortletDataHandlerControl
	implements Cloneable, Serializable {

	public static PreviewPortletDataHandlerBoolean toDTO(String json) {
		return PreviewPortletDataHandlerBooleanSerDes.toDTO(json);
	}

	public Long getAdditionCount() {
		return additionCount;
	}

	public void setAdditionCount(Long additionCount) {
		this.additionCount = additionCount;
	}

	public void setAdditionCount(
		UnsafeSupplier<Long, Exception> additionCountUnsafeSupplier) {

		try {
			additionCount = additionCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long additionCount;

	public Boolean getDefaultState() {
		return defaultState;
	}

	public void setDefaultState(Boolean defaultState) {
		this.defaultState = defaultState;
	}

	public void setDefaultState(
		UnsafeSupplier<Boolean, Exception> defaultStateUnsafeSupplier) {

		try {
			defaultState = defaultStateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean defaultState;

	public Long getDeletionCount() {
		return deletionCount;
	}

	public void setDeletionCount(Long deletionCount) {
		this.deletionCount = deletionCount;
	}

	public void setDeletionCount(
		UnsafeSupplier<Long, Exception> deletionCountUnsafeSupplier) {

		try {
			deletionCount = deletionCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long deletionCount;

	public PreviewPortletDataHandlerControl[]
		getPreviewPortletDataHandlerControls() {

		return previewPortletDataHandlerControls;
	}

	public void setPreviewPortletDataHandlerControls(
		PreviewPortletDataHandlerControl[] previewPortletDataHandlerControls) {

		this.previewPortletDataHandlerControls =
			previewPortletDataHandlerControls;
	}

	public void setPreviewPortletDataHandlerControls(
		UnsafeSupplier<PreviewPortletDataHandlerControl[], Exception>
			previewPortletDataHandlerControlsUnsafeSupplier) {

		try {
			previewPortletDataHandlerControls =
				previewPortletDataHandlerControlsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PreviewPortletDataHandlerControl[]
		previewPortletDataHandlerControls;

	@Override
	public PreviewPortletDataHandlerBoolean clone()
		throws CloneNotSupportedException {

		return (PreviewPortletDataHandlerBoolean)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PreviewPortletDataHandlerBoolean)) {
			return false;
		}

		PreviewPortletDataHandlerBoolean previewPortletDataHandlerBoolean =
			(PreviewPortletDataHandlerBoolean)object;

		return Objects.equals(
			toString(), previewPortletDataHandlerBoolean.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PreviewPortletDataHandlerBooleanSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-225201440