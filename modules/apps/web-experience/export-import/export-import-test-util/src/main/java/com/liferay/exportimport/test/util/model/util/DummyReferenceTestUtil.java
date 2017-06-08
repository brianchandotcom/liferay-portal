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

import com.liferay.exportimport.test.util.model.DummyReference;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Date;

/**
 * @author Akos Thurzo
 */
public class DummyReferenceTestUtil {

	public static DummyReference createDummyReference(long groupId)
		throws Exception {

		DummyReference dummyReference = new DummyReference();

		dummyReference.setCompanyId(TestPropsValues.getCompanyId());
		dummyReference.setGroupId(groupId);
		dummyReference.setLastPublishDate(null);

		User user = TestPropsValues.getUser();

		dummyReference.setUserId(user.getUserId());
		dummyReference.setUserName(user.getScreenName());
		dummyReference.setUserUuid(user.getUserUuid());

		return dummyReference;
	}

	public static DummyReference createDummyReference(
			long groupId, Date createdDate)
		throws Exception {

		DummyReference dummyReference = createDummyReference(groupId);

		dummyReference.setCreateDate(createdDate);

		return dummyReference;
	}

}