/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Detailed info of the creator.", value = "CreatorStatistics"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CreatorStatistics")
public class CreatorStatistics implements Serializable {

	public static CreatorStatistics toDTO(String json) {
		return ObjectMapperUtil.readValue(CreatorStatistics.class, json);
	}

	public static CreatorStatistics unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(CreatorStatistics.class, json);
	}

	@Schema(description = "Join date of the author.")
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@JsonIgnore
	public void setJoinDate(
		UnsafeSupplier<Date, Exception> joinDateUnsafeSupplier) {

		try {
			joinDate = joinDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "Join date of the author.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date joinDate;

	@Schema(description = "Last post created by the author.")
	public Date getLastPostDate() {
		return lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		this.lastPostDate = lastPostDate;
	}

	@JsonIgnore
	public void setLastPostDate(
		UnsafeSupplier<Date, Exception> lastPostDateUnsafeSupplier) {

		try {
			lastPostDate = lastPostDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "Last post created by the author.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date lastPostDate;

	@Schema(description = "Number of posts publicated by the author.")
	public Integer getPostsNumber() {
		return postsNumber;
	}

	public void setPostsNumber(Integer postsNumber) {
		this.postsNumber = postsNumber;
	}

	@JsonIgnore
	public void setPostsNumber(
		UnsafeSupplier<Integer, Exception> postsNumberUnsafeSupplier) {

		try {
			postsNumber = postsNumberUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "Number of posts publicated by the author.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Integer postsNumber;

	@Schema(description = "The rank of the author.")
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@JsonIgnore
	public void setRank(UnsafeSupplier<String, Exception> rankUnsafeSupplier) {
		try {
			rank = rankUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The rank of the author.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String rank;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreatorStatistics)) {
			return false;
		}

		CreatorStatistics creatorStatistics = (CreatorStatistics)object;

		return Objects.equals(toString(), creatorStatistics.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (joinDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"joinDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(joinDate));

			sb.append("\"");
		}

		if (lastPostDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPostDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(lastPostDate));

			sb.append("\"");
		}

		if (postsNumber != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postsNumber\": ");

			sb.append(postsNumber);
		}

		if (rank != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rank\": ");

			sb.append("\"");

			sb.append(_escape(rank));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.CreatorStatistics",
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