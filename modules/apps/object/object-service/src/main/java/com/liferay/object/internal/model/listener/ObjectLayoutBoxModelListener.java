/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ModelListener.class)
public class ObjectLayoutBoxModelListener
	extends BaseModelListener<ObjectLayoutBox> {

	@Override
	public void onBeforeCreate(ObjectLayoutBox objectLayoutBox)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectLayoutBox);
	}

	@Override
	public void onBeforeRemove(ObjectLayoutBox objectLayoutBox)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectLayoutBox);
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

	private void _route(String eventType, ObjectLayoutBox objectLayoutBox)
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

	@Reference
	private AuditRouter _auditRouter;

}