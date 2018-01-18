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

package com.liferay.document.library.search.test;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.service.test.ServiceTestUtil;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;

/**
 * @author Eric Yan
 */
public class DLFixture {

	public DLFixture(
		DLAppLocalService dlAppLocalService, List<Group> groups,
		List<User> users) {

		_dlAppLocalService = dlAppLocalService;
		_groups = groups;
		_users = users;
	}

	public FileEntry addFileEntry(
			String fileName, ServiceContext serviceContext)
		throws Exception {

		try (InputStream inputStream = DLFixture.class.getResourceAsStream(
				"dependencies/" + fileName)) {

			File file = FileUtil.createTempFile(inputStream);

			try {
				return _dlAppLocalService.addFileEntry(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
					getContentType(fileName), fileName, StringPool.BLANK,
					StringPool.BLANK, file, serviceContext);
			}
			finally {
				FileUtil.delete(file);
			}
		}
	}

	public DLFolder addFolder(
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws Exception {

		Folder folder = _dlAppLocalService.addFolder(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			parentFolderId, name, description, serviceContext);

		return (DLFolder)folder.getModel();
	}

	public Group addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	public User addUser() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		return user;
	}

	public ServiceContext getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), getUserId());
	}

	public void setGroup(Group group) {
		_group = group;
	}

	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());
	}

	public void setUser(User user) {
		_user = user;
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	protected String getContentType(String fileName) {
		return MimeTypesUtil.getContentType((File)null, fileName);
	}

	protected long getUserId() throws Exception {
		if (_user != null) {
			return _user.getUserId();
		}

		return TestPropsValues.getUserId();
	}

	private final DLAppLocalService _dlAppLocalService;
	private Group _group;
	private final List<Group> _groups;
	private User _user;
	private final List<User> _users;

}