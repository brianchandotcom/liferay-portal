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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchLayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.LayoutFriendlyURLLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.HashMap;
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
 * com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService} interface.
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
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		long layoutFriendlyURLId = counterLocalService.increment();

		LayoutFriendlyURL layoutFriendlyURL =
			layoutFriendlyURLPersistence.create(layoutFriendlyURLId);

		layoutFriendlyURL.setUuid(serviceContext.getUuid());
		layoutFriendlyURL.setGroupId(groupId);
		layoutFriendlyURL.setCompanyId(companyId);
		layoutFriendlyURL.setUserId(user.getUserId());
		layoutFriendlyURL.setUserName(user.getFullName());
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
		throws PortalException {

		List<LayoutFriendlyURL> layoutFriendlyURLs = new ArrayList<>();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
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
		long groupId, boolean privateLayout, String friendlyURL) {

		return layoutFriendlyURLPersistence.fetchByG_P_F_First(
			groupId, privateLayout, friendlyURL, null);
	}

	@Override
	public LayoutFriendlyURL fetchLayoutFriendlyURL(
		long groupId, boolean privateLayout, String friendlyURL,
		String languageId) {

		return layoutFriendlyURLPersistence.fetchByG_P_F_L(
			groupId, privateLayout, friendlyURL, languageId);
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
	public LayoutFriendlyURL getLayoutFriendlyURL(
			long groupId, boolean privateLayout, String friendlyURL,
			String languageId)
		throws NoSuchLayoutFriendlyURLException {

		return layoutFriendlyURLPersistence.findByG_P_F_L(
			groupId, privateLayout, friendlyURL, languageId);
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
			throw new NoSuchLayoutFriendlyURLException(
				StringBundler.concat(
					"{plid=", plid, ", languageId=", languageId, "}"));
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
	public Map<Long, String> getLayoutFriendlyURLs(
		Group siteGroup, List<Layout> layouts, String languageId) {

		Map<Long, String> layoutFriendlyURLMap = new HashMap<>();

		UnicodeProperties typeSettingsUnicodeProperties =
			siteGroup.getTypeSettingsProperties();

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			layoutFriendlyURLPersistence.findByP_L(
				ListUtil.toLongArray(layouts, Layout.PLID_ACCESSOR),
				languageId);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			layoutFriendlyURLMap.put(
				layoutFriendlyURL.getPlid(),
				layoutFriendlyURL.getFriendlyURL());
		}

		if (GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty(
					GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES),
				true)) {

			return layoutFriendlyURLMap;
		}

		Map<Long, String> filteredLayoutFriendlyURLMap = new HashMap<>();

		String[] locales = StringUtil.split(
			typeSettingsUnicodeProperties.getProperty(PropsKeys.LOCALES));

		if (!ArrayUtil.contains(locales, languageId)) {
			for (Layout layout : layouts) {
				String friendlyURL = layoutFriendlyURLMap.get(layout.getPlid());

				filteredLayoutFriendlyURLMap.put(layout.getPlid(), friendlyURL);
			}
		}

		return filteredLayoutFriendlyURLMap;
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
			boolean privateLayout, String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws PortalException {

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
		throws PortalException {

		Map<String, LayoutFriendlyURL> layoutFriendlyURLMap = new HashMap<>();

		for (LayoutFriendlyURL layoutFriendlyURL :
				layoutFriendlyURLPersistence.findByPlid(plid)) {

			layoutFriendlyURLMap.put(
				layoutFriendlyURL.getLanguageId(), layoutFriendlyURL);
		}

		List<LayoutFriendlyURL> layoutFriendlyURLs = new ArrayList<>(
			friendlyURLMap.size());

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String friendlyURL = friendlyURLMap.get(locale);

			String languageId = LocaleUtil.toLanguageId(locale);

			LayoutFriendlyURL layoutFriendlyURL = layoutFriendlyURLMap.get(
				languageId);

			if (Validator.isNull(friendlyURL)) {
				if (layoutFriendlyURL != null) {
					deleteLayoutFriendlyURL(layoutFriendlyURL);
				}
			}
			else {
				if (layoutFriendlyURL == null) {
					layoutFriendlyURL = addLayoutFriendlyURL(
						userId, companyId, groupId, plid, privateLayout,
						friendlyURL, languageId, serviceContext);
				}
				else {
					layoutFriendlyURL.setFriendlyURL(friendlyURL);

					layoutFriendlyURL = layoutFriendlyURLPersistence.update(
						layoutFriendlyURL);
				}

				layoutFriendlyURLs.add(layoutFriendlyURL);
			}
		}

		return layoutFriendlyURLs;
	}

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}