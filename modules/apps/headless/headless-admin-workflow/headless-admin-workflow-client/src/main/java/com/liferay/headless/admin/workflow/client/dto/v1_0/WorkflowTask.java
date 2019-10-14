/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.workflow.client.dto.v1_0;

import com.liferay.headless.admin.workflow.client.function.UnsafeSupplier;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskSerDes;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WorkflowTask {

	public Creator getAssigneePerson() {
		return assigneePerson;
	}

	public void setAssigneePerson(Creator assigneePerson) {
		this.assigneePerson = assigneePerson;
	}

	public void setAssigneePerson(
		UnsafeSupplier<Creator, Exception> assigneePersonUnsafeSupplier) {

		try {
			assigneePerson = assigneePersonUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Creator assigneePerson;

	public Role[] getAssigneeRoles() {
		return assigneeRoles;
	}

	public void setAssigneeRoles(Role[] assigneeRoles) {
		this.assigneeRoles = assigneeRoles;
	}

	public void setAssigneeRoles(
		UnsafeSupplier<Role[], Exception> assigneeRolesUnsafeSupplier) {

		try {
			assigneeRoles = assigneeRolesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Role[] assigneeRoles;

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public void setCompleted(
		UnsafeSupplier<Boolean, Exception> completedUnsafeSupplier) {

		try {
			completed = completedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean completed;

	public Date getDateCompletion() {
		return dateCompletion;
	}

	public void setDateCompletion(Date dateCompletion) {
		this.dateCompletion = dateCompletion;
	}

	public void setDateCompletion(
		UnsafeSupplier<Date, Exception> dateCompletionUnsafeSupplier) {

		try {
			dateCompletion = dateCompletionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCompletion;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateDue() {
		return dateDue;
	}

	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}

	public void setDateDue(
		UnsafeSupplier<Date, Exception> dateDueUnsafeSupplier) {

		try {
			dateDue = dateDueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateDue;

	public Long getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}

	public void setDefinitionId(
		UnsafeSupplier<Long, Exception> definitionIdUnsafeSupplier) {

		try {
			definitionId = definitionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long definitionId;

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public void setDefinitionName(
		UnsafeSupplier<String, Exception> definitionNameUnsafeSupplier) {

		try {
			definitionName = definitionNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String definitionName;

	public String getDefinitionVersion() {
		return definitionVersion;
	}

	public void setDefinitionVersion(String definitionVersion) {
		this.definitionVersion = definitionVersion;
	}

	public void setDefinitionVersion(
		UnsafeSupplier<String, Exception> definitionVersionUnsafeSupplier) {

		try {
			definitionVersion = definitionVersionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String definitionVersion;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public void setInstanceId(
		UnsafeSupplier<Long, Exception> instanceIdUnsafeSupplier) {

		try {
			instanceId = instanceIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long instanceId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public ObjectReviewed getObjectReviewed() {
		return objectReviewed;
	}

	public void setObjectReviewed(ObjectReviewed objectReviewed) {
		this.objectReviewed = objectReviewed;
	}

	public void setObjectReviewed(
		UnsafeSupplier<ObjectReviewed, Exception>
			objectReviewedUnsafeSupplier) {

		try {
			objectReviewed = objectReviewedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ObjectReviewed objectReviewed;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowTask)) {
			return false;
		}

		WorkflowTask workflowTask = (WorkflowTask)object;

		return Objects.equals(toString(), workflowTask.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WorkflowTaskSerDes.toJSON(this);
	}

}