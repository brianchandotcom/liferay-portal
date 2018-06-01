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

package com.liferay.portal.security.audit.event.generators.user.management.internal.model.listener;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.util.Attribute;
import com.liferay.portal.security.audit.event.generators.util.AttributesBuilder;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = ModelListener.class)
public class OrganizationModelListener extends BaseModelListener<Organization> {

	@Override
	public void onBeforeAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		auditOnAddorRemoveAssociation(
			EventTypes.ASSIGN, classPK, associationClassName,
			associationClassPK);
	}

	public void onBeforeCreate(Organization organization)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ADD, organization);
	}

	public void onBeforeRemove(Organization organization)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.DELETE, organization);
	}

	@Override
	public void onBeforeRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		auditOnAddorRemoveAssociation(
			EventTypes.UNASSIGN, classPK, associationClassName,
			associationClassPK);
	}

	public void onBeforeUpdate(Organization newOrganization)
		throws ModelListenerException {

		try {
			Organization oldOrganization =
				_organizationLocalService.getOrganization(
					newOrganization.getOrganizationId());

			List<Attribute> attributes = getModifiedAttributes(
				newOrganization, oldOrganization);

			if (!attributes.isEmpty()) {
				AuditMessage auditMessage =
					AuditMessageBuilder.buildAuditMessage(
						EventTypes.UPDATE, Organization.class.getName(),
						newOrganization.getOrganizationId(), attributes);

				_auditRouter.route(auditMessage);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void auditOnAddorRemoveAssociation(
			String eventType, Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		if (!associationClassName.equals(User.class.getName())) {
			return;
		}

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, associationClassName, (Long)associationClassPK,
				null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			long organizationId = (Long)classPK;

			additionalInfoJSONObject.put("organizationId", organizationId);

			Organization organization =
				_organizationLocalService.getOrganization(organizationId);

			additionalInfoJSONObject.put(
				"organizationName", organization.getName());

			_auditRouter.route(auditMessage);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void auditOnCreateOrRemove(
			String eventType, Organization organization)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, Organization.class.getName(),
				organization.getOrganizationId(), null);

			_auditRouter.route(auditMessage);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected List<Attribute> getModifiedAttributes(
		Organization newOrganization, Organization oldOrganization) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			newOrganization, oldOrganization);

		attributesBuilder.add("comments");
		attributesBuilder.add("countryId");
		attributesBuilder.add("name");
		attributesBuilder.add("parentOrganizationId");
		attributesBuilder.add("regionId");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}