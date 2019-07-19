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

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.base.SiteFriendlyURLLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.site.model.SiteFriendlyURL",
	service = AopService.class
)
public class SiteFriendlyURLLocalServiceImpl
	extends SiteFriendlyURLLocalServiceBaseImpl {

	@Override
	public SiteFriendlyURL addSiteFriendlyURL(
			long userId, long companyId, long groupId, String friendlyURL,
			String languageId, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long siteFriendlyURLId = counterLocalService.increment();

		SiteFriendlyURL siteFriendlyURL = siteFriendlyURLPersistence.create(
			siteFriendlyURLId);

		if (serviceContext != null) {
			siteFriendlyURL.setUuid(serviceContext.getUuid());
		}

		siteFriendlyURL.setCompanyId(companyId);
		siteFriendlyURL.setUserId(user.getUserId());
		siteFriendlyURL.setUserName(user.getFullName());
		siteFriendlyURL.setGroupId(groupId);
		siteFriendlyURL.setFriendlyURL(friendlyURL);
		siteFriendlyURL.setLanguageId(languageId);

		return siteFriendlyURLPersistence.update(siteFriendlyURL);
	}

	@Override
	public List<SiteFriendlyURL> addSiteFriendlyURLs(
			long userId, long companyId, long groupId,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		List<SiteFriendlyURL> siteFriendlyURLs = new ArrayList<>();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String friendlyURL = friendlyURLMap.get(locale);

			if (Validator.isNull(friendlyURL)) {
				continue;
			}

			SiteFriendlyURL siteFriendlyURL = addSiteFriendlyURL(
				userId, companyId, groupId, friendlyURL,
				LocaleUtil.toLanguageId(locale), serviceContext);

			siteFriendlyURLs.add(siteFriendlyURL);
		}

		return siteFriendlyURLs;
	}

	@Override
	public SiteFriendlyURL deleteSiteFriendlyURL(
			long companyId, long groupId, String languageId)
		throws PortalException {

		return siteFriendlyURLPersistence.removeByC_G_L(
			companyId, groupId, languageId);
	}

	@Override
	public SiteFriendlyURL deleteSiteFriendlyURL(
		SiteFriendlyURL siteFriendlyURL) {

		return siteFriendlyURLPersistence.remove(siteFriendlyURL);
	}

	@Override
	public void deleteSiteFriendlyURLs(long companyId, long groupId) {
		siteFriendlyURLPersistence.removeByC_G(companyId, groupId);
	}

	@Override
	public SiteFriendlyURL fetchSiteFriendlyURL(
		long companyId, long groupId, String languageId) {

		return siteFriendlyURLPersistence.fetchByC_G_L(
			companyId, groupId, languageId);
	}

	@Override
	public SiteFriendlyURL fetchSiteFriendlyURLByFriendlyURL(
		long companyId, String friendlyURL) {

		return siteFriendlyURLPersistence.fetchByC_F(companyId, friendlyURL);
	}

	@Override
	public List<SiteFriendlyURL> getSiteFriendlyURLs(
		long companyId, long groupId) {

		return siteFriendlyURLPersistence.findByC_G(companyId, groupId);
	}

	@Override
	public SiteFriendlyURL updateSiteFriendlyURL(
			long userId, long companyId, long groupId, String friendlyURL,
			String languageId, ServiceContext serviceContext)
		throws PortalException {

		SiteFriendlyURL siteFriendlyURL =
			siteFriendlyURLPersistence.fetchByC_G_L(
				companyId, groupId, languageId);

		if (siteFriendlyURL == null) {
			siteFriendlyURL = addSiteFriendlyURL(
				userId, companyId, groupId, friendlyURL, languageId,
				serviceContext);
		}

		siteFriendlyURL.setFriendlyURL(friendlyURL);

		return siteFriendlyURLPersistence.update(siteFriendlyURL);
	}

	@Override
	public List<SiteFriendlyURL> updateSiteFriendlyURLs(
			long userId, long companyId, long groupId,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		List<SiteFriendlyURL> siteFriendlyURLs = new ArrayList<>();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String friendlyURL = friendlyURLMap.get(locale);

			String languageId = LocaleUtil.toLanguageId(locale);

			SiteFriendlyURL siteFriendlyURL =
				siteFriendlyURLPersistence.fetchByC_G_L(
					companyId, groupId, languageId);

			if (Validator.isNull(friendlyURL) && (siteFriendlyURL != null)) {
				deleteSiteFriendlyURL(companyId, groupId, languageId);
			}

			siteFriendlyURL = updateSiteFriendlyURL(
				userId, companyId, groupId, friendlyURL, languageId,
				serviceContext);

			siteFriendlyURLs.add(siteFriendlyURL);
		}

		return siteFriendlyURLs;
	}

}