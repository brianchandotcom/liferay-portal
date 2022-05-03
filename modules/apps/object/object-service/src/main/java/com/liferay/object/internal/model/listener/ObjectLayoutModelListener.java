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
@Component(immediate = true, service = ModelListener.class)
public class ObjectLayoutModelListener extends BaseModelListener<ObjectLayout> {

	@Override
	public void onBeforeCreate(ObjectLayout objectLayout)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ADD, objectLayout);
	}

	@Override
	public void onBeforeRemove(ObjectLayout objectLayout)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.DELETE, objectLayout);
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

	protected void auditOnCreateOrRemove(
			String eventType, ObjectLayout objectLayout)
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

	private List<Attribute> _getModifiedAttributes(
		ObjectLayout originalObjectLayout, ObjectLayout objectLayout) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectLayout, originalObjectLayout);

		attributesBuilder.add("defaultObjectLayout");
		attributesBuilder.add("nameMap");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

}