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

package com.liferay.portal.workflow.kaleo.uad.anonymizer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = UADAnonymizer.class)
public class KaleoLogUADAnonymizer extends BaseKaleoLogUADAnonymizer {

	@Override
	public void autoAnonymize(
			KaleoLog kaleoLog, long userId, User anonymousUser)
		throws PortalException {

		if (kaleoLog.getUserId() == userId) {
			kaleoLog.setUserId(anonymousUser.getUserId());
			kaleoLog.setUserName(anonymousUser.getFullName());

			autoAnonymizeAssetEntry(kaleoLog, anonymousUser);
		}

		if (kaleoLog.getCurrentAssigneeClassPK() == userId) {
			kaleoLog.setCurrentAssigneeClassPK(anonymousUser.getUserId());
		}

		if (kaleoLog.getPreviousAssigneeClassPK() == userId) {
			kaleoLog.setPreviousAssigneeClassPK(anonymousUser.getUserId());
		}

		kaleoLogLocalService.updateKaleoLog(kaleoLog);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return new String[] {
			"currentAssigneeClassPK", "previousAssigneeClassPK", "userId"
		};
	}

}