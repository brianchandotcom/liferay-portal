/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.service.base.ListTypeServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ListTypeServiceImpl extends ListTypeServiceBaseImpl {

	@Override
	public ListType getListType(long listTypeId) throws PortalException {
		return listTypeLocalService.getListType(listTypeId);
	}

	@Override
	public ListType getListType(String name, String type) {
		return listTypeLocalService.getListType(name, type);
	}

	@Override
	public List<ListType> getListTypes(String type) {
		return listTypeLocalService.getListTypes(type);
	}

	@Override
	public void validate(long listTypeId, long classNameId, String type)
		throws PortalException {

		listTypeLocalService.validate(listTypeId, classNameId, type);
	}

	@Override
	public void validate(long listTypeId, String type) throws PortalException {
		listTypeLocalService.validate(listTypeId, type);
	}

}