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

package com.liferay.petra.salesforce.client.partner;

import com.liferay.petra.salesforce.client.SalesforceClient;

import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.GetDeletedResult;
import com.sforce.soap.partner.GetUpdatedResult;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.UpsertResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

import java.util.Calendar;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public interface SalesforcePartnerClient extends SalesforceClient {

	public List<SaveResult> create(SObject[] sObjects)
		throws ConnectionException;

	public List<DeleteResult> delete(String[] salesforceKeys)
		throws ConnectionException;

	public DescribeGlobalResult describeGlobal(int retryCount)
		throws ConnectionException;

	public List<DescribeSObjectResult> describeSObjects(
			String[] typeNames, int retryCount)
		throws ConnectionException;

	public GetDeletedResult getDeleted(
			String typeName, Calendar startCalendar, Calendar endCalendar,
			int retryCount)
		throws ConnectionException;

	public GetUpdatedResult getUpdated(
			String typeName, Calendar startCalendar, Calendar endCalendar,
			int retryCount)
		throws ConnectionException;

	public GetUserInfoResult getUserInfo() throws ConnectionException;

	public LoginResult login(String username, String password, int retryCount)
		throws ConnectionException;

	public QueryResult query(String queryString, int retryCount)
		throws ConnectionException;

	public QueryResult queryAll(String queryString, int retryCount)
		throws ConnectionException;

	public QueryResult queryMore(String queryLocator, int retryCount)
		throws ConnectionException;

	public List<SObject> retrieve(
			String fieldNames, String typeName, String[] salesforceKeys,
			int retryCount)
		throws ConnectionException;

	public List<SaveResult> update(SObject[] sObjects)
		throws ConnectionException;

	public List<UpsertResult> upsert(
			String salesforceExternalKeyFieldName, SObject[] sObjects)
		throws ConnectionException;

}