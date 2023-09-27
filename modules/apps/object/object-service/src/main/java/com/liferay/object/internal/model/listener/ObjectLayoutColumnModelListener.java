/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectLayoutColumn;
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
public class ObjectLayoutColumnModelListener
	extends BaseModelListener<ObjectLayoutColumn> {

	@Override
	public void onBeforeCreate(ObjectLayoutColumn objectLayoutColumn)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectLayoutColumn);
	}

	@Override
	public void onBeforeRemove(ObjectLayoutColumn objectLayoutColumn)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectLayoutColumn);
	}

	@Override
	public void onBeforeUpdate(
			ObjectLayoutColumn originalObjectLayoutColumn,
			ObjectLayoutColumn objectLayoutColumn)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectLayoutColumn.class.getName(),
					objectLayoutColumn.getObjectLayoutColumnId(),
					_getModifiedAttributes(
						originalObjectLayoutColumn, objectLayoutColumn)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectLayoutColumn originalObjectLayoutColumn,
		ObjectLayoutColumn objectLayoutColumn) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectLayoutColumn, originalObjectLayoutColumn);

		attributesBuilder.add("objectFieldId");
		attributesBuilder.add("objectLayoutRowId");
		attributesBuilder.add("priority");
		attributesBuilder.add("size");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectLayoutColumn objectLayoutColumn)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectLayoutColumn.class.getName(),
				objectLayoutColumn.getObjectLayoutColumnId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"objectFieldId", objectLayoutColumn.getObjectFieldId()
			).put(
				"objectLayoutRowId", objectLayoutColumn.getObjectLayoutRowId()
			).put(
				"priority", objectLayoutColumn.getPriority()
			).put(
				"size", objectLayoutColumn.getSize()
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