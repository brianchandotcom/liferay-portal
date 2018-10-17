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

package com.liferay.email.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.email.apio.architect.identifier.EmailIdentifier;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.UserService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/email">Email</a> resources of a <a
 * href="http://schema.org/Person">Person</a> through a web API. The resources
 * are mapped from the internal model {@code EmailAddress}.
 *
 * @author Javier Gamarra
 * @review
 */
@Component(immediate = true, service = ReusableNestedCollectionRouter.class)
public class EmailReusableNestedCollectionRouter
	implements ReusableNestedCollectionRouter
		<EmailAddress, Long, EmailIdentifier, ClassNameClassPK> {

	@Override
	public NestedCollectionRoutes
		<EmailAddress, Long, ClassNameClassPK> collectionRoutes(
			NestedCollectionRoutes.Builder
				<EmailAddress, Long, ClassNameClassPK> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<EmailAddress> _getPageItems(
			Pagination pagination, ClassNameClassPK classNameClassPK)
		throws PortalException {

		User user = _userService.getUserById(classNameClassPK.getClassPK());

		List<EmailAddress> emailAddresses =
			_emailAddressService.getEmailAddresses(
				Contact.class.getName(), user.getContactId());

		int count = emailAddresses.size();

		int endPosition = Math.min(count, pagination.getEndPosition());

		return new PageItems<>(
			emailAddresses.subList(pagination.getStartPosition(), endPosition),
			count);
	}

	@Reference
	private EmailAddressService _emailAddressService;

	@Reference
	private UserService _userService;

}