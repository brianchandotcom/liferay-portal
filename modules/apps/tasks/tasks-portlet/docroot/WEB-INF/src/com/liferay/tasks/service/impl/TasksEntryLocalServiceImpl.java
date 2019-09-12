/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */

package com.liferay.tasks.service.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.tasks.exception.TasksEntryDueDateException;
import com.liferay.tasks.exception.TasksEntryTitleException;
import com.liferay.tasks.model.TasksEntry;
import com.liferay.tasks.model.TasksEntryConstants;
import com.liferay.tasks.service.base.TasksEntryLocalServiceBaseImpl;
import com.liferay.tasks.social.TasksActivityKeys;
import com.liferay.tasks.util.PortletKeys;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author Ryan Park
 * @author Jonathan Lee
 */
public class TasksEntryLocalServiceImpl extends TasksEntryLocalServiceBaseImpl {

	@Override
	public TasksEntry addTasksEntry(
			long userId, String title, int priority, long assigneeUserId,
			int dueDateMonth, int dueDateDay, int dueDateYear, int dueDateHour,
			int dueDateMinute, boolean addDueDate,
			ServiceContext serviceContext)
		throws PortalException {

		// Tasks entry

		User user = userLocalService.getUserById(userId);
		long groupId = serviceContext.getScopeGroupId();
		Date now = new Date();

		validate(title);

		Date dueDate = null;

		if (addDueDate) {
			dueDate = PortalUtil.getDate(
				dueDateMonth, dueDateDay, dueDateYear, dueDateHour,
				dueDateMinute, user.getTimeZone(),
				TasksEntryDueDateException.class);
		}

		long tasksEntryId = counterLocalService.increment();

		TasksEntry tasksEntry = tasksEntryPersistence.create(tasksEntryId);

		tasksEntry.setGroupId(groupId);
		tasksEntry.setCompanyId(user.getCompanyId());
		tasksEntry.setUserId(user.getUserId());
		tasksEntry.setUserName(user.getFullName());
		tasksEntry.setCreateDate(now);
		tasksEntry.setModifiedDate(now);
		tasksEntry.setTitle(title);
		tasksEntry.setPriority(priority);
		tasksEntry.setAssigneeUserId(assigneeUserId);
		tasksEntry.setDueDate(dueDate);
		tasksEntry.setStatus(TasksEntryConstants.STATUS_OPEN);

		tasksEntryPersistence.update(tasksEntry);

		// Resources

		resourceLocalService.addModelResources(tasksEntry, serviceContext);

		// Asset

		updateAsset(
			userId, tasksEntry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", tasksEntry.getTitle());

		socialActivityLocalService.addActivity(
			userId, groupId, TasksEntry.class.getName(), tasksEntryId,
			TasksActivityKeys.ADD_ENTRY, extraDataJSONObject.toString(),
			assigneeUserId);

		// Notifications

		sendNotificationEvent(
			tasksEntry, TasksEntryConstants.STATUS_ALL, assigneeUserId,
			serviceContext);

		return tasksEntry;
	}

	@Override
	public TasksEntry deleteTasksEntry(long tasksEntryId)
		throws PortalException {

		TasksEntry tasksEntry = tasksEntryPersistence.findByPrimaryKey(
			tasksEntryId);

		return deleteTasksEntry(tasksEntry);
	}

	@Override
	public TasksEntry deleteTasksEntry(TasksEntry tasksEntry)
		throws PortalException {

		// Tasks entry

		tasksEntryPersistence.remove(tasksEntry);

		// Asset

		assetEntryLocalService.deleteEntry(
			TasksEntry.class.getName(), tasksEntry.getTasksEntryId());

		// Comment

		CommentManagerUtil.deleteDiscussion(
			TasksEntry.class.getName(), tasksEntry.getTasksEntryId());

		// Social

		socialActivityLocalService.deleteActivities(
			TasksEntry.class.getName(), tasksEntry.getTasksEntryId());

		return tasksEntry;
	}

	@Override
	public List<TasksEntry> getAssigneeTasksEntries(
		long assigneeUserId, int start, int end) {

		return tasksEntryPersistence.findByAssigneeUserId(
			assigneeUserId, start, end);
	}

	@Override
	public int getAssigneeTasksEntriesCount(long assigneeUserId) {
		return tasksEntryPersistence.countByAssigneeUserId(assigneeUserId);
	}

	@Override
	public List<TasksEntry> getGroupAssigneeTasksEntries(
		long groupId, long assigneeUserId, int start, int end) {

		return tasksEntryPersistence.findByG_A(
			groupId, assigneeUserId, start, end);
	}

	@Override
	public int getGroupAssigneeTasksEntriesCount(
		long groupId, long assigneeUserId) {

		return tasksEntryPersistence.countByG_A(groupId, assigneeUserId);
	}

	@Override
	public List<TasksEntry> getGroupResolverTasksEntries(
		long groupId, long resolverUserId, int start, int end) {

		return tasksEntryPersistence.findByG_R(
			groupId, resolverUserId, start, end);
	}

	@Override
	public int getGroupResolverTasksEntriesCount(
		long groupId, long resolverUserId) {

		return tasksEntryPersistence.countByG_R(groupId, resolverUserId);
	}

	@Override
	public List<TasksEntry> getGroupUserTasksEntries(
		long groupId, long userId, int start, int end) {

		return tasksEntryPersistence.findByG_U(groupId, userId, start, end);
	}

	@Override
	public int getGroupUserTasksEntriesCount(long groupId, long userId) {
		return tasksEntryPersistence.countByG_U(groupId, userId);
	}

	@Override
	public List<TasksEntry> getResolverTasksEntries(
		long resolverUserId, int start, int end) {

		return tasksEntryPersistence.findByResolverUserId(
			resolverUserId, start, end);
	}

	@Override
	public int getResolverTasksEntriesCount(long resolverUserId) {
		return tasksEntryPersistence.countByResolverUserId(resolverUserId);
	}

	@Override
	public List<TasksEntry> getTasksEntries(long groupId, int start, int end) {
		return tasksEntryPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<TasksEntry> getTasksEntries(
		long groupId, long userId, int priority, long assigneeUserId,
		int status, long[] assetTagIds, long[] notAssetTagIds, int start,
		int end) {

		return tasksEntryFinder.findByG_U_P_A_S_T_N(
			groupId, userId, priority, assigneeUserId, status, assetTagIds,
			notAssetTagIds, start, end);
	}

	@Override
	public int getTasksEntriesCount(long groupId) {
		return tasksEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getTasksEntriesCount(
		long groupId, long userId, int priority, long assigneeUserId,
		int status, long[] tagsEntryIds, long[] notTagsEntryIds) {

		return tasksEntryFinder.countByG_U_P_A_S_T_N(
			groupId, userId, priority, assigneeUserId, status, tagsEntryIds,
			notTagsEntryIds);
	}

	@Override
	public TasksEntry getTasksEntry(long tasksEntryId) throws PortalException {
		return tasksEntryPersistence.findByPrimaryKey(tasksEntryId);
	}

	@Override
	public List<TasksEntry> getUserTasksEntries(
		long userId, int start, int end) {

		return tasksEntryPersistence.findByUserId(userId, start, end);
	}

	@Override
	public int getUserTasksEntriesCount(long userId) {
		return tasksEntryPersistence.countByUserId(userId);
	}

	@Override
	public void updateAsset(
			long userId, TasksEntry tasksEntry, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException {

		assetEntryLocalService.updateEntry(
			userId, tasksEntry.getGroupId(), TasksEntry.class.getName(),
			tasksEntry.getTasksEntryId(), assetCategoryIds, assetTagNames);
	}

	@Override
	public TasksEntry updateTasksEntry(
			long tasksEntryId, String title, int priority, long assigneeUserId,
			long resolverUserId, int dueDateMonth, int dueDateDay,
			int dueDateYear, int dueDateHour, int dueDateMinute,
			boolean addDueDate, int status, ServiceContext serviceContext)
		throws PortalException {

		// Tasks entry

		Date now = new Date();

		TasksEntry tasksEntry = tasksEntryPersistence.findByPrimaryKey(
			tasksEntryId);

		validate(title);

		Date dueDate = null;

		if (addDueDate) {
			User user = userLocalService.getUserById(tasksEntry.getUserId());

			dueDate = PortalUtil.getDate(
				dueDateMonth, dueDateDay, dueDateYear, dueDateHour,
				dueDateMinute, user.getTimeZone(),
				TasksEntryDueDateException.class);
		}

		long oldAssigneeUserId = tasksEntry.getAssigneeUserId();
		int oldStatus = tasksEntry.getStatus();

		tasksEntry.setModifiedDate(now);
		tasksEntry.setTitle(title);
		tasksEntry.setPriority(priority);
		tasksEntry.setAssigneeUserId(assigneeUserId);
		tasksEntry.setDueDate(dueDate);

		if (status == TasksEntryConstants.STATUS_RESOLVED) {
			tasksEntry.setResolverUserId(resolverUserId);
			tasksEntry.setFinishDate(now);
		}
		else {
			tasksEntry.setResolverUserId(0);
			tasksEntry.setFinishDate(null);
		}

		tasksEntry.setStatus(status);

		tasksEntryPersistence.update(tasksEntry);

		// Asset

		updateAsset(
			tasksEntry.getUserId(), tasksEntry,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Social

		addSocialActivity(status, tasksEntry, serviceContext);

		// Notifications

		sendNotificationEvent(
			tasksEntry, oldStatus, oldAssigneeUserId, serviceContext);

		return tasksEntry;
	}

	@Override
	public TasksEntry updateTasksEntryStatus(
			long tasksEntryId, long resolverUserId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Tasks entry

		Date now = new Date();

		TasksEntry tasksEntry = tasksEntryPersistence.findByPrimaryKey(
			tasksEntryId);

		tasksEntry.setModifiedDate(now);

		if (status == TasksEntryConstants.STATUS_RESOLVED) {
			tasksEntry.setResolverUserId(resolverUserId);
			tasksEntry.setFinishDate(now);
		}
		else {
			tasksEntry.setResolverUserId(0);
			tasksEntry.setFinishDate(null);
		}

		int oldStatus = tasksEntry.getStatus();

		tasksEntry.setStatus(status);

		tasksEntryPersistence.update(tasksEntry);

		// Social

		addSocialActivity(status, tasksEntry, serviceContext);

		// Notifications

		sendNotificationEvent(
			tasksEntry, oldStatus, tasksEntry.getAssigneeUserId(),
			serviceContext);

		return tasksEntry;
	}

	protected void addSocialActivity(
			int status, TasksEntry tasksEntry, ServiceContext serviceContext)
		throws PortalException {

		int activity = TasksActivityKeys.UPDATE_ENTRY;

		if (status == TasksEntryConstants.STATUS_REOPENED) {
			activity = TasksActivityKeys.REOPEN_ENTRY;
		}
		else if (status == TasksEntryConstants.STATUS_RESOLVED) {
			activity = TasksActivityKeys.RESOLVE_ENTRY;
		}

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", tasksEntry.getTitle());

		socialActivityLocalService.addActivity(
			serviceContext.getUserId(), tasksEntry.getGroupId(),
			TasksEntry.class.getName(), tasksEntry.getTasksEntryId(), activity,
			extraDataJSONObject.toString(), tasksEntry.getAssigneeUserId());
	}

	protected void sendNotificationEvent(
			TasksEntry tasksEntry, int oldStatus, long oldAssigneeUserId,
			ServiceContext serviceContext)
		throws PortalException {

		HashSet<Long> receiverUserIds = new HashSet<>();

		receiverUserIds.add(oldAssigneeUserId);
		receiverUserIds.add(tasksEntry.getUserId());
		receiverUserIds.add(tasksEntry.getAssigneeUserId());

		receiverUserIds.remove(serviceContext.getUserId());

		JSONObject notificationEventJSONObject = JSONUtil.put(
			"classPK", tasksEntry.getTasksEntryId()
		).put(
			"userId", serviceContext.getUserId()
		);

		for (long receiverUserId : receiverUserIds) {
			if ((receiverUserId == 0) ||
				!UserNotificationManagerUtil.isDeliver(
					receiverUserId, PortletKeys.TASKS, 0,
					TasksEntryConstants.STATUS_ALL,
					UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

				continue;
			}

			String title = StringPool.BLANK;

			if (oldStatus == TasksEntryConstants.STATUS_ALL) {
				title = "x-assigned-you-a-task";
			}
			else if (tasksEntry.getAssigneeUserId() != oldAssigneeUserId) {
				if (receiverUserId == oldAssigneeUserId) {
					title = "x-reassigned-your-task";
				}
				else {
					title = "x-assigned-you-a-task";
				}
			}
			else if (tasksEntry.getStatus() != oldStatus) {
				if ((tasksEntry.getStatus() !=
						TasksEntryConstants.STATUS_OPEN) &&
					(tasksEntry.getStatus() !=
						TasksEntryConstants.STATUS_REOPENED) &&
					(tasksEntry.getStatus() !=
						TasksEntryConstants.STATUS_RESOLVED)) {

					return;
				}

				String statusLabel = TasksEntryConstants.getStatusLabel(
					tasksEntry.getStatus());

				title = "x-" + statusLabel + "-the-task";
			}
			else {
				title = "x-modified-the-task";
			}

			notificationEventJSONObject.put("title", title);

			userNotificationEventLocalService.sendUserNotificationEvents(
				receiverUserId, PortletKeys.TASKS,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				notificationEventJSONObject);
		}
	}

	protected void validate(String title) throws PortalException {
		if (Validator.isNull(title)) {
			throw new TasksEntryTitleException();
		}
	}

}