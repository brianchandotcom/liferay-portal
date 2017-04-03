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

package com.liferay.site.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.model.GroupFriendlyURL;
import com.liferay.site.service.base.GroupFriendlyURLLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class GroupFriendlyURLLocalServiceImpl
	extends GroupFriendlyURLLocalServiceBaseImpl {

	@Override
	public GroupFriendlyURL addGroupFriendlyURL(
			long userId, long companyId, long groupId, String friendlyURL,
			String languageId, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long groupFriendlyURLId = counterLocalService.increment();

		GroupFriendlyURL groupFriendlyURL = groupFriendlyURLPersistence.create(
			groupFriendlyURLId);

		if (serviceContext != null) {
			groupFriendlyURL.setUuid(serviceContext.getUuid());
		}

		groupFriendlyURL.setGroupId(groupId);
		groupFriendlyURL.setCompanyId(companyId);
		groupFriendlyURL.setUserId(user.getUserId());
		groupFriendlyURL.setUserName(user.getFullName());
		groupFriendlyURL.setFriendlyURL(friendlyURL);
		groupFriendlyURL.setLanguageId(languageId);

		return groupFriendlyURLPersistence.update(groupFriendlyURL);
	}

	@Override
	public List<GroupFriendlyURL> addGroupFriendlyURLs(
			long userId, long companyId, long groupId,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		List<GroupFriendlyURL> groupFriendlyURLs = new ArrayList<>();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String friendlyURL = friendlyURLMap.get(locale);

			if (Validator.isNull(friendlyURL)) {
				continue;
			}

			GroupFriendlyURL groupFriendlyURL = addGroupFriendlyURL(
				userId, companyId, groupId, friendlyURL,
				LocaleUtil.toLanguageId(locale), serviceContext);

			groupFriendlyURLs.add(groupFriendlyURL);
		}

		return groupFriendlyURLs;
	}

	@Override
	public GroupFriendlyURL deleteGroupFriendlyURL(
		GroupFriendlyURL groupFriendlyURL) {

		return groupFriendlyURLPersistence.remove(groupFriendlyURL);
	}

	@Override
	public GroupFriendlyURL deleteGroupFriendlyURL(
			long companyId, long groupId, String languageId)
		throws PortalException {

		return groupFriendlyURLPersistence.removeByC_G_L(
			companyId, groupId, languageId);
	}

	@Override
	public void deleteGroupFriendlyURLs(long companyId, long groupId) {
		groupFriendlyURLPersistence.removeByC_G(companyId, groupId);
	}

	@Override
	public GroupFriendlyURL fetchGroupFriendlyURL(
		long companyId, long groupId, String languageId) {

		return groupFriendlyURLPersistence.fetchByC_G_L(
			companyId, groupId, languageId);
	}

	@Override
	public GroupFriendlyURL fetchGroupFriendlyURLByFriendlyURL(
		long companyId, String friendlyURL) {

		return groupFriendlyURLPersistence.fetchByC_F(companyId, friendlyURL);
	}

	@Override
	public List<GroupFriendlyURL> getGroupFriendlyURLs(
		long companyId, long groupId) {

		return groupFriendlyURLPersistence.findByC_G(companyId, groupId);
	}

	@Override
	public GroupFriendlyURL updateGroupFriendlyURL(
			long userId, long companyId, long groupId, String friendlyURL,
			String languageId, ServiceContext serviceContext)
		throws PortalException {

		GroupFriendlyURL groupFriendlyURL =
			groupFriendlyURLPersistence.fetchByC_G_L(
				companyId, groupId, languageId);

		if (groupFriendlyURL == null) {
			groupFriendlyURL = addGroupFriendlyURL(
				userId, companyId, groupId, friendlyURL, languageId,
				serviceContext);
		}

		groupFriendlyURL.setFriendlyURL(friendlyURL);

		return groupFriendlyURLPersistence.update(groupFriendlyURL);
	}

	@Override
	public List<GroupFriendlyURL> updateGroupFriendlyURLs(
			long userId, long companyId, long groupId,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		List<GroupFriendlyURL> groupFriendlyURLs = new ArrayList<>();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String friendlyURL = friendlyURLMap.get(locale);

			String languageId = LocaleUtil.toLanguageId(locale);

			GroupFriendlyURL groupFriendlyURL =
				groupFriendlyURLPersistence.fetchByC_F_L(
					companyId, friendlyURL, languageId);

			if (Validator.isNull(friendlyURL) && (groupFriendlyURL != null)) {
				deleteGroupFriendlyURL(companyId, groupId, languageId);
			}
			else if (Validator.isNotNull(friendlyURL)) {
				groupFriendlyURL = updateGroupFriendlyURL(
					userId, companyId, groupId, friendlyURL,
					LocaleUtil.toLanguageId(locale), serviceContext);

				groupFriendlyURLs.add(groupFriendlyURL);
			}
			else {
				continue;
			}
		}

		return groupFriendlyURLs;
	}

}