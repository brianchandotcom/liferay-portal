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

package com.liferay.phone.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.phone.apio.architect.identifier.PhoneIdentifier;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.UserService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/telephone">Telephone</a> resources through a web API.
 * The resources are mapped from the internal model {@code Phone}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true, service = ReusableNestedCollectionRouter.class)
public class PhoneReusableNestedCollectionRouter
	implements ReusableNestedCollectionRouter
		<Phone, Long, PhoneIdentifier, ClassNameClassPK> {

	@Override
	public NestedCollectionRoutes<Phone, Long, ClassNameClassPK>
		collectionRoutes(
			NestedCollectionRoutes.Builder<Phone, Long, ClassNameClassPK>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<Phone> _getPageItems(
			Pagination pagination, ClassNameClassPK classNameClassPK)
		throws PortalException {

		List<Phone> phones = _getPhones(classNameClassPK);

		int count = phones.size();

		int endPosition = Math.min(count, pagination.getEndPosition());

		return new PageItems<>(
			phones.subList(pagination.getStartPosition(), endPosition), count);
	}

	private List<Phone> _getPhones(ClassNameClassPK classNameClassPK)
		throws PortalException {

		String className = classNameClassPK.getClassName();

		if (className.equals(Organization.class.getName())) {
			Organization organization = _organizationService.getOrganization(
				classNameClassPK.getClassPK());

			return _phoneService.getPhones(
				organization.getModelClassName(),
				organization.getOrganizationId());
		}
		else {
			User user = _userService.getUserById(classNameClassPK.getClassPK());

			return _phoneService.getPhones(
				Contact.class.getName(), user.getContactId());
		}
	}

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private PhoneService _phoneService;

	@Reference
	private UserService _userService;

}