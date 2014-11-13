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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class DLFileEntryTypeFinderUtil {
	public static int countByKeywords(long companyId, long[] groupIds,
		java.lang.String keywords, boolean includeBasicFileEntryType) {
		return getFinder()
				   .countByKeywords(companyId, groupIds, keywords,
			includeBasicFileEntryType);
	}

	public static int filterCountByKeywords(long companyId, long[] groupIds,
		java.lang.String keywords, boolean includeBasicFileEntryType) {
		return getFinder()
				   .filterCountByKeywords(companyId, groupIds, keywords,
			includeBasicFileEntryType);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByKeywords(
		long companyId, long[] groupIds, java.lang.String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return getFinder()
				   .filterFindByKeywords(companyId, groupIds, keywords,
			includeBasicFileEntryType, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByKeywords(
		long companyId, long[] groupIds, java.lang.String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return getFinder()
				   .findByKeywords(companyId, groupIds, keywords,
			includeBasicFileEntryType, start, end, orderByComparator);
	}

	public static DLFileEntryTypeFinder getFinder() {
		if (_finder == null) {
			_finder = (DLFileEntryTypeFinder)PortalBeanLocatorUtil.locate(DLFileEntryTypeFinder.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryTypeFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DLFileEntryTypeFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DLFileEntryTypeFinderUtil.class,
			"_finder");
	}

	private static DLFileEntryTypeFinder _finder;
}