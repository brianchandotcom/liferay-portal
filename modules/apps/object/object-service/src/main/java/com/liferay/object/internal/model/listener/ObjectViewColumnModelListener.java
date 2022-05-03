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
@Component(immediate = true, service = ModelListener.class)
public class ObjectViewColumnModelListener
	extends BaseModelListener<ObjectViewColumn> {

	@Override
	public void onBeforeCreate(ObjectViewColumn objectViewColumn)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ADD, objectViewColumn);
	}

	@Override
	public void onBeforeRemove(ObjectViewColumn objectViewColumn)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.DELETE, objectViewColumn);
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

	protected void auditOnCreateOrRemove(
			String eventType, ObjectViewColumn objectViewColumn)
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

	@Reference
	private AuditRouter _auditRouter;

}