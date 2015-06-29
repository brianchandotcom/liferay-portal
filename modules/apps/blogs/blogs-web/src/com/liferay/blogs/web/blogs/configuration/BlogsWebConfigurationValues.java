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

package com.liferay.blogs.web.blogs.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Sergio González
 */
public class BlogsWebConfigurationValues {

	public static final String ENTRY_CHECK_INTERVAL =
		GetterUtil.getString(
			BlogsWebConfigurationUtil.get("entry.check.interval"));

	public static final String LINKBACK_JOB_INTERVAL =
		GetterUtil.getString(
			BlogsWebConfigurationUtil.get("linkback.job.interval"));

}