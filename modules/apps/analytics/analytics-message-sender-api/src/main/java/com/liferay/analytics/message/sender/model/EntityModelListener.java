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

package com.liferay.analytics.message.sender.model;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;

import java.util.List;

/**
 * @author Rachael Koestartyo
 */
public interface EntityModelListener<T extends BaseModel<T>> {

	public void addAnalyticsMessage(
		String eventType, List<String> includeAttributeNames, T model);

	public List<String> getAttributeNames();

	public long[] getMembershipIds(User user) throws Exception;

	public String getModelClassName();

	public void syncAll(long companyId) throws Exception;

}