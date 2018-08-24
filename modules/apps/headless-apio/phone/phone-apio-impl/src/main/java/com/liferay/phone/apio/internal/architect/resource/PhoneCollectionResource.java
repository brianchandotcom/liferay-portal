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

package com.liferay.phone.apio.internal.architect.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.phone.apio.architect.identifier.PhoneIdentifier;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.service.PhoneService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code Phone} resources through
 * a web API.
 *
 * @author Javier Gamarra
 * @review
 */
@Component(immediate = true)
public class PhoneCollectionResource
	implements ItemResource<Phone, Long, PhoneIdentifier> {

	@Override
	public String getName() {
		return "phones";
	}

	@Override
	public ItemRoutes<Phone, Long> itemRoutes(
		ItemRoutes.Builder<Phone, Long> builder) {

		return builder.addGetter(
			_phoneService::getPhone
		).build();
	}

	@Override
	public Representor<Phone> representor(
		Representor.Builder<Phone, Long> builder) {

		return builder.types(
			"Phone"
		).identifier(
			Phone::getPhoneId
		).addString(
			"extension", Phone::getExtension
		).addString(
			"phoneNumber", Phone::getNumber
		).addString(
			"phoneType",
			phone -> Try.fromFallible(
				phone::getType
			).map(
				ListType::getName
			).orElse(
				null
			)
		).build();
	}

	@Reference
	private PhoneService _phoneService;

}