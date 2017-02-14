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

import com.liferay.friendly.url.exception.DuplicateFriendlyURLException;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.exception.NoSuchFriendlyURLException;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.service.base.FriendlyURLLocalServiceBaseImpl;
import com.liferay.friendly.url.util.comparator.FriendlyURLCreateDateComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public class FriendlyURLLocalServiceImpl
	extends FriendlyURLLocalServiceBaseImpl {

	@Override
	public FriendlyURL addFriendlyURL(
			long groupId, long companyId, Class<?> clazz, long classPK,
			String urlTitle)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return addFriendlyURL(
			companyId, groupId, classNameId, classPK, urlTitle);
	}

	@Override
	public FriendlyURL addFriendlyURL(
			long groupId, long companyId, long classNameId, long classPK,
			String urlTitle)
		throws PortalException {

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		validate(companyId, groupId, classNameId, classPK, normalizedUrlTitle);

		FriendlyURL mainFriendlyURL = friendlyURLPersistence.fetchByG_C_C_C_M(
			groupId, companyId, classNameId, classPK, true);

		if (mainFriendlyURL != null) {
			mainFriendlyURL.setMain(false);

			friendlyURLPersistence.update(mainFriendlyURL);
		}

		FriendlyURL oldFriendlyURL = friendlyURLPersistence.fetchByG_C_C_C_U(
			groupId, companyId, classNameId, classPK, normalizedUrlTitle);

		if (oldFriendlyURL != null) {
			oldFriendlyURL.setMain(true);

			return friendlyURLPersistence.update(oldFriendlyURL);
		}

		long friendlyURLId = counterLocalService.increment();

		FriendlyURL friendlyURL = createFriendlyURL(friendlyURLId);

		friendlyURL.setCompanyId(companyId);
		friendlyURL.setGroupId(groupId);
		friendlyURL.setClassNameId(classNameId);
		friendlyURL.setClassPK(classPK);
		friendlyURL.setUrlTitle(normalizedUrlTitle);
		friendlyURL.setMain(true);

		return friendlyURLPersistence.update(friendlyURL);
	}

	@Override
	public void deleteFriendlyURL(
		long groupId, long companyId, Class<?> clazz, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		friendlyURLPersistence.removeByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	@Override
	public void deleteFriendlyURL(
			long groupId, long companyId, Class<?> clazz, long classPK,
			String urlTitle)
		throws NoSuchFriendlyURLException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		deleteFriendlyURL(companyId, groupId, classNameId, classPK, urlTitle);
	}

	@Override
	public void deleteFriendlyURL(
			long groupId, long companyId, long classNameId, long classPK,
			String urlTitle)
		throws NoSuchFriendlyURLException {

		friendlyURLPersistence.removeByG_C_C_C_U(
			groupId, companyId, classNameId, classPK, urlTitle);

		List<FriendlyURL> friendlyURLs = friendlyURLPersistence.findByG_C_C_C(
			groupId, companyId, classNameId, classPK, 0, 1,
			new FriendlyURLCreateDateComparator());

		if (!friendlyURLs.isEmpty()) {
			FriendlyURL friendlyURL = friendlyURLs.get(0);

			friendlyURL.setMain(true);

			friendlyURLPersistence.update(friendlyURL);
		}
	}

	@Override
	public void deleteGroupFriendlyURLs(long groupId, long classNameId) {
		friendlyURLPersistence.removeByG_C(groupId, classNameId);
	}

	@Override
	public FriendlyURL fetchFriendlyURL(
		long groupId, long companyId, Class<?> clazz, String urlTitle) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return fetchFriendlyURL(companyId, groupId, classNameId, urlTitle);
	}

	@Override
	public FriendlyURL fetchFriendlyURL(
		long groupId, long companyId, long classNameId, String urlTitle) {

		return friendlyURLPersistence.fetchByG_C_C_U(
			groupId, companyId, classNameId, urlTitle);
	}

	@Override
	public List<FriendlyURL> getFriendlyURLs(
		long groupId, long companyId, long classNameId, long classPK) {

		return friendlyURLPersistence.findByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	@Override
	public FriendlyURL getMainFriendlyURL(
			long groupId, long companyId, Class<?> clazz, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return getMainFriendlyURL(companyId, groupId, classNameId, classPK);
	}

	@Override
	public FriendlyURL getMainFriendlyURL(
			long groupId, long companyId, long classNameId, long classPK)
		throws PortalException {

		return friendlyURLPersistence.findByG_C_C_C_M(
			groupId, companyId, classNameId, classPK, true);
	}

	@Override
	public String getUniqueUrlTitle(
		long groupId, long companyId, long classNameId, long classPK,
		String urlTitle) {

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURL.class.getName(), "urlTitle");

		String curUrlTitle = normalizedUrlTitle.substring(
			0, Math.min(maxLength, normalizedUrlTitle.length()));

		for (int i = 1;; i++) {
			FriendlyURL curFriendlyURL = fetchFriendlyURL(
				companyId, groupId, classNameId, curUrlTitle);

			if ((curFriendlyURL == null) ||
				(curFriendlyURL.getClassPK() == classPK)) {

				break;
			}

			String suffix = StringPool.DASH + i;

			String prefix = normalizedUrlTitle.substring(
				0,
				Math.min(
					maxLength - suffix.length(), normalizedUrlTitle.length()));

			curUrlTitle = prefix + suffix;
		}

		return curUrlTitle;
	}

	@Override
	public void validate(
			long groupId, long companyId, long classNameId, long classPK,
			String urlTitle)
		throws PortalException {

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURL.class.getName(), "urlTitle");

		if (urlTitle.length() > maxLength) {
			throw new FriendlyURLLengthException();
		}

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		if (classPK > 0) {
			FriendlyURL friendlyURL = friendlyURLPersistence.fetchByG_C_C_C_U(
				groupId, companyId, classNameId, classPK, normalizedUrlTitle);

			if (friendlyURL != null) {
				return;
			}
		}

		int count = friendlyURLPersistence.countByG_C_C_U(
			groupId, companyId, classNameId, normalizedUrlTitle);

		if (count > 0) {
			throw new DuplicateFriendlyURLException();
		}
	}

	@Override
	public void validate(
			long groupId, long companyId, long classNameId, String urlTitle)
		throws PortalException {

		validate(companyId, groupId, classNameId, 0, urlTitle);
	}

}