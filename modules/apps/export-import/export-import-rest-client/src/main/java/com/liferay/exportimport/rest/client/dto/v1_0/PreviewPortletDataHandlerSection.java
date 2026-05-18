/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.PreviewPortletDataHandlerSectionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class PreviewPortletDataHandlerSection
	implements Cloneable, Serializable {

	public static PreviewPortletDataHandlerSection toDTO(String json) {
		return PreviewPortletDataHandlerSectionSerDes.toDTO(json);
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

	public PreviewPortletDataHandler[] getPreviewPortletDataHandlers() {
		return previewPortletDataHandlers;
	}

	public void setPreviewPortletDataHandlers(
		PreviewPortletDataHandler[] previewPortletDataHandlers) {

		this.previewPortletDataHandlers = previewPortletDataHandlers;
	}

	public void setPreviewPortletDataHandlers(
		UnsafeSupplier<PreviewPortletDataHandler[], Exception>
			previewPortletDataHandlersUnsafeSupplier) {

		try {
			previewPortletDataHandlers =
				previewPortletDataHandlersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PreviewPortletDataHandler[] previewPortletDataHandlers;

	@Override
	public PreviewPortletDataHandlerSection clone()
		throws CloneNotSupportedException {

		return (PreviewPortletDataHandlerSection)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PreviewPortletDataHandlerSection)) {
			return false;
		}

		PreviewPortletDataHandlerSection previewPortletDataHandlerSection =
			(PreviewPortletDataHandlerSection)object;

		return Objects.equals(
			toString(), previewPortletDataHandlerSection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PreviewPortletDataHandlerSectionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:2011130413