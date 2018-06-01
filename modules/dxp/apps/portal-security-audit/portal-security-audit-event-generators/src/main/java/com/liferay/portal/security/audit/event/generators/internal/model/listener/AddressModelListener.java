/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.security.audit.event.generators.internal.model.listener;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
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
@Component(immediate = true, service = ModelListener.class)
public class AddressModelListener extends BaseModelListener<Address> {

	public void onBeforeUpdate(Address newAddress)
		throws ModelListenerException {

		try {
			String className = newAddress.getClassName();

			if (!className.equals(User.class.getName())) {
				return;
			}

			Address oldAddress = _addressLocalService.getAddress(
				newAddress.getAddressId());

			List<Attribute> attributes = getModifiedAttributes(
				newAddress, oldAddress);

			if (!attributes.isEmpty()) {
				AuditMessage auditMessage =
					AuditMessageBuilder.buildAuditMessage(
						EventTypes.UPDATE, User.class.getName(),
						newAddress.getClassPK(), attributes);

				_auditRouter.route(auditMessage);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected List<Attribute> getModifiedAttributes(
		Address newAddress, Address oldAddress) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			newAddress, oldAddress);

		attributesBuilder.add("countryId");
		attributesBuilder.add("city");
		attributesBuilder.add("mailing");
		attributesBuilder.add("primary");
		attributesBuilder.add("regionId");
		attributesBuilder.add("street1");
		attributesBuilder.add("street2");
		attributesBuilder.add("street3");
		attributesBuilder.add("typeId");
		attributesBuilder.add("zip");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AuditRouter _auditRouter;

}