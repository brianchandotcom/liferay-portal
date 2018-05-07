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