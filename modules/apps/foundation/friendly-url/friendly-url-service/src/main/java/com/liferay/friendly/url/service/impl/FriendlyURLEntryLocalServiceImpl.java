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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class FriendlyURLEntryLocalServiceImpl
	extends FriendlyURLEntryLocalServiceBaseImpl {

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, Class<?> clazz, long classPK, String urlTitle,
			ServiceContext serviceContext)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return addFriendlyURLEntry(
			groupId, classNameId, classPK, urlTitle, serviceContext);
	}

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, long classNameId, long classPK, String urlTitle,
			ServiceContext serviceContext)
		throws PortalException {

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		validate(groupId, classNameId, classPK, normalizedUrlTitle);

		FriendlyURLEntry mainFriendlyURLEntry =
			friendlyURLEntryPersistence.fetchByG_C_C_M(
				groupId, classNameId, classPK, true);

		if (mainFriendlyURLEntry != null) {
			mainFriendlyURLEntry.setMain(false);

			friendlyURLEntryPersistence.update(mainFriendlyURLEntry);
		}

		FriendlyURLEntry oldFriendlyURLEntry =
			friendlyURLEntryPersistence.fetchByG_C_C_U(
				groupId, classNameId, classPK, normalizedUrlTitle);

		if (oldFriendlyURLEntry != null) {
			oldFriendlyURLEntry.setMain(true);

			return friendlyURLEntryPersistence.update(oldFriendlyURLEntry);
		}

		long friendlyURLEntryId = counterLocalService.increment();

		FriendlyURLEntry friendlyURLEntry = createFriendlyURLEntry(
			friendlyURLEntryId);

		friendlyURLEntry.setUuid(serviceContext.getUuid());

		Group group = _groupLocalService.getGroup(groupId);

		friendlyURLEntry.setCompanyId(group.getCompanyId());

		friendlyURLEntry.setGroupId(groupId);
		friendlyURLEntry.setClassNameId(classNameId);
		friendlyURLEntry.setClassPK(classPK);
		friendlyURLEntry.setUrlTitle(normalizedUrlTitle);
		friendlyURLEntry.setMain(true);

		return friendlyURLEntryPersistence.update(friendlyURLEntry);
	}

	@Override
	public void deleteFriendlyURLEntry(
		long groupId, Class<?> clazz, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		List<FriendlyURLEntry> friendlyURLEntries =
			friendlyURLEntryPersistence.findByG_C_C(
				groupId, classNameId, classPK);

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			friendlyURLEntryLocalizationPersistence.removeByG_F(
				groupId, friendlyURLEntry.getFriendlyURLEntryId());
		}

		friendlyURLEntryPersistence.removeByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public void deleteFriendlyURLEntry(
			long groupId, Class<?> clazz, long classPK, String urlTitle)
		throws NoSuchFriendlyURLEntryException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		deleteFriendlyURLEntry(groupId, classNameId, classPK, urlTitle);
	}

	@Override
	public void deleteFriendlyURLEntry(
			long groupId, long classNameId, long classPK, String urlTitle)
		throws NoSuchFriendlyURLEntryException {

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryPersistence.removeByG_C_C_U(
				groupId, classNameId, classPK, urlTitle);

		friendlyURLEntryLocalizationPersistence.removeByG_F(
			groupId, friendlyURLEntry.getFriendlyURLEntryId());

		List<FriendlyURLEntry> friendlyURLEntries =
			friendlyURLEntryPersistence.findByG_C_C(
				groupId, classNameId, classPK, 0, 1,
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

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(property.eq(classNameId));
				}

			});
		actionableDynamicQuery.setGroupId(groupId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<FriendlyURLEntry>() {

				@Override
				public void performAction(FriendlyURLEntry friendlyURLEntry)
					throws PortalException {

					friendlyURLEntryLocalizationPersistence.removeByG_F(
						groupId, friendlyURLEntry.getFriendlyURLEntryId());

					friendlyURLEntryPersistence.remove(friendlyURLEntry);
				}

			});
	}

	@Override
	public FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, Class<?> clazz, String urlTitle) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return fetchFriendlyURLEntry(groupId, classNameId, urlTitle);
	}

	@Override
	public FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, long classNameId, String urlTitle) {

		return friendlyURLEntryPersistence.fetchByG_C_U(
			groupId, classNameId, urlTitle);
	}

	@Override
	public List<FriendlyURLEntry> getFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {

		return friendlyURLEntryPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public FriendlyURLEntry getMainFriendlyURLEntry(
			long groupId, Class<?> clazz, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return getMainFriendlyURLEntry(groupId, classNameId, classPK);
	}

	@Override
	public FriendlyURLEntry getMainFriendlyURLEntry(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		return friendlyURLEntryPersistence.findByG_C_C_M(
			groupId, classNameId, classPK, true);
	}

	@Override
	public String getUniqueUrlTitle(
		long groupId, long classNameId, long classPK, String urlTitle) {

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntry.class.getName(), "urlTitle");

		String curUrlTitle = normalizedUrlTitle.substring(
			0, Math.min(maxLength, normalizedUrlTitle.length()));

		for (int i = 1;; i++) {
			FriendlyURLEntry curFriendlyURLEntry = fetchFriendlyURLEntry(
				groupId, classNameId, curUrlTitle);

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
			long groupId, long classNameId, long classPK, String urlTitle)
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
				friendlyURLEntryPersistence.fetchByG_C_C_U(
					groupId, classNameId, classPK, normalizedUrlTitle);

			if (friendlyURLEntry != null) {
				return;
			}
		}

		int count = friendlyURLEntryPersistence.countByG_C_U(
			groupId, classNameId, normalizedUrlTitle);

		if (count > 0) {
			throw new DuplicateFriendlyURLEntryException();
		}
	}

	@Override
	public void validate(long groupId, long classNameId, String urlTitle)
		throws PortalException {

		validate(groupId, classNameId, 0, urlTitle);
	}

	@ServiceReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

}