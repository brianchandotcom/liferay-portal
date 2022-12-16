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

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.autofill;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;

/**
 * @author Nilton Vieira
 */
public interface SiteInitializerTestrayAutoFillHelper {

	public void addTestrayCaseResultIssue(
			long companyId, long testrayCaseResultId, String testrayIssueName)
		throws Exception;

	public void testrayAutoFillBuilds(
			long companyId, ObjectEntry testrayBuildObjectEntry1,
			ObjectEntry testrayBuildObjectEntry2)
		throws Exception;

	public void testrayAutoFillRuns(
			long companyId, ObjectEntry testrayRunObjectEntry1,
			ObjectEntry testrayRunObjectEntry2)
		throws Exception;

}