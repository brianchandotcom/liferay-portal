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

package com.liferay.email.apio.internal.architect.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.email.apio.architect.identifier.EmailIdentifier;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.EmailAddressService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code EmailAddress} resources
 * through a web API.
 *
 * @author Javier Gamarra
 * @review
 */
@Component(immediate = true)
public class EmailCollectionResource
	implements ItemResource<EmailAddress, Long, EmailIdentifier> {

	@Override
	public String getName() {
		return "emails";
	}

	@Override
	public ItemRoutes<EmailAddress, Long> itemRoutes(
		ItemRoutes.Builder<EmailAddress, Long> builder) {

		return builder.addGetter(
			_emailAddressService::getEmailAddress
		).build();
	}

	@Override
	public Representor<EmailAddress> representor(
		Representor.Builder<EmailAddress, Long> builder) {

		return builder.types(
			"Email"
		).identifier(
			EmailAddress::getEmailAddressId
		).addString(
			"email", EmailAddress::getAddress
		).addString(
			"type",
			email -> Try.fromFallible(
				email::getType
			).map(
				ListType::getName
			).orElse(
				null
			)
		).build();
	}

	@Reference
	private EmailAddressService _emailAddressService;

}