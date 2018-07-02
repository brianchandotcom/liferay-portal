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

package com.liferay.portal.kernel.test.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.model.ResourceBlock;
import com.liferay.portal.kernel.service.ResourceBlockLocalServiceUtil;

/**
 * @author     Alberto Chaparro
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ResourceBlockTestUtil {

	public static ResourceBlock addResourceBlock(long groupId, String name)
		throws Exception {

		long resourceBlockId = CounterLocalServiceUtil.increment(
			ResourceBlock.class.getName());

		ResourceBlock resourceBlock =
			ResourceBlockLocalServiceUtil.createResourceBlock(resourceBlockId);

		resourceBlock.setCompanyId(TestPropsValues.getCompanyId());
		resourceBlock.setGroupId(groupId);
		resourceBlock.setName(name);
		resourceBlock.setPermissionsHash(RandomTestUtil.randomString());
		resourceBlock.setReferenceCount(0);

		return ResourceBlockLocalServiceUtil.addResourceBlock(resourceBlock);
	}

	public static ResourceBlock addResourceBlock(String name) throws Exception {
		return addResourceBlock(RandomTestUtil.nextLong(), name);
	}

}