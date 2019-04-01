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
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	public String[] getPauseNodeNames() {
		return pauseNodeNames;
	}

	public void setPauseNodeNames(String[] pauseNodeNames) {
		this.pauseNodeNames = pauseNodeNames;
	}

	@JsonIgnore
	public void setPauseNodeNames(
		UnsafeSupplier<String[], Exception> pauseNodeNamesUnsafeSupplier) {

		try {
			pauseNodeNames = pauseNodeNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] pauseNodeNames;

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
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long processId;

	public String[] getStartNodeNames() {
		return startNodeNames;
	}

	public void setStartNodeNames(String[] startNodeNames) {
		this.startNodeNames = startNodeNames;
	}

	@JsonIgnore
	public void setStartNodeNames(
		UnsafeSupplier<String[], Exception> startNodeNamesUnsafeSupplier) {

		try {
			startNodeNames = startNodeNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] startNodeNames;

	public String[] getStopNodeNames() {
		return stopNodeNames;
	}

	public void setStopNodeNames(String[] stopNodeNames) {
		this.stopNodeNames = stopNodeNames;
	}

	@JsonIgnore
	public void setStopNodeNames(
		UnsafeSupplier<String[], Exception> stopNodeNamesUnsafeSupplier) {

		try {
			stopNodeNames = stopNodeNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] stopNodeNames;

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

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(description);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"duration\": ");

		sb.append(duration);
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"pauseNodeNames\": ");

		if (pauseNodeNames == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < pauseNodeNames.length; i++) {
				sb.append("\"");
				sb.append(pauseNodeNames[i]);
				sb.append("\"");

				if ((i + 1) < pauseNodeNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"processId\": ");

		sb.append(processId);
		sb.append(", ");

		sb.append("\"startNodeNames\": ");

		if (startNodeNames == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < startNodeNames.length; i++) {
				sb.append("\"");
				sb.append(startNodeNames[i]);
				sb.append("\"");

				if ((i + 1) < startNodeNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"stopNodeNames\": ");

		if (stopNodeNames == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < stopNodeNames.length; i++) {
				sb.append("\"");
				sb.append(stopNodeNames[i]);
				sb.append("\"");

				if ((i + 1) < stopNodeNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}