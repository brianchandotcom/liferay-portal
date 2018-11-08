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

import com.liferay.petra.salesforce.client.BaseSalesforceClientImpl;

import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.GetDeletedResult;
import com.sforce.soap.partner.GetUpdatedResult;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.UpsertResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 * @author Rachael Koestartyo
 */
public class SalesforcePartnerClientImpl
	extends BaseSalesforceClientImpl implements SalesforcePartnerClient {

	@Override
	public List<SaveResult> create(SObject[] sObjects)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		SaveResult[] saveResults = partnerConnection.create(sObjects);

		return _toList(saveResults);
	}

	@Override
	public List<DeleteResult> delete(String[] salesforceKeys)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		DeleteResult[] deleteResults = partnerConnection.delete(salesforceKeys);

		return _toList(deleteResults);
	}

	@Override
	public DescribeGlobalResult describeGlobal(int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.describeGlobal();
		}
		catch (ConnectionException ce) {
			return describeGlobal(getRetryCount(retryCount, ce));
		}
	}

	@Override
	public List<DescribeSObjectResult> describeSObjects(
			String[] typeNames, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			DescribeSObjectResult[] describeSObjectResults =
				partnerConnection.describeSObjects(typeNames);

			return _toList(describeSObjectResults);
		}
		catch (ConnectionException ce) {
			return describeSObjects(typeNames, getRetryCount(retryCount, ce));
		}
	}

	@Override
	public GetDeletedResult getDeleted(
			String typeName, Calendar startCalendar, Calendar endCalendar,
			int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.getDeleted(
				typeName, startCalendar, endCalendar);
		}
		catch (ConnectionException ce) {
			return getDeleted(
				typeName, startCalendar, endCalendar,
				getRetryCount(retryCount, ce));
		}
	}

	@Override
	public GetUpdatedResult getUpdated(
			String typeName, Calendar startCalendar, Calendar endCalendar,
			int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.getUpdated(
				typeName, startCalendar, endCalendar);
		}
		catch (ConnectionException ce) {
			return getUpdated(
				typeName, startCalendar, endCalendar,
				getRetryCount(retryCount, ce));
		}
	}

	@Override
	public GetUserInfoResult getUserInfo() throws ConnectionException {
		PartnerConnection partnerConnection = getPartnerConnection();

		return partnerConnection.getUserInfo();
	}

	@Override
	public LoginResult login(String username, String password, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.login(username, password);
		}
		catch (ConnectionException ce) {
			return login(username, password, getRetryCount(retryCount, ce));
		}
	}

	@Override
	public QueryResult query(String queryString, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.query(queryString);
		}
		catch (ConnectionException ce) {
			return query(queryString, getRetryCount(retryCount, ce));
		}
	}

	@Override
	public QueryResult queryAll(String queryString, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.queryAll(queryString);
		}
		catch (ConnectionException ce) {
			return queryAll(queryString, getRetryCount(retryCount, ce));
		}
	}

	@Override
	public QueryResult queryMore(String queryLocator, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.queryMore(queryLocator);
		}
		catch (ConnectionException ce) {
			return queryMore(queryLocator, getRetryCount(retryCount, ce));
		}
	}

	@Override
	public List<SObject> retrieve(
			String fieldNames, String typeName, String[] salesforceKeys,
			int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			SObject[] sObjects = partnerConnection.retrieve(
				fieldNames, typeName, salesforceKeys);

			return _toList(sObjects);
		}
		catch (ConnectionException ce) {
			return retrieve(
				fieldNames, typeName, salesforceKeys,
				getRetryCount(retryCount, ce));
		}
	}

	@Override
	public List<SaveResult> update(SObject[] sObjects)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		SaveResult[] saveResults = partnerConnection.update(sObjects);

		return _toList(saveResults);
	}

	@Override
	public List<UpsertResult> upsert(
			String salesforceExternalKeyFieldName, SObject[] sObjects)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		UpsertResult[] upsertResults = partnerConnection.upsert(
			salesforceExternalKeyFieldName, sObjects);

		return _toList(upsertResults);
	}

	private <E> List<E> _toList(E[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<>();
		}

		return new ArrayList<>(Arrays.asList(array));
	}

}