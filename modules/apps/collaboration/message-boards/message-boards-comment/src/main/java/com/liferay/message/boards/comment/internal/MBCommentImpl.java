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

package com.liferay.message.boards.comment.internal;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.util.MBUtil;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class MBCommentImpl implements WorkflowableComment {

	public MBCommentImpl(MBMessage message) {
		_message = message;
	}

	@Override
	public String getBody() {
		return _message.getBody();
	}

	@Override
	public String getClassName() {
		return _message.getClassName();
	}

	@Override
	public long getClassPK() {
		return _message.getClassPK();
	}

	@Override
	public long getCommentId() {
		return _message.getMessageId();
	}

	@Override
	public long getCompanyId() {
		return _message.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _message.getCreateDate();
	}

	@Override
	public long getGroupId() {
		return _message.getGroupId();
	}

	public MBMessage getMessage() {
		return _message;
	}

	@Override
	public Class<?> getModelClass() {
		return MBMessage.class;
	}

	@Override
	public String getModelClassName() {
		return MBMessage.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _message.getModifiedDate();
	}

	@Override
	public long getParentCommentId() {
		MBMessage message = getMessage();

		return message.getParentMessageId();
	}

	@Override
	public long getPrimaryKey() {
		return _message.getPrimaryKey();
	}

	@Override
	public int getStatus() {
		return _message.getStatus();
	}

	@Override
	public String getTranslatedBody(String pathThemeImages) {
		MBMessage message = getMessage();

		if (message.isFormatBBCode()) {
			return MBUtil.getBBCodeHTML(getBody(), pathThemeImages);
		}

		return getBody();
	}

	@Override
	public User getUser() {
		return UserLocalServiceUtil.fetchUser(getUserId());
	}

	@Override
	public long getUserId() {
		return _message.getUserId();
	}

	@Override
	public String getUserName() {
		return _message.getUserName();
	}

	@Override
	public String getUuid() {
		return _message.getUuid();
	}

	@Override
	public boolean isRoot() {
		MBMessage message = getMessage();

		return message.isRoot();
	}

	private final MBMessage _message;

}