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

package com.liferay.portal.background.task.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * The cache model class for representing BackgroundTask in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BackgroundTaskCacheModel
	implements CacheModel<BackgroundTask>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BackgroundTaskCacheModel)) {
			return false;
		}

		BackgroundTaskCacheModel backgroundTaskCacheModel =
			(BackgroundTaskCacheModel)obj;

		if ((backgroundTaskId == backgroundTaskCacheModel.backgroundTaskId) &&
			(mvccVersion == backgroundTaskCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, backgroundTaskId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", backgroundTaskId=");
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", servletContextNames=");
		sb.append(servletContextNames);
		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);
		sb.append(", taskContextMap=");
		sb.append(taskContextMap);
		sb.append(", completed=");
		sb.append(completed);
		sb.append(", completionDate=");
		sb.append(completionDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusMessage=");
		sb.append(statusMessage);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BackgroundTask toEntityModel() {
		BackgroundTaskImpl backgroundTaskImpl = new BackgroundTaskImpl();

		backgroundTaskImpl.setMvccVersion(mvccVersion);
		backgroundTaskImpl.setBackgroundTaskId(backgroundTaskId);
		backgroundTaskImpl.setGroupId(groupId);
		backgroundTaskImpl.setCompanyId(companyId);
		backgroundTaskImpl.setUserId(userId);

		if (userName == null) {
			backgroundTaskImpl.setUserName("");
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

		if (name == null) {
			backgroundTaskImpl.setName("");
		}
		else {
			backgroundTaskImpl.setName(name);
		}

		if (servletContextNames == null) {
			backgroundTaskImpl.setServletContextNames("");
		}
		else {
			backgroundTaskImpl.setServletContextNames(servletContextNames);
		}

		if (taskExecutorClassName == null) {
			backgroundTaskImpl.setTaskExecutorClassName("");
		}
		else {
			backgroundTaskImpl.setTaskExecutorClassName(taskExecutorClassName);
		}

		backgroundTaskImpl.setTaskContextMap(taskContextMap);
		backgroundTaskImpl.setCompleted(completed);

		if (completionDate == Long.MIN_VALUE) {
			backgroundTaskImpl.setCompletionDate(null);
		}
		else {
			backgroundTaskImpl.setCompletionDate(new Date(completionDate));
		}

		backgroundTaskImpl.setStatus(status);

		if (statusMessage == null) {
			backgroundTaskImpl.setStatusMessage("");
		}
		else {
			backgroundTaskImpl.setStatusMessage(statusMessage);
		}

		backgroundTaskImpl.resetOriginalValues();

		return backgroundTaskImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		backgroundTaskId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		servletContextNames = objectInput.readUTF();
		taskExecutorClassName = objectInput.readUTF();
		taskContextMap = (Map<String, Serializable>)objectInput.readObject();

		completed = objectInput.readBoolean();
		completionDate = objectInput.readLong();

		status = objectInput.readInt();
		statusMessage = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(backgroundTaskId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (servletContextNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(servletContextNames);
		}

		if (taskExecutorClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(taskExecutorClassName);
		}

		objectOutput.writeObject(taskContextMap);

		objectOutput.writeBoolean(completed);
		objectOutput.writeLong(completionDate);

		objectOutput.writeInt(status);

		if (statusMessage == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusMessage);
		}
	}

	public long mvccVersion;
	public long backgroundTaskId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String servletContextNames;
	public String taskExecutorClassName;
	public Map<String, Serializable> taskContextMap;
	public boolean completed;
	public long completionDate;
	public int status;
	public String statusMessage;

}