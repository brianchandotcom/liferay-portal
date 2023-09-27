/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectView;
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
public class ObjectViewModelListener extends BaseModelListener<ObjectView> {

	@Override
	public void onBeforeCreate(ObjectView objectView)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectView);
	}

	@Override
	public void onBeforeRemove(ObjectView objectView)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectView);
	}

	@Override
	public void onBeforeUpdate(
			ObjectView originalObjectView, ObjectView objectView)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectView.class.getName(),
					objectView.getObjectViewId(),
					_getModifiedAttributes(originalObjectView, objectView)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectView originalObjectView, ObjectView objectView) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectView, originalObjectView);

		attributesBuilder.add("defaultObjectView");
		attributesBuilder.add("nameMap");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectView objectView)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectView.class.getName(),
				objectView.getObjectViewId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"defaultObjectView", objectView.isDefaultObjectView()
			).put(
				"nameMap", objectView.getNameMap()
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