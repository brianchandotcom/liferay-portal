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

package com.liferay.portal.model.impl;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class LayoutPrototypeImpl extends LayoutPrototypeBaseImpl {

	@Override
	public Group getGroup() throws PortalException {
		return GroupLocalServiceUtil.getLayoutPrototypeGroup(
			getCompanyId(), getLayoutPrototypeId());
	}

	@Override
	public long getGroupId() throws PortalException {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public Layout getLayout() throws PortalException {
		Group group = getGroup();

		if (group.hasLayouts()) {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				group.getGroupId());

			return layouts.get(0);
		}

		throw new NoSuchLayoutException("{groupId=" + group.getGroupId() + "}");
	}

}