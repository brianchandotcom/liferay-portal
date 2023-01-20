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

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nilton Vieira
 */
public class SiteInitializerTestrayObjectUtil {

	public static ObjectEntry addObjectEntry(
			ObjectEntryManager objectEntryManager,
			String objectDefinitionShortName, Map<String, Object> properties)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(
			objectDefinitionShortName);

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(properties);

		return objectEntryManager.addObjectEntry(
			_defaultDTOConverterContext, objectDefinition, objectEntry, null);
	}

	public static void createDefaultDTOConverterContext(User user) {
		_defaultDTOConverterContext = new DefaultDTOConverterContext(
			false, null, null, null, null, LocaleUtil.getSiteDefault(), null,
			user);
	}

	public static List<ObjectEntry> getObjectEntries(
			Aggregation aggregation, long companyId, String filter,
			String objectDefinitionShortName, Sort[] sorts,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		Page<ObjectEntry> objectEntriesPage = getObjectEntriesPage(
			aggregation, companyId, filter, objectDefinitionShortName, sorts,
			objectEntryManager);

		return (List<ObjectEntry>)objectEntriesPage.getItems();
	}

	public static Page<ObjectEntry> getObjectEntriesPage(
			Aggregation aggregation, long companyId, String filter,
			String objectDefinitionShortName, Sort[] sorts,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		return objectEntryManager.getObjectEntries(
			companyId, _getObjectDefinition(objectDefinitionShortName), null,
			aggregation, _defaultDTOConverterContext, filter, null, null,
			sorts);
	}

	public static ObjectEntry getObjectEntry(
			String objectDefinitionShortName, long objectEntryId,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		return objectEntryManager.getObjectEntry(
			_defaultDTOConverterContext,
			_getObjectDefinition(objectDefinitionShortName), objectEntryId);
	}

	public static Object getProperty(String key, ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return properties.get(key);
	}

	public static long incrementTestrayFieldValue(
			long companyId, String fieldName, String filterString,
			String objectDefinitionShortName, Sort[] sorts,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		Page<ObjectEntry> objectEntriesPage = getObjectEntriesPage(
			null, companyId, filterString, objectDefinitionShortName, sorts,
			objectEntryManager);

		ObjectEntry objectEntry = objectEntriesPage.fetchFirstItem();

		if (objectEntry == null) {
			return 1;
		}

		Long fieldValue = (Long)getProperty(fieldName, objectEntry);

		if (fieldValue == null) {
			return 1;
		}

		return fieldValue.longValue() + 1;
	}

	public static void loadObjectDefinitions(
		long companyId,
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		List<ObjectDefinition> objectDefinitions =
			objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_objectDefinitionsMap.put(
				objectDefinition.getShortName(), objectDefinition);
		}
	}

	public static void updateObjectEntry(
			String objectDefinitionShortName, ObjectEntry objectEntry,
			long objectEntryId, ObjectEntryManager objectEntryManager)
		throws Exception {

		objectEntryManager.updateObjectEntry(
			_defaultDTOConverterContext,
			_getObjectDefinition(objectDefinitionShortName), objectEntryId,
			objectEntry);
	}

	private static ObjectDefinition _getObjectDefinition(
			String objectDefinitionShortName)
		throws Exception {

		ObjectDefinition objectDefinition = _objectDefinitionsMap.get(
			objectDefinitionShortName);

		if (objectDefinition == null) {
			throw new PortalException(
				"No object definition found with short name " +
					objectDefinitionShortName);
		}

		return objectDefinition;
	}

	private static DefaultDTOConverterContext _defaultDTOConverterContext;
	private static final Map<String, ObjectDefinition> _objectDefinitionsMap =
		new HashMap<>();

}