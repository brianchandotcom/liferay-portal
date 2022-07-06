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

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(immediate = true, service = ModelListener.class)
public class ListTypeEntryModelListener
	extends BaseModelListener<ListTypeEntry> {

	@Override
	public void onAfterCreate(ListTypeEntry listTypeEntry)
		throws ModelListenerException {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getListTypeDefinitionObjectFields(
				listTypeEntry.getListTypeDefinitionId(), true);

		if (objectFields.isEmpty()) {
			return;
		}

		for (ObjectField objectField : objectFields) {
			ObjectStateFlow objectStateFlow =
				_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
					objectField.getObjectFieldId());

			try {
				_objectStateLocalService.addObjectState(
					listTypeEntry.getUserId(),
					listTypeEntry.getListTypeEntryId(),
					objectStateFlow.getObjectStateFlowId());
			}
			catch (PortalException portalException) {
				throw new ModelListenerException(portalException);
			}
		}
	}

	@Override
	public void onAfterRemove(ListTypeEntry listTypeEntry)
		throws ModelListenerException {

		_objectStateLocalService.deleteListTypeEntryObjectStates(
			listTypeEntry.getListTypeEntryId());
	}

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

}