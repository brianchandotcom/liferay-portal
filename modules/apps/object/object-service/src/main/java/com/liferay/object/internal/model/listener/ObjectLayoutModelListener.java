/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectLayout;
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
public class ObjectLayoutModelListener extends BaseModelListener<ObjectLayout> {

	@Override
	public void onBeforeCreate(ObjectLayout objectLayout)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectLayout);
	}

	@Override
	public void onBeforeRemove(ObjectLayout objectLayout)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectLayout);
	}

	@Override
	public void onBeforeUpdate(
			ObjectLayout originalObjectLayout, ObjectLayout objectLayout)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectLayout.class.getName(),
					objectLayout.getObjectLayoutId(),
					_getModifiedAttributes(
						originalObjectLayout, objectLayout)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectLayout originalObjectLayout, ObjectLayout objectLayout) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectLayout, originalObjectLayout);

		attributesBuilder.add("defaultObjectLayout");
		attributesBuilder.add("nameMap");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectLayout objectLayout)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectLayout.class.getName(),
				objectLayout.getObjectLayoutId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"defaultObjectLayout", objectLayout.isDefaultObjectLayout()
			).put(
				"nameMap", objectLayout.getNameMap()
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