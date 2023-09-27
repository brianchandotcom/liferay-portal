/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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