/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
@GraphQLName("GeneralConfiguration")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "GeneralConfiguration")
public class GeneralConfiguration implements Serializable {

	public static GeneralConfiguration toDTO(String json) {
		return ObjectMapperUtil.readValue(GeneralConfiguration.class, json);
	}

	public static GeneralConfiguration unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			GeneralConfiguration.class, json);
	}

	@Schema
	public String[] getClauseContributorsExcludes() {
		return clauseContributorsExcludes;
	}

	public void setClauseContributorsExcludes(
		String[] clauseContributorsExcludes) {

		this.clauseContributorsExcludes = clauseContributorsExcludes;
	}

	@JsonIgnore
	public void setClauseContributorsExcludes(
		UnsafeSupplier<String[], Exception>
			clauseContributorsExcludesUnsafeSupplier) {

		try {
			clauseContributorsExcludes =
				clauseContributorsExcludesUnsafeSupplier.get();
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
	protected String[] clauseContributorsExcludes;

	@Schema
	public String[] getClauseContributorsIncludes() {
		return clauseContributorsIncludes;
	}

	public void setClauseContributorsIncludes(
		String[] clauseContributorsIncludes) {

		this.clauseContributorsIncludes = clauseContributorsIncludes;
	}

	@JsonIgnore
	public void setClauseContributorsIncludes(
		UnsafeSupplier<String[], Exception>
			clauseContributorsIncludesUnsafeSupplier) {

		try {
			clauseContributorsIncludes =
				clauseContributorsIncludesUnsafeSupplier.get();
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
	protected String[] clauseContributorsIncludes;

	@Schema
	public Boolean getEmptySearchEnabled() {
		return emptySearchEnabled;
	}

	public void setEmptySearchEnabled(Boolean emptySearchEnabled) {
		this.emptySearchEnabled = emptySearchEnabled;
	}

	@JsonIgnore
	public void setEmptySearchEnabled(
		UnsafeSupplier<Boolean, Exception> emptySearchEnabledUnsafeSupplier) {

		try {
			emptySearchEnabled = emptySearchEnabledUnsafeSupplier.get();
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
	protected Boolean emptySearchEnabled;

	@Schema
	public Boolean getExplain() {
		return explain;
	}

	public void setExplain(Boolean explain) {
		this.explain = explain;
	}

	@JsonIgnore
	public void setExplain(
		UnsafeSupplier<Boolean, Exception> explainUnsafeSupplier) {

		try {
			explain = explainUnsafeSupplier.get();
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
	protected Boolean explain;

	@Schema
	public Boolean getIncludeResponseString() {
		return includeResponseString;
	}

	public void setIncludeResponseString(Boolean includeResponseString) {
		this.includeResponseString = includeResponseString;
	}

	@JsonIgnore
	public void setIncludeResponseString(
		UnsafeSupplier<Boolean, Exception>
			includeResponseStringUnsafeSupplier) {

		try {
			includeResponseString = includeResponseStringUnsafeSupplier.get();
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
	protected Boolean includeResponseString;

	@Schema
	public String[] getSearchableAssetTypes() {
		return searchableAssetTypes;
	}

	public void setSearchableAssetTypes(String[] searchableAssetTypes) {
		this.searchableAssetTypes = searchableAssetTypes;
	}

	@JsonIgnore
	public void setSearchableAssetTypes(
		UnsafeSupplier<String[], Exception>
			searchableAssetTypesUnsafeSupplier) {

		try {
			searchableAssetTypes = searchableAssetTypesUnsafeSupplier.get();
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
	protected String[] searchableAssetTypes;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GeneralConfiguration)) {
			return false;
		}

		GeneralConfiguration generalConfiguration =
			(GeneralConfiguration)object;

		return Objects.equals(toString(), generalConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (clauseContributorsExcludes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauseContributorsExcludes\": ");

			sb.append("[");

			for (int i = 0; i < clauseContributorsExcludes.length; i++) {
				sb.append("\"");

				sb.append(_escape(clauseContributorsExcludes[i]));

				sb.append("\"");

				if ((i + 1) < clauseContributorsExcludes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (clauseContributorsIncludes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauseContributorsIncludes\": ");

			sb.append("[");

			for (int i = 0; i < clauseContributorsIncludes.length; i++) {
				sb.append("\"");

				sb.append(_escape(clauseContributorsIncludes[i]));

				sb.append("\"");

				if ((i + 1) < clauseContributorsIncludes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (emptySearchEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emptySearchEnabled\": ");

			sb.append(emptySearchEnabled);
		}

		if (explain != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"explain\": ");

			sb.append(explain);
		}

		if (includeResponseString != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"includeResponseString\": ");

			sb.append(includeResponseString);
		}

		if (searchableAssetTypes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"searchableAssetTypes\": ");

			sb.append("[");

			for (int i = 0; i < searchableAssetTypes.length; i++) {
				sb.append("\"");

				sb.append(_escape(searchableAssetTypes[i]));

				sb.append("\"");

				if ((i + 1) < searchableAssetTypes.length) {
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
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append(entry.getKey());
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
				sb.append(value);
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

}