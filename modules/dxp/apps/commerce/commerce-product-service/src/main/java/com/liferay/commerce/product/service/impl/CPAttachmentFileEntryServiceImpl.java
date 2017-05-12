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

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.base.CPAttachmentFileEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CPAttachmentFileEntryServiceImpl
	extends CPAttachmentFileEntryServiceBaseImpl {

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

		return cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
			classNameId, classPK, fileEntryId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, json,
			priority, type, serviceContext);
	}

	@Override
	public CPAttachmentFileEntry deleteCPAttachmentFileEntry(
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws PortalException {

		return cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntry(
			cpAttachmentFileEntry);
	}

	@Override
	public CPAttachmentFileEntry deleteCPAttachmentFileEntry(
			long cpAttachmentFileEntryId)
		throws PortalException {

		return cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntry(
			cpAttachmentFileEntryId);
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntrys(
			long classNameId, int classPK, int start, int end)
		throws PortalException {

		return cpAttachmentFileEntryLocalService.getCPAttachmentFileEntrys(
			classNameId, classPK, start, end);
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntrys(
			long classNameId, int classPK, int start, int end,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws PortalException {

		return cpAttachmentFileEntryLocalService.getCPAttachmentFileEntrys(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCPAttachmentFileEntrysCount(
		long classNameId, int classPK) {

		return cpAttachmentFileEntryLocalService.getCPAttachmentFileEntrysCount(
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

		return cpAttachmentFileEntryLocalService.updateCPAttachmentFileEntry(
			cpAttachmentFileEntryId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, json,
			priority, type, serviceContext);
	}

}