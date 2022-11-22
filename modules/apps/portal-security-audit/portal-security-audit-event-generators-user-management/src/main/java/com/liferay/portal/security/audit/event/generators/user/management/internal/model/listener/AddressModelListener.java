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
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.util.Attribute;
import com.liferay.portal.security.audit.event.generators.util.AttributesBuilder;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(service = ModelListener.class)
public class AddressModelListener extends BaseModelListener<Address> {

	public void onBeforeUpdate(Address originalAddress, Address address)
		throws ModelListenerException {

		try {
			String className = address.getClassName();

			if (!className.equals(User.class.getName())) {
				return;
			}

			List<Attribute> attributes = getModifiedAttributes(
				originalAddress, address);

			if (!attributes.isEmpty()) {
				AuditMessage auditMessage =
					AuditMessageBuilder.buildAuditMessage(
						EventTypes.UPDATE, User.class.getName(),
						address.getClassPK(), attributes);

				_auditRouter.route(auditMessage);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	protected List<Attribute> getModifiedAttributes(
		Address originalAddress, Address address) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			address, originalAddress);

		attributesBuilder.add("city");
		attributesBuilder.add("countryId");
		attributesBuilder.add("listTypeId");
		attributesBuilder.add("mailing");
		attributesBuilder.add("primary");
		attributesBuilder.add("regionId");
		attributesBuilder.add("street1");
		attributesBuilder.add("street2");
		attributesBuilder.add("street3");
		attributesBuilder.add("zip");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

}