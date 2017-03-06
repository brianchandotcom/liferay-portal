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

package com.liferay.friendly.url.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.base.FriendlyURLEntryLocalizationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
@ProviderType
public class FriendlyURLEntryLocalizationLocalServiceImpl
	extends FriendlyURLEntryLocalizationLocalServiceBaseImpl {

	@Override
	public FriendlyURLEntryLocalization addFriendlyURLEntryLocalization(
			FriendlyURLEntry friendlyURLEntry, String urlTitle,
			String languageId)
		throws PortalException {

		if (friendlyURLEntry == null) {
			return null;
		}

		long companyId = friendlyURLEntry.getCompanyId();
		long groupId = friendlyURLEntry.getGroupId();

		long friendlyURLEntryLocalizationId = counterLocalService.increment();

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalizationPersistence.create(
				friendlyURLEntryLocalizationId);

		friendlyURLEntryLocalization.setCompanyId(companyId);
		friendlyURLEntryLocalization.setGroupId(groupId);
		friendlyURLEntryLocalization.setFriendlyURLEntryId(
			friendlyURLEntry.getFriendlyURLEntryId());
		friendlyURLEntryLocalization.setUrlTitle(urlTitle);
		friendlyURLEntryLocalization.setLanguageId(languageId);

		friendlyURLEntryLocalizationPersistence.update(
			friendlyURLEntryLocalization);

		return friendlyURLEntryLocalization;
	}

	@Override
	public FriendlyURLEntryLocalization deleteFriendlyURLEntryLocalization(
			FriendlyURLEntry friendlyURLEntry, String languageId)
		throws PortalException {

		if (friendlyURLEntry == null) {
			return null;
		}

		return friendlyURLEntryLocalizationPersistence.removeByG_F_L(
			friendlyURLEntry.getGroupId(),
			friendlyURLEntry.getFriendlyURLEntryId(), languageId);
	}

	@Override
	public void deleteFriendlyURLEntryLocalizations(
			FriendlyURLEntry friendlyURLEntry)
		throws PortalException {

		if (friendlyURLEntry == null) {
			return;
		}

		friendlyURLEntryLocalizationPersistence.removeByG_F(
			friendlyURLEntry.getGroupId(),
			friendlyURLEntry.getFriendlyURLEntryId());
	}

	@Override
	public FriendlyURLEntryLocalization fetchFriendlyURLEntryLocalization(
		FriendlyURLEntry friendlyURLEntry, String languageId) {

		if (friendlyURLEntry == null) {
			return null;
		}

		return friendlyURLEntryLocalizationPersistence.fetchByG_F_L(
			friendlyURLEntry.getGroupId(),
			friendlyURLEntry.getFriendlyURLEntryId(), languageId);
	}

	@Override
	public FriendlyURLEntryLocalization fetchFriendlyURLEntryLocalization(
		long companyId, long groupId, long classNameId, long classPK,
		String languageId) {

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryPersistence.fetchByG_C_C_C_M(
				groupId, companyId, classNameId, classPK, true);

		if (friendlyURLEntry == null) {
			return null;
		}

		return friendlyURLEntryLocalizationPersistence.fetchByG_F_L(
			groupId, friendlyURLEntry.getFriendlyURLEntryId(), languageId);
	}

	@Override
	public int getFriendlyURLEntryLocalizationCount(
		FriendlyURLEntry friendlyURLEntry) {

		if (friendlyURLEntry == null) {
			return 0;
		}

		return friendlyURLEntryLocalizationPersistence.countByG_F(
			friendlyURLEntry.getGroupId(),
			friendlyURLEntry.getFriendlyURLEntryId());
	}

	@Override
	public List<FriendlyURLEntryLocalization>
			updateFriendlyURLEntryLocalizations(
				FriendlyURLEntry friendlyURLEntry,
				Map<Locale, String> urlTitleMap)
		throws PortalException {

		if (friendlyURLEntry == null) {
			return Collections.emptyList();
		}

		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
			new ArrayList<>();

		long groupId = friendlyURLEntry.getGroupId();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String urlTitle = urlTitleMap.get(locale);

			String languageId = LocaleUtil.toLanguageId(locale);

			if (Validator.isNull(urlTitle)) {
				deleteFriendlyURLEntryLocalization(
					friendlyURLEntry, languageId);

				continue;
			}

			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				friendlyURLEntryLocalizationPersistence.fetchByG_U_L(
					groupId, urlTitle, languageId);

			if (friendlyURLEntryLocalization != null) {
				friendlyURLEntryLocalizations.add(friendlyURLEntryLocalization);

				continue;
			}

			friendlyURLEntryLocalization = addFriendlyURLEntryLocalization(
				friendlyURLEntry, urlTitle, languageId);

			friendlyURLEntryLocalizations.add(friendlyURLEntryLocalization);
		}

		return friendlyURLEntryLocalizations;
	}

}