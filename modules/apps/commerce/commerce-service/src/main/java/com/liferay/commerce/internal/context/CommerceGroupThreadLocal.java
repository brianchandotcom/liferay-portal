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

package com.liferay.commerce.internal.context;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Drew Brokke
 */
public class CommerceGroupThreadLocal {

	public static Group get() {
		return _commerceGroup.get();
	}

	public static void set(Group group) {
		_commerceGroup.set(group);
	}

	public static SafeCloseable setWithSafeCloseable(long groupId) {
		return _commerceGroup.setWithSafeCloseable(
			GroupLocalServiceUtil.fetchGroup(groupId));
	}

	private static final CentralizedThreadLocal<Group> _commerceGroup =
		new CentralizedThreadLocal<>(
			CommerceGroupThreadLocal.class + "._commerceGroup");

}