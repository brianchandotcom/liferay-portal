/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.site.cms.site.initializer.util.CMSDefaultPermissionUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Stefano Motta
 */
@Component(service = ModelListener.class)
public class ObjectEntryFolderModelListener
	extends BaseModelListener<ObjectEntryFolder> {

	@Override
	public void onAfterCreate(ObjectEntryFolder objectEntryFolder)
		throws ModelListenerException {

		try {
			_onAfterCreate(objectEntryFolder);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterRemove(ObjectEntryFolder objectEntryFolder)
		throws ModelListenerException {

		try {
			_onAfterRemove(objectEntryFolder);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterUpdate(
			ObjectEntryFolder originalObjectEntryFolder,
			ObjectEntryFolder objectEntryFolder)
		throws ModelListenerException {

		try {
			if (!Objects.equals(
					originalObjectEntryFolder.getExternalReferenceCode(),
					objectEntryFolder.getExternalReferenceCode())) {

				_updateCMSDefaultPermissionObjectEntry(
					objectEntryFolder.getExternalReferenceCode(),
					objectEntryFolder,
					originalObjectEntryFolder.getExternalReferenceCode());
			}

			if (originalObjectEntryFolder.getParentObjectEntryFolderId() !=
					objectEntryFolder.getParentObjectEntryFolderId()) {

				_updateCMSDefaultPermissions(objectEntryFolder);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _addCMSDefaultPermissions(ObjectEntryFolder objectEntryFolder)
		throws Exception {

		JSONObject defaultPermissionsJSONObject =
			_getCMSDefaultPermissionJSONObject(objectEntryFolder);

		if ((defaultPermissionsJSONObject == null) ||
			JSONUtil.isEmpty(defaultPermissionsJSONObject)) {

			return;
		}

		if (!Objects.equals(
				objectEntryFolder.getExternalReferenceCode(),
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS) &&
			!Objects.equals(
				objectEntryFolder.getExternalReferenceCode(),
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES)) {

			CMSDefaultPermissionUtil.addOrUpdateObjectEntry(
				null, objectEntryFolder.getCompanyId(),
				objectEntryFolder.getUserId(),
				objectEntryFolder.getExternalReferenceCode(),
				objectEntryFolder.getModelClassName(),
				defaultPermissionsJSONObject, objectEntryFolder.getGroupId(),
				objectEntryFolder.getTreePath());
		}

		JSONObject objectEntryFoldersJSONObject =
			defaultPermissionsJSONObject.getJSONObject("OBJECT_ENTRY_FOLDERS");

		if (objectEntryFoldersJSONObject == null) {
			return;
		}

		List<String> resourceActions = ResourceActionsUtil.getResourceActions(
			ObjectEntryFolder.class.getName());

		List<Role> roles = _roleLocalService.getGroupRolesAndTeamRoles(
			objectEntryFolder.getCompanyId(), null,
			Arrays.asList(
				RoleConstants.ADMINISTRATOR,
				DepotRolesConstants.ASSET_LIBRARY_OWNER),
			null, null,
			new int[] {RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_DEPOT},
			null, 0, 0, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Role role : roles) {
			String[] actionIds = JSONUtil.toStringArray(
				objectEntryFoldersJSONObject.getJSONArray(role.getName()));

			if (objectEntryFolder.getParentObjectEntryFolderId() ==
					ObjectEntryFolderConstants.
						PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT) {

				actionIds = ArrayUtil.remove(actionIds, ActionKeys.DELETE);
			}

			_resourcePermissionLocalService.setResourcePermissions(
				objectEntryFolder.getCompanyId(),
				ObjectEntryFolder.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntryFolder.getObjectEntryFolderId()),
				role.getRoleId(),
				ArrayUtil.filter(actionIds, resourceActions::contains));
		}
	}

	private JSONObject _getCMSDefaultPermissionJSONObject(
			ObjectEntryFolder objectEntryFolder)
		throws Exception {

		ObjectDefinition cmsDefaultPermissionObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_CMS_DEFAULT_PERMISSION",
					objectEntryFolder.getCompanyId());

		if (cmsDefaultPermissionObjectDefinition == null) {
			return null;
		}

		if (objectEntryFolder.getParentObjectEntryFolderId() != 0) {
			ObjectEntryFolder parentObjectEntryFolder =
				_objectEntryFolderLocalService.getObjectEntryFolder(
					objectEntryFolder.getParentObjectEntryFolderId());

			JSONObject jsonObject = CMSDefaultPermissionUtil.getJSONObject(
				parentObjectEntryFolder.getCompanyId(),
				parentObjectEntryFolder.getUserId(),
				parentObjectEntryFolder.getExternalReferenceCode(),
				parentObjectEntryFolder.getModelClassName(), _filterFactory);

			if ((jsonObject != null) && !JSONUtil.isEmpty(jsonObject)) {
				return jsonObject;
			}
		}

		Group group = _groupLocalService.getGroup(
			objectEntryFolder.getGroupId());

		return CMSDefaultPermissionUtil.getJSONObject(
			group.getCompanyId(), group.getCreatorUserId(),
			group.getExternalReferenceCode(), DepotEntry.class.getName(),
			_filterFactory);
	}

	private void _onAfterCreate(ObjectEntryFolder objectEntryFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				objectEntryFolder.getCompanyId(), "LPD-17564")) {

			return;
		}

		_addCMSDefaultPermissions(objectEntryFolder);
	}

	private void _onAfterRemove(ObjectEntryFolder objectEntryFolder)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled(
				objectEntryFolder.getCompanyId(), "LPD-17564")) {

			return;
		}

		_sharingEntryLocalService.deleteSharingEntries(
			_portal.getClassNameId(ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId());

		ObjectDefinition cmsDefaultPermissionObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_CMS_DEFAULT_PERMISSION",
					objectEntryFolder.getCompanyId());

		if (cmsDefaultPermissionObjectDefinition == null) {
			return;
		}

		ObjectEntry objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder.getCompanyId(), objectEntryFolder.getUserId(),
			objectEntryFolder.getExternalReferenceCode(),
			objectEntryFolder.getModelClassName(), _filterFactory);

		if (objectEntry == null) {
			return;
		}

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry.getObjectEntryId());
	}

	private void _updateCMSDefaultPermissionObjectEntry(
			String externalReferenceCode, ObjectEntryFolder objectEntryFolder,
			String originalExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				objectEntryFolder.getCompanyId(), "LPD-17564")) {

			return;
		}

		ObjectEntry objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder.getCompanyId(), objectEntryFolder.getUserId(),
			originalExternalReferenceCode,
			objectEntryFolder.getModelClassName(), _filterFactory);

		if (objectEntry == null) {
			return;
		}

		Map<String, Serializable> values = objectEntry.getValues();

		CMSDefaultPermissionUtil.addOrUpdateObjectEntry(
			objectEntry.getExternalReferenceCode(),
			objectEntryFolder.getCompanyId(), objectEntryFolder.getUserId(),
			externalReferenceCode, objectEntryFolder.getModelClassName(),
			_jsonFactory.createJSONObject(
				GetterUtil.getString(values.get("defaultPermissions"), "{}")),
			GetterUtil.getLong(values.get("depotGroupId")),
			GetterUtil.getString(values.get("treePath")));
	}

	private void _updateCMSDefaultPermissions(
			ObjectEntryFolder objectEntryFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				objectEntryFolder.getCompanyId(), "LPD-17564")) {

			return;
		}

		JSONObject defaultPermissionsJSONObject =
			_getCMSDefaultPermissionJSONObject(objectEntryFolder);

		if ((defaultPermissionsJSONObject == null) ||
			JSONUtil.isEmpty(defaultPermissionsJSONObject)) {

			return;
		}

		ObjectEntry objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder.getCompanyId(), objectEntryFolder.getUserId(),
			objectEntryFolder.getExternalReferenceCode(),
			objectEntryFolder.getModelClassName(), _filterFactory);

		if (objectEntry != null) {
			_objectEntryLocalService.deleteObjectEntry(
				objectEntry.getObjectEntryId());
		}

		_addCMSDefaultPermissions(objectEntryFolder);
	}

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}