/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.v1_0;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.util.GroupUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
public abstract class BaseObjectEntryManager {

	protected Map<String, String> addDeleteAction(
		ObjectDefinition objectDefinition, String scopeKey, User user) {

		if (!_hasPortletResourcePermission(
				objectDefinition, scopeKey, user, ActionKeys.DELETE)) {

			return null;
		}

		return Collections.emptyMap();
	}

	protected void checkPortletResourcePermission(
			String actionId, ObjectDefinition objectDefinition, String scopeKey,
			User user)
		throws Exception {

		PortletResourcePermission portletResourcePermission =
			getPortletResourcePermission(objectDefinition);

		portletResourcePermission.check(
			permissionCheckerFactory.create(user),
			getGroupId(objectDefinition, scopeKey), actionId);
	}

	protected long getGroupId(
		ObjectDefinition objectDefinition, String scopeKey) {

		ObjectScopeProvider objectScopeProvider =
			objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			if (Objects.equals(objectDefinition.getScope(), "site")) {
				return GetterUtil.getLong(
					GroupUtil.getGroupId(
						objectDefinition.getCompanyId(), scopeKey,
						groupLocalService));
			}

			return GetterUtil.getLong(
				GroupUtil.getDepotGroupId(
					scopeKey, objectDefinition.getCompanyId(),
					depotEntryLocalService, groupLocalService));
		}

		return 0;
	}

	protected PortletResourcePermission getPortletResourcePermission(
		ObjectDefinition objectDefinition) {

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				objectDefinition.getClassName());

		return modelResourcePermission.getPortletResourcePermission();
	}

	protected void validateReadOnlyObjectFields(
			String externalReferenceCode, ObjectDefinition objectDefinition,
			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry)
		throws Exception {

		Map<String, Object> values = new HashMap<>();

		if (externalReferenceCode != null) {
			ObjectEntry serviceBuilderObjectEntry =
				objectEntryLocalService.fetchObjectEntry(
					externalReferenceCode,
					objectDefinition.getObjectDefinitionId());

			if (serviceBuilderObjectEntry == null) {
				return;
			}

			values.putAll(
				objectEntryLocalService.getSystemValues(
					serviceBuilderObjectEntry));
			values.putAll(
				objectEntryLocalService.getValues(serviceBuilderObjectEntry));
		}

		ObjectFieldUtil.validateReadOnlyObjectFields(
			ddmExpressionFactory, values,
			objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId()),
			objectEntry.getProperties());
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	@Reference
	protected DepotEntryLocalService depotEntryLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected Language language;

	@Reference
	protected ObjectEntryLocalService objectEntryLocalService;

	@Reference
	protected ObjectFieldLocalService objectFieldLocalService;

	@Reference
	protected ObjectScopeProviderRegistry objectScopeProviderRegistry;

	@Reference
	protected PermissionCheckerFactory permissionCheckerFactory;

	private boolean _hasPortletResourcePermission(
		ObjectDefinition objectDefinition, String scopeKey, User user,
		String actionId) {

		PortletResourcePermission portletResourcePermission =
			getPortletResourcePermission(objectDefinition);

		return portletResourcePermission.contains(
			permissionCheckerFactory.create(user),
			getGroupId(objectDefinition, scopeKey), actionId);
	}

}