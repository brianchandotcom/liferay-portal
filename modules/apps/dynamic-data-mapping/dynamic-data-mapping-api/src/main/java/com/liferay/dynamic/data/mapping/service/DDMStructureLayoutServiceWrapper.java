/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStructureLayoutService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutService
 * @generated
 */
public class DDMStructureLayoutServiceWrapper
	implements DDMStructureLayoutService,
			   ServiceWrapper<DDMStructureLayoutService> {

	public DDMStructureLayoutServiceWrapper() {
		this(null);
	}

	public DDMStructureLayoutServiceWrapper(
		DDMStructureLayoutService ddmStructureLayoutService) {

		_ddmStructureLayoutService = ddmStructureLayoutService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmStructureLayoutService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<DDMStructureLayout> getStructureLayouts(
			long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmStructureLayoutService.getStructureLayouts(
			groupId, start, end);
	}

	@Override
	public int getStructureLayoutsCount(long groupId) {
		return _ddmStructureLayoutService.getStructureLayoutsCount(groupId);
	}

	@Override
	public java.util.List<DDMStructureLayout> search(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmStructureLayoutService.search(
			companyId, groupIds, classNameId, keywords, start, end,
			orderByComparator);
	}

	@Override
	public DDMStructureLayoutService getWrappedService() {
		return _ddmStructureLayoutService;
	}

	@Override
	public void setWrappedService(
		DDMStructureLayoutService ddmStructureLayoutService) {

		_ddmStructureLayoutService = ddmStructureLayoutService;
	}

	private DDMStructureLayoutService _ddmStructureLayoutService;

}