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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureLayoutServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * The implementation of the ddm structure layout remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.dynamic.data.mapping.service.DDMStructureLayoutService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutServiceBaseImpl
 */
public class DDMStructureLayoutServiceImpl
	extends DDMStructureLayoutServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.dynamic.data.mapping.service.DDMStructureLayoutServiceUtil</code> to access the ddm structure layout remote service.
	 */
	@Override
	public List<DDMStructureLayout> getStructureLayouts(
			long groupId, int start, int end)
		throws PortalException {

		return ddmStructureLayoutLocalService.getStructureLayouts(
			groupId, start, end);
	}

	@Override
	public int getStructureLayoutsCount(long groupId) {
		return ddmStructureLayoutLocalService.getStructureLayoutsCount(groupId);
	}

}