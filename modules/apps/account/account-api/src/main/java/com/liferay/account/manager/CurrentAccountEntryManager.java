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

package com.liferay.account.manager;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Pei-Jung Lan
 */
public interface CurrentAccountEntryManager {

	public AccountEntry getCurrentAccountEntry(long groupId, long userId)
		throws PortalException;

	public void setCurrentAccountEntry(
			long accountEntryId, long groupId, long userId)
		throws PortalException;

}