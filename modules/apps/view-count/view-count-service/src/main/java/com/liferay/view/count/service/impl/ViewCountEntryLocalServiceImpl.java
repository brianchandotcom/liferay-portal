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
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.SQLStateAcceptor;
import com.liferay.portal.kernel.spring.aop.Property;
import com.liferay.portal.kernel.spring.aop.Retry;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.view.count.ViewCountManager;
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
	extends ViewCountEntryLocalServiceBaseImpl implements ViewCountManager {

	@Override
	public void deleteViewCount(
		long companyId, long classNameId, long classPK) {

		ViewCountEntryPK viewCountEntryPK = new ViewCountEntryPK(
			companyId, classNameId, classPK);

		ViewCountEntry viewCountEntry =
			viewCountEntryPersistence.fetchByPrimaryKey(viewCountEntryPK);

		if (viewCountEntry != null) {
			viewCountEntryPersistence.remove(viewCountEntry);
		}
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			IdentifiableOSGiService.class, PersistedModelLocalService.class,
			ViewCountEntryLocalService.class, ViewCountManager.class
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

	@BufferedIncrement(incrementClass = NumberIncrement.class)
	@Override
	@Retry(
		acceptor = SQLStateAcceptor.class,
		properties = {
			@Property(
				name = SQLStateAcceptor.SQLSTATE,
				value = SQLStateAcceptor.SQLSTATE_INTEGRITY_CONSTRAINT_VIOLATION + "," + SQLStateAcceptor.SQLSTATE_TRANSACTION_ROLLBACK
			)
		}
	)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment) {

		viewCountEntryFinder.incrementViewCount(
			companyId, classNameId, classPK, increment);
	}

}