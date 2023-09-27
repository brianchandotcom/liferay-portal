/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ModelListener.class)
public class ObjectFieldModelListener extends BaseModelListener<ObjectField> {

	@Override
	public void onBeforeCreate(ObjectField objectField)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectField);
	}

	@Override
	public void onBeforeRemove(ObjectField objectField)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectField);
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

	private void _route(String eventType, ObjectField objectField)
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

	@Reference
	private AuditRouter _auditRouter;

}