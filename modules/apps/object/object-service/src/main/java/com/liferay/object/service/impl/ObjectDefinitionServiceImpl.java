/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.base.ObjectDefinitionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectDefinition"
	},
	service = AopService.class
)
public class ObjectDefinitionServiceImpl
	extends ObjectDefinitionServiceBaseImpl {

	@Override
	public ObjectDefinition addCustomObjectDefinition(
			long objectFolderId, boolean enableComments,
			boolean enableLocalization, boolean enableObjectEntryDraft,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			boolean portlet, String scope, String storageType,
			List<ObjectField> objectFields)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		_objectFolderModelResourcePermission.check(
			getPermissionChecker(), objectFolderId,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return objectDefinitionLocalService.addCustomObjectDefinition(
			getUserId(), objectFolderId, enableComments, enableLocalization,
			enableObjectEntryDraft, labelMap, name, panelAppOrder,
			panelCategoryKey, pluralLabelMap, portlet, scope, storageType,
			objectFields);
	}

	@Override
	public ObjectDefinition addObjectDefinition(
			String externalReferenceCode, long objectFolderId,
			boolean modifiable, boolean system)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		_objectFolderModelResourcePermission.check(
			getPermissionChecker(), objectFolderId,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return objectDefinitionLocalService.addObjectDefinition(
			externalReferenceCode, getUserId(), objectFolderId, modifiable,
			system);
	}

	@Override
	public ObjectDefinition addSystemObjectDefinition(
			String externalReferenceCode, long userId, long objectFolderId,
			boolean enableComments, Map<Locale, String> labelMap, String name,
			String panelAppOrder, String panelCategoryKey,
			Map<Locale, String> pluralLabelMap, String scope,
			List<ObjectField> objectFields)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		_objectFolderModelResourcePermission.check(
			getPermissionChecker(), objectFolderId,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return objectDefinitionLocalService.addSystemObjectDefinition(
			externalReferenceCode, userId, objectFolderId, null, null,
			enableComments, labelMap, true, name, panelAppOrder,
			panelCategoryKey, null, null, pluralLabelMap, scope, null, 1,
			WorkflowConstants.STATUS_DRAFT, objectFields);
	}

	@Override
	public ObjectDefinition deleteObjectDefinition(long objectDefinitionId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.DELETE);

		return objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition fetchObjectDefinitionByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					externalReferenceCode, companyId);

		if (objectDefinition != null) {
			_objectDefinitionModelResourcePermission.check(
				getPermissionChecker(), objectDefinition, ActionKeys.VIEW);
		}

		return objectDefinition;
	}

	@Override
	public ObjectDefinition getObjectDefinition(long objectDefinitionId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.VIEW);

		return objectDefinitionLocalService.getObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition getObjectDefinitionByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					externalReferenceCode, companyId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinition, ActionKeys.VIEW);

		return objectDefinition;
	}

	@Override
	public List<ObjectDefinition> getObjectDefinitions(int start, int end) {
		return objectDefinitionLocalService.getObjectDefinitions(start, end);
	}

	@Override
	public List<ObjectDefinition> getObjectDefinitions(
		long companyId, int start, int end) {

		return objectDefinitionPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getObjectDefinitionsCount() throws PortalException {
		return objectDefinitionLocalService.getObjectDefinitionsCount();
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws PortalException {

		return objectDefinitionLocalService.getObjectDefinitionsCount(
			companyId);
	}

	@Override
	public ObjectDefinition publishCustomObjectDefinition(
			long objectDefinitionId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.PUBLISH_OBJECT_DEFINITION);

		return objectDefinitionLocalService.publishCustomObjectDefinition(
			getUserId(), objectDefinitionId);
	}

	@Override
	public ObjectDefinition publishSystemObjectDefinition(
			long objectDefinitionId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.PUBLISH_OBJECT_DEFINITION);

		return objectDefinitionLocalService.publishSystemObjectDefinition(
			getUserId(), objectDefinitionId);
	}

	@Override
	public ObjectDefinition updateCustomObjectDefinition(
			String externalReferenceCode, long objectDefinitionId,
			long accountEntryRestrictedObjectFieldId,
			long descriptionObjectFieldId, long objectFolderId,
			long titleObjectFieldId, boolean accountEntryRestricted,
			boolean active, boolean enableCategorization,
			boolean enableComments, boolean enableLocalization,
			boolean enableObjectEntryDraft, boolean enableObjectEntryHistory,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, boolean portlet,
			Map<Locale, String> pluralLabelMap, String scope)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		_objectFolderModelResourcePermission.check(
			getPermissionChecker(), objectFolderId,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return objectDefinitionLocalService.updateCustomObjectDefinition(
			externalReferenceCode, objectDefinitionId,
			accountEntryRestrictedObjectFieldId, descriptionObjectFieldId,
			objectFolderId, titleObjectFieldId, accountEntryRestricted, active,
			enableCategorization, enableComments, enableLocalization,
			enableObjectEntryDraft, enableObjectEntryHistory, labelMap, name,
			panelAppOrder, panelCategoryKey, portlet, pluralLabelMap, scope);
	}

	@Override
	public ObjectDefinition updateExternalReferenceCode(
			long objectDefinitionId, String externalReferenceCode)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectDefinitionLocalService.updateExternalReferenceCode(
			objectDefinitionId, externalReferenceCode);
	}

	@Override
	public ObjectDefinition updateRootObjectDefinitionId(
			long objectDefinitionId, long rootObjectDefinitionId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectDefinitionLocalService.updateRootObjectDefinitionId(
			objectDefinitionId, rootObjectDefinitionId);
	}

	@Override
	public ObjectDefinition updateSystemObjectDefinition(
			String externalReferenceCode, long objectDefinitionId,
			long objectFolderId, long titleObjectFieldId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		_objectFolderModelResourcePermission.check(
			getPermissionChecker(), objectFolderId,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return objectDefinitionLocalService.updateSystemObjectDefinition(
			externalReferenceCode, objectDefinitionId, objectFolderId,
			titleObjectFieldId);
	}

	@Override
	public ObjectDefinition updateTitleObjectFieldId(
			long objectDefinitionId, long titleObjectFieldId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinitionId, titleObjectFieldId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectFolder)"
	)
	private ModelResourcePermission<ObjectFolder>
		_objectFolderModelResourcePermission;

	@Reference(target = "(resource.name=" + ObjectConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}