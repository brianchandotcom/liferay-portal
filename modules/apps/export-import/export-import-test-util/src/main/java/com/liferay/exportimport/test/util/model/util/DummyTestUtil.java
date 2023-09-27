/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util.model.util;

import com.liferay.exportimport.test.util.model.Dummy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Date;

/**
 * @author Akos Thurzo
 */
public class DummyTestUtil {

	public static Dummy createDummy(long groupId, long folderId)
		throws Exception {

		Dummy dummy = new Dummy();

		dummy.setCompanyId(TestPropsValues.getCompanyId());
		dummy.setFolderId(folderId);
		dummy.setGroupId(groupId);
		dummy.setLastPublishDate(null);

		User user = TestPropsValues.getUser();

		dummy.setUserId(user.getUserId());
		dummy.setUserName(user.getScreenName());
		dummy.setUserUuid(user.getUserUuid());

		return dummy;
	}

	public static Dummy createDummy(
			long groupId, long folderId, Date createdDate)
		throws Exception {

		Dummy dummy = createDummy(groupId, folderId);

		dummy.setCreateDate(createdDate);

		return dummy;
	}

}