/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
@GraphQLName("DocumentBulkAction")
@JsonFilter("Liferay.Vulcan")
@JsonSubTypes(
	{
		@JsonSubTypes.Type(
			name = "CategoryAction", value = CategoryAction.class
		),
		@JsonSubTypes.Type(name = "DeleteAction", value = DeleteAction.class),
		@JsonSubTypes.Type(name = "MoveAction", value = MoveAction.class),
		@JsonSubTypes.Type(
			name = "PermissionAction", value = PermissionAction.class
		),
		@JsonSubTypes.Type(name = "StatusAction", value = StatusAction.class),
		@JsonSubTypes.Type(name = "TagAction", value = TagAction.class)
	}
)
@JsonTypeInfo(
	include = JsonTypeInfo.As.PROPERTY, property = "type",
	use = JsonTypeInfo.Id.NAME, visible = true
)
@XmlRootElement(name = "DocumentBulkAction")
public abstract class DocumentBulkAction implements Serializable {

	public static DocumentBulkAction toDTO(String json) {
		return ObjectMapperUtil.readValue(DocumentBulkAction.class, json);
	}

	public static DocumentBulkAction unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(DocumentBulkAction.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("action")
	@Valid
	public Action getAction() {
		if (_actionSupplier != null) {
			action = _actionSupplier.get();

			_actionSupplier = null;
		}

		return action;
	}

	@JsonIgnore
	public String getActionAsString() {
		Action action = getAction();

		if (action == null) {
			return null;
		}

		return action.toString();
	}

	public void setAction(Action action) {
		this.action = action;

		_actionSupplier = null;
	}

	@JsonIgnore
	public void setAction(
		UnsafeSupplier<Action, Exception> actionUnsafeSupplier) {

		_actionSupplier = () -> {
			try {
				return actionUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Action action;

	@JsonIgnore
	private Supplier<Action> _actionSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getSelectAll() {
		if (_selectAllSupplier != null) {
			selectAll = _selectAllSupplier.get();

			_selectAllSupplier = null;
		}

		return selectAll;
	}

	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;

		_selectAllSupplier = null;
	}

	@JsonIgnore
	public void setSelectAll(
		UnsafeSupplier<Boolean, Exception> selectAllUnsafeSupplier) {

		_selectAllSupplier = () -> {
			try {
				return selectAllUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean selectAll;

	@JsonIgnore
	private Supplier<Boolean> _selectAllSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Selection[] getSelections() {
		if (_selectionsSupplier != null) {
			selections = _selectionsSupplier.get();

			_selectionsSupplier = null;
		}

		return selections;
	}

	public void setSelections(Selection[] selections) {
		this.selections = selections;

		_selectionsSupplier = null;
	}

	@JsonIgnore
	public void setSelections(
		UnsafeSupplier<Selection[], Exception> selectionsUnsafeSupplier) {

		_selectionsSupplier = () -> {
			try {
				return selectionsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Selection[] selections;

	@JsonIgnore
	private Supplier<Selection[]> _selectionsSupplier;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		Action action = getAction();

		if (action != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			sb.append("\"");

			sb.append(action);

			sb.append("\"");
		}

		Boolean selectAll = getSelectAll();

		if (selectAll != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectAll\": ");

			sb.append(selectAll);
		}

		Selection[] selections = getSelections();

		if (selections != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selections\": ");

			sb.append("[");

			for (int i = 0; i < selections.length; i++) {
				sb.append(String.valueOf(selections[i]));

				if ((i + 1) < selections.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.cms.dto.v1_0.DocumentBulkAction",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Action")
	public static enum Action {

		DELETE_ACTION("DeleteAction"), KEYWORD_ACTION("KeywordAction"),
		MOVE_ACTION("MoveAction"), PERMISSION_ACTION("PermissionAction"),
		STATUS_ACTION("StatusAction"),
		TAXONOMY_CATEGORY_ACTION("TaxonomyCategoryAction");

		@JsonCreator
		public static Action create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Action action : values()) {
				if (Objects.equals(action.getValue(), value)) {
					return action;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}