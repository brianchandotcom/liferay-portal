/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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