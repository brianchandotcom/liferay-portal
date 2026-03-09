/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.EditObjectCategoriesBulkSelectionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class EditObjectCategoriesBulkSelectionAction
	extends BulkAction implements Cloneable, Serializable {

	public static EditObjectCategoriesBulkSelectionAction toDTO(String json) {
		return EditObjectCategoriesBulkSelectionActionSerDes.toDTO(json);
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

	public Long[] getTaxonomyCategoryIdsToAdd() {
		return taxonomyCategoryIdsToAdd;
	}

	public void setTaxonomyCategoryIdsToAdd(Long[] taxonomyCategoryIdsToAdd) {
		this.taxonomyCategoryIdsToAdd = taxonomyCategoryIdsToAdd;
	}

	public void setTaxonomyCategoryIdsToAdd(
		UnsafeSupplier<Long[], Exception>
			taxonomyCategoryIdsToAddUnsafeSupplier) {

		try {
			taxonomyCategoryIdsToAdd =
				taxonomyCategoryIdsToAddUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] taxonomyCategoryIdsToAdd;

	public Long[] getTaxonomyCategoryIdsToRemove() {
		return taxonomyCategoryIdsToRemove;
	}

	public void setTaxonomyCategoryIdsToRemove(
		Long[] taxonomyCategoryIdsToRemove) {

		this.taxonomyCategoryIdsToRemove = taxonomyCategoryIdsToRemove;
	}

	public void setTaxonomyCategoryIdsToRemove(
		UnsafeSupplier<Long[], Exception>
			taxonomyCategoryIdsToRemoveUnsafeSupplier) {

		try {
			taxonomyCategoryIdsToRemove =
				taxonomyCategoryIdsToRemoveUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] taxonomyCategoryIdsToRemove;

	@Override
	public EditObjectCategoriesBulkSelectionAction clone()
		throws CloneNotSupportedException {

		return (EditObjectCategoriesBulkSelectionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EditObjectCategoriesBulkSelectionAction)) {
			return false;
		}

		EditObjectCategoriesBulkSelectionAction
			editObjectCategoriesBulkSelectionAction =
				(EditObjectCategoriesBulkSelectionAction)object;

		return Objects.equals(
			toString(), editObjectCategoriesBulkSelectionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return EditObjectCategoriesBulkSelectionActionSerDes.toJSON(this);
	}

}