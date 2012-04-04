/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordFinderUtil {
	public static int countByC_S_S(long companyId, int status, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByC_S_S(companyId, status, scope);
	}

	public static int countByR_S(long recordSetId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByR_S(recordSetId, status);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByC_S_S(
		long companyId, int status, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_S_S(companyId, status, scope, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByR_S(
		long recordSetId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByR_S(recordSetId, status, start, end, orderByComparator);
	}

	public static DDLRecordFinder getFinder() {
		if (_finder == null) {
			_finder = (DDLRecordFinder)PortalBeanLocatorUtil.locate(DDLRecordFinder.class.getName());

			ReferenceRegistry.registerReference(DDLRecordFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DDLRecordFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DDLRecordFinderUtil.class, "_finder");
	}

	private static DDLRecordFinder _finder;
}