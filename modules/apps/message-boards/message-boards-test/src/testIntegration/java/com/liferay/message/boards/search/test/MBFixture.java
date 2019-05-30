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

package com.liferay.message.boards.search.test;

import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Luan Maoski
 */
public class MBFixture {

	public MBFixture(Group group) throws PortalException {
		_group = group;
		_user = TestPropsValues.getUser();
	}

	public MBFixture(Group group, User user) {
		_group = group;
		_user = user;
	}

	public MBCategory createMBCategory() throws Exception {
		MBCategory mbCategory = MBCategoryLocalServiceUtil.addCategory(
			getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), getServiceContext());

		_mbCategories.add(mbCategory);

		return mbCategory;
	}

	public MBMessage createMBMessage(long userId, long categoryId, String title)
		throws Exception {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		MBMessage mbMessage = MBMessageLocalServiceUtil.addMessage(
			userId, RandomTestUtil.randomString(), _group.getGroupId(),
			categoryId, 0, MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, title,
			RandomTestUtil.randomString(), MBMessageConstants.DEFAULT_FORMAT,
			inputStreamOVPs, false, 0.0, false, getServiceContext());

		_mbMessages.add(mbMessage);

		_mbThreads.add(mbMessage.getThread());

		return mbMessage;
	}

	public MBMessage createMBMessageWithCategory(String title, long userId)
		throws Exception {

		MBCategory mbCategory = createMBCategory();

		MBMessage mbMessage = createMBMessage(
			userId, mbCategory.getCategoryId(), title);

		_mbMessages.add(mbMessage);

		_mbThreads.add(mbMessage.getThread());

		return mbMessage;
	}

	public List<MBCategory> getMbCategories() {
		return _mbCategories;
	}

	public List<MBMessage> getMbMessages() {
		return _mbMessages;
	}

	public List<MBThread> getMbThreads() {
		return _mbThreads;
	}

	public ServiceContext getServiceContext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), getUserId());

		Date now = new Date();

		serviceContext.setCreateDate(now);
		serviceContext.setModifiedDate(now);

		return serviceContext;
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	protected long getUserId() {
		return _user.getUserId();
	}

	private final Group _group;
	private final List<MBCategory> _mbCategories = new ArrayList<>();
	private final List<MBMessage> _mbMessages = new ArrayList<>();
	private final List<MBThread> _mbThreads = new ArrayList<>();
	private final User _user;

}