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

import com.liferay.petra.function.UnsafeSupplier;

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

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setInstanceCount(Integer instanceCount) {
		this.instanceCount = instanceCount;
	}

	public void setInstanceCount(
		UnsafeSupplier<Integer, Throwable> instanceCountUnsafeSupplier) {

		try {
			instanceCount = instanceCountUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setOntimeInstanceCount(Integer ontimeInstanceCount) {
		this.ontimeInstanceCount = ontimeInstanceCount;
	}

	public void setOntimeInstanceCount(
		UnsafeSupplier<Integer, Throwable> ontimeInstanceCountUnsafeSupplier) {

		try {
			ontimeInstanceCount = ontimeInstanceCountUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setOverdueInstanceCount(Integer overdueInstanceCount) {
		this.overdueInstanceCount = overdueInstanceCount;
	}

	public void setOverdueInstanceCount(
		UnsafeSupplier<Integer, Throwable> overdueInstanceCountUnsafeSupplier) {

		try {
			overdueInstanceCount = overdueInstanceCountUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<String, Throwable> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected Integer instanceCount;

	@GraphQLField
	protected Integer ontimeInstanceCount;

	@GraphQLField
	protected Integer overdueInstanceCount;

	@GraphQLField
	protected String title;

}