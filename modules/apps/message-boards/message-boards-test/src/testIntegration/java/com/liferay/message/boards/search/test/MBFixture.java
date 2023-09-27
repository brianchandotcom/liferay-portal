/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public MBMessage createMBMessage(long categoryId, String subject)
		throws Exception {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		MBMessage mbMessage = MBMessageLocalServiceUtil.addMessage(
			getUserId(), RandomTestUtil.randomString(), _group.getGroupId(),
			categoryId, 0, MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID,
			subject, RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
			false, getServiceContext());

		_mbMessages.add(mbMessage);

		_mbThreads.add(mbMessage.getThread());

		return mbMessage;
	}

	public MBMessage createMBMessageWithCategory(String subject)
		throws Exception {

		MBCategory mbCategory = createMBCategory();

		MBMessage mbMessage = createMBMessage(
			mbCategory.getCategoryId(), subject);

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

		Date date = new Date();

		serviceContext.setCreateDate(date);
		serviceContext.setModifiedDate(date);

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