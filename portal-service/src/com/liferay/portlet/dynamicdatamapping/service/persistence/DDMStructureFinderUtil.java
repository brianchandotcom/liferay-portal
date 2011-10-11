/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureFinderUtil {
	public static int countByKeywords(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByKeywords(companyId, groupIds, classNameIds, keywords);
	}

	public static int countByC_G_C_N_D_S(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_C_N_D_S(companyId, groupIds, classNameIds, name,
			description, storageType, andOperator);
	}

	public static int countByC_G_C_N_D_S(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String[] names,
		java.lang.String[] descriptions, java.lang.String[] storageTypes,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_C_N_D_S(companyId, groupIds, classNameIds,
			names, descriptions, storageTypes, andOperator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByKeywords(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByKeywords(companyId, groupIds, classNameIds, keywords,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByC_G_C_N_D_S(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_C_N_D_S(companyId, groupIds, classNameIds, name,
			description, storageType, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByC_G_C_N_D_S(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] storageTypes, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_C_N_D_S(companyId, groupIds, classNameIds, names,
			descriptions, storageTypes, andOperator, start, end,
			orderByComparator);
	}

	public static DDMStructureFinder getFinder() {
		if (_finder == null) {
			_finder = (DDMStructureFinder)PortalBeanLocatorUtil.locate(DDMStructureFinder.class.getName());

			ReferenceRegistry.registerReference(DDMStructureFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DDMStructureFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DDMStructureFinderUtil.class,
			"_finder");
	}

	private static DDMStructureFinder _finder;
}