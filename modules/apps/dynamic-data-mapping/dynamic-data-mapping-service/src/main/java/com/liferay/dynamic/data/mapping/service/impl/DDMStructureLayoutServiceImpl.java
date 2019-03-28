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
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureLayoutServiceImpl
	extends DDMStructureLayoutServiceBaseImpl {

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

	@Override
	public List<DDMStructureLayout> getStructureLayoutsSearch(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int start, int end,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws PortalException {

		return ddmStructureLayoutLocalService.getStructureLayoutsSearch(
			companyId, groupIds, classNameId, keywords, start, end,
			orderByComparator);
	}

}