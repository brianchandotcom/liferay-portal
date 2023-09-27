/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test.util;

import com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class ObjectReviewedTestUtil {

	public static ObjectReviewed addObjectReviewed() {
		ObjectReviewed objectReviewed = new ObjectReviewed() {
			{
				assetTitle = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				assetType = "ObjectReviewed";
				id = RandomTestUtil.randomLong();
			}
		};

		_objectReviewedMap.put(objectReviewed.getId(), objectReviewed);

		return objectReviewed;
	}

	public static void clear() {
		_objectReviewedMap.clear();
	}

	public static ObjectReviewed getObjectReviewed(long id) {
		return _objectReviewedMap.get(id);
	}

	private static final Map<Long, ObjectReviewed> _objectReviewedMap =
		new HashMap<>();

}