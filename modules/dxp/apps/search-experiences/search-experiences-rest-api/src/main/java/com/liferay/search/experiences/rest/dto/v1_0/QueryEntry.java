/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
@GraphQLName("QueryEntry")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "QueryEntry")
public class QueryEntry implements Serializable {

	public static QueryEntry toDTO(String json) {
		return ObjectMapperUtil.readValue(QueryEntry.class, json);
	}

	public static QueryEntry unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(QueryEntry.class, json);
	}

	@Schema
	@Valid
	public Clause[] getClauses() {
		return clauses;
	}

	public void setClauses(Clause[] clauses) {
		this.clauses = clauses;
	}

	@JsonIgnore
	public void setClauses(
		UnsafeSupplier<Clause[], Exception> clausesUnsafeSupplier) {

		try {
			clauses = clausesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Clause[] clauses;

	@Schema
	@Valid
	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@JsonIgnore
	public void setCondition(
		UnsafeSupplier<Condition, Exception> conditionUnsafeSupplier) {

		try {
			condition = conditionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Condition condition;

	@Schema
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	public void setEnabled(
		UnsafeSupplier<Boolean, Exception> enabledUnsafeSupplier) {

		try {
			enabled = enabledUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean enabled;

	@Schema
	@Valid
	public Clause[] getPostFilterClauses() {
		return postFilterClauses;
	}

	public void setPostFilterClauses(Clause[] postFilterClauses) {
		this.postFilterClauses = postFilterClauses;
	}

	@JsonIgnore
	public void setPostFilterClauses(
		UnsafeSupplier<Clause[], Exception> postFilterClausesUnsafeSupplier) {

		try {
			postFilterClauses = postFilterClausesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Clause[] postFilterClauses;

	@Schema
	@Valid
	public Rescore[] getRescores() {
		return rescores;
	}

	public void setRescores(Rescore[] rescores) {
		this.rescores = rescores;
	}

	@JsonIgnore
	public void setRescores(
		UnsafeSupplier<Rescore[], Exception> rescoresUnsafeSupplier) {

		try {
			rescores = rescoresUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Rescore[] rescores;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof QueryEntry)) {
			return false;
		}

		QueryEntry queryEntry = (QueryEntry)object;

		return Objects.equals(toString(), queryEntry.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (clauses != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauses\": ");

			sb.append("[");

			for (int i = 0; i < clauses.length; i++) {
				sb.append(String.valueOf(clauses[i]));

				if ((i + 1) < clauses.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (condition != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"condition\": ");

			sb.append(String.valueOf(condition));
		}

		if (enabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enabled\": ");

			sb.append(enabled);
		}

		if (postFilterClauses != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postFilterClauses\": ");

			sb.append("[");

			for (int i = 0; i < postFilterClauses.length; i++) {
				sb.append(String.valueOf(postFilterClauses[i]));

				if ((i + 1) < postFilterClauses.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (rescores != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rescores\": ");

			sb.append("[");

			for (int i = 0; i < rescores.length; i++) {
				sb.append(String.valueOf(rescores[i]));

				if ((i + 1) < rescores.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.QueryEntry",
		name = "x-class-name"
	)
	public String xClassName;

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
					if (valueArray[i] instanceof String) {
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