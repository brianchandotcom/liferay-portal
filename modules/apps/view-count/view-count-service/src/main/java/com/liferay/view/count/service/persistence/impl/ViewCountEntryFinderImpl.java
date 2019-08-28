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

package com.liferay.view.count.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.view.count.model.ViewCountEntry;
import com.liferay.view.count.model.impl.ViewCountEntryImpl;
import com.liferay.view.count.service.persistence.ViewCountEntryFinder;
import com.liferay.view.count.service.persistence.ViewCountEntryPK;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = ViewCountEntryFinder.class)
public class ViewCountEntryFinderImpl
	extends ViewCountEntryFinderBaseImpl implements ViewCountEntryFinder {

	@Override
	public void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment) {

		ViewCountEntryPK viewCountEntryPK = new ViewCountEntryPK(
			companyId, classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			ViewCountEntry viewCountEntry = (ViewCountEntry)session.get(
				ViewCountEntryImpl.class, viewCountEntryPK, LockMode.UPGRADE);

			viewCountEntry.setViewCount(
				viewCountEntry.getViewCount() + increment);

			session.saveOrUpdate(viewCountEntry);

			_entityCache.putResult(
				entityCacheEnabled, ViewCountEntryImpl.class, viewCountEntryPK,
				viewCountEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private EntityCache _entityCache;

}