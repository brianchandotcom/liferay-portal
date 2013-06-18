/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutFriendlyURLLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * friendly URLs for layouts.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the {@link
 * com.liferay.portal.service.LayoutFriendlyURLLocalService} interface.
 * </p>
 *
 * <p>
 * Methods of this service will not have security checks based on the propagated
 * JAAS credentials because this service can only be accessed from within the
 * same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class LayoutFriendlyURLLocalServiceImpl
	extends LayoutFriendlyURLLocalServiceBaseImpl {

	@Override
	public LayoutFriendlyURL addLayoutFriendlyURL(
			long userId, long companyId, long groupId, long plid,
			boolean privateLayout, String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long layoutFriendlyURLId = counterLocalService.increment();

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.create(layoutFriendlyURLId);

		layoutFriendlyURL.setUuid(serviceContext.getUuid());
		layoutFriendlyURL.setGroupId(groupId);
		layoutFriendlyURL.setCompanyId(companyId);
		layoutFriendlyURL.setUserId(user.getUserId());
		layoutFriendlyURL.setUserName(user.getFullName());
		layoutFriendlyURL.setCreateDate(serviceContext.getCreateDate(now));
		layoutFriendlyURL.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutFriendlyURL.setPlid(plid);
		layoutFriendlyURL.setPrivateLayout(privateLayout);
		layoutFriendlyURL.setFriendlyURL(friendlyURL);
		layoutFriendlyURL.setLanguageId(languageId);

		return layoutFriendlyURLPersistence.update(layoutFriendlyURL);
	}

	@Override
	public List<LayoutFriendlyURL> addLayoutFriendlyURLs(
			long userId, long companyId, long groupId, long plid,
			boolean privateLayout, Map<Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			new ArrayList<LayoutFriendlyURL>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String friendlyURL = friendlyURLMap.get(locale);

			if (Validator.isNull(friendlyURL)) {
				continue;
			}

			LayoutFriendlyURL layoutFriendlyURL = addLayoutFriendlyURL(
				userId, companyId, groupId, plid, privateLayout, friendlyURL,
				LocaleUtil.toLanguageId(locale), serviceContext);

			layoutFriendlyURLs.add(layoutFriendlyURL);
		}

		return layoutFriendlyURLs;
	}

	@Override
	public LayoutFriendlyURL deleteLayoutFriendlyURL(
			LayoutFriendlyURL layoutFriendlyURL)
		throws SystemException {

		return layoutFriendlyURLPersistence.remove(layoutFriendlyURL);
	}

	@Override
	public void deleteLayoutFriendlyURL(long plid, String languageId)
		throws SystemException {

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if (layoutFriendlyURL != null) {
			deleteLayoutFriendlyURL(layoutFriendlyURL);
		}
	}

	@Override
	public void deleteLayoutFriendlyURLs(long plid) throws SystemException {
		List<LayoutFriendlyURL> layoutFriendlyURLs =
			layoutFriendlyURLPersistence.findByPlid(plid);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			deleteLayoutFriendlyURL(layoutFriendlyURL);
		}
	}

	@Override
	public LayoutFriendlyURL getLayoutFriendlyURL(long plid, String languageId)
		throws PortalException, SystemException {

		return getLayoutFriendlyURL(plid, languageId, true);
	}

	@Override
	public LayoutFriendlyURL getLayoutFriendlyURL(
			long plid, String languageId, boolean useDefault)
		throws PortalException, SystemException {

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if (layoutFriendlyURL == null) {
			layoutFriendlyURL = layoutFriendlyURLPersistence.fetchByP_L(
				plid, LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
		}

		if (layoutFriendlyURL == null) {
			layoutFriendlyURL = layoutFriendlyURLPersistence.findByPlid_First(
				plid, null);
		}

		return layoutFriendlyURL;
	}

	@Override
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(long plid)
		throws SystemException {

		return layoutFriendlyURLPersistence.findByPlid(plid);
	}

	@Override
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(
			long plid, String friendlyURL, int start, int end)
		throws SystemException {

		return layoutFriendlyURLPersistence.findByP_F(
			plid, friendlyURL, start, end);
	}

	@Override
	public LayoutFriendlyURL updateLayoutFriendlyURL(
			long userId, long companyId, long groupId, long plid,
			boolean privateLayout, String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if (layoutFriendlyURL == null) {
			return addLayoutFriendlyURL(
				userId, companyId, groupId, plid, privateLayout, friendlyURL,
				languageId, serviceContext);
		}

		layoutFriendlyURL.setFriendlyURL(friendlyURL);

		return layoutFriendlyURLPersistence.update(layoutFriendlyURL);
	}

	@Override
	public List<LayoutFriendlyURL> updateLayoutFriendlyURLs(
			long userId, long companyId, long groupId, long plid,
			boolean privateLayout, Map<Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			new ArrayList<LayoutFriendlyURL>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String friendlyURL = friendlyURLMap.get(locale);
			String languageId = LocaleUtil.toLanguageId(locale);

			if (Validator.isNull(friendlyURL)) {
				deleteLayoutFriendlyURL(plid, languageId);
			}
			else {
				LayoutFriendlyURL layoutFriendlyURL = updateLayoutFriendlyURL(
					userId, companyId, groupId, plid, privateLayout,
					friendlyURL, languageId, serviceContext);

				layoutFriendlyURLs.add(layoutFriendlyURL);
			}
		}

		return layoutFriendlyURLs;
	}

}