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

package com.liferay.lcs.rest;

import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;

/**
 * @author Igor Beslic
 */
public interface LCSSubscriptionEntryService {

	public void addCorpProjectLCSSubscriptionEntries(
			long corpProjectId, String lcsSubscriptionEntriesJSON)
		throws JSONWebServiceInvocationException;

	public LCSSubscriptionEntry fetchLCSSubscriptionEntry(String key);

	public void incrementServerUsed(String key)
		throws JSONWebServiceInvocationException;

}