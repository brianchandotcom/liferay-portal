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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.exception.CPAttachmentFileEntryFileEntryIdException;
import com.liferay.commerce.product.exception.CPDefinitionDisplayDateException;
import com.liferay.commerce.product.exception.CPDefinitionExpirationDateException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.base.CPAttachmentFileEntryLocalServiceBaseImpl;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPAttachmentFileEntryLocalServiceImpl
	extends CPAttachmentFileEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPAttachmentFileEntry addCPAttachmentFileEntry(
			long classNameId, long classPK, long fileEntryId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> titleMap, String json,
			double priority, int type, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product attachment file entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		Date displayDate = null;
		Date expirationDate = null;

		displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CPDefinitionDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CPDefinitionExpirationDateException.class);
		}

		validate(fileEntryId);

		long cpAttachmentFileEntryId = counterLocalService.increment();

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntryPersistence.create(cpAttachmentFileEntryId);

		cpAttachmentFileEntry.setUuid(serviceContext.getUuid());
		cpAttachmentFileEntry.setGroupId(groupId);
		cpAttachmentFileEntry.setCompanyId(user.getCompanyId());
		cpAttachmentFileEntry.setUserId(user.getUserId());
		cpAttachmentFileEntry.setUserName(user.getFullName());
		cpAttachmentFileEntry.setClassNameId(classNameId);
		cpAttachmentFileEntry.setClassPK(classPK);
		cpAttachmentFileEntry.setFileEntryId(fileEntryId);
		cpAttachmentFileEntry.setDisplayDate(displayDate);
		cpAttachmentFileEntry.setExpirationDate(expirationDate);
		cpAttachmentFileEntry.setTitleMap(titleMap);
		cpAttachmentFileEntry.setJson(json);
		cpAttachmentFileEntry.setPriority(priority);
		cpAttachmentFileEntry.setType(type);
		cpAttachmentFileEntry.setExpandoBridgeAttributes(serviceContext);

		cpAttachmentFileEntryPersistence.update(cpAttachmentFileEntry);

		return cpAttachmentFileEntry;
	}

	@Override
	public void deleteCPAttachmentFileEntries(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpAttachmentFileEntryPersistence.findByC_C(classNameId, classPK);

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntry(
				cpAttachmentFileEntry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPAttachmentFileEntry deleteCPAttachmentFileEntry(
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws PortalException {

		// Commerce product attachment file entry

		cpAttachmentFileEntryPersistence.remove(cpAttachmentFileEntry);

		// Expando

		expandoRowLocalService.deleteRows(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());

		return cpAttachmentFileEntry;
	}

	@Override
	public CPAttachmentFileEntry deleteCPAttachmentFileEntry(
			long cpAttachmentFileEntryId)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntryPersistence.findByPrimaryKey(
				cpAttachmentFileEntryId);

		return cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntry(
			cpAttachmentFileEntry);
	}

	@Override
	public Folder getAttachmentsFolder(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, CPConstants.SERVICE_NAME, serviceContext);

		Folder classNameFolder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, className,
			serviceContext);

		Folder entityFolder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(), classNameFolder.getFolderId(),
			String.valueOf(classPK), serviceContext);

		return entityFolder;
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long classNameId, long classPK, int type, int start, int end)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.findByC_C_T(
			classNameId, classPK, type, start, end);
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long classNameId, long classPK, int type, int start, int end,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator);
	}

	@Override
	public int getCPAttachmentFileEntriesCount(
		long classNameId, long classPK, int type) {

		return cpAttachmentFileEntryPersistence.countByC_C_T(
			classNameId, classPK, type);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPAttachmentFileEntry updateCPAttachmentFileEntry(
			long cpAttachmentFileEntryId, long fileEntryId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> titleMap, String json,
			double priority, int type, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntryPersistence.findByPrimaryKey(
				cpAttachmentFileEntryId);

		Date displayDate = null;
		Date expirationDate = null;

		displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CPDefinitionDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CPDefinitionExpirationDateException.class);
		}

		validate(fileEntryId);

		cpAttachmentFileEntry.setFileEntryId(fileEntryId);
		cpAttachmentFileEntry.setDisplayDate(displayDate);
		cpAttachmentFileEntry.setExpirationDate(expirationDate);
		cpAttachmentFileEntry.setTitleMap(titleMap);
		cpAttachmentFileEntry.setJson(json);
		cpAttachmentFileEntry.setPriority(priority);
		cpAttachmentFileEntry.setType(type);
		cpAttachmentFileEntry.setExpandoBridgeAttributes(serviceContext);

		cpAttachmentFileEntryPersistence.update(cpAttachmentFileEntry);

		return cpAttachmentFileEntry;
	}

	protected void validate(long fileEntryId) throws PortalException {
		if (fileEntryId <= 0) {
			throw new CPAttachmentFileEntryFileEntryIdException();
		}
	}

}