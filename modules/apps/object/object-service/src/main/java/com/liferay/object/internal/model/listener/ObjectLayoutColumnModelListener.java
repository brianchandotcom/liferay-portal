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
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
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

		auditObjectLayoutColumn(
			null, objectLayoutColumn.getObjectLayoutColumnId(), EventTypes.ADD);
	}

	@Override
	public void onBeforeRemove(ObjectLayoutColumn objectLayoutColumn)
		throws ModelListenerException {

		auditObjectLayoutColumn(
			null, objectLayoutColumn.getObjectLayoutColumnId(),
			EventTypes.DELETE);
	}

	@Override
	public void onBeforeUpdate(
			ObjectLayoutColumn originalObjectLayoutColumn,
			ObjectLayoutColumn objectLayoutColumn)
		throws ModelListenerException {

		auditObjectLayoutColumn(
			_getModifiedAttributes(
				originalObjectLayoutColumn, objectLayoutColumn),
			objectLayoutColumn.getObjectLayoutColumnId(), EventTypes.UPDATE);
	}

	protected void auditObjectLayoutColumn(
			List<Attribute> attributes, long classPK, String eventType)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					eventType, ObjectLayoutColumn.class.getName(), classPK,
					attributes));
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