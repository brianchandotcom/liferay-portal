/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
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
	public SavedContentEntry addSavedContentEntry(
			long groupId, long userId, String className, long classPK)
		throws PortalException {

		_validate(groupId, userId, className, classPK);

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

		return savedContentEntryPersistence.update(savedContentEntry);
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
	private UserLocalService _userLocalService;

}