/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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