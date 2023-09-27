/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectViewColumn;
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
public class ObjectViewColumnModelListener
	extends BaseModelListener<ObjectViewColumn> {

	@Override
	public void onBeforeCreate(ObjectViewColumn objectViewColumn)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectViewColumn);
	}

	@Override
	public void onBeforeRemove(ObjectViewColumn objectViewColumn)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectViewColumn);
	}

	@Override
	public void onBeforeUpdate(
			ObjectViewColumn originalObjectViewColumn,
			ObjectViewColumn objectViewColumn)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectViewColumn.class.getName(),
					objectViewColumn.getObjectViewColumnId(),
					_getModifiedAttributes(
						originalObjectViewColumn, objectViewColumn)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectViewColumn originalObjectViewColumn,
		ObjectViewColumn objectViewColumn) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectViewColumn, originalObjectViewColumn);

		attributesBuilder.add("labelMap");
		attributesBuilder.add("objectFieldName");
		attributesBuilder.add("priority");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectViewColumn objectViewColumn)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectViewColumn.class.getName(),
				objectViewColumn.getObjectViewColumnId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"labelMap", objectViewColumn.getLabelMap()
			).put(
				"objectFieldName", objectViewColumn.getObjectFieldName()
			).put(
				"priority", objectViewColumn.getPriority()
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