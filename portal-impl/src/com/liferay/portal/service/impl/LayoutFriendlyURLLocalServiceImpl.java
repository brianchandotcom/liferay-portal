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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutFriendlyURLLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the layout friendly u r l local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.service.LayoutFriendlyURLLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.base.LayoutFriendlyURLLocalServiceBaseImpl
 * @see com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil
 */
public class LayoutFriendlyURLLocalServiceImpl
	extends LayoutFriendlyURLLocalServiceBaseImpl {

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

	public List<LayoutFriendlyURL> addLayoutFriendlyURLs(
			long companyId, long groupId, long plid, boolean privateLayout,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws SystemException {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			new ArrayList<LayoutFriendlyURL>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String friendlyURL = friendlyURLMap.get(locale);

			if (Validator.isNotNull(friendlyURL)) {
				LayoutFriendlyURL layoutFriendlyURL = addLayoutFriendlyURL(
					companyId, groupId, plid, privateLayout, friendlyURL,
					LocaleUtil.toLanguageId(locale), serviceContext);

				layoutFriendlyURLs.add(layoutFriendlyURL);
			}
		}

		return layoutFriendlyURLs;
	}

	public void deleteLayoutFriendlyURL(long plid, String languageId)
		throws SystemException {

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.fetchByP_L(plid, languageId);

		if (layoutFriendlyURL != null) {
			layoutFriendlyURLPersistence.remove(layoutFriendlyURL);
		}
	}

	public void deleteLayoutFriendlyURLsByPlid(long plid)
		throws SystemException {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			layoutFriendlyURLPersistence.findByLayout(plid);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			layoutFriendlyURLPersistence.remove(layoutFriendlyURL);
		}
	}

	public List<LayoutFriendlyURL> getLayoutFriendlyURLsByPlid(long plid)
		throws SystemException {

		return layoutFriendlyURLPersistence.findByLayout(plid);
	}

	public LayoutFriendlyURL updateLayoutFriendlyURL(
			long companyId, long groupId, long plid, boolean privateLayout,
			String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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

	public List<LayoutFriendlyURL> updateLayoutFriendlyURLs(
			long companyId, long groupId, long plid, boolean privateLayout,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			new ArrayList<LayoutFriendlyURL>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String friendlyURL = friendlyURLMap.get(locale);
			String languageId = LocaleUtil.toLanguageId(locale);

			if (Validator.isNotNull(friendlyURL)) {
				LayoutFriendlyURL layoutFriendlyURL = updateLayoutFriendlyURL(
					companyId, groupId, plid, privateLayout, friendlyURL,
					languageId, serviceContext);

				layoutFriendlyURLs.add(layoutFriendlyURL);
			}
			else {
				deleteLayoutFriendlyURL(plid, languageId);
			}
		}

		return layoutFriendlyURLs;
	}

}