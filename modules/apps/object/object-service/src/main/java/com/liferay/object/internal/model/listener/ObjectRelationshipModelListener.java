/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectRelationship;
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
public class ObjectRelationshipModelListener
	extends BaseModelListener<ObjectRelationship> {

	@Override
	public void onBeforeCreate(ObjectRelationship objectRelationship)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectRelationship);
	}

	@Override
	public void onBeforeRemove(ObjectRelationship objectRelationship)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectRelationship);
	}

	@Override
	public void onBeforeUpdate(
			ObjectRelationship originalObjectRelationship,
			ObjectRelationship objectRelationship)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectRelationship.class.getName(),
					objectRelationship.getObjectRelationshipId(),
					_getModifiedAttributes(
						originalObjectRelationship, objectRelationship)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectRelationship originalObjectRelationship,
		ObjectRelationship objectRelationship) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectRelationship, originalObjectRelationship);

		attributesBuilder.add("deletionType");
		attributesBuilder.add("labelMap");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectRelationship objectRelationship)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectRelationship.class.getName(),
				objectRelationship.getObjectRelationshipId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"deletionType", objectRelationship.getDeletionType()
			).put(
				"labelMap", objectRelationship.getLabelMap()
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