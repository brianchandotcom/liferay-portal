/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ListUtil;
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
public class ObjectEntryUtil {

	public static ObjectEntry addObjectEntry(
			DefaultDTOConverterContext defaultDTOConverterContext,
			String objectDefinitionShortName,
			ObjectEntryManager objectEntryManager,
			Map<String, Object> properties)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(
			objectDefinitionShortName);

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(properties);

		return objectEntryManager.addObjectEntry(
			defaultDTOConverterContext, objectDefinition, objectEntry, null);
	}

	public static List<ObjectEntry> getObjectEntries(
			Aggregation aggregation, long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			String filterString, String objectDefinitionShortName,
			ObjectEntryManager objectEntryManager, Sort[] sorts)
		throws Exception {

		Page<ObjectEntry> page = getObjectEntriesPage(
			aggregation, companyId, defaultDTOConverterContext, filterString,
			objectDefinitionShortName, objectEntryManager, sorts);

		return (List<ObjectEntry>)page.getItems();
	}

	public static Page<ObjectEntry> getObjectEntriesPage(
			Aggregation aggregation, long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			String filterString, String objectDefinitionShortName,
			ObjectEntryManager objectEntryManager, Sort[] sorts)
		throws Exception {

		return objectEntryManager.getObjectEntries(
			companyId, _getObjectDefinition(objectDefinitionShortName), null,
			aggregation, defaultDTOConverterContext, filterString, null, null,
			sorts);
	}

	public static ObjectEntry getObjectEntry(
			DefaultDTOConverterContext defaultDTOConverterContext,
			String objectDefinitionShortName, long objectEntryId,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		DefaultObjectEntryManager defaultObjectEntryManager =
			(DefaultObjectEntryManager)objectEntryManager;

		return defaultObjectEntryManager.getObjectEntry(
			defaultDTOConverterContext,
			_getObjectDefinition(objectDefinitionShortName), objectEntryId);
	}

	public static Object getProperty(String key, ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return properties.get(key);
	}

	public static long increment(
			long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			String filterString, String key, String objectDefinitionShortName,
			ObjectEntryManager objectEntryManager, Sort[] sorts)
		throws Exception {

		Page<ObjectEntry> page = getObjectEntriesPage(
			null, companyId, defaultDTOConverterContext, filterString,
			objectDefinitionShortName, objectEntryManager, sorts);

		ObjectEntry objectEntry = page.fetchFirstItem();

		if (objectEntry == null) {
			return 1;
		}

		Long fieldValue = (Long)getProperty(key, objectEntry);

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
			if (_objectDefinitionsMap.get(objectDefinition.getShortName()) !=
					null) {

				continue;
			}

			_objectDefinitionsMap.put(
				objectDefinition.getShortName(), objectDefinition);
		}
	}

	public static void updateObjectEntry(
			DefaultDTOConverterContext defaultDTOConverterContext,
			String objectDefinitionShortName, ObjectEntry objectEntry,
			long objectEntryId, ObjectEntryManager objectEntryManager)
		throws Exception {

		DefaultObjectEntryManager defaultObjectEntryManager =
			(DefaultObjectEntryManager)objectEntryManager;

		defaultObjectEntryManager.updateObjectEntry(
			defaultDTOConverterContext,
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

	private static final Map<String, ObjectDefinition> _objectDefinitionsMap =
		new HashMap<>();

}