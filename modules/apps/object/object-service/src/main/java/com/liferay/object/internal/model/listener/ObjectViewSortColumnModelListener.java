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

import com.liferay.object.model.ObjectViewSortColumn;
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
public class ObjectViewSortColumnModelListener
	extends BaseModelListener<ObjectViewSortColumn> {

	@Override
	public void onBeforeCreate(ObjectViewSortColumn objectViewSortColumn)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ADD, objectViewSortColumn);
	}

	@Override
	public void onBeforeRemove(ObjectViewSortColumn objectViewSortColumn)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.DELETE, objectViewSortColumn);
	}

	@Override
	public void onBeforeUpdate(
			ObjectViewSortColumn originalObjectViewSortColumn,
			ObjectViewSortColumn objectViewSortColumn)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectViewSortColumn.class.getName(),
					objectViewSortColumn.getObjectViewSortColumnId(),
					_getModifiedAttributes(
						originalObjectViewSortColumn, objectViewSortColumn)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	protected void auditOnCreateOrRemove(
			String eventType, ObjectViewSortColumn objectViewSortColumn)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectViewSortColumn.class.getName(),
				objectViewSortColumn.getObjectViewSortColumnId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"objectFieldName", objectViewSortColumn.getObjectFieldName()
			).put(
				"priority", objectViewSortColumn.getPriority()
			).put(
				"sortOrder", objectViewSortColumn.getSortOrder()
			);

			_auditRouter.route(auditMessage);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectViewSortColumn originalObjectViewSortColumn,
		ObjectViewSortColumn objectViewSortColumn) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectViewSortColumn, originalObjectViewSortColumn);

		attributesBuilder.add("objectFieldName");
		attributesBuilder.add("priority");
		attributesBuilder.add("sortOrder");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

}