/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.model.listener;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(service = ModelListener.class)
public class MBMessageModelListener extends BaseModelListener<MBMessage> {

	@Override
	public void onAfterCreate(MBMessage mbMessage)
		throws ModelListenerException {

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					mbMessage.getCompanyId(), mbMessage.getClassName());

			if ((objectDefinition == null) ||
				(!Objects.equals(
					objectDefinition.getExternalReferenceCode(),
					"L_CMP_PROJECT") &&
				 !Objects.equals(
					 objectDefinition.getExternalReferenceCode(),
					 "L_CMP_TASK"))) {

				return;
			}

			ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
				mbMessage.getClassPK());

			if (objectEntry == null) {
				return;
			}

			Message message = new Message();

			message.put("objectEntry", objectEntry);

			_messageBus.sendMessage(
				DestinationNames.CMP_COMMENT_ADDED, message);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private MessageBus _messageBus;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}