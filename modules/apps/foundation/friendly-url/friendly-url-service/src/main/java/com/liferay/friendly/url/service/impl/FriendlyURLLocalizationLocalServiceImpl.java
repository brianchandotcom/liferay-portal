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
import com.liferay.friendly.url.model.FriendlyURLLocalization;
import com.liferay.friendly.url.service.base.FriendlyURLLocalizationLocalServiceBaseImpl;
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
public class FriendlyURLLocalizationLocalServiceImpl
	extends FriendlyURLLocalizationLocalServiceBaseImpl {

	@Override
	public FriendlyURLLocalization addFriendlyURLLocalization(
			FriendlyURLEntry friendlyURLEntry, String urlTitle,
			String languageId)
		throws PortalException {

		if (friendlyURLEntry == null) {
			return null;
		}

		long companyId = friendlyURLEntry.getCompanyId();
		long groupId = friendlyURLEntry.getGroupId();

		long friendlyURLLocalizationId = counterLocalService.increment();

		FriendlyURLLocalization friendlyURLLocalization =
			friendlyURLLocalizationPersistence.create(
				friendlyURLLocalizationId);

		friendlyURLLocalization.setCompanyId(companyId);
		friendlyURLLocalization.setGroupId(groupId);
		friendlyURLLocalization.setFriendlyURLEntryId(
			friendlyURLEntry.getFriendlyURLEntryId());
		friendlyURLLocalization.setUrlTitle(urlTitle);
		friendlyURLLocalization.setLanguageId(languageId);

		friendlyURLLocalizationPersistence.update(friendlyURLLocalization);

		return friendlyURLLocalization;
	}

	@Override
	public FriendlyURLLocalization deleteFriendlyURLLocalization(
			FriendlyURLEntry friendlyURLEntry, String languageId)
		throws PortalException {

		if (friendlyURLEntry == null) {
			return null;
		}

		return friendlyURLLocalizationPersistence.removeByG_F_L(
			friendlyURLEntry.getGroupId(),
			friendlyURLEntry.getFriendlyURLEntryId(), languageId);
	}

	@Override
	public void deleteFriendlyURLLocalizations(
			FriendlyURLEntry friendlyURLEntry)
		throws PortalException {

		if (friendlyURLEntry == null) {
			return;
		}

		friendlyURLLocalizationPersistence.removeByG_F(
			friendlyURLEntry.getGroupId(),
			friendlyURLEntry.getFriendlyURLEntryId());
	}

	@Override
	public FriendlyURLLocalization fetchFriendlyURLLocalization(
		FriendlyURLEntry friendlyURLEntry, String languageId) {

		if (friendlyURLEntry == null) {
			return null;
		}

		return friendlyURLLocalizationPersistence.fetchByG_F_L(
			friendlyURLEntry.getGroupId(),
			friendlyURLEntry.getFriendlyURLEntryId(), languageId);
	}

	@Override
	public FriendlyURLLocalization fetchFriendlyURLLocalization(
		long companyId, long groupId, long classNameId, long classPK,
		String languageId) {

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryPersistence.fetchByC_G_C_C_M(
				companyId, groupId, classNameId, classPK, true);

		if (friendlyURLEntry == null) {
			return null;
		}

		return friendlyURLLocalizationPersistence.fetchByG_F_L(
			groupId, friendlyURLEntry.getFriendlyURLEntryId(), languageId);
	}

	@Override
	public int getFriendlyURLLocalizationCount(
		FriendlyURLEntry friendlyURLEntry) {

		if (friendlyURLEntry == null) {
			return 0;
		}

		return friendlyURLLocalizationPersistence.countByG_F(
			friendlyURLEntry.getGroupId(),
			friendlyURLEntry.getFriendlyURLEntryId());
	}

	@Override
	public List<FriendlyURLLocalization> updateFriendlyURLLocalizations(
			FriendlyURLEntry friendlyURLEntry, Map<Locale, String> urlTitleMap)
		throws PortalException {

		if (friendlyURLEntry == null) {
			return Collections.emptyList();
		}

		List<FriendlyURLLocalization> friendlyURLLocalizations =
			new ArrayList<>();

		long groupId = friendlyURLEntry.getGroupId();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String urlTitle = urlTitleMap.get(locale);

			String languageId = LocaleUtil.toLanguageId(locale);

			if (Validator.isNull(urlTitle)) {
				deleteFriendlyURLLocalization(friendlyURLEntry, languageId);

				continue;
			}

			FriendlyURLLocalization friendlyURLLocalization =
				friendlyURLLocalizationPersistence.fetchByG_U_L(
					groupId, urlTitle, languageId);

			if (friendlyURLLocalization != null) {
				friendlyURLLocalizations.add(friendlyURLLocalization);

				continue;
			}

			friendlyURLLocalization = addFriendlyURLLocalization(
				friendlyURLEntry, urlTitle, languageId);

			friendlyURLLocalizations.add(friendlyURLLocalization);
		}

		return friendlyURLLocalizations;
	}

}