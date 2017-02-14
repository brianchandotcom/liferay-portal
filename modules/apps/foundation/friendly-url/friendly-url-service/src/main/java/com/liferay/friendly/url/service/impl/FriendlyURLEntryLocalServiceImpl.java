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

import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryException;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.base.FriendlyURLEntryLocalServiceBaseImpl;
import com.liferay.friendly.url.util.comparator.FriendlyURLEntryCreateDateComparator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public class FriendlyURLEntryLocalServiceImpl
	extends FriendlyURLEntryLocalServiceBaseImpl {

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long companyId, long groupId, Class<?> clazz, long classPK,
			String urlTitle)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return addFriendlyURLEntry(
			companyId, groupId, classNameId, classPK, urlTitle);
	}

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long companyId, long groupId, long classNameId, long classPK,
			String urlTitle)
		throws PortalException {

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		validate(companyId, groupId, classNameId, classPK, normalizedUrlTitle);

		FriendlyURLEntry mainFriendlyURLEntry =
			friendlyURLEntryPersistence.fetchByC_G_C_C_M(
				companyId, groupId, classNameId, classPK, true);

		if (mainFriendlyURLEntry != null) {
			mainFriendlyURLEntry.setMain(false);

			friendlyURLEntryPersistence.update(mainFriendlyURLEntry);
		}

		FriendlyURLEntry oldFriendlyURLEntry =
			friendlyURLEntryPersistence.fetchByC_G_C_C_U(
				companyId, groupId, classNameId, classPK, normalizedUrlTitle);

		if (oldFriendlyURLEntry != null) {
			oldFriendlyURLEntry.setMain(true);

			return friendlyURLEntryPersistence.update(oldFriendlyURLEntry);
		}

		long friendlyURLEntryId = counterLocalService.increment();

		FriendlyURLEntry friendlyURLEntry = createFriendlyURLEntry(
			friendlyURLEntryId);

		friendlyURLEntry.setCompanyId(companyId);
		friendlyURLEntry.setGroupId(groupId);
		friendlyURLEntry.setClassNameId(classNameId);
		friendlyURLEntry.setClassPK(classPK);
		friendlyURLEntry.setUrlTitle(normalizedUrlTitle);
		friendlyURLEntry.setMain(true);

		return friendlyURLEntryPersistence.update(friendlyURLEntry);
	}

	@Override
	public void deleteFriendlyURLEntry(
		long companyId, long groupId, Class<?> clazz, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		List<FriendlyURLEntry> friendlyURLEntries =
			friendlyURLEntryPersistence.findByC_G_C_C(
				companyId, groupId, classNameId, classPK);

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			friendlyURLLocalizationPersistence.removeByG_F(
				groupId, friendlyURLEntry.getFriendlyURLEntryId());
		}

		friendlyURLEntryPersistence.removeByC_G_C_C(
			companyId, groupId, classNameId, classPK);
	}

	@Override
	public void deleteFriendlyURLEntry(
			long companyId, long groupId, Class<?> clazz, long classPK,
			String urlTitle)
		throws NoSuchFriendlyURLEntryException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		deleteFriendlyURLEntry(
			companyId, groupId, classNameId, classPK, urlTitle);
	}

	@Override
	public void deleteFriendlyURLEntry(
			long companyId, long groupId, long classNameId, long classPK,
			String urlTitle)
		throws NoSuchFriendlyURLEntryException {

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryPersistence.removeByC_G_C_C_U(
				companyId, groupId, classNameId, classPK, urlTitle);

		friendlyURLLocalizationPersistence.removeByG_F(
			groupId, friendlyURLEntry.getFriendlyURLEntryId());

		List<FriendlyURLEntry> friendlyURLEntries =
			friendlyURLEntryPersistence.findByC_G_C_C(
				companyId, groupId, classNameId, classPK, 0, 1,
				new FriendlyURLEntryCreateDateComparator());

		if (!friendlyURLEntries.isEmpty()) {
			friendlyURLEntry = friendlyURLEntries.get(0);

			friendlyURLEntry.setMain(true);

			friendlyURLEntryPersistence.update(friendlyURLEntry);
		}
	}

	@Override
	public void deleteGroupFriendlyURLEntries(
		final long groupId, final long classNameId) {

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setGroupId(groupId);
		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(classNameIdProperty.eq(classNameId));
				}

			});

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<FriendlyURLEntry>() {

				@Override
				public void performAction(FriendlyURLEntry friendlyURLEntry)
					throws PortalException {

					friendlyURLLocalizationPersistence.removeByG_F(
						groupId, friendlyURLEntry.getFriendlyURLEntryId());

					friendlyURLEntryPersistence.remove(friendlyURLEntry);
				}

			});
	}

	@Override
	public FriendlyURLEntry fetchFriendlyURLEntry(
		long companyId, long groupId, Class<?> clazz, String urlTitle) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return fetchFriendlyURLEntry(companyId, groupId, classNameId, urlTitle);
	}

	@Override
	public FriendlyURLEntry fetchFriendlyURLEntry(
		long companyId, long groupId, long classNameId, String urlTitle) {

		return friendlyURLEntryPersistence.fetchByC_G_C_U(
			companyId, groupId, classNameId, urlTitle);
	}

	@Override
	public List<FriendlyURLEntry> getFriendlyURLEntries(
		long companyId, long groupId, long classNameId, long classPK) {

		return friendlyURLEntryPersistence.findByC_G_C_C(
			companyId, groupId, classNameId, classPK);
	}

	@Override
	public FriendlyURLEntry getMainFriendlyURLEntry(
			long companyId, long groupId, Class<?> clazz, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return getMainFriendlyURLEntry(
			companyId, groupId, classNameId, classPK);
	}

	@Override
	public FriendlyURLEntry getMainFriendlyURLEntry(
			long companyId, long groupId, long classNameId, long classPK)
		throws PortalException {

		return friendlyURLEntryPersistence.findByC_G_C_C_M(
			companyId, groupId, classNameId, classPK, true);
	}

	@Override
	public String getUniqueUrlTitle(
		long companyId, long groupId, long classNameId, long classPK,
		String urlTitle) {

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		String curUrlTitle = normalizedUrlTitle.substring(
			0, Math.min(maxLength, normalizedUrlTitle.length()));

		for (int i = 1;; i++) {
			FriendlyURLEntry curFriendlyURLEntry = fetchFriendlyURLEntry(
				companyId, groupId, classNameId, curUrlTitle);

			if ((curFriendlyURLEntry == null) ||
				(curFriendlyURLEntry.getClassPK() == classPK)) {

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
			long companyId, long groupId, long classNameId, long classPK,
			String urlTitle)
		throws PortalException {

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		if (urlTitle.length() > maxLength) {
			throw new FriendlyURLLengthException();
		}

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		if (classPK > 0) {
			FriendlyURLEntry friendlyURLEntry =
				friendlyURLEntryPersistence.fetchByC_G_C_C_U(
					companyId, groupId, classNameId, classPK,
					normalizedUrlTitle);

			if (friendlyURLEntry != null) {
				return;
			}
		}

		int count = friendlyURLEntryPersistence.countByC_G_C_U(
			companyId, groupId, classNameId, normalizedUrlTitle);

		if (count > 0) {
			throw new DuplicateFriendlyURLEntryException();
		}
	}

	@Override
	public void validate(
			long companyId, long groupId, long classNameId, String urlTitle)
		throws PortalException {

		validate(companyId, groupId, classNameId, 0, urlTitle);
	}

}