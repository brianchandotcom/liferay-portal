/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;

/**
 * @author Alberto Chaparro
 */
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
		resourceBlock.setPermissionsHash(ServiceTestUtil.randomString());
		resourceBlock.setReferenceCount(0);

		return ResourceBlockLocalServiceUtil.addResourceBlock(resourceBlock);
	}

	public static ResourceBlock addResourceBlock(String name) throws Exception {
		return addResourceBlock(ServiceTestUtil.nextLong(), name);
	}

}