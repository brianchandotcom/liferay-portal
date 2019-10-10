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

package com.liferay.view.count.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.view.count.ViewCountService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.view.count.model.ViewCountEntry;
import com.liferay.view.count.service.ViewCountEntryLocalService;
import com.liferay.view.count.service.base.ViewCountEntryLocalServiceBaseImpl;
import com.liferay.view.count.service.persistence.ViewCountEntryPK;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.view.count.model.ViewCountEntry",
	service = AopService.class
)
public class ViewCountEntryLocalServiceImpl
	extends ViewCountEntryLocalServiceBaseImpl implements ViewCountService {

	@Override
	public void addViewCountEntry(
		long companyId, long classNameId, long classPK) {

		ViewCountEntry viewCountEntry = viewCountEntryPersistence.create(
			new ViewCountEntryPK(companyId, classNameId, classPK));

		viewCountEntry.setCompanyId(companyId);

		viewCountEntryPersistence.update(viewCountEntry);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			ViewCountService.class, ViewCountEntryLocalService.class,
			IdentifiableOSGiService.class, PersistedModelLocalService.class
		};
	}

	@Override
	public long getViewCount(long companyId, long classNameId, long classPK) {
		ViewCountEntry viewCountEntry =
			viewCountEntryPersistence.fetchByPrimaryKey(
				new ViewCountEntryPK(companyId, classNameId, classPK));

		if (viewCountEntry == null) {
			return 0;
		}

		return viewCountEntry.getViewCount();
	}

	@Override
	@Transactional(enabled = false)
	public void incrementViewCount(
		long companyId, long classNameId, long classPK) {

		viewCountEntryLocalService.incrementViewCount(
			companyId, classNameId, classPK, 1);
	}

	@BufferedIncrement(incrementClass = NumberIncrement.class)
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment) {

		viewCountEntryFinder.incrementViewCount(
			companyId, classNameId, classPK, increment);
	}

	@Override
	public void removeViewCount(long companyId, long classNameId, long classPK)
		throws PortalException {

		viewCountEntryPersistence.remove(
			new ViewCountEntryPK(companyId, classNameId, classPK));
	}

}