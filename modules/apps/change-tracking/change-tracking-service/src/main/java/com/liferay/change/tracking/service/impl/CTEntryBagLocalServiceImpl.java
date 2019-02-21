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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryBag;
import com.liferay.change.tracking.service.base.CTEntryBagLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @author Daniel Kocsis
 */
public class CTEntryBagLocalServiceImpl extends CTEntryBagLocalServiceBaseImpl {

	@Override
	public void addCTEntry(CTEntryBag ctEntryBag, CTEntry ctEntry) {
		if ((ctEntryBag == null) || (ctEntry == null) ||
			hasCTEntry(ctEntryBag, ctEntry)) {

			return;
		}

		ctEntryBagPersistence.addCTEntry(
			ctEntryBag.getCtEntryBagId(), ctEntry.getCtEntryId());
	}

	@Override
	public CTEntryBag createCTEntryBag(
			long userId, long ownerCTEntryId, long ctCollectionId,
			ServiceContext serviceContext)
		throws PortalException {

		long ctEntryBagId = counterLocalService.increment();

		CTEntryBag ctEntryBag = ctEntryBagPersistence.create(ctEntryBagId);

		User user = userLocalService.getUser(userId);

		ctEntryBag.setCompanyId(user.getCompanyId());
		ctEntryBag.setUserId(user.getUserId());
		ctEntryBag.setUserName(user.getFullName());

		Date now = new Date();

		ctEntryBag.setCreateDate(serviceContext.getCreateDate(now));
		ctEntryBag.setModifiedDate(serviceContext.getModifiedDate(now));

		ctEntryBag.setOwnerCTEntryId(ownerCTEntryId);
		ctEntryBag.setCtCollectionId(ctCollectionId);

		ctEntryBagPersistence.update(ctEntryBag);

		ctEntryBagPersistence.addCTEntry(
			ctEntryBag.getCtEntryBagId(), ownerCTEntryId);

		return ctEntryBag;
	}

	@Override
	public List<CTEntryBag> fetchCTEntryBags(
		long ownerCTEntryId, long ctCollectionId) {

		return ctEntryBagPersistence.findByO_C(ownerCTEntryId, ctCollectionId);
	}

	@Override
	public CTEntryBag fetchLatestCTEntryBag(
		long ownerCTEntryId, long ctCollectionId) {

		return ctEntryBagPersistence.fetchByO_C_Last(
			ownerCTEntryId, ctCollectionId,
			OrderByComparatorFactoryUtil.create(
				"CTEntryBag", "createDate", false));
	}

	@Override
	public boolean hasCTEntry(CTEntryBag ctEntryBag, CTEntry ctEntry) {
		LongStream ctEntryIdsStream = Arrays.stream(
			ctEntryBagLocalService.getCTEntryPrimaryKeys(
				ctEntryBag.getCtEntryBagId()));

		if (ctEntryIdsStream.anyMatch(
				bagCTEntryId -> bagCTEntryId == ctEntry.getCtEntryId())) {

			return true;
		}

		return false;
	}

	@Override
	public void removeCTEntry(CTEntryBag ctEntryBag, CTEntry ctEntry) {
		if (!hasCTEntry(ctEntryBag, ctEntry)) {
			return;
		}

		ctEntryBagPersistence.removeCTEntry(
			ctEntryBag.getCtEntryBagId(), ctEntry.getCtEntryId());
	}

}