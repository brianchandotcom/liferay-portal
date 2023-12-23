/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStructureLinkService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkService
 * @generated
 */
public class DDMStructureLinkServiceWrapper
	implements DDMStructureLinkService,
			   ServiceWrapper<DDMStructureLinkService> {

	public DDMStructureLinkServiceWrapper() {
		this(null);
	}

	public DDMStructureLinkServiceWrapper(
		DDMStructureLinkService ddmStructureLinkService) {

		_ddmStructureLinkService = ddmStructureLinkService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmStructureLinkService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<DDMStructureLink> getStructureLinkStructures(
		long classNameId, long classPK, long[] groupIds, String keywords,
		String resourceClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator) {

		return _ddmStructureLinkService.getStructureLinkStructures(
			classNameId, classPK, groupIds, keywords, resourceClassName, start,
			end, orderByComparator);
	}

	@Override
	public int getStructureLinkStructuresCount(
		long classNameId, long classPK, long[] groupIds, String keywords,
		String resourceClassName) {

		return _ddmStructureLinkService.getStructureLinkStructuresCount(
			classNameId, classPK, groupIds, keywords, resourceClassName);
	}

	@Override
	public DDMStructureLinkService getWrappedService() {
		return _ddmStructureLinkService;
	}

	@Override
	public void setWrappedService(
		DDMStructureLinkService ddmStructureLinkService) {

		_ddmStructureLinkService = ddmStructureLinkService;
	}

	private DDMStructureLinkService _ddmStructureLinkService;

}