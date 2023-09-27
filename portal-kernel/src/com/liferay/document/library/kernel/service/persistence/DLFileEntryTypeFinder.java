/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface DLFileEntryTypeFinder {

	public int countByKeywords(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType);

	public int filterCountByKeywords(
		long companyId, long folderId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, boolean inherited);

	public int filterCountByKeywords(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType);

	public java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
			filterFindByKeywords(
				long companyId, long folderId, long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, boolean inherited, int start,
				int end);

	public java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
			filterFindByKeywords(
				long companyId, long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntryType>
						orderByComparator);

	public java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
			findByKeywords(
				long companyId, long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntryType>
						orderByComparator);

}