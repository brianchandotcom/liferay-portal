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

package com.liferay.exportimport.test.util.model.util;

import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Date;

/**
 * @author Akos Thurzo
 */
public class DummyFolderTestUtil {

	public static DummyFolder createDummyFolder(long groupId) throws Exception {
		DummyFolder dummyFolder = new DummyFolder();

		dummyFolder.setCompanyId(TestPropsValues.getCompanyId());
		dummyFolder.setGroupId(groupId);
		dummyFolder.setLastPublishDate(null);

		User user = UserLocalServiceUtil.getUserByEmailAddress(
			TestPropsValues.getCompanyId(), "test@liferay.com");

		dummyFolder.setUserId(user.getUserId());
		dummyFolder.setUserName(user.getScreenName());
		dummyFolder.setUserUuid(user.getUserUuid());

		return dummyFolder;
	}

	public static DummyFolder createDummyFolder(long groupId, Date createdDate)
		throws Exception {

		DummyFolder dummyFolder = createDummyFolder(groupId);

		dummyFolder.setCreateDate(createdDate);

		return dummyFolder;
	}

}