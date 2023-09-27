/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.base.SiteFriendlyURLLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		User user = _userLocalService.getUser(userId);

		long siteFriendlyURLId = counterLocalService.increment();

		SiteFriendlyURL siteFriendlyURL = siteFriendlyURLPersistence.create(
			siteFriendlyURLId);

		if (serviceContext != null) {
			siteFriendlyURL.setUuid(serviceContext.getUuid());
		}

		siteFriendlyURL.setGroupId(groupId);
		siteFriendlyURL.setCompanyId(companyId);
		siteFriendlyURL.setUserId(user.getUserId());
		siteFriendlyURL.setUserName(user.getFullName());
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

		for (Locale locale : _language.getAvailableLocales(groupId)) {
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

		return siteFriendlyURLPersistence.removeByG_C_L(
			groupId, companyId, languageId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SiteFriendlyURL deleteSiteFriendlyURL(
		SiteFriendlyURL siteFriendlyURL) {

		return siteFriendlyURLPersistence.remove(siteFriendlyURL);
	}

	@Override
	public void deleteSiteFriendlyURLs(long companyId, long groupId) {
		siteFriendlyURLPersistence.removeByG_C(groupId, companyId);
	}

	@Override
	public SiteFriendlyURL fetchSiteFriendlyURL(
		long companyId, long groupId, String languageId) {

		return siteFriendlyURLPersistence.fetchByG_C_L(
			groupId, companyId, languageId);
	}

	@Override
	public SiteFriendlyURL fetchSiteFriendlyURLByFriendlyURL(
		long companyId, String friendlyURL) {

		return siteFriendlyURLPersistence.fetchByC_F(companyId, friendlyURL);
	}

	@Override
	public List<SiteFriendlyURL> getSiteFriendlyURLs(
		long companyId, long groupId) {

		return siteFriendlyURLPersistence.findByG_C(groupId, companyId);
	}

	@Override
	public SiteFriendlyURL updateSiteFriendlyURL(
			long userId, long companyId, long groupId, String friendlyURL,
			String languageId, ServiceContext serviceContext)
		throws PortalException {

		SiteFriendlyURL siteFriendlyURL =
			siteFriendlyURLPersistence.fetchByG_C_L(
				groupId, companyId, languageId);

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

		for (Locale locale : _language.getAvailableLocales(groupId)) {
			String friendlyURL = friendlyURLMap.get(locale);

			String languageId = LocaleUtil.toLanguageId(locale);

			SiteFriendlyURL siteFriendlyURL =
				siteFriendlyURLPersistence.fetchByG_C_L(
					groupId, companyId, languageId);

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

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}