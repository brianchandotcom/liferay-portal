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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.base.CTEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTEntry",
	service = AopService.class
)
public class CTEntryLocalServiceImpl extends CTEntryLocalServiceBaseImpl {

	@Override
	public CTEntry addCTEntry(
			long ctCollectionId, long modelClassNameId, CTModel<?> ctModel,
			long userId, int changeType)
		throws PortalException {

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		long ctEntryId = counterLocalService.increment(CTEntry.class.getName());

		CTEntry ctEntry = ctEntryPersistence.create(ctEntryId);

		ctEntry.setCompanyId(ctCollection.getCompanyId());
		ctEntry.setUserId(userId);
		ctEntry.setCtCollectionId(ctCollectionId);
		ctEntry.setModelClassNameId(modelClassNameId);
		ctEntry.setModelClassPK(ctModel.getPrimaryKey());
		ctEntry.setModelMvccVersion(ctModel.getMvccVersion());
		ctEntry.setChangeType(changeType);

		return ctEntryPersistence.update(ctEntry);
	}

	@Override
	public CTEntry fetchCTEntry(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return ctEntryPersistence.fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(long ctCollectionId) {
		return getCTCollectionCTEntries(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return Collections.emptyList();
		}

		return ctEntryPersistence.findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	@Override
	public int getCTCollectionCTEntriesCount(long ctCollectionId) {
		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return 0;
		}

		return ctEntryPersistence.countByCTCollectionId(ctCollectionId);
	}

	@Override
	public List<CTEntry> getCTEntries(
		long ctCollectionId, long modelClassNameId) {

		return ctEntryPersistence.findByC_MCNI(
			ctCollectionId, modelClassNameId);
	}

	public List<Long> getExclusiveModelClassPKs(
		long ctCollectionId, long modelClassNameId) {

		List<CTEntry> ctEntries = ctEntryPersistence.findByC_MCNI(
			ctCollectionId, modelClassNameId);

		if (ctEntries.isEmpty()) {
			return Collections.emptyList();
		}

		List<Long> modelClassPKs = ListUtil.toList(
			ctEntries, CTEntry::getModelClassPK);

		for (CTEntry ctEntry :
				ctEntryPersistence.findByNotC_MCPK(
					ctCollectionId,
					ArrayUtil.toArray(modelClassPKs.toArray(new Long[0])))) {

			modelClassPKs.remove(ctEntry.getModelClassPK());
		}

		return modelClassPKs;
	}

	@Override
	public boolean hasCTEntries(long ctCollectionId, long modelClassNameId) {
		int count = ctEntryPersistence.countByC_MCNI(
			ctCollectionId, modelClassNameId);

		if (count == 0) {
			return false;
		}

		return true;
	}

}