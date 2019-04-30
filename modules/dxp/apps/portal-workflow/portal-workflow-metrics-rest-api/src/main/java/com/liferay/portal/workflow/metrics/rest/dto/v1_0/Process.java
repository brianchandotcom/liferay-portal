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

package com.liferay.portal.workflow.metrics.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("Process")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Process")
public class Process {

	@Schema
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
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
	protected Long id;

	@Schema
	public Long getInstanceCount() {
		return instanceCount;
	}

	public void setInstanceCount(Long instanceCount) {
		this.instanceCount = instanceCount;
	}

	@JsonIgnore
	public void setInstanceCount(
		UnsafeSupplier<Long, Exception> instanceCountUnsafeSupplier) {

		try {
			instanceCount = instanceCountUnsafeSupplier.get();
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
	protected Long instanceCount;

	@Schema
	public Long getOnTimeInstanceCount() {
		return onTimeInstanceCount;
	}

	public void setOnTimeInstanceCount(Long onTimeInstanceCount) {
		this.onTimeInstanceCount = onTimeInstanceCount;
	}

	@JsonIgnore
	public void setOnTimeInstanceCount(
		UnsafeSupplier<Long, Exception> onTimeInstanceCountUnsafeSupplier) {

		try {
			onTimeInstanceCount = onTimeInstanceCountUnsafeSupplier.get();
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
	protected Long onTimeInstanceCount;

	@Schema
	public Long getOverdueInstanceCount() {
		return overdueInstanceCount;
	}

	public void setOverdueInstanceCount(Long overdueInstanceCount) {
		this.overdueInstanceCount = overdueInstanceCount;
	}

	@JsonIgnore
	public void setOverdueInstanceCount(
		UnsafeSupplier<Long, Exception> overdueInstanceCountUnsafeSupplier) {

		try {
			overdueInstanceCount = overdueInstanceCountUnsafeSupplier.get();
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
	protected Long overdueInstanceCount;

	@Schema
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
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
	protected String title;

	@Schema
	public Long getUntrackedInstanceCount() {
		return untrackedInstanceCount;
	}

	public void setUntrackedInstanceCount(Long untrackedInstanceCount) {
		this.untrackedInstanceCount = untrackedInstanceCount;
	}

	@JsonIgnore
	public void setUntrackedInstanceCount(
		UnsafeSupplier<Long, Exception> untrackedInstanceCountUnsafeSupplier) {

		try {
			untrackedInstanceCount = untrackedInstanceCountUnsafeSupplier.get();
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
	protected Long untrackedInstanceCount;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Process)) {
			return false;
		}

		Process process = (Process)object;

		return Objects.equals(toString(), process.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (instanceCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instanceCount\": ");

			sb.append(instanceCount);
		}

		if (onTimeInstanceCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onTimeInstanceCount\": ");

			sb.append(onTimeInstanceCount);
		}

		if (overdueInstanceCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"overdueInstanceCount\": ");

			sb.append(overdueInstanceCount);
		}

		if (title != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(title));

			sb.append("\"");
		}

		if (untrackedInstanceCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"untrackedInstanceCount\": ");

			sb.append(untrackedInstanceCount);
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}