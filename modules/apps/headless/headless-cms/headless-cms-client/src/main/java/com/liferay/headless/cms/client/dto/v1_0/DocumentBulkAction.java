/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.DocumentBulkActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public abstract class DocumentBulkAction implements Cloneable, Serializable {

	public static DocumentBulkAction toDTO(String json) {
		return DocumentBulkActionSerDes.toDTO(json);
	}

	public Action getAction() {
		return action;
	}

	public String getActionAsString() {
		if (action == null) {
			return null;
		}

		return action.toString();
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setAction(
		UnsafeSupplier<Action, Exception> actionUnsafeSupplier) {

		try {
			action = actionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Action action;

	public Boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	public void setSelectAll(
		UnsafeSupplier<Boolean, Exception> selectAllUnsafeSupplier) {

		try {
			selectAll = selectAllUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean selectAll;

	public Selection[] getSelections() {
		return selections;
	}

	public void setSelections(Selection[] selections) {
		this.selections = selections;
	}

	public void setSelections(
		UnsafeSupplier<Selection[], Exception> selectionsUnsafeSupplier) {

		try {
			selections = selectionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Selection[] selections;

	@Override
	public DocumentBulkAction clone() throws CloneNotSupportedException {
		return (DocumentBulkAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DocumentBulkAction)) {
			return false;
		}

		DocumentBulkAction documentBulkAction = (DocumentBulkAction)object;

		return Objects.equals(toString(), documentBulkAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DocumentBulkActionSerDes.toJSON(this);
	}

	public static enum Action {

		DELETE_ACTION("DeleteAction"), KEYWORD_ACTION("KeywordAction"),
		MOVE_ACTION("MoveAction"), PERMISSION_ACTION("PermissionAction"),
		STATUS_ACTION("StatusAction"),
		TAXONOMY_CATEGORY_ACTION("TaxonomyCategoryAction");

		public static Action create(String value) {
			for (Action action : values()) {
				if (Objects.equals(action.getValue(), value) ||
					Objects.equals(action.name(), value)) {

					return action;
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

		private Action(String value) {
			_value = value;
		}

		private final String _value;

	}

}