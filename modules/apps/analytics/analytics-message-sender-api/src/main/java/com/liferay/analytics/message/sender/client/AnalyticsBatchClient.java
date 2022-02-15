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

package com.liferay.analytics.message.sender.client;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 * @deprecated As of Cavanaugh (7.4.x)
 */
@Deprecated
public interface AnalyticsBatchClient {

	public File downloadResource(
		long companyId, Date resourceLastModifiedDate, String resourceName);

	public void uploadResource(
		long companyId, InputStream resourceInputStream, String resourceName);

}