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