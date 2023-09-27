/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.util.Attribute;
import com.liferay.portal.security.audit.event.generators.util.AttributesBuilder;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(service = ModelListener.class)
public class ObjectFieldSettingModelListener
	extends BaseModelListener<ObjectFieldSetting> {

	@Override
	public void onBeforeCreate(ObjectFieldSetting objectFieldSetting)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectFieldSetting);
	}

	@Override
	public void onBeforeRemove(ObjectFieldSetting objectFieldSetting)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectFieldSetting);
	}

	@Override
	public void onBeforeUpdate(
			ObjectFieldSetting originalOObjectFieldSetting,
			ObjectFieldSetting objectFieldSetting)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectFieldSetting.class.getName(),
					objectFieldSetting.getObjectFieldSettingId(),
					_getModifiedAttributes(
						originalOObjectFieldSetting, objectFieldSetting)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectFieldSetting originalObjectFieldSetting,
		ObjectFieldSetting objectFieldSetting) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectFieldSetting, originalObjectFieldSetting);

		attributesBuilder.add("objectFieldId");
		attributesBuilder.add("name");
		attributesBuilder.add("value");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectFieldSetting objectFieldSetting)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectFieldSetting.class.getName(),
				objectFieldSetting.getObjectFieldSettingId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"name", objectFieldSetting.getName()
			).put(
				"objectFieldId", objectFieldSetting.getObjectFieldId()
			).put(
				"value", objectFieldSetting.getValue()
			);

			_auditRouter.route(auditMessage);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private AuditRouter _auditRouter;

}