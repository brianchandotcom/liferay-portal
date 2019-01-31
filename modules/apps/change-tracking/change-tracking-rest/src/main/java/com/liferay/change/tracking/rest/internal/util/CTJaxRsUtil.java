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

package com.liferay.change.tracking.rest.internal.util;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.rest.internal.exception.CTJaxRsException;
import com.liferay.change.tracking.rest.internal.exception.ChangeTrackingNotEnabledException;
import com.liferay.change.tracking.rest.internal.exception.NoSuchCompanyException;
import com.liferay.change.tracking.rest.internal.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Máté Thurzó
 */
public class CTJaxRsUtil {

	public static void checkChangeTrackingEnabled(
			long companyId, CTEngineManager ctEngineManager)
		throws CTJaxRsException {

		if (!ctEngineManager.isChangeTrackingEnabled(companyId)) {
			throw new ChangeTrackingNotEnabledException(
				companyId,
				"Cannot create Change Tracking Collection, Change Tracking " +
					"is disabled in the company");
		}
	}

	public static void checkCompany(long companyId) throws CTJaxRsException {
		try {
			CompanyLocalServiceUtil.getCompany(companyId);
		}
		catch (PortalException pe) {
			throw new NoSuchCompanyException(companyId, pe.getMessage());
		}
	}

	public static User getUser(long userId) throws CTJaxRsException {
		User user = UserLocalServiceUtil.fetchUser(userId);

		if (user == null) {
			throw new NoSuchUserException(
				0, "No user is found with id " + userId);
		}

		return user;
	}

}