/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectLayoutTab;
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
public class ObjectLayoutTabModelListener
	extends BaseModelListener<ObjectLayoutTab> {

	@Override
	public void onBeforeCreate(ObjectLayoutTab objectLayoutTab)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectLayoutTab);
	}

	@Override
	public void onBeforeRemove(ObjectLayoutTab objectLayoutTab)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectLayoutTab);
	}

	@Override
	public void onBeforeUpdate(
			ObjectLayoutTab originalObjectLayoutTab,
			ObjectLayoutTab objectLayoutTab)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectLayoutTab.class.getName(),
					objectLayoutTab.getObjectLayoutTabId(),
					_getModifiedAttributes(
						originalObjectLayoutTab, objectLayoutTab)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectLayoutTab originalObjectLayoutTab,
		ObjectLayoutTab objectLayoutTab) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectLayoutTab, originalObjectLayoutTab);

		attributesBuilder.add("nameMap");
		attributesBuilder.add("objectLayoutId");
		attributesBuilder.add("objectRelationshipId");
		attributesBuilder.add("priority");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectLayoutTab objectLayoutTab)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectLayoutTab.class.getName(),
				objectLayoutTab.getObjectLayoutTabId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"nameMap", objectLayoutTab.getNameMap()
			).put(
				"objectLayoutId", objectLayoutTab.getObjectLayoutId()
			).put(
				"objectRelationshipId",
				objectLayoutTab.getObjectRelationshipId()
			).put(
				"priority", objectLayoutTab.getPriority()
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