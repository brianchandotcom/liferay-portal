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

package com.liferay.portal.workflow.metrics.rest.client.dto.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.function.UnsafeSupplier;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.AssigneeMetricBulkSelectionSerDes;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AssigneeMetricBulkSelection implements Cloneable {

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

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateEnd(
		UnsafeSupplier<Date, Exception> dateEndUnsafeSupplier) {

		try {
			dateEnd = dateEndUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateEnd;

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDateStart(
		UnsafeSupplier<Date, Exception> dateStartUnsafeSupplier) {

		try {
			dateStart = dateStartUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateStart;

	public Long[] getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(Long[] instanceIds) {
		this.instanceIds = instanceIds;
	}

	public void setInstanceIds(
		UnsafeSupplier<Long[], Exception> instanceIdsUnsafeSupplier) {

		try {
			instanceIds = instanceIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] instanceIds;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setKeywords(
		UnsafeSupplier<String, Exception> keywordsUnsafeSupplier) {

		try {
			keywords = keywordsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String keywords;

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public void setRoleIds(
		UnsafeSupplier<Long[], Exception> roleIdsUnsafeSupplier) {

		try {
			roleIds = roleIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] roleIds;

	public String[] getTaskNames() {
		return taskNames;
	}

	public void setTaskNames(String[] taskNames) {
		this.taskNames = taskNames;
	}

	public void setTaskNames(
		UnsafeSupplier<String[], Exception> taskNamesUnsafeSupplier) {

		try {
			taskNames = taskNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] taskNames;

	@Override
	public AssigneeMetricBulkSelection clone()
		throws CloneNotSupportedException {

		return (AssigneeMetricBulkSelection)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssigneeMetricBulkSelection)) {
			return false;
		}

		AssigneeMetricBulkSelection assigneeMetricBulkSelection =
			(AssigneeMetricBulkSelection)object;

		return Objects.equals(
			toString(), assigneeMetricBulkSelection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AssigneeMetricBulkSelectionSerDes.toJSON(this);
	}

}