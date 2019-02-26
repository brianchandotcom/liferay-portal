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

package com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;

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
public class SLAImpl implements SLA {

	public String getName() {
			return name;
	}

	public void setName(
			String name) {

			this.name = name;
	}

	@JsonIgnore
	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier) {

			try {
				name =
					nameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String name;
	public String getDescription() {
			return description;
	}

	public void setDescription(
			String description) {

			this.description = description;
	}

	@JsonIgnore
	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier) {

			try {
				description =
					descriptionUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String description;
	public Long getDuration() {
			return duration;
	}

	public void setDuration(
			Long duration) {

			this.duration = duration;
	}

	@JsonIgnore
	public void setDuration(
			UnsafeSupplier<Long, Throwable>
				durationUnsafeSupplier) {

			try {
				duration =
					durationUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long duration;
	public Long getProcessId() {
			return processId;
	}

	public void setProcessId(
			Long processId) {

			this.processId = processId;
	}

	@JsonIgnore
	public void setProcessId(
			UnsafeSupplier<Long, Throwable>
				processIdUnsafeSupplier) {

			try {
				processId =
					processIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long processId;
	public String[] getStartNodeNames() {
			return startNodeNames;
	}

	public void setStartNodeNames(
			String[] startNodeNames) {

			this.startNodeNames = startNodeNames;
	}

	@JsonIgnore
	public void setStartNodeNames(
			UnsafeSupplier<String[], Throwable>
				startNodeNamesUnsafeSupplier) {

			try {
				startNodeNames =
					startNodeNamesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] startNodeNames;
	public String[] getPauseNodeNames() {
			return pauseNodeNames;
	}

	public void setPauseNodeNames(
			String[] pauseNodeNames) {

			this.pauseNodeNames = pauseNodeNames;
	}

	@JsonIgnore
	public void setPauseNodeNames(
			UnsafeSupplier<String[], Throwable>
				pauseNodeNamesUnsafeSupplier) {

			try {
				pauseNodeNames =
					pauseNodeNamesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] pauseNodeNames;
	public String[] getStopNodeNames() {
			return stopNodeNames;
	}

	public void setStopNodeNames(
			String[] stopNodeNames) {

			this.stopNodeNames = stopNodeNames;
	}

	@JsonIgnore
	public void setStopNodeNames(
			UnsafeSupplier<String[], Throwable>
				stopNodeNamesUnsafeSupplier) {

			try {
				stopNodeNames =
					stopNodeNamesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] stopNodeNames;

}