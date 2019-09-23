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

package com.liferay.tasks.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TasksEntry}.
 * </p>
 *
 * @author Ryan Park
 * @see TasksEntry
 * @generated
 */
public class TasksEntryWrapper
	extends BaseModelWrapper<TasksEntry>
	implements ModelWrapper<TasksEntry>, TasksEntry {

	public TasksEntryWrapper(TasksEntry tasksEntry) {
		super(tasksEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("tasksEntryId", getTasksEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("priority", getPriority());
		attributes.put("assigneeUserId", getAssigneeUserId());
		attributes.put("resolverUserId", getResolverUserId());
		attributes.put("dueDate", getDueDate());
		attributes.put("finishDate", getFinishDate());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long tasksEntryId = (Long)attributes.get("tasksEntryId");

		if (tasksEntryId != null) {
			setTasksEntryId(tasksEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Long assigneeUserId = (Long)attributes.get("assigneeUserId");

		if (assigneeUserId != null) {
			setAssigneeUserId(assigneeUserId);
		}

		Long resolverUserId = (Long)attributes.get("resolverUserId");

		if (resolverUserId != null) {
			setResolverUserId(resolverUserId);
		}

		Date dueDate = (Date)attributes.get("dueDate");

		if (dueDate != null) {
			setDueDate(dueDate);
		}

		Date finishDate = (Date)attributes.get("finishDate");

		if (finishDate != null) {
			setFinishDate(finishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public String getAssigneeFullName() {
		return model.getAssigneeFullName();
	}

	/**
	 * Returns the assignee user ID of this tasks entry.
	 *
	 * @return the assignee user ID of this tasks entry
	 */
	@Override
	public long getAssigneeUserId() {
		return model.getAssigneeUserId();
	}

	/**
	 * Returns the assignee user uuid of this tasks entry.
	 *
	 * @return the assignee user uuid of this tasks entry
	 */
	@Override
	public String getAssigneeUserUuid() {
		return model.getAssigneeUserUuid();
	}

	/**
	 * Returns the company ID of this tasks entry.
	 *
	 * @return the company ID of this tasks entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this tasks entry.
	 *
	 * @return the create date of this tasks entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the due date of this tasks entry.
	 *
	 * @return the due date of this tasks entry
	 */
	@Override
	public Date getDueDate() {
		return model.getDueDate();
	}

	/**
	 * Returns the finish date of this tasks entry.
	 *
	 * @return the finish date of this tasks entry
	 */
	@Override
	public Date getFinishDate() {
		return model.getFinishDate();
	}

	/**
	 * Returns the group ID of this tasks entry.
	 *
	 * @return the group ID of this tasks entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this tasks entry.
	 *
	 * @return the modified date of this tasks entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this tasks entry.
	 *
	 * @return the primary key of this tasks entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this tasks entry.
	 *
	 * @return the priority of this tasks entry
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	@Override
	public String getPriorityLabel() {
		return model.getPriorityLabel();
	}

	@Override
	public String getReporterFullName() {
		return model.getReporterFullName();
	}

	/**
	 * Returns the resolver user ID of this tasks entry.
	 *
	 * @return the resolver user ID of this tasks entry
	 */
	@Override
	public long getResolverUserId() {
		return model.getResolverUserId();
	}

	/**
	 * Returns the resolver user uuid of this tasks entry.
	 *
	 * @return the resolver user uuid of this tasks entry
	 */
	@Override
	public String getResolverUserUuid() {
		return model.getResolverUserUuid();
	}

	/**
	 * Returns the status of this tasks entry.
	 *
	 * @return the status of this tasks entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	@Override
	public String getStatusLabel() {
		return model.getStatusLabel();
	}

	/**
	 * Returns the tasks entry ID of this tasks entry.
	 *
	 * @return the tasks entry ID of this tasks entry
	 */
	@Override
	public long getTasksEntryId() {
		return model.getTasksEntryId();
	}

	/**
	 * Returns the title of this tasks entry.
	 *
	 * @return the title of this tasks entry
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the user ID of this tasks entry.
	 *
	 * @return the user ID of this tasks entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this tasks entry.
	 *
	 * @return the user name of this tasks entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this tasks entry.
	 *
	 * @return the user uuid of this tasks entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a tasks entry model instance should use the <code>TasksEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the assignee user ID of this tasks entry.
	 *
	 * @param assigneeUserId the assignee user ID of this tasks entry
	 */
	@Override
	public void setAssigneeUserId(long assigneeUserId) {
		model.setAssigneeUserId(assigneeUserId);
	}

	/**
	 * Sets the assignee user uuid of this tasks entry.
	 *
	 * @param assigneeUserUuid the assignee user uuid of this tasks entry
	 */
	@Override
	public void setAssigneeUserUuid(String assigneeUserUuid) {
		model.setAssigneeUserUuid(assigneeUserUuid);
	}

	/**
	 * Sets the company ID of this tasks entry.
	 *
	 * @param companyId the company ID of this tasks entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this tasks entry.
	 *
	 * @param createDate the create date of this tasks entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the due date of this tasks entry.
	 *
	 * @param dueDate the due date of this tasks entry
	 */
	@Override
	public void setDueDate(Date dueDate) {
		model.setDueDate(dueDate);
	}

	/**
	 * Sets the finish date of this tasks entry.
	 *
	 * @param finishDate the finish date of this tasks entry
	 */
	@Override
	public void setFinishDate(Date finishDate) {
		model.setFinishDate(finishDate);
	}

	/**
	 * Sets the group ID of this tasks entry.
	 *
	 * @param groupId the group ID of this tasks entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this tasks entry.
	 *
	 * @param modifiedDate the modified date of this tasks entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this tasks entry.
	 *
	 * @param primaryKey the primary key of this tasks entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this tasks entry.
	 *
	 * @param priority the priority of this tasks entry
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the resolver user ID of this tasks entry.
	 *
	 * @param resolverUserId the resolver user ID of this tasks entry
	 */
	@Override
	public void setResolverUserId(long resolverUserId) {
		model.setResolverUserId(resolverUserId);
	}

	/**
	 * Sets the resolver user uuid of this tasks entry.
	 *
	 * @param resolverUserUuid the resolver user uuid of this tasks entry
	 */
	@Override
	public void setResolverUserUuid(String resolverUserUuid) {
		model.setResolverUserUuid(resolverUserUuid);
	}

	/**
	 * Sets the status of this tasks entry.
	 *
	 * @param status the status of this tasks entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the tasks entry ID of this tasks entry.
	 *
	 * @param tasksEntryId the tasks entry ID of this tasks entry
	 */
	@Override
	public void setTasksEntryId(long tasksEntryId) {
		model.setTasksEntryId(tasksEntryId);
	}

	/**
	 * Sets the title of this tasks entry.
	 *
	 * @param title the title of this tasks entry
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the user ID of this tasks entry.
	 *
	 * @param userId the user ID of this tasks entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this tasks entry.
	 *
	 * @param userName the user name of this tasks entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this tasks entry.
	 *
	 * @param userUuid the user uuid of this tasks entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected TasksEntryWrapper wrap(TasksEntry tasksEntry) {
		return new TasksEntryWrapper(tasksEntry);
	}

}