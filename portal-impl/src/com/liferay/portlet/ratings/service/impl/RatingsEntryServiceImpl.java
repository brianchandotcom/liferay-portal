/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.base.RatingsEntryServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class RatingsEntryServiceImpl extends RatingsEntryServiceBaseImpl {

	@Override
	public void deleteEntry(String className, long classPK)
		throws PortalException, SystemException {

		ratingsEntryLocalService.deleteEntry(getUserId(), className, classPK);
	}

	@Override
	public RatingsEntry updateEntry(
			String className, long classPK, double score)
		throws PortalException, SystemException {

		return ratingsEntryLocalService.updateEntry(
			getUserId(), className, classPK, score, new ServiceContext());
	}

}