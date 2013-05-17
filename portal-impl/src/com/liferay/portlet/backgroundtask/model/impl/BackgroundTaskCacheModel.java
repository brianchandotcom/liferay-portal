/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.backgroundtask.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.backgroundtask.model.BackgroundTask;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BackgroundTask in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTask
 * @generated
 */
public class BackgroundTaskCacheModel implements CacheModel<BackgroundTask>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{backgroundTaskId=");
		sb.append(backgroundTaskId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", backgroundTaskContext=");
		sb.append(backgroundTaskContext);
		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);
		sb.append(", completionDate=");
		sb.append(completionDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", servletContextName=");
		sb.append(servletContextName);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	public BackgroundTask toEntityModel() {
		BackgroundTaskImpl backgroundTaskImpl = new BackgroundTaskImpl();

		backgroundTaskImpl.setBackgroundTaskId(backgroundTaskId);
		backgroundTaskImpl.setGroupId(groupId);
		backgroundTaskImpl.setCompanyId(companyId);
		backgroundTaskImpl.setUserId(userId);

		if (userName == null) {
			backgroundTaskImpl.setUserName(StringPool.BLANK);
		}
		else {
			backgroundTaskImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			backgroundTaskImpl.setCreateDate(null);
		}
		else {
			backgroundTaskImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			backgroundTaskImpl.setModifiedDate(null);
		}
		else {
			backgroundTaskImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (backgroundTaskContext == null) {
			backgroundTaskImpl.setBackgroundTaskContext(StringPool.BLANK);
		}
		else {
			backgroundTaskImpl.setBackgroundTaskContext(backgroundTaskContext);
		}

		if (taskExecutorClassName == null) {
			backgroundTaskImpl.setTaskExecutorClassName(StringPool.BLANK);
		}
		else {
			backgroundTaskImpl.setTaskExecutorClassName(taskExecutorClassName);
		}

		if (completionDate == Long.MIN_VALUE) {
			backgroundTaskImpl.setCompletionDate(null);
		}
		else {
			backgroundTaskImpl.setCompletionDate(new Date(completionDate));
		}

		if (name == null) {
			backgroundTaskImpl.setName(StringPool.BLANK);
		}
		else {
			backgroundTaskImpl.setName(name);
		}

		if (servletContextName == null) {
			backgroundTaskImpl.setServletContextName(StringPool.BLANK);
		}
		else {
			backgroundTaskImpl.setServletContextName(servletContextName);
		}

		backgroundTaskImpl.setStatus(status);

		backgroundTaskImpl.resetOriginalValues();

		return backgroundTaskImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		backgroundTaskId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		backgroundTaskContext = objectInput.readUTF();
		taskExecutorClassName = objectInput.readUTF();
		completionDate = objectInput.readLong();
		name = objectInput.readUTF();
		servletContextName = objectInput.readUTF();
		status = objectInput.readInt();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(backgroundTaskId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (backgroundTaskContext == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(backgroundTaskContext);
		}

		if (taskExecutorClassName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(taskExecutorClassName);
		}

		objectOutput.writeLong(completionDate);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (servletContextName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(servletContextName);
		}

		objectOutput.writeInt(status);
	}

	public long backgroundTaskId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String backgroundTaskContext;
	public String taskExecutorClassName;
	public long completionDate;
	public String name;
	public String servletContextName;
	public int status;
}