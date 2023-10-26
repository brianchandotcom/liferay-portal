/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.saved.content.exception.DuplicateSavedContentEntryException;
import com.liferay.saved.content.model.SavedContentEntry;
import com.liferay.saved.content.service.base.SavedContentEntryLocalServiceBaseImpl;

import java.util.Date;

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

	@Override
	public void addEntryResources(
			SavedContentEntry entry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			SavedContentEntry.class.getName(), entry.getSavedContentEntryId(),
			false, addGroupPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			SavedContentEntry entry, ModelPermissions modelPermissions)
		throws PortalException {

		_resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			SavedContentEntry.class.getName(), entry.getSavedContentEntryId(),
			modelPermissions);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SavedContentEntry addSavedContentEntry(
			long groupId, long userId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(
			GroupThreadLocal.getGroupId(), userId, className, classPK);

		long savedContentEntryId = counterLocalService.increment(
			SavedContentEntry.class.getName());

		SavedContentEntry savedContentEntry =
			savedContentEntryPersistence.create(savedContentEntryId);

		savedContentEntry.setGroupId(groupId);

		User user = _userLocalService.getUserById(userId);

		savedContentEntry.setUserId(user.getUserId());
		savedContentEntry.setUserName(user.getFullName());

		savedContentEntry.setCreateDate(new Date());
		savedContentEntry.setModifiedDate(new Date());
		savedContentEntry.setClassNameId(
			_classNameLocalService.getClassNameId(className));
		savedContentEntry.setClassPK(classPK);

		savedContentEntry = savedContentEntryPersistence.update(
			savedContentEntry);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addEntryResources(
				savedContentEntry, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addEntryResources(
				savedContentEntry, serviceContext.getModelPermissions());
		}

		return savedContentEntry;
	}

	@Override
	public SavedContentEntry fetchSavedContentEntry(
		long groupId, long userId, String className, long classPK) {

		return savedContentEntryPersistence.fetchByG_U_C_C(
			groupId, userId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	private void _validate(
			long groupId, long userId, String className, long classPK)
		throws DuplicateSavedContentEntryException {

		SavedContentEntry savedContentEntry =
			savedContentEntryPersistence.fetchByG_U_C_C(
				groupId, userId,
				_classNameLocalService.getClassNameId(className), classPK);

		if (savedContentEntry != null) {
			throw new DuplicateSavedContentEntryException();
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}