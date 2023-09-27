/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectLayoutRow;
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
public class ObjectLayoutRowModelListener
	extends BaseModelListener<ObjectLayoutRow> {

	@Override
	public void onBeforeCreate(ObjectLayoutRow objectLayoutRow)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectLayoutRow);
	}

	@Override
	public void onBeforeRemove(ObjectLayoutRow objectLayoutRow)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectLayoutRow);
	}

	@Override
	public void onBeforeUpdate(
			ObjectLayoutRow originalObjectLayoutRow,
			ObjectLayoutRow objectLayoutRow)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectLayoutRow.class.getName(),
					objectLayoutRow.getObjectLayoutRowId(),
					_getModifiedAttributes(
						originalObjectLayoutRow, objectLayoutRow)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectLayoutRow originalObjectLayoutRow,
		ObjectLayoutRow objectLayoutRow) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectLayoutRow, originalObjectLayoutRow);

		attributesBuilder.add("objectLayoutBoxId");
		attributesBuilder.add("priority");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectLayoutRow objectLayoutRow)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectLayoutRow.class.getName(),
				objectLayoutRow.getObjectLayoutRowId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"objectLayoutBoxId", objectLayoutRow.getObjectLayoutBoxId()
			).put(
				"priority", objectLayoutRow.getPriority()
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