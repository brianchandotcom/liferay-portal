/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryVersion;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yuri Monteiro
 */
@Component(service = ModelListener.class)
public class ObjectEntryVersionModelListener
	extends BaseModelListener<ObjectEntryVersion> {

	@Override
	public void onAfterRemove(ObjectEntryVersion objectEntryVersion)
		throws ModelListenerException {

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntryVersion.getObjectDefinitionId());

			if (!objectDefinition.isEnableObjectEntryHistory()) {
				return;
			}

			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				EventTypes.DELETE, objectEntryVersion, null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			JSONObject contentJSONObject = _jsonFactory.createJSONObject(
				objectEntryVersion.getContent());

			JSONObject propertiesJSONObject = contentJSONObject.getJSONObject(
				"properties");

			for (String key : propertiesJSONObject.keySet()) {
				additionalInfoJSONObject.put(
					key, propertiesJSONObject.get(key));
			}

			_auditRouter.route(auditMessage);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}