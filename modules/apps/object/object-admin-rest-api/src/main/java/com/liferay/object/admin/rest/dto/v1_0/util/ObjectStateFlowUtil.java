/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.dto.v1_0.util;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.admin.rest.dto.v1_0.ObjectStateFlow;
import com.liferay.object.admin.rest.dto.v1_0.ObjectStateTransition;
import com.liferay.object.model.ObjectState;
import com.liferay.object.service.ObjectStateLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Feliphe Marinho
 */
public class ObjectStateFlowUtil {

	public static ObjectStateFlow toObjectStateFlow(
		com.liferay.object.model.ObjectStateFlow objectStateFlow) {

		return new ObjectStateFlow() {
			{
				id = objectStateFlow.getObjectStateFlowId();
				objectStates = TransformUtil.transformToArray(
					ObjectStateLocalServiceUtil.getObjectStateFlowObjectStates(
						objectStateFlow.getObjectStateFlowId()),
					ObjectStateFlowUtil::_toObjectState,
					com.liferay.object.admin.rest.dto.v1_0.ObjectState.class);
			}
		};
	}

	private static com.liferay.object.admin.rest.dto.v1_0.ObjectState
			_toObjectState(ObjectState objectState)
		throws PortalException {

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.getListTypeEntry(
				objectState.getListTypeEntryId());

		return new com.liferay.object.admin.rest.dto.v1_0.ObjectState() {
			{
				id = objectState.getObjectStateId();
				key = listTypeEntry.getKey();
				objectStateTransitions = TransformUtil.transformToArray(
					ObjectStateLocalServiceUtil.getNextObjectStates(
						objectState.getObjectStateId()),
					ObjectStateFlowUtil::_toObjectStateTransition,
					ObjectStateTransition.class);
			}
		};
	}

	private static ObjectStateTransition _toObjectStateTransition(
			ObjectState objectState)
		throws PortalException {

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.getListTypeEntry(
				objectState.getListTypeEntryId());

		return new ObjectStateTransition() {
			{
				key = listTypeEntry.getKey();
			}
		};
	}

}