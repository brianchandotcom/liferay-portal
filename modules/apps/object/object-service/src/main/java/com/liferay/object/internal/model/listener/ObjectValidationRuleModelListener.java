/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectValidationRule;
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
public class ObjectValidationRuleModelListener
	extends BaseModelListener<ObjectValidationRule> {

	@Override
	public void onBeforeCreate(ObjectValidationRule objectValidationRule)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectValidationRule);
	}

	@Override
	public void onBeforeRemove(ObjectValidationRule objectValidationRule)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectValidationRule);
	}

	@Override
	public void onBeforeUpdate(
			ObjectValidationRule originalObjectValidationRule,
			ObjectValidationRule objectValidationRule)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectValidationRule.class.getName(),
					objectValidationRule.getObjectValidationRuleId(),
					_getModifiedAttributes(
						originalObjectValidationRule, objectValidationRule)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectValidationRule originalObjectValidationRule,
		ObjectValidationRule objectValidationRule) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectValidationRule, originalObjectValidationRule);

		attributesBuilder.add("active");
		attributesBuilder.add("engine");
		attributesBuilder.add("errorLabelMap");
		attributesBuilder.add("nameMap");
		attributesBuilder.add("script");

		return attributesBuilder.getAttributes();
	}

	private void _route(
			String eventType, ObjectValidationRule objectValidationRule)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectValidationRule.class.getName(),
				objectValidationRule.getObjectValidationRuleId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"active", objectValidationRule.isActive()
			).put(
				"engine", objectValidationRule.getEngine()
			).put(
				"errorLabelMap", objectValidationRule.getErrorLabelMap()
			).put(
				"nameMap", objectValidationRule.getNameMap()
			).put(
				"script", objectValidationRule.getScript()
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