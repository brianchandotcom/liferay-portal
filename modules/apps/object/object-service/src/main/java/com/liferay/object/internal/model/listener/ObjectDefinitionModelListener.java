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

import com.liferay.object.model.ObjectDefinition;
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
public class ObjectDefinitionModelListener
	extends BaseModelListener<ObjectDefinition> {

	@Override
	public void onBeforeCreate(ObjectDefinition objectDefinition)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ADD, objectDefinition);
	}

	@Override
	public void onBeforeRemove(ObjectDefinition objectDefinition)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.DELETE, objectDefinition);
	}

	@Override
	public void onBeforeUpdate(
			ObjectDefinition originalObjectDefinition,
			ObjectDefinition objectDefinition)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectDefinition.class.getName(),
					objectDefinition.getObjectDefinitionId(),
					_getModifiedAttributes(
						originalObjectDefinition, objectDefinition)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	protected void auditOnCreateOrRemove(
			String eventType, ObjectDefinition objectDefinition)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectDefinition.class.getName(),
				objectDefinition.getObjectDefinitionId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"active", objectDefinition.isActive()
			).put(
				"labelMap", objectDefinition.getLabelMap()
			).put(
				"name", objectDefinition.getName()
			).put(
				"scope", objectDefinition.getScope()
			);

			_auditRouter.route(auditMessage);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectDefinition originalObjectDefinition,
		ObjectDefinition objectDefinition) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectDefinition, originalObjectDefinition);

		attributesBuilder.add("active");
		attributesBuilder.add("descriptionObjectFieldId");
		attributesBuilder.add("labelMap");
		attributesBuilder.add("name");
		attributesBuilder.add("panelAppOrder");
		attributesBuilder.add("panelCategoryKey");
		attributesBuilder.add("pluralLabelMap");
		attributesBuilder.add("portlet");
		attributesBuilder.add("scope");
		attributesBuilder.add("titleObjectFieldId");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

}