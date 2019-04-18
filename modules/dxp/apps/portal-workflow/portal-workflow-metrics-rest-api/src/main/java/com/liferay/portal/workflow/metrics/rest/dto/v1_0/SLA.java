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

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("SLA")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SLA")
public class SLA {

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
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
	protected Date dateModified;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
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
	protected String description;

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@JsonIgnore
	public void setDuration(
		UnsafeSupplier<Long, Exception> durationUnsafeSupplier) {

		try {
			duration = durationUnsafeSupplier.get();
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
	protected Long duration;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
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
	protected String name;

	public String[] getPauseNodeKeys() {
		return pauseNodeKeys;
	}

	public void setPauseNodeKeys(String[] pauseNodeKeys) {
		this.pauseNodeKeys = pauseNodeKeys;
	}

	@JsonIgnore
	public void setPauseNodeKeys(
		UnsafeSupplier<String[], Exception> pauseNodeKeysUnsafeSupplier) {

		try {
			pauseNodeKeys = pauseNodeKeysUnsafeSupplier.get();
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
	protected String[] pauseNodeKeys;

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@JsonIgnore
	public void setProcessId(
		UnsafeSupplier<Long, Exception> processIdUnsafeSupplier) {

		try {
			processId = processIdUnsafeSupplier.get();
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
	protected Long processId;

	public String[] getStartNodeKeys() {
		return startNodeKeys;
	}

	public void setStartNodeKeys(String[] startNodeKeys) {
		this.startNodeKeys = startNodeKeys;
	}

	@JsonIgnore
	public void setStartNodeKeys(
		UnsafeSupplier<String[], Exception> startNodeKeysUnsafeSupplier) {

		try {
			startNodeKeys = startNodeKeysUnsafeSupplier.get();
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
	protected String[] startNodeKeys;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<Integer, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
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
	protected Integer status;

	public String[] getStopNodeKeys() {
		return stopNodeKeys;
	}

	public void setStopNodeKeys(String[] stopNodeKeys) {
		this.stopNodeKeys = stopNodeKeys;
	}

	@JsonIgnore
	public void setStopNodeKeys(
		UnsafeSupplier<String[], Exception> stopNodeKeysUnsafeSupplier) {

		try {
			stopNodeKeys = stopNodeKeysUnsafeSupplier.get();
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
	protected String[] stopNodeKeys;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SLA)) {
			return false;
		}

		SLA sla = (SLA)object;

		return Objects.equals(toString(), sla.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"dateModified\": ");

		if (dateModified == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateModified);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (description == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(description);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"duration\": ");

		if (duration == null) {
			sb.append("null");
		}
		else {
			sb.append(duration);
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (name == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(name);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"pauseNodeKeys\": ");

		if (pauseNodeKeys == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < pauseNodeKeys.length; i++) {
				sb.append("\"");
				sb.append(pauseNodeKeys[i]);
				sb.append("\"");

				if ((i + 1) < pauseNodeKeys.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"processId\": ");

		if (processId == null) {
			sb.append("null");
		}
		else {
			sb.append(processId);
		}

		sb.append(", ");

		sb.append("\"startNodeKeys\": ");

		if (startNodeKeys == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < startNodeKeys.length; i++) {
				sb.append("\"");
				sb.append(startNodeKeys[i]);
				sb.append("\"");

				if ((i + 1) < startNodeKeys.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"status\": ");

		if (status == null) {
			sb.append("null");
		}
		else {
			sb.append(status);
		}

		sb.append(", ");

		sb.append("\"stopNodeKeys\": ");

		if (stopNodeKeys == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < stopNodeKeys.length; i++) {
				sb.append("\"");
				sb.append(stopNodeKeys[i]);
				sb.append("\"");

				if ((i + 1) < stopNodeKeys.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}