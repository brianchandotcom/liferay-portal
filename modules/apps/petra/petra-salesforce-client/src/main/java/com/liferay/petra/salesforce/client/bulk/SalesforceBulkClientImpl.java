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

package com.liferay.petra.salesforce.client.bulk;

import com.liferay.petra.salesforce.client.BaseSalesforceClientImpl;

import com.sforce.async.AsyncApiException;
import com.sforce.async.BatchInfo;
import com.sforce.async.BulkConnection;
import com.sforce.async.JobInfo;
import com.sforce.async.QueryResultList;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public class SalesforceBulkClientImpl
	extends BaseSalesforceClientImpl implements SalesforceBulkClient {

	@Override
	public JobInfo abortJob(String jobInfoId, int retryCount)
		throws AsyncApiException, ConnectionException {

		try {
			BulkConnection bulkConnection = _getBulkConnection();

			return bulkConnection.abortJob(jobInfoId);
		}
		catch (AsyncApiException aae) {
			return abortJob(jobInfoId, getRetryCount(retryCount, aae));
		}
	}

	@Override
	public JobInfo closeJob(String jobInfoId, int retryCount)
		throws AsyncApiException, ConnectionException {

		try {
			BulkConnection bulkConnection = _getBulkConnection();

			return bulkConnection.closeJob(jobInfoId);
		}
		catch (AsyncApiException aae) {
			return closeJob(jobInfoId, getRetryCount(retryCount, aae));
		}
	}

	@Override
	public BatchInfo createBatchFromStream(
			JobInfo jobInfo, InputStream inputStream, int retryCount)
		throws AsyncApiException, ConnectionException {

		try {
			BulkConnection bulkConnection = _getBulkConnection();

			return bulkConnection.createBatchFromStream(jobInfo, inputStream);
		}
		catch (AsyncApiException aae) {
			return createBatchFromStream(
				jobInfo, inputStream, getRetryCount(retryCount, aae));
		}
	}

	@Override
	public JobInfo createJob(JobInfo jobInfo, int retryCount)
		throws AsyncApiException, ConnectionException {

		try {
			BulkConnection bulkConnection = _getBulkConnection();

			return bulkConnection.createJob(jobInfo);
		}
		catch (AsyncApiException aae) {
			return createJob(jobInfo, getRetryCount(retryCount, aae));
		}
	}

	@Override
	public BatchInfo getBatchInfo(
			String jobInfoId, String batchInfoId, int retryCount)
		throws AsyncApiException, ConnectionException {

		try {
			BulkConnection bulkConnection = _getBulkConnection();

			return bulkConnection.getBatchInfo(jobInfoId, batchInfoId);
		}
		catch (AsyncApiException aae) {
			return getBatchInfo(
				jobInfoId, batchInfoId, getRetryCount(retryCount, aae));
		}
	}

	@Override
	public QueryResultList getQueryResultList(
			String jobInfoId, String batchInfoId, int retryCount)
		throws AsyncApiException, ConnectionException {

		try {
			BulkConnection bulkConnection = _getBulkConnection();

			return bulkConnection.getQueryResultList(jobInfoId, batchInfoId);
		}
		catch (AsyncApiException aae) {
			return getQueryResultList(
				jobInfoId, batchInfoId, getRetryCount(retryCount, aae));
		}
	}

	@Override
	public InputStream getQueryResultStream(
			String jobInfoId, String batchInfoId, String queryResultId,
			int retryCount)
		throws AsyncApiException, ConnectionException {

		try {
			BulkConnection bulkConnection = _getBulkConnection();

			return bulkConnection.getQueryResultStream(
				jobInfoId, batchInfoId, queryResultId);
		}
		catch (AsyncApiException aae) {
			return getQueryResultStream(
				jobInfoId, batchInfoId, queryResultId,
				getRetryCount(retryCount, aae));
		}
	}

	private BulkConnection _getBulkConnection()
		throws AsyncApiException, ConnectionException {

		if (_bulkConnection != null) {
			return _bulkConnection;
		}

		PartnerConnection partnerConnection = getPartnerConnection();

		ConnectorConfig connectorConfig = partnerConnection.getConfig();

		connectorConfig.setCompression(true);

		StringBuilder sb = new StringBuilder();

		String serviceEndpoint = connectorConfig.getServiceEndpoint();

		sb.append(
			serviceEndpoint.substring(0, serviceEndpoint.indexOf("/Soap/")));

		sb.append("/async/");

		String authEndpoint = connectorConfig.getAuthEndpoint();

		sb.append(authEndpoint.substring(authEndpoint.lastIndexOf("/") + 1));

		connectorConfig.setRestEndpoint(sb.toString());

		connectorConfig.setSessionId(connectorConfig.getSessionId());

		_bulkConnection = new BulkConnection(connectorConfig);

		return _bulkConnection;
	}

	private BulkConnection _bulkConnection;

}