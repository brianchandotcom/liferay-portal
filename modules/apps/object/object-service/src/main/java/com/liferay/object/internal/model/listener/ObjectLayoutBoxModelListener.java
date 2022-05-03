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

import com.liferay.object.model.ObjectLayoutBox;
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
public class ObjectLayoutBoxModelListener
	extends BaseModelListener<ObjectLayoutBox> {

	@Override
	public void onBeforeCreate(ObjectLayoutBox objectLayoutBox)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ADD, objectLayoutBox);
	}

	@Override
	public void onBeforeRemove(ObjectLayoutBox objectLayoutBox)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.DELETE, objectLayoutBox);
	}

	@Override
	public void onBeforeUpdate(
			ObjectLayoutBox originalObjectLayoutBox,
			ObjectLayoutBox objectLayoutBox)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectLayoutBox.class.getName(),
					objectLayoutBox.getObjectLayoutBoxId(),
					_getModifiedAttributes(
						originalObjectLayoutBox, objectLayoutBox)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	protected void auditOnCreateOrRemove(
			String eventType, ObjectLayoutBox objectLayoutBox)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectLayoutBox.class.getName(),
				objectLayoutBox.getObjectLayoutBoxId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"collapsable", objectLayoutBox.isCollapsable()
			).put(
				"nameMap", objectLayoutBox.getNameMap()
			).put(
				"objectLayoutTabId", objectLayoutBox.getObjectLayoutTabId()
			).put(
				"priority", objectLayoutBox.getPriority()
			);

			_auditRouter.route(auditMessage);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectLayoutBox originalObjectLayoutBox,
		ObjectLayoutBox objectLayoutBox) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectLayoutBox, originalObjectLayoutBox);

		attributesBuilder.add("collapsable");
		attributesBuilder.add("nameMap");
		attributesBuilder.add("objectLayoutTabId");
		attributesBuilder.add("priority");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

}