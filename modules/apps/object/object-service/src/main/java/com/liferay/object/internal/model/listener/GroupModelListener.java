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

package com.liferay.object.internal.model.listener;

import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
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

		boolean disassociateRelatedModels =
			ObjectEntryThreadLocal.isDisassociateRelatedModels();

		try {
			ObjectEntryThreadLocal.setDisassociateRelatedModels(true);

			actionableDynamicQuery.performActions();
		}
		finally {
			ObjectEntryThreadLocal.setDisassociateRelatedModels(
				disassociateRelatedModels);
		}
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}