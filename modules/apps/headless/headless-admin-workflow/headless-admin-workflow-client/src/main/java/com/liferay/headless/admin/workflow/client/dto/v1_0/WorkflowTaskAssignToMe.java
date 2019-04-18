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

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WorkflowTaskAssignToMe {

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setComment(
		UnsafeSupplier<String, Exception> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String comment;

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setDueDate(
		UnsafeSupplier<Date, Exception> dueDateUnsafeSupplier) {

		try {
			dueDate = dueDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dueDate;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowTaskAssignToMe)) {
			return false;
		}

		WorkflowTaskAssignToMe workflowTaskAssignToMe =
			(WorkflowTaskAssignToMe)object;

		return Objects.equals(toString(), workflowTaskAssignToMe.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"comment\": ");

		if (comment == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(comment);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dueDate\": ");

		if (dueDate == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dueDate);
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

}