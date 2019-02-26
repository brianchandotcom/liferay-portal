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
@GraphQLName("Process")
@XmlRootElement(name = "Process")
public class Process {

	public Long getId() {
		return id;
	}

	public Integer getInstanceCount() {
		return instanceCount;
	}

	public Integer getOntimeInstanceCount() {
		return ontimeInstanceCount;
	}

	public Integer getOverdueInstanceCount() {
		return overdueInstanceCount;
	}

	public String getTitle() {
		return title;
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

	public void setInstanceCount(Integer instanceCount) {
		this.instanceCount = instanceCount;
	}

	@JsonIgnore
	public void setInstanceCount(
		UnsafeSupplier<Integer, Exception> instanceCountUnsafeSupplier) {

		try {
			instanceCount = instanceCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setOntimeInstanceCount(Integer ontimeInstanceCount) {
		this.ontimeInstanceCount = ontimeInstanceCount;
	}

	@JsonIgnore
	public void setOntimeInstanceCount(
		UnsafeSupplier<Integer, Exception> ontimeInstanceCountUnsafeSupplier) {

		try {
			ontimeInstanceCount = ontimeInstanceCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setOverdueInstanceCount(Integer overdueInstanceCount) {
		this.overdueInstanceCount = overdueInstanceCount;
	}

	@JsonIgnore
	public void setOverdueInstanceCount(
		UnsafeSupplier<Integer, Exception> overdueInstanceCountUnsafeSupplier) {

		try {
			overdueInstanceCount = overdueInstanceCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
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
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(12);

		sb.append("{");

		sb.append("id=");

		sb.append(id);
		sb.append(", instanceCount=");

		sb.append(instanceCount);
		sb.append(", ontimeInstanceCount=");

		sb.append(ontimeInstanceCount);
		sb.append(", overdueInstanceCount=");

		sb.append(overdueInstanceCount);
		sb.append(", title=");

		sb.append(title);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected Integer instanceCount;

	@GraphQLField
	@JsonProperty
	protected Integer ontimeInstanceCount;

	@GraphQLField
	@JsonProperty
	protected Integer overdueInstanceCount;

	@GraphQLField
	@JsonProperty
	protected String title;

}