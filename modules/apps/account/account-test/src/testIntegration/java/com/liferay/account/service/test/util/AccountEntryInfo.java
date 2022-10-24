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

package com.liferay.account.service.test.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Drew Brokke
 */
public class AccountEntryInfo {

	public AccountEntryInfo() throws PortalException {
	}

	public AccountGroup[] accountGroups = null;
	public String[] assetTagNames = null;
	public String description = RandomTestUtil.randomString(50);
	public String[] domains = null;
	public String emailAddress = null;
	public byte[] logoBytes = null;
	public String name = RandomTestUtil.randomString(50);
	public Organization[] organizations = null;
	public long parentAccountEntryId = 0L;
	public ServiceContext serviceContext = null;
	public int status = WorkflowConstants.STATUS_APPROVED;
	public String taxIdNumber = RandomTestUtil.randomString(50);
	public String type = AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS;
	public long userId = TestPropsValues.getUserId();
	public long[] userIds = null;
	public User[] users = null;

}