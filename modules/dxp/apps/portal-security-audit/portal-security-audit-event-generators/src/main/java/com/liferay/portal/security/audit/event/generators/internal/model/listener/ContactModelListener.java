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

package com.liferay.portal.security.audit.event.generators.internal.model.listener;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.security.audit.event.generators.internal.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.internal.model.listener.util.Attribute;
import com.liferay.portal.security.audit.event.generators.internal.model.listener.util.AttributesBuilder;
import com.liferay.portal.security.audit.event.generators.internal.model.listener.util.AuditMessageBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ModelListener.class)
public class ContactModelListener extends BaseModelListener<Contact> {

	public void onBeforeUpdate(Contact newContact)
		throws ModelListenerException {

		try {
			Contact oldContact = _contactLocalService.getContact(
				newContact.getContactId());

			List<Attribute> attributes = getModifiedAttributes(
				newContact, oldContact);

			if (!attributes.isEmpty()) {
				AuditMessage auditMessage =
					AuditMessageBuilder.buildAuditMessage(
						EventTypes.UPDATE, User.class.getName(),
						newContact.getClassPK(), attributes);

				_auditRouter.route(auditMessage);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected List<Attribute> getModifiedAttributes(
		Contact newContact, Contact oldContact) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			newContact, oldContact);

		//attributesBuilder.add("aimSn");
		//attributesBuilder.add("icqSn");
		//attributesBuilder.add("msnSn");
		//attributesBuilder.add("mySpaceSn");
		//attributesBuilder.add("ymSn");
		attributesBuilder.add("birthday");
		attributesBuilder.add("employeeNumber");
		attributesBuilder.add("employeeStatusId");
		attributesBuilder.add("facebookSn");
		attributesBuilder.add("firstName");
		attributesBuilder.add("hoursOfOperation");
		attributesBuilder.add("jabberSn");
		attributesBuilder.add("jobClass");
		attributesBuilder.add("jobTitle");
		attributesBuilder.add("lastName");
		attributesBuilder.add("male");
		attributesBuilder.add("middleName");
		attributesBuilder.add("prefixId");
		attributesBuilder.add("skypeSn");
		attributesBuilder.add("smsSn");
		attributesBuilder.add("suffixId");
		attributesBuilder.add("twitterSn");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private ContactLocalService _contactLocalService;

}