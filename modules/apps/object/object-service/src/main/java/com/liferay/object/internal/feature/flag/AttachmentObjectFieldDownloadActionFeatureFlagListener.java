/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.feature.flag;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.definition.security.permission.resource.util.ObjectDefinitionResourcePermissionUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.override.service.PLOEntryLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Manuele Castro
 */
@Component(
	property = "feature.flag.key=LPD-17564", service = FeatureFlagListener.class
)
public class AttachmentObjectFieldDownloadActionFeatureFlagListener
	implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		if (!Objects.equals(featureFlagKey, "LPD-17564")) {
			return;
		}

		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, WorkflowConstants.STATUS_APPROVED);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			List<ObjectField> objectFields =
				_objectFieldLocalService.getObjectFieldsByBusinessType(
					objectDefinition.getObjectDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT);

			for (ObjectField objectField : objectFields) {
				String actionId = objectField.getAttachmentDownloadActionKey();

				if (enabled) {
					ResourceAction resourceAction =
						_resourceActionLocalService.fetchResourceAction(
							objectDefinition.getClassName(), actionId);

					if (resourceAction != null) {
						continue;
					}

					try {
						ObjectDefinitionResourcePermissionUtil.
							populateResourceActions(
								null, null, objectDefinition,
								_objectFieldLocalService,
								Collections.singletonList(objectField),
								_portletLocalService, _resourceActions);

						_objectFieldLocalService.
							addOrUpdateObjectFieldResourceActionPLOEntries(
								objectField);

						_updateResourcePermissions(actionId, objectDefinition);
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				}
				else {
					_resourceActions.removeModelResource(
						objectDefinition.getClassName(), actionId);

					_ploEntryLocalService.deletePLOEntries(
						objectField.getCompanyId(), "action." + actionId);
				}
			}
		}
	}

	private void _updateResourcePermissions(
			String actionId, ObjectDefinition objectDefinition)
		throws PortalException {

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				objectDefinition.getCompanyId(),
				objectDefinition.getClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (!resourcePermission.hasActionId(actionId) &&
				resourcePermission.isViewActionId()) {

				resourcePermission.addResourceAction(actionId);

				_resourcePermissionLocalService.updateResourcePermission(
					resourcePermission);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AttachmentObjectFieldDownloadActionFeatureFlagListener.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}