/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectAction;
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
public class ObjectActionModelListener extends BaseModelListener<ObjectAction> {

	@Override
	public void onBeforeCreate(ObjectAction objectAction)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectAction);
	}

	@Override
	public void onBeforeRemove(ObjectAction objectAction)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectAction);
	}

	@Override
	public void onBeforeUpdate(
			ObjectAction originalObjectAction, ObjectAction objectAction)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectAction.class.getName(),
					objectAction.getObjectActionId(),
					_getModifiedAttributes(
						originalObjectAction, objectAction)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectAction originalObjectAction, ObjectAction objectAction) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectAction, originalObjectAction);

		attributesBuilder.add("active");
		attributesBuilder.add("name");
		attributesBuilder.add("parameters");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectAction objectAction)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectAction.class.getName(),
				objectAction.getObjectActionId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"active", objectAction.isActive()
			).put(
				"conditionExpression", objectAction.getConditionExpression()
			).put(
				"name", objectAction.getName()
			).put(
				"parameters", objectAction.getParameters()
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