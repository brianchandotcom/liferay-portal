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

package com.liferay.sharing.search.internal.permission;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFieldContributor;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Indexes a new field in the search document to include all users the resource
 * has been shared with. This information is used to do permission checks when
 * returning search results via {@link
 * SharingEntrySearchPermissionFilterContributor}.
 *
 * <p>
 * Each time a resource is shared, the associated search document is reindexed
 * and this {@code SearchPermissionFieldContributor} ensures that the user the
 * resource is shared with is added to the search field.
 * </p>
 *
 * @author Sergio González
 */
@Component(immediate = true, service = SearchPermissionFieldContributor.class)
public class SharingEntrySearchPermissionDocumentContributor
	implements SearchPermissionFieldContributor {

	@Override
	public void contribute(Document document, String className, long classPK) {
		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getSharingEntries(
				_portal.getClassNameId(className), classPK);

		if (sharingEntries.isEmpty()) {
			return;
		}

		long[] userIds = new long[sharingEntries.size()];

		for (int i = 0; i < userIds.length; i++) {
			SharingEntry sharingEntry = sharingEntries.get(i);

			userIds[i] = sharingEntry.getToUserId();
		}

		document.addKeyword("sharedToUserId", userIds);
	}

	@Reference
	private Portal _portal;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}