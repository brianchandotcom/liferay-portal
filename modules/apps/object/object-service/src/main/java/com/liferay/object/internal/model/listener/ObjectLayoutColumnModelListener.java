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
@Component(immediate = true, service = ModelListener.class)
public class ObjectLayoutColumnModelListener
	extends BaseModelListener<ObjectLayoutColumn> {

	@Override
	public void onBeforeCreate(ObjectLayoutColumn objectLayoutColumn)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ADD, objectLayoutColumn);
	}

	@Override
	public void onBeforeRemove(ObjectLayoutColumn objectLayoutColumn)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.DELETE, objectLayoutColumn);
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

	protected void auditOnCreateOrRemove(
			String eventType, ObjectLayoutColumn objectLayoutColumn)
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

	@Reference
	private AuditRouter _auditRouter;

}