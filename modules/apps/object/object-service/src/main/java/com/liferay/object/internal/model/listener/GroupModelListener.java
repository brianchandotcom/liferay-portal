/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge García Jiménez
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		ActionableDynamicQuery actionableDynamicQuery =
			_objectDefinitionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("companyId", group.getCompanyId())
			).add(
				RestrictionsFactoryUtil.eq("active", true)
			).add(
				RestrictionsFactoryUtil.eq("system", false)
			).add(
				RestrictionsFactoryUtil.eq(
					"status", WorkflowConstants.STATUS_APPROVED)
			));
		actionableDynamicQuery.setPerformActionMethod(
			(ObjectDefinition objectDefinition) -> _deleteObjectEntries(
				group.getGroupId(), objectDefinition.getObjectDefinitionId()));

		try {
			actionableDynamicQuery.performActions();

			if (group.isDepot()) {
				_updateObjectDefinitionSettings(group);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _deleteObjectEntries(long groupId, long objectDefinitionId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_objectEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.and(
					RestrictionsFactoryUtil.eq("groupId", groupId),
					RestrictionsFactoryUtil.eq(
						"objectDefinitionId", objectDefinitionId))));
		actionableDynamicQuery.setPerformActionMethod(
			(ObjectEntry objectEntry) ->
				_objectEntryLocalService.deleteObjectEntry(objectEntry));

		try (SafeCloseable safeCloseable =
				ObjectEntryThreadLocal.
					setDisassociateRelatedModelsWithSafeCloseable(true)) {

			actionableDynamicQuery.performActions();
		}
	}

	private void _updateObjectDefinitionSettings(Group group)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_objectDefinitionSettingLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("companyId", group.getCompanyId())
			).add(
				RestrictionsFactoryUtil.eq(
					"name",
					ObjectDefinitionSettingConstants.NAME_ACCEPTED_GROUP_IDS)
			));
		actionableDynamicQuery.setPerformActionMethod(
			(ObjectDefinitionSetting objectDefinitionSetting) -> {
				String oldAcceptedGroupIds = objectDefinitionSetting.getValue();

				String[] newAcceptedGroupIds = ArrayUtil.filter(
					oldAcceptedGroupIds.split("\\s*,\\s*"),
					groupId -> !StringUtil.equals(
						groupId, String.valueOf(group.getGroupId())));

				if (newAcceptedGroupIds.length == 0) {
					objectDefinitionSetting.setName(
						ObjectDefinitionSettingConstants.
							NAME_ACCEPT_ALL_GROUPS);
					objectDefinitionSetting.setValue(StringPool.TRUE);
				}
				else {
					objectDefinitionSetting.setValue(
						StringUtil.merge(newAcceptedGroupIds));
				}

				_objectDefinitionSettingLocalService.
					updateObjectDefinitionSetting(objectDefinitionSetting);
			});

		actionableDynamicQuery.performActions();
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}