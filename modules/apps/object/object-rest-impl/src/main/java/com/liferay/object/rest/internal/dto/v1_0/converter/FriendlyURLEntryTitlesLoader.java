/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.dto.v1_0.converter;

import com.liferay.friendly.url.model.FriendlyURLEntryTitles;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class FriendlyURLEntryTitlesLoader {

	public FriendlyURLEntryTitlesLoader(
		long classNameId,
		FriendlyURLEntryLocalService friendlyURLEntryLocalService,
		Collection<ObjectEntry> objectEntries) {

		_classNameId = classNameId;
		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;

		_objectEntryIds = TransformUtil.transformToLongArray(
			objectEntries, ObjectEntry::getObjectEntryId);
	}

	public String getURLTitle(long objectEntryId, Locale locale) {
		FriendlyURLEntryTitles friendlyURLEntryTitles =
			_getFriendlyURLEntryTitles(objectEntryId);

		if (friendlyURLEntryTitles == null) {
			return null;
		}

		return friendlyURLEntryTitles.getUrlTitle(
			LocaleUtil.toLanguageId(locale));
	}

	public Map<String, String> getURLTitleMap(long objectEntryId) {
		FriendlyURLEntryTitles friendlyURLEntryTitles =
			_getFriendlyURLEntryTitles(objectEntryId);

		if (friendlyURLEntryTitles == null) {
			return null;
		}

		return friendlyURLEntryTitles.getLanguageIdToUrlTitleMap();
	}

	private FriendlyURLEntryTitles _getFriendlyURLEntryTitles(
		long objectEntryId) {

		if (_friendlyURLEntryTitlesMap == null) {
			_friendlyURLEntryTitlesMap =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntryTitles(
					_classNameId, _objectEntryIds);
		}

		return _friendlyURLEntryTitlesMap.get(objectEntryId);
	}

	private final long _classNameId;
	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;
	private Map<Long, FriendlyURLEntryTitles> _friendlyURLEntryTitlesMap;
	private final long[] _objectEntryIds;

}