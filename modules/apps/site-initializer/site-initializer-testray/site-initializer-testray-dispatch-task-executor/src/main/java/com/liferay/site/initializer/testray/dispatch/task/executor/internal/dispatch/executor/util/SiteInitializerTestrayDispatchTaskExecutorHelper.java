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

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Nilton Vieira
 */
public interface SiteInitializerTestrayDispatchTaskExecutorHelper {

	public ObjectEntry addObjectEntry(
			String objectDefinitionShortName, Map<String, Object> properties)
		throws Exception;

	public void createDefaultDTOConverterContext(User user);

	public List<ObjectEntry> getObjectEntries(
			Aggregation aggregation, long companyId, String filter,
			String objectDefinitionShortName, Sort[] sorts)
		throws Exception;

	public Page<ObjectEntry> getObjectEntriesPage(
			Aggregation aggregation, long companyId, String filter,
			String objectDefinitionShortName, Sort[] sorts)
		throws Exception;

	public ObjectEntry getObjectEntry(
			String objectDefinitionShortName, long objectEntryId)
		throws Exception;

	public Object getProperty(String key, ObjectEntry objectEntry);

	public long incrementTestrayFieldValue(
			long companyId, String fieldName, String filterString,
			String objectDefinitionShortName, Sort[] sorts)
		throws Exception;

	public void loadObjectDefinitions(long companyId);

	public void updateObjectEntry(
			String objectDefinitionShortName, ObjectEntry objectEntry,
			long objectEntryId)
		throws Exception;

}