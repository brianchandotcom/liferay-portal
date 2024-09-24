/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.mass.delete.MassDeleteCacheThreadLocal;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.saved.content.exception.DuplicateSavedContentEntryException;
import com.liferay.saved.content.exception.NoSuchSavedContentEntryException;
import com.liferay.saved.content.model.SavedContentEntry;
import com.liferay.saved.content.service.base.SavedContentEntryLocalServiceBaseImpl;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.saved.content.model.SavedContentEntry",
	service = AopService.class
)
public class SavedContentEntryLocalServiceImpl
	extends SavedContentEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SavedContentEntry addSavedContentEntry(
			long userId, long groupId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		long classNameId = _classNameLocalService.getClassNameId(className);

		SavedContentEntry savedContentEntry =
			savedContentEntryPersistence.fetchByG_U_C_C(
				groupId, userId, classNameId, classPK);

		if (savedContentEntry != null) {
			throw new DuplicateSavedContentEntryException();
		}

		savedContentEntry = savedContentEntryPersistence.create(
			counterLocalService.increment(SavedContentEntry.class.getName()));

		savedContentEntry.setGroupId(groupId);

		User user = _userLocalService.getUserById(userId);

		savedContentEntry.setUserId(user.getUserId());
		savedContentEntry.setUserName(user.getFullName());

		savedContentEntry.setClassNameId(classNameId);
		savedContentEntry.setClassPK(classPK);

		savedContentEntry = savedContentEntryPersistence.update(
			savedContentEntry);

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			_resourceLocalService.addResources(
				savedContentEntry.getCompanyId(),
				savedContentEntry.getGroupId(), savedContentEntry.getUserId(),
				SavedContentEntry.class.getName(),
				savedContentEntry.getSavedContentEntryId(), false,
				serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			_resourceLocalService.addModelResources(
				savedContentEntry.getCompanyId(),
				savedContentEntry.getGroupId(), savedContentEntry.getUserId(),
				SavedContentEntry.class.getName(),
				savedContentEntry.getSavedContentEntryId(),
				serviceContext.getModelPermissions());
		}

		return savedContentEntry;
	}

	@Override
	public void deleteSavedContentEntries(
		long groupId, long classNameId, long classPK) {

		Map<Long, List<SavedContentEntry>> partitionSavedContentEntries =
			MassDeleteCacheThreadLocal.getMassDeleteCache(
				StringBundler.concat(
					SavedContentEntryLocalServiceImpl.class.getName(),
					".deleteSavedContentEntries#", groupId, classNameId),
				() -> MapUtil.toPartitionMap(
					savedContentEntryPersistence.findByG_CN(
						groupId, classNameId),
					SavedContentEntry::getClassPK));

		if (partitionSavedContentEntries == null) {
			savedContentEntryPersistence.removeByG_C_C(
				groupId, classNameId, classPK);

			return;
		}

		List<SavedContentEntry> savedContentEntries =
			partitionSavedContentEntries.remove(classPK);

		ListUtil.isNotEmptyForEach(
			savedContentEntries,
			savedContentEntry -> savedContentEntryPersistence.remove(
				savedContentEntry));
	}

	@Override
	public void deleteSavedContentEntriesByUserId(long userId) {
		savedContentEntryPersistence.removeByUserId(userId);
	}

	@Override
	public SavedContentEntry fetchSavedContentEntry(
		long userId, long groupId, String className, long classPK) {

		return savedContentEntryPersistence.fetchByG_U_C_C(
			groupId, userId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public SavedContentEntry getSavedContentEntry(
			long userId, long groupId, String className, long classPK)
		throws NoSuchSavedContentEntryException {

		return savedContentEntryPersistence.findByG_U_C_C(
			groupId, userId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}