/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.PreviewPortletDataHandlerSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class PreviewPortletDataHandler implements Cloneable, Serializable {

	public static PreviewPortletDataHandler toDTO(String json) {
		return PreviewPortletDataHandlerSerDes.toDTO(json);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLabel(
		UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String label;

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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setTag(UnsafeSupplier<String, Exception> tagUnsafeSupplier) {
		try {
			tag = tagUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String tag;

	@Override
	public PreviewPortletDataHandler clone() throws CloneNotSupportedException {
		return (PreviewPortletDataHandler)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PreviewPortletDataHandler)) {
			return false;
		}

		PreviewPortletDataHandler previewPortletDataHandler =
			(PreviewPortletDataHandler)object;

		return Objects.equals(toString(), previewPortletDataHandler.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PreviewPortletDataHandlerSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-248876666