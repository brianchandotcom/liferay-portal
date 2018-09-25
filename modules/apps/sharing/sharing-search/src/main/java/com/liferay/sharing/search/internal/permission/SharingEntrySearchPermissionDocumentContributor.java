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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFieldContributor;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Index a new field in the search document to include all the users who have
 * been shared the document. This information will be used to do permission
 * checks when returning search results by
 * {@link SharingEntrySearchPermissionFilterContributor}.
 *
 * Everytime a resource is shared the associated search document is reindexed
 * and this {@link SearchPermissionFieldContributor} will make sure that the new
 * user who has been shared the document is added to the search field.
 *
 * @author Sergio González
 * @review
 */
@Component(immediate = true, service = SearchPermissionFieldContributor.class)
public class SharingEntrySearchPermissionDocumentContributor
	implements SearchPermissionFieldContributor {

	@Override
	public void contribute(Document document, String className, long classPK) {
		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getSharingEntries(
				_portal.getClassNameId(className), classPK);

		Stream<SharingEntry> stream = sharingEntries.stream();

		Long[] userIds = stream.map(
			SharingEntry::getToUserId
		).toArray(
			Long[]::new
		);

		if (ArrayUtil.isNotEmpty(userIds)) {
			document.addKeyword("sharedToUserId", userIds);
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}