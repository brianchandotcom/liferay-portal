/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.model.listener;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = ModelListener.class)
public class ObjectEntryModelListener extends BaseModelListener<ObjectEntry> {

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_setResourcePermissions(objectEntry);
			_updateGroup(objectEntry);
			_updateProjectCompletionRate(objectEntry);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_updateProjectCompletionRate(objectEntry);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_updateGroup(objectEntry);
			_updateProjectCompletionRate(objectEntry);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private String[] _getAssetLibraryContentReviewerActionIds(
		ObjectDefinition objectDefinition) {

		if (StringUtil.equals(
				objectDefinition.getExternalReferenceCode(), "L_CMP_TASK")) {

			return new String[] {
				ActionKeys.ADD_DISCUSSION, ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW
			};
		}

		return new String[] {ActionKeys.ADD_DISCUSSION, ActionKeys.VIEW};
	}

	private JSONObject _getCMPDefaultPermissionJSONObject(
		ObjectDefinition objectDefinition) {

		String[] actionIds = TransformUtil.transformToArray(
			_resourceActionLocalService.getResourceActions(
				objectDefinition.getClassName()),
			ResourceAction::getActionId, String.class);

		return JSONUtil.put(
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR, actionIds
		).put(
			DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER,
			_getAssetLibraryContentReviewerActionIds(objectDefinition)
		).put(
			DepotRolesConstants.ASSET_LIBRARY_MEMBER,
			new String[] {ActionKeys.ADD_DISCUSSION, ActionKeys.VIEW}
		).put(
			RoleConstants.CMS_ADMINISTRATOR, actionIds
		).put(
			RoleConstants.OWNER, actionIds
		);
	}

	private int _getCount(
			String filterString, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry)
		throws Exception {

		return _objectEntryLocalService.getValuesListCount(
			new Long[] {objectEntry.getGroupId()}, 0, 0,
			objectEntry.getObjectDefinitionId(),
			_filterFactory.create(filterString, objectDefinition), false, null);
	}

	private void _setResourcePermissions(ObjectEntry objectEntry)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!StringUtil.equals(
				objectDefinition.getExternalReferenceCode(), "L_CMP_PROJECT") &&
			!StringUtil.equals(
				objectDefinition.getExternalReferenceCode(), "L_CMP_TASK")) {

			return;
		}

		JSONObject defaultPermissionsJSONObject =
			_getCMPDefaultPermissionJSONObject(
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectEntry.getObjectDefinitionId()));

		List<Role> roles = _roleLocalService.getGroupRolesAndTeamRoles(
			objectEntry.getCompanyId(), null,
			Arrays.asList(
				RoleConstants.ADMINISTRATOR,
				DepotRolesConstants.ASSET_LIBRARY_OWNER),
			null, null,
			new int[] {RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_DEPOT}, 0,
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Role role : roles) {
			String[] actionIds = (String[])defaultPermissionsJSONObject.get(
				role.getName());

			if (actionIds == null) {
				actionIds = new String[0];
			}

			_resourcePermissionLocalService.setResourcePermissions(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				role.getRoleId(), actionIds);
		}
	}

	private void _updateGroup(ObjectEntry objectEntry) {
		if (!FeatureFlagManagerUtil.isEnabled(
				objectEntry.getCompanyId(), "LPD-58677")) {

			return;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!StringUtil.equals(
				objectDefinition.getExternalReferenceCode(), "L_CMP_PROJECT")) {

			return;
		}

		String title = MapUtil.getString(objectEntry.getValues(), "title");

		if (Validator.isNull(title)) {
			return;
		}

		Group group = _groupLocalService.fetchGroup(objectEntry.getGroupId());

		if (group == null) {
			return;
		}

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			group.getDefaultLanguageId());

		if (StringUtil.equals(group.getName(defaultLocale), title)) {
			return;
		}

		group.setName(title, defaultLocale);

		_groupLocalService.updateGroup(group);
	}

	private void _updateProjectCompletionRate(ObjectEntry objectEntry)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!StringUtil.equals(
				objectDefinition.getExternalReferenceCode(), "L_CMP_TASK")) {

			return;
		}

		ObjectEntry parentObjectEntry =
			_objectEntryLocalService.fetchObjectEntry(
				MapUtil.getLong(
					objectEntry.getValues(),
					"r_cmpProjectToCMPTasks_c_cmpProjectId"));

		if (parentObjectEntry == null) {
			return;
		}

		int totalCount = _getCount(null, objectDefinition, objectEntry);

		int completionRate = 0;

		if (totalCount != 0) {
			int filteredCount = _getCount(
				"state eq 'done'", objectDefinition, objectEntry);

			completionRate = (filteredCount * 100) / totalCount;
		}

		if (Objects.equals(
				MapUtil.getInteger(
					parentObjectEntry.getValues(), "completionRate"),
				completionRate)) {

			return;
		}

		_objectEntryLocalService.partialUpdateObjectEntry(
			parentObjectEntry.getUserId(), parentObjectEntry.getObjectEntryId(),
			parentObjectEntry.getObjectEntryFolderId(),
			HashMapBuilder.<String, Serializable>put(
				"completionRate", completionRate
			).build(),
			new ServiceContext());
	}

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}