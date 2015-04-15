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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchLayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
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
			String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws PortalException {

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
		layoutFriendlyURL.setFriendlyURL(friendlyURL);
		layoutFriendlyURL.setLanguageId(languageId);

		return layoutFriendlyURLPersistence.update(layoutFriendlyURL);
	}

	@Override
	public List<LayoutFriendlyURL> addLayoutFriendlyURLs(
			long userId, long companyId, long groupId, long plid,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		List<LayoutFriendlyURL> layoutFriendlyURLs = new ArrayList<>();

		Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

		for (Locale locale : locales) {
			String friendlyURL = friendlyURLMap.get(locale);

			if (Validator.isNull(friendlyURL)) {
				continue;
			}

			LayoutFriendlyURL layoutFriendlyURL = addLayoutFriendlyURL(
				userId, companyId, groupId, plid, friendlyURL,
				LocaleUtil.toLanguageId(locale), serviceContext);

			layoutFriendlyURLs.add(layoutFriendlyURL);
		}

		return layoutFriendlyURLs;
	}

	@Override
	public LayoutFriendlyURL deleteLayoutFriendlyURL(
		LayoutFriendlyURL layoutFriendlyURL) {

		return layoutFriendlyURLPersistence.remove(layoutFriendlyURL);
	}

	@Override
	public void deleteLayoutFriendlyURL(long plid, String languageId) {
		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if (layoutFriendlyURL != null) {
			deleteLayoutFriendlyURL(layoutFriendlyURL);
		}
	}

	@Override
	public void deleteLayoutFriendlyURLs(long plid) {
		List<LayoutFriendlyURL> layoutFriendlyURLs =
			layoutFriendlyURLPersistence.findByPlid(plid);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			deleteLayoutFriendlyURL(layoutFriendlyURL);
		}
	}

	@Override
	public LayoutFriendlyURL fetchFirstLayoutFriendlyURL(
		long groupId, String friendlyURL) {

		return layoutFriendlyURLPersistence.fetchByG_F_First(
			groupId, friendlyURL, null);
	}

	@Override
	public LayoutFriendlyURL fetchLayoutFriendlyURL(
		long plid, String languageId) {

		return fetchLayoutFriendlyURL(plid, languageId, true);
	}

	@Override
	public LayoutFriendlyURL fetchLayoutFriendlyURL(
		long plid, String languageId, boolean useDefault) {

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if ((layoutFriendlyURL == null) && !useDefault) {
			return null;
		}

		if (layoutFriendlyURL == null) {
			layoutFriendlyURL = layoutFriendlyURLPersistence.fetchByP_L(
				plid, LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
		}

		if (layoutFriendlyURL == null) {
			layoutFriendlyURL = layoutFriendlyURLPersistence.fetchByPlid_First(
				plid, null);
		}

		return layoutFriendlyURL;
	}

	@Override
	public LayoutFriendlyURL fetchLayoutFriendlyURL(
		long groupId, String friendlyURL, String languageId) {

		return layoutFriendlyURLPersistence.fetchByG_F_L(
			groupId, friendlyURL, languageId);
	}

	@Override
	public LayoutFriendlyURL getLayoutFriendlyURL(long plid, String languageId)
		throws PortalException {

		return getLayoutFriendlyURL(plid, languageId, true);
	}

	@Override
	public LayoutFriendlyURL getLayoutFriendlyURL(
			long plid, String languageId, boolean useDefault)
		throws PortalException {

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if ((layoutFriendlyURL == null) && !useDefault) {
			StringBundler sb = new StringBundler(5);

			sb.append("{plid=");
			sb.append(plid);
			sb.append(", languageId=");
			sb.append(languageId);
			sb.append("}");

			throw new NoSuchLayoutFriendlyURLException(sb.toString());
		}

		if (layoutFriendlyURL == null) {
			layoutFriendlyURL = layoutFriendlyURLPersistence.fetchByP_L(
				plid, LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
		}

		if (layoutFriendlyURL == null) {
			layoutFriendlyURL = layoutFriendlyURLPersistence.findByPlid_First(
				plid, null);
		}

		return layoutFriendlyURL;
	}

	@Override
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(long plid) {
		return layoutFriendlyURLPersistence.findByPlid(plid);
	}

	@Override
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(
		long plid, String friendlyURL, int start, int end) {

		return layoutFriendlyURLPersistence.findByP_F(
			plid, friendlyURL, start, end);
	}

	@Override
	public LayoutFriendlyURL updateLayoutFriendlyURL(
			long userId, long companyId, long groupId, long plid,
			String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if (layoutFriendlyURL == null) {
			return addLayoutFriendlyURL(
				userId, companyId, groupId, plid, friendlyURL, languageId,
				serviceContext);
		}

		layoutFriendlyURL.setFriendlyURL(friendlyURL);

		return layoutFriendlyURLPersistence.update(layoutFriendlyURL);
	}

	@Override
	public List<LayoutFriendlyURL> updateLayoutFriendlyURLs(
			long userId, long companyId, long groupId, long plid,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		List<LayoutFriendlyURL> layoutFriendlyURLs = new ArrayList<>();

		Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

		for (Locale locale : locales) {
			String friendlyURL = friendlyURLMap.get(locale);
			String languageId = LocaleUtil.toLanguageId(locale);

			if (Validator.isNull(friendlyURL)) {
				deleteLayoutFriendlyURL(plid, languageId);
			}
			else {
				LayoutFriendlyURL layoutFriendlyURL = updateLayoutFriendlyURL(
					userId, companyId, groupId, plid, friendlyURL, languageId,
					serviceContext);

				layoutFriendlyURLs.add(layoutFriendlyURL);
			}
		}

		return layoutFriendlyURLs;
	}

}