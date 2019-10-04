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

package com.liferay.content.repository.service.impl;

import com.liferay.content.repository.exception.ContentRepositoryEntryNameException;
import com.liferay.content.repository.internal.constants.ContentRepositoryEntryConstants;
import com.liferay.content.repository.model.ContentRepositoryEntry;
import com.liferay.content.repository.service.base.ContentRepositoryEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the content repository entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.content.repository.service.ContentRepositoryEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ContentRepositoryEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.content.repository.model.ContentRepositoryEntry",
	service = AopService.class
)
public class ContentRepositoryEntryLocalServiceImpl
	extends ContentRepositoryEntryLocalServiceBaseImpl {

	@Override
	public ContentRepositoryEntry addContentRepositoryEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		_validateNameMap(nameMap);

		long entryId = counterLocalService.increment();

		ContentRepositoryEntry entry = contentRepositoryEntryPersistence.create(
			entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setCompanyId(serviceContext.getCompanyId());
		entry.setUserId(serviceContext.getUserId());

		entry = contentRepositoryEntryPersistence.update(entry);

		Group group = _groupLocalService.addGroup(
			entry.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			ContentRepositoryEntry.class.getName(),
			entry.getContentRepositoryEntryId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, descriptionMap,
			ContentRepositoryEntryConstants.TYPE_CONTENT_REPOSITORY, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, false,
			true, serviceContext);

		entry.setGroupId(group.getGroupId());

		return contentRepositoryEntryPersistence.update(entry);
	}

	@Override
	public ContentRepositoryEntry updateContentRepositoryEntry(
			long contentRepositoryEntryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		_validateNameMap(nameMap);

		ContentRepositoryEntry entry = getContentRepositoryEntry(
			contentRepositoryEntryId);

		Group group = _groupLocalService.getGroup(entry.getGroupId());

		_groupLocalService.updateGroup(
			entry.getGroupId(), group.getParentGroupId(), nameMap,
			descriptionMap, group.getType(), group.isManualMembership(),
			group.getMembershipRestriction(), group.getFriendlyURL(),
			group.isInheritContent(), group.isActive(), serviceContext);

		return contentRepositoryEntryPersistence.update(entry);
	}

	private void _validateNameMap(Map<Locale, String> nameMap)
		throws ContentRepositoryEntryNameException.MustNotBeNull {

		if (MapUtil.isEmpty(nameMap) ||
			Validator.isNull(nameMap.get(LocaleUtil.getDefault()))) {

			throw new ContentRepositoryEntryNameException.MustNotBeNull();
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

}