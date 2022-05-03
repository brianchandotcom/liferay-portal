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

import com.liferay.object.model.ObjectField;
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
public class ObjectFieldModelListener extends BaseModelListener<ObjectField> {

	@Override
	public void onBeforeCreate(ObjectField objectField)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ADD, objectField);
	}

	@Override
	public void onBeforeRemove(ObjectField objectField)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.DELETE, objectField);
	}

	@Override
	public void onBeforeUpdate(
			ObjectField originalObjectField, ObjectField objectField)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectField.class.getName(),
					objectField.getObjectFieldId(),
					_getModifiedAttributes(originalObjectField, objectField)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	protected void auditOnCreateOrRemove(
			String eventType, ObjectField objectField)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectField.class.getName(),
				objectField.getObjectFieldId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"businessType", objectField.getBusinessType()
			).put(
				"DBColumnName", objectField.getDBColumnName()
			).put(
				"DBType", objectField.getDBType()
			).put(
				"indexed", objectField.isIndexed()
			).put(
				"indexedAsKeyword", objectField.isIndexedAsKeyword()
			).put(
				"indexedLanguageId", objectField.getIndexedLanguageId()
			).put(
				"labelMap", objectField.getLabelMap()
			).put(
				"listTypeDefinitionId", objectField.getListTypeDefinitionId()
			).put(
				"name", objectField.getName()
			).put(
				"required", objectField.isRequired()
			);

			_auditRouter.route(auditMessage);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectField originalObjectField, ObjectField objectField) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectField, originalObjectField);

		attributesBuilder.add("businessType");
		attributesBuilder.add("DBColumnName");
		attributesBuilder.add("DBType");
		attributesBuilder.add("indexed");
		attributesBuilder.add("indexedAsKeyword");
		attributesBuilder.add("indexedLanguageId");
		attributesBuilder.add("labelMap");
		attributesBuilder.add("listTypeDefinitionId");
		attributesBuilder.add("name");
		attributesBuilder.add("required");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

}