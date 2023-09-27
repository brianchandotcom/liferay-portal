/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.service.ObjectStateFlowLocalServiceUtil;
import com.liferay.object.service.ObjectStateLocalServiceUtil;
import com.liferay.object.service.ObjectStateTransitionLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Feliphe Marinho
 */
public class ObjectStateFlowUtil {

	public static ObjectStateFlow toObjectStateFlow(
			long listTypeDefinitionId,
			com.liferay.object.admin.rest.dto.v1_0.ObjectStateFlow
				objectStateFlow)
		throws PortalException {

		if (objectStateFlow == null) {
			return null;
		}

		ObjectStateFlow serviceBuilderObjectStateFlow =
			ObjectStateFlowLocalServiceUtil.createObjectStateFlow(0L);

		serviceBuilderObjectStateFlow.setObjectStateFlowId(
			GetterUtil.getLong(objectStateFlow.getId()));
		serviceBuilderObjectStateFlow.setObjectStates(
			TransformUtil.transformToList(
				objectStateFlow.getObjectStates(),
				objectState -> _toObjectState(
					listTypeDefinitionId, objectState,
					GetterUtil.getLong(objectStateFlow.getId()))));

		return serviceBuilderObjectStateFlow;
	}

	private static ObjectState _toObjectState(
			long listTypeDefinitionId,
			com.liferay.object.admin.rest.dto.v1_0.ObjectState objectState,
			long objectStateFlowId)
		throws PortalException {

		ObjectState serviceBuilderObjectState =
			ObjectStateLocalServiceUtil.createObjectState(0L);

		serviceBuilderObjectState.setObjectStateId(
			GetterUtil.getLong(objectState.getId()));

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.getListTypeEntry(
				listTypeDefinitionId, objectState.getKey());

		serviceBuilderObjectState.setListTypeEntryId(
			listTypeEntry.getListTypeEntryId());

		serviceBuilderObjectState.setObjectStateFlowId(objectStateFlowId);
		serviceBuilderObjectState.setObjectStateTransitions(
			TransformUtil.transformToList(
				objectState.getObjectStateTransitions(),
				nextObjectState -> _toObjectStateTransition(
					listTypeDefinitionId, nextObjectState, objectStateFlowId,
					GetterUtil.getLong(objectState.getId()))));

		return serviceBuilderObjectState;
	}

	private static ObjectStateTransition _toObjectStateTransition(
			long listTypeDefinitionId,
			com.liferay.object.admin.rest.dto.v1_0.ObjectStateTransition
				objectStateTransition,
			long objectStateFlowId, long sourceObjectStateId)
		throws PortalException {

		ObjectStateTransition serviceBuilderObjectStateTransition =
			ObjectStateTransitionLocalServiceUtil.createObjectStateTransition(
				0L);

		serviceBuilderObjectStateTransition.setObjectStateFlowId(
			objectStateFlowId);
		serviceBuilderObjectStateTransition.setSourceObjectStateId(
			sourceObjectStateId);

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
				listTypeDefinitionId, objectStateTransition.getKey());

		if ((objectStateFlowId == 0) && (sourceObjectStateId == 0)) {
			serviceBuilderObjectStateTransition.
				setTargetObjectStateListTypeEntryId(
					listTypeEntry.getListTypeEntryId());

			return serviceBuilderObjectStateTransition;
		}

		ObjectState targetObjectState =
			ObjectStateLocalServiceUtil.getObjectStateFlowObjectState(
				listTypeEntry.getListTypeEntryId(), objectStateFlowId);

		serviceBuilderObjectStateTransition.setTargetObjectStateId(
			targetObjectState.getObjectStateId());

		return serviceBuilderObjectStateTransition;
	}

}