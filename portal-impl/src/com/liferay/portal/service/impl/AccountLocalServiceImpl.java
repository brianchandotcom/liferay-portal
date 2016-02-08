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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.service.base.AccountLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountLocalServiceImpl extends AccountLocalServiceBaseImpl {

	@Override
	public Account getAccount(long companyId, long accountId)
		throws PortalException {

		return accountPersistence.findByPrimaryKey(accountId);
	}

}