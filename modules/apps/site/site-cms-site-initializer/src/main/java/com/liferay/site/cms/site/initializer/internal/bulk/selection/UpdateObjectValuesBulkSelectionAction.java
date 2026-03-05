/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.bulk.selection;

import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.site.cms.site.initializer.bulk.selection.BaseObjectBulkSelectionAction;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "bulk.selection.action.key=update.object.values",
	service = BulkSelectionAction.class
)
public class UpdateObjectValuesBulkSelectionAction
	extends BaseObjectBulkSelectionAction {

	@Override
	protected void doExecute(
			User user, Map<String, Serializable> inputMap, Object object)
		throws Exception {

		if (!(object instanceof ObjectEntry)) {
			return;
		}

		Serializable values = inputMap.get("values");

		if (!(values instanceof Map)) {
			return;
		}

		Map<String, Object> valuesMap = (Map<String, Object>)values;

		ObjectEntry objectEntry = (ObjectEntry)object;

		String key = String.valueOf(objectEntry.getObjectEntryId());

		if (!valuesMap.containsKey(key)) {
			return;
		}

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getCompanyId(),
				objectDefinition.getStorageType());

		objectEntryManager.partialUpdateObjectEntry(
			objectEntry.getCompanyId(),
			new DefaultDTOConverterContext(
				false, null, null, null, null, user.getLocale(), null, user),
			objectEntry.getExternalReferenceCode(), objectDefinition,
			new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
				{
					setProperties(
						() -> (Map<String, Object>)valuesMap.get(key));
				}
			},
			_getScopeKey(objectEntry.getGroupId(), objectDefinition));
	}

	private String _getScopeKey(
		long groupId, ObjectDefinition objectDefinition) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (!objectScopeProvider.isGroupAware()) {
			return null;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		return group.getGroupKey();
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}