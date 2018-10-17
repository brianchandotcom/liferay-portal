/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.phone.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.phone.apio.architect.identifier.PhoneIdentifier;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.UserService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/telephone">Telephone</a> resources of a <a
 * href="http://schema.org/Person">Person</a> through a web API. The resources
 * are mapped from the internal model {@code Phone}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true, service = ReusableNestedCollectionRouter.class)
public class PhoneReusableNestedCollectionRouter
	implements
	ReusableNestedCollectionRouter<Phone, Long, PhoneIdentifier, ClassNameClassPK> {

	@Override
	public NestedCollectionRoutes<Phone, Long, ClassNameClassPK> collectionRoutes(
		NestedCollectionRoutes.Builder<Phone, Long, ClassNameClassPK> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Reference
	protected PhoneService phoneService;

	@Reference
	protected UserService userService;

	private PageItems<Phone> _getPageItems(
			Pagination pagination, ClassNameClassPK classNameClassPK)
		throws PortalException {

		User user = userService.getUserById(classNameClassPK.getClassPK());

		List<Phone> phones = phoneService.getPhones(
			Contact.class.getName(), user.getContactId());

		int count = phones.size();

		int endPosition = Math.min(count, pagination.getEndPosition());

		return new PageItems<>(
			phones.subList(pagination.getStartPosition(), endPosition), count);
	}

}