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