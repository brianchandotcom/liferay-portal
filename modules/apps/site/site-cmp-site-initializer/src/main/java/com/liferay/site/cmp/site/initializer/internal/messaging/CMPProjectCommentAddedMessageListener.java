/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.messaging;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.entry.util.ObjectEntryPayloadUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(
	property = "destination.name=" + DestinationNames.CMP_PROJECT_COMMENT_ADDED,
	service = MessageListener.class
)
public class CMPProjectCommentAddedMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		ObjectEntry objectEntry = (ObjectEntry)message.get("objectEntry");

		if (objectEntry == null) {
			return;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());
		User user = _userLocalService.getUser(objectEntry.getUserId());

		_objectActionEngine.executeObjectActions(
			objectDefinition.getClassName(), objectEntry.getCompanyId(),
			DestinationNames.CMP_PROJECT_COMMENT_ADDED,
			() -> ObjectEntryPayloadUtil.getPayloadJSONObject(
				_dtoConverterRegistry, _jsonFactory,
				DestinationNames.CMP_PROJECT_COMMENT_ADDED, objectDefinition,
				objectEntry, null, null, user),
			objectEntry.getUserId());
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}