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

package com.liferay.lcs.rest.client;

import com.liferay.lcs.rest.client.exception.DuplicateLCSClusterEntryNameException;
import com.liferay.lcs.rest.client.exception.RequiredLCSClusterEntryNameException;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;

import java.util.List;

/**
 * @author Ivica Cardic
 */
public interface LCSClusterEntryClient {

	public LCSClusterEntry addLCSClusterEntry(
			long lcsProjectId, String name, String description, String location,
			String subscriptionType, int type)
		throws DuplicateLCSClusterEntryNameException,
			   JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   RequiredLCSClusterEntryNameException;

	public LCSClusterEntry getLCSClusterEntry(long lcsClusterEntryId)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException;

	public List<LCSClusterEntry> getLCSProjectManageableLCSClusterEntries(
			long lcsProjectId, int localLCSClusterEntryType)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException;

}