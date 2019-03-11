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

package com.liferay.headless.foundation.internal.resource;

import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = AopService.class)
public class UserServiceHelperImpl implements AopService, UserServiceHelper {

	@Override
	public User addUserWithPortrait(
			UserAccount userAccount, long companyId, byte[] portraitBytes,
			long prefixId, long suffixId, Calendar birthDateCalendar)
		throws PortalException {

		User user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, companyId, true, null, null,
			Validator.isNull(userAccount.getAlternateName()),
			userAccount.getAlternateName(), userAccount.getEmail(), 0,
			StringPool.BLANK, LocaleUtil.getDefault(),
			userAccount.getGivenName(), StringPool.BLANK,
			userAccount.getFamilyName(), prefixId, suffixId, true,
			birthDateCalendar.get(Calendar.MONTH),
			birthDateCalendar.get(Calendar.DATE),
			birthDateCalendar.get(Calendar.YEAR), userAccount.getJobTitle(),
			null, null, null, null, false, new ServiceContext());

		return _userLocalService.updatePortrait(
			user.getUserId(), portraitBytes);
	}

	@Reference
	private UserLocalService _userLocalService;

}