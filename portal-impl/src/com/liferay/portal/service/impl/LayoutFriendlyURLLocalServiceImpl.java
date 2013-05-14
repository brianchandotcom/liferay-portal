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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutFriendlyURLLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutFriendlyURLLocalServiceImpl
	extends LayoutFriendlyURLLocalServiceBaseImpl {

	@Override
	public LayoutFriendlyURL addLayoutFriendlyURL(
			long companyId, long groupId, long plid, boolean privateLayout,
			String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws SystemException {

		long layoutFriendlyURLId = counterLocalService.increment();

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.create(layoutFriendlyURLId);

		layoutFriendlyURL.setUuid(serviceContext.getUuid());
		layoutFriendlyURL.setGroupId(groupId);
		layoutFriendlyURL.setCompanyId(companyId);
		layoutFriendlyURL.setPlid(plid);
		layoutFriendlyURL.setPrivateLayout(privateLayout);
		layoutFriendlyURL.setFriendlyURL(friendlyURL);
		layoutFriendlyURL.setLanguageId(languageId);

		return layoutFriendlyURLPersistence.update(layoutFriendlyURL);
	}

	@Override
	public List<LayoutFriendlyURL> addLayoutFriendlyURLs(
			long companyId, long groupId, long plid, boolean privateLayout,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws SystemException {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			new ArrayList<LayoutFriendlyURL>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String friendlyURL = friendlyURLMap.get(locale);

			if (Validator.isNull(friendlyURL)) {
				continue;
			}

			LayoutFriendlyURL layoutFriendlyURL = addLayoutFriendlyURL(
				companyId, groupId, plid, privateLayout, friendlyURL,
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
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(long plid)
		throws SystemException {

		return layoutFriendlyURLPersistence.findByPlid(plid);
	}

	@Override
	public LayoutFriendlyURL updateLayoutFriendlyURL(
			long companyId, long groupId, long plid, boolean privateLayout,
			String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws SystemException {

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if (layoutFriendlyURL == null) {
			return addLayoutFriendlyURL(
				companyId, groupId, plid, privateLayout, friendlyURL,
				languageId, serviceContext);
		}

		layoutFriendlyURL.setFriendlyURL(friendlyURL);

		return layoutFriendlyURLPersistence.update(layoutFriendlyURL);
	}

	@Override
	public List<LayoutFriendlyURL> updateLayoutFriendlyURLs(
			long companyId, long groupId, long plid, boolean privateLayout,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws SystemException {

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
					companyId, groupId, plid, privateLayout, friendlyURL,
					languageId, serviceContext);

				layoutFriendlyURLs.add(layoutFriendlyURL);
			}
		}

		return layoutFriendlyURLs;
	}

}