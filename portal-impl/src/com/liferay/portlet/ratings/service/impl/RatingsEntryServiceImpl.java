/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.ratings.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portlet.ratings.service.base.RatingsEntryServiceBaseImpl;
import com.liferay.ratings.kernel.model.RatingsEntry;

/**
 * @author Brian Wing Shun Chan
 */
public class RatingsEntryServiceImpl extends RatingsEntryServiceBaseImpl {

	@Override
	public void deleteEntry(String className, long classPK)
		throws PortalException {

		ratingsEntryLocalService.deleteEntry(getUserId(), className, classPK);
	}

	@Override
	public RatingsEntry updateEntry(
			String className, long classPK, double score)
		throws PortalException {

		return ratingsEntryLocalService.updateEntry(
			getUserId(), className, classPK, score, new ServiceContext());
	}

}