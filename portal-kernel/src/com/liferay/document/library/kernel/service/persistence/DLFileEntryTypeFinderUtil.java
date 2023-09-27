/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryTypeFinderUtil {

	public static int countByKeywords(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType) {

		return getFinder().countByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	public static int filterCountByKeywords(
		long companyId, long folderId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, boolean inherited) {

		return getFinder().filterCountByKeywords(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited);
	}

	public static int filterCountByKeywords(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType) {

		return getFinder().filterCountByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
			filterFindByKeywords(
				long companyId, long folderId, long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, boolean inherited, int start,
				int end) {

		return getFinder().filterFindByKeywords(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited, start, end);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
			filterFindByKeywords(
				long companyId, long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntryType>
						orderByComparator) {

		return getFinder().filterFindByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
			findByKeywords(
				long companyId, long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntryType>
						orderByComparator) {

		return getFinder().findByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	public static DLFileEntryTypeFinder getFinder() {
		if (_finder == null) {
			_finder = (DLFileEntryTypeFinder)PortalBeanLocatorUtil.locate(
				DLFileEntryTypeFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(DLFileEntryTypeFinder finder) {
		_finder = finder;
	}

	private static DLFileEntryTypeFinder _finder;

}