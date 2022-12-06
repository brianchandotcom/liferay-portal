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

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Nilton Vieira
 */
public abstract class BaseSiteInitializerTestrayDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	protected ObjectEntry addObjectEntry(
			String objectDefinitionShortName, Map<String, Object> properties)
		throws Exception {

		ObjectDefinition objectDefinition = getObjectDefinition(
			objectDefinitionShortName);

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(properties);

		return objectEntryManager.addObjectEntry(
			defaultDTOConverterContext, objectDefinition, objectEntry, null);
	}

	protected ObjectDefinition getObjectDefinition(
			String objectDefinitionShortName)
		throws Exception {

		ObjectDefinition objectDefinition = objectDefinitionsMap.get(
			objectDefinitionShortName);

		if (objectDefinition == null) {
			throw new PortalException(
				"No object definition found with short name " +
					objectDefinitionShortName);
		}

		return objectDefinition;
	}

	protected List<ObjectEntry> getObjectEntries(
			Aggregation aggregation, long companyId, String filter,
			String objectDefinitionShortName, Sort[] sorts)
		throws Exception {

		Page<ObjectEntry> objectEntriesPage = getObjectEntriesPage(
			aggregation, companyId, filter, objectDefinitionShortName, sorts);

		return (List<ObjectEntry>)objectEntriesPage.getItems();
	}

	protected Page<ObjectEntry> getObjectEntriesPage(
			Aggregation aggregation, long companyId, String filter,
			String objectDefinitionShortName, Sort[] sorts)
		throws Exception {

		return objectEntryManager.getObjectEntries(
			companyId, getObjectDefinition(objectDefinitionShortName), null,
			aggregation, defaultDTOConverterContext, filter, null, null, sorts);
	}

	protected ObjectEntry getObjectEntry(
			String objectDefinitionShortName, long objectEntryId)
		throws Exception {

		return objectEntryManager.getObjectEntry(
			defaultDTOConverterContext,
			getObjectDefinition(objectDefinitionShortName), objectEntryId);
	}

	protected Object getProperty(String key, ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return properties.get(key);
	}

	protected long incrementTestrayFieldValue(
			long companyId, String fieldName, String filterString,
			String objectDefinitionShortName, Sort[] sorts)
		throws Exception {

		Page<ObjectEntry> objectEntriesPage = getObjectEntriesPage(
			null, companyId, filterString, objectDefinitionShortName, sorts);

		ObjectEntry objectEntry = objectEntriesPage.fetchFirstItem();

		if (objectEntry == null) {
			return 1;
		}

		String fieldValue = String.valueOf(getProperty(fieldName, objectEntry));

		if (fieldValue == null) {
			return 1;
		}

		return Long.valueOf(StringUtil.extractDigits(fieldValue)) + 1;
	}

	protected void loadObjectDefinitions(long companyId) {
		List<ObjectDefinition> objectDefinitions =
			objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			objectDefinitionsMap.put(
				objectDefinition.getShortName(), objectDefinition);
		}
	}

	protected void updateObjectEntry(
			String objectDefinitionShortName, ObjectEntry objectEntry,
			long objectEntryId)
		throws Exception {

		objectEntryManager.updateObjectEntry(
			defaultDTOConverterContext,
			getObjectDefinition(objectDefinitionShortName), objectEntryId,
			objectEntry);
	}

	protected DefaultDTOConverterContext defaultDTOConverterContext;

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	protected final Map<String, ObjectDefinition> objectDefinitionsMap =
		new HashMap<>();

	@Reference(target = "(object.entry.manager.storage.type=default)")
	protected ObjectEntryManager objectEntryManager;

}