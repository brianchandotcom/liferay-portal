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

import com.liferay.commerce.product.exception.CPDefinitionDisplayDateException;
import com.liferay.commerce.product.exception.CPDefinitionExpirationDateException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.base.CPAttachmentFileEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Date;
import java.util.List;

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
			boolean neverExpire, String json, int priority, int type,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product attachment file entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		Date displayDate = null;
		Date expirationDate = null;
		Date now = new Date();

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
		cpAttachmentFileEntry.setJson(json);
		cpAttachmentFileEntry.setPriority(priority);
		cpAttachmentFileEntry.setType(type);
		cpAttachmentFileEntry.setExpandoBridgeAttributes(serviceContext);

		cpAttachmentFileEntryPersistence.update(cpAttachmentFileEntry);

		return cpAttachmentFileEntry;
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
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntrys(
			long classNameId, int classPK, int start, int end)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.findByC_C(
			classNameId, classPK, start, end);
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntrys(
			long classNameId, int classPK, int start, int end,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCPAttachmentFileEntrysCount(long classNameId, int classPK) {
		return cpAttachmentFileEntryPersistence.countByC_C(
			classNameId, classPK);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPAttachmentFileEntry updateCPAttachmentFileEntry(
			long cpAttachmentFileEntryId, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String json, int priority, int type,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product attachment file entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();
		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntryPersistence.findByPrimaryKey(
				cpAttachmentFileEntryId);

		Date displayDate = null;
		Date expirationDate = null;
		Date now = new Date();

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

		cpAttachmentFileEntry.setDisplayDate(displayDate);
		cpAttachmentFileEntry.setExpirationDate(expirationDate);
		cpAttachmentFileEntry.setJson(json);
		cpAttachmentFileEntry.setPriority(priority);
		cpAttachmentFileEntry.setType(type);
		cpAttachmentFileEntry.setExpandoBridgeAttributes(serviceContext);

		cpAttachmentFileEntryPersistence.update(cpAttachmentFileEntry);

		return cpAttachmentFileEntry;
	}

}