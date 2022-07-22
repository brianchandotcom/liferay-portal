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

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.util.comparator.FriendlyURLEntryLocalizationComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "item.class.name=com.liferay.portal.kernel.repository.model.FileEntry",
	service = InfoItemFriendlyURLProvider.class
)
public class FileEntryInfoItemFriendlyURLProvider
	implements InfoItemFriendlyURLProvider<FileEntry> {

	@Override
	public String getFriendlyURL(FileEntry fileEntry, String languageId) {
		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.fetchMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		if (mainFriendlyURLEntry == null) {
			return String.valueOf(fileEntry.getFileEntryId());
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long groupId = serviceContext.getScopeGroupId();

		if ((groupId != GroupConstants.DEFAULT_LIVE_GROUP_ID) &&
			(groupId != mainFriendlyURLEntry.getGroupId())) {

			return String.valueOf(fileEntry.getFileEntryId());
		}

		return mainFriendlyURLEntry.getUrlTitle();
	}

	@Override
	public List<FriendlyURLEntryLocalization> getFriendlyURLEntryLocalizations(
		FileEntry fileEntry, String languageId) {

		return _friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
			fileEntry.getGroupId(), _portal.getClassNameId(FileEntry.class),
			fileEntry.getFileEntryId(),
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			_friendlyURLEntryLocalizationComparator);
	}

	private final FriendlyURLEntryLocalizationComparator
		_friendlyURLEntryLocalizationComparator =
			new FriendlyURLEntryLocalizationComparator();

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}