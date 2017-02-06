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

import com.liferay.friendly.url.model.FriendlyURL;
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
			FriendlyURL friendlyURL, String urlTitle, String languageId)
		throws PortalException {

		if (friendlyURL == null) {
			return null;
		}

		long companyId = friendlyURL.getCompanyId();
		long groupId = friendlyURL.getGroupId();

		long friendlyURLLocalizationId = counterLocalService.increment();

		FriendlyURLLocalization friendlyURLLocalization =
			friendlyURLLocalizationPersistence.create(
				friendlyURLLocalizationId);

		friendlyURLLocalization.setCompanyId(companyId);
		friendlyURLLocalization.setGroupId(groupId);
		friendlyURLLocalization.setFriendlyURLId(
			friendlyURL.getFriendlyURLId());
		friendlyURLLocalization.setUrlTitle(urlTitle);
		friendlyURLLocalization.setLanguageId(languageId);

		friendlyURLLocalizationPersistence.update(friendlyURLLocalization);

		return friendlyURLLocalization;
	}

	@Override
	public FriendlyURLLocalization deleteFriendlyURLLocalization(
			FriendlyURL friendlyURL, String languageId)
		throws PortalException {

		if (friendlyURL == null) {
			return null;
		}

		return friendlyURLLocalizationPersistence.removeByG_F_L(
			friendlyURL.getGroupId(), friendlyURL.getFriendlyURLId(),
			languageId);
	}

	@Override
	public void deleteFriendlyURLLocalizations(FriendlyURL friendlyURL)
		throws PortalException {

		if (friendlyURL == null) {
			return;
		}

		friendlyURLLocalizationPersistence.removeByG_F(
			friendlyURL.getGroupId(), friendlyURL.getFriendlyURLId());
	}

	@Override
	public FriendlyURLLocalization fetchFriendlyURLLocalization(
		FriendlyURL friendlyURL, String languageId) {

		if (friendlyURL == null) {
			return null;
		}

		return friendlyURLLocalizationPersistence.fetchByG_F_L(
			friendlyURL.getGroupId(), friendlyURL.getFriendlyURLId(),
			languageId);
	}

	@Override
	public FriendlyURLLocalization fetchFriendlyURLLocalization(
		long companyId, long groupId, long classNameId, long classPK,
		String languageId) {

		FriendlyURL friendlyURL = friendlyURLPersistence.fetchByC_G_C_C_M(
			companyId, groupId, classNameId, classPK, true);

		if (friendlyURL == null) {
			return null;
		}

		return friendlyURLLocalizationPersistence.fetchByG_F_L(
			groupId, friendlyURL.getFriendlyURLId(), languageId);
	}

	@Override
	public int getFriendlyURLLocalizationCount(FriendlyURL friendlyURL) {
		if (friendlyURL == null) {
			return 0;
		}

		return friendlyURLLocalizationPersistence.countByG_F(
			friendlyURL.getGroupId(), friendlyURL.getFriendlyURLId());
	}

	@Override
	public List<FriendlyURLLocalization> updateFriendlyURLLocalizations(
			FriendlyURL friendlyURL, Map<Locale, String> urlTitleMap)
		throws PortalException {

		if (friendlyURL == null) {
			return Collections.emptyList();
		}

		List<FriendlyURLLocalization> friendlyURLLocalizations =
			new ArrayList<>();

		long groupId = friendlyURL.getGroupId();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String urlTitle = urlTitleMap.get(locale);

			String languageId = LocaleUtil.toLanguageId(locale);

			if (Validator.isNull(urlTitle)) {
				deleteFriendlyURLLocalization(friendlyURL, languageId);

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
				friendlyURL, urlTitle, languageId);

			friendlyURLLocalizations.add(friendlyURLLocalization);
		}

		return friendlyURLLocalizations;
	}

}