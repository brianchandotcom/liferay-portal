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

import com.liferay.petra.salesforce.client.SalesforceClient;

import com.sforce.async.AsyncApiException;
import com.sforce.async.BatchInfo;
import com.sforce.async.JobInfo;
import com.sforce.async.QueryResultList;
import com.sforce.ws.ConnectionException;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public interface SalesforceBulkClient extends SalesforceClient {

	public JobInfo abortJob(String jobInfoId, int retryCount)
		throws AsyncApiException, ConnectionException;

	public JobInfo closeJob(String jobInfoId, int retryCount)
		throws AsyncApiException, ConnectionException;

	public BatchInfo createBatchFromStream(
			JobInfo jobInfo, InputStream inputStream, int retryCount)
		throws AsyncApiException, ConnectionException;

	public JobInfo createJob(JobInfo jobInfo, int retryCount)
		throws AsyncApiException, ConnectionException;

	public BatchInfo getBatchInfo(
			String jobInfoId, String batchInfoId, int retryCount)
		throws AsyncApiException, ConnectionException;

	public QueryResultList getQueryResultList(
			String jobInfoId, String batchInfoId, int retryCount)
		throws AsyncApiException, ConnectionException;

	public InputStream getQueryResultStream(
			String jobInfoId, String batchInfoId, String queryResultId,
			int retryCount)
		throws AsyncApiException, ConnectionException;

}