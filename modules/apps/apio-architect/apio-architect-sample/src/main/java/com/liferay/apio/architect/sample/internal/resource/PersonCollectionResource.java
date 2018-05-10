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

package com.liferay.apio.architect.sample.internal.resource;

import static com.liferay.apio.architect.sample.internal.auth.PermissionChecker.hasPermission;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.sample.internal.auth.PermissionChecker;
import com.liferay.apio.architect.sample.internal.form.PersonForm;
import com.liferay.apio.architect.sample.internal.identifier.PersonIdentifier;
import com.liferay.apio.architect.sample.internal.model.PersonModel;
import com.liferay.apio.architect.sample.internal.model.PostalAddressModel;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the information necessary to expose <a
 * href="http://schema.org/Person">Person</a> resources through a web API. The
 * resources are mapped from the internal {@link PersonModel} model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PersonCollectionResource
	implements CollectionResource<PersonModel, Long, PersonIdentifier> {

	@Override
	public CollectionRoutes<PersonModel> collectionRoutes(
		CollectionRoutes.Builder<PersonModel> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addPerson, Credentials.class,
			PermissionChecker::hasPermission, PersonForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "people";
	}

	@Override
	public ItemRoutes<PersonModel, Long> itemRoutes(
		ItemRoutes.Builder<PersonModel, Long> builder) {

		return builder.addGetter(
			this::_getPerson
		).addRemover(
			this::_deletePerson, Credentials.class,
			(credentials, id) -> hasPermission(credentials)
		).addUpdater(
			this::_updatePerson, Credentials.class,
			(credentials, id) -> hasPermission(credentials),
			PersonForm::buildForm
		).build();
	}

	@Override
	public Representor<PersonModel> representor(
		Representor.Builder<PersonModel, Long> builder) {

		return builder.types(
			"Person"
		).identifier(
			PersonModel::getId
		).addDate(
			"birthDate", PersonModel::getBirthDate
		).addNested(
			"address", PersonModel::getPostalAddressModel,
			nestedBuilder -> nestedBuilder.types(
				"PostalAddress"
			).addString(
				"addressCountry", PostalAddressModel::getCountryCode
			).addString(
				"addressLocality", PostalAddressModel::getCity
			).addString(
				"addressRegion", PostalAddressModel::getState
			).addString(
				"postalCode", PostalAddressModel::getZipCode
			).addString(
				"streetAddress", PostalAddressModel::getStreetAddress
			).build()
		).addString(
			"email", PersonModel::getEmail
		).addString(
			"familyName", PersonModel::getLastName
		).addString(
			"givenName", PersonModel::getFirstName
		).addString(
			"image", PersonModel::getAvatar
		).addStringList(
			"jobTitle", PersonModel::getJobTitles
		).addString(
			"name", PersonModel::getFullName
		).build();
	}

	private PersonModel _addPerson(
		PersonForm personForm, Credentials credentials) {

		if (!hasPermission(credentials)) {
			throw new ForbiddenException();
		}

		return PersonModel.create(
			personForm.getPostalAddressModel(), personForm.getImage(),
			personForm.getBirthDate(), personForm.getEmail(),
			personForm.getGivenName(), personForm.getJobTitles(),
			personForm.getFamilyName());
	}

	private void _deletePerson(Long id, Credentials credentials) {
		if (!hasPermission(credentials)) {
			throw new ForbiddenException();
		}

		PersonModel.remove(id);
	}

	private PageItems<PersonModel> _getPageItems(Pagination pagination) {
		List<PersonModel> personModels = PersonModel.getPage(
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = PersonModel.getCount();

		return new PageItems<>(personModels, count);
	}

	private PersonModel _getPerson(Long id) {
		Optional<PersonModel> optional = PersonModel.get(id);

		return optional.orElseThrow(
			() -> new NotFoundException("Unable to get person " + id));
	}

	private PersonModel _updatePerson(
		Long id, PersonForm personForm, Credentials credentials) {

		if (!hasPermission(credentials)) {
			throw new ForbiddenException();
		}

		Optional<PersonModel> optional = PersonModel.update(
			personForm.getPostalAddressModel(), personForm.getImage(),
			personForm.getBirthDate(), personForm.getEmail(),
			personForm.getGivenName(), personForm.getJobTitles(),
			personForm.getFamilyName(), id);

		return optional.orElseThrow(
			() -> new NotFoundException("Unable to get person " + id));
	}

}