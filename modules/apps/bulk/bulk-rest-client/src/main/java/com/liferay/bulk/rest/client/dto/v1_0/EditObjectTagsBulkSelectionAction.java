/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.EditObjectTagsBulkSelectionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class EditObjectTagsBulkSelectionAction
	extends BulkAction implements Cloneable, Serializable {

	public static EditObjectTagsBulkSelectionAction toDTO(String json) {
		return EditObjectTagsBulkSelectionActionSerDes.toDTO(json);
	}

	public Boolean getAppend() {
		return append;
	}

	public void setAppend(Boolean append) {
		this.append = append;
	}

	public void setAppend(
		UnsafeSupplier<Boolean, Exception> appendUnsafeSupplier) {

		try {
			append = appendUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean append;

	public String[] getKeywordsToAdd() {
		return keywordsToAdd;
	}

	public void setKeywordsToAdd(String[] keywordsToAdd) {
		this.keywordsToAdd = keywordsToAdd;
	}

	public void setKeywordsToAdd(
		UnsafeSupplier<String[], Exception> keywordsToAddUnsafeSupplier) {

		try {
			keywordsToAdd = keywordsToAddUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] keywordsToAdd;

	public String[] getKeywordsToRemove() {
		return keywordsToRemove;
	}

	public void setKeywordsToRemove(String[] keywordsToRemove) {
		this.keywordsToRemove = keywordsToRemove;
	}

	public void setKeywordsToRemove(
		UnsafeSupplier<String[], Exception> keywordsToRemoveUnsafeSupplier) {

		try {
			keywordsToRemove = keywordsToRemoveUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] keywordsToRemove;

	@Override
	public EditObjectTagsBulkSelectionAction clone()
		throws CloneNotSupportedException {

		return (EditObjectTagsBulkSelectionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EditObjectTagsBulkSelectionAction)) {
			return false;
		}

		EditObjectTagsBulkSelectionAction editObjectTagsBulkSelectionAction =
			(EditObjectTagsBulkSelectionAction)object;

		return Objects.equals(
			toString(), editObjectTagsBulkSelectionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return EditObjectTagsBulkSelectionActionSerDes.toJSON(this);
	}

}