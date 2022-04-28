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

import com.liferay.object.model.ObjectLayoutRow;
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
public class ObjectLayoutRowModelListener
	extends BaseModelListener<ObjectLayoutRow> {

	@Override
	public void onBeforeCreate(ObjectLayoutRow objectLayoutRow)
		throws ModelListenerException {

		auditObjectLayoutRow(
			null, objectLayoutRow.getObjectLayoutRowId(), EventTypes.ADD);
	}

	@Override
	public void onBeforeRemove(ObjectLayoutRow objectLayoutRow)
		throws ModelListenerException {

		auditObjectLayoutRow(
			null, objectLayoutRow.getObjectLayoutRowId(), EventTypes.DELETE);
	}

	@Override
	public void onBeforeUpdate(
			ObjectLayoutRow originalObjectLayoutRow,
			ObjectLayoutRow objectLayoutRow)
		throws ModelListenerException {

		auditObjectLayoutRow(
			_getModifiedAttributes(originalObjectLayoutRow, objectLayoutRow),
			objectLayoutRow.getObjectLayoutRowId(), EventTypes.UPDATE);
	}

	protected void auditObjectLayoutRow(
			List<Attribute> attributes, long classPK, String eventType)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					eventType, ObjectLayoutRow.class.getName(), classPK,
					attributes));
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

	@Reference
	private AuditRouter _auditRouter;

}