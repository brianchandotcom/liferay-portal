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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("SLA")
@XmlRootElement(name = "SLA")
public class SLA {

	public String getDescription() {
		return description;
	}

	public Long getDuration() {
		return duration;
	}

	public String getName() {
		return name;
	}

	public String[] getPauseNodeNames() {
		return pauseNodeNames;
	}

	public Long getProcessId() {
		return processId;
	}

	public String[] getStartNodeNames() {
		return startNodeNames;
	}

	public String[] getStopNodeNames() {
		return stopNodeNames;
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

	public String toString() {
		StringBundler sb = new StringBundler(16);

		sb.append("{");

		sb.append("name=");

		sb.append(name);
		sb.append(", description=");

		sb.append(description);
		sb.append(", duration=");

		sb.append(duration);
		sb.append(", processId=");

		sb.append(processId);
		sb.append(", startNodeNames=");

		sb.append(startNodeNames);
		sb.append(", pauseNodeNames=");

		sb.append(pauseNodeNames);
		sb.append(", stopNodeNames=");

		sb.append(stopNodeNames);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String description;

	@GraphQLField
	@JsonProperty
	protected Long duration;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected String[] pauseNodeNames;

	@GraphQLField
	@JsonProperty
	protected Long processId;

	@GraphQLField
	@JsonProperty
	protected String[] startNodeNames;

	@GraphQLField
	@JsonProperty
	protected String[] stopNodeNames;

}