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

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.sample.internal.form.PersonForm;
import com.liferay.apio.architect.sample.internal.identifier.PersonModelId;
import com.liferay.apio.architect.sample.internal.model.PersonModel;

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
	implements CollectionResource<PersonModel, Long, PersonModelId> {

	@Override
	public CollectionRoutes<PersonModel> collectionRoutes(
		CollectionRoutes.Builder<PersonModel> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addPerson, PersonForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "people";
	}

	@Override
	public ItemRoutes<PersonModel> itemRoutes(
		ItemRoutes.Builder<PersonModel, Long> builder) {

		return builder.addGetter(
			this::_getPerson
		).addRemover(
			this::_deletePerson
		).addUpdater(
			this::_updatePerson, PersonForm::buildForm
		).build();
	}

	@Override
	public Representor<PersonModel, Long> representor(
		Representor.Builder<PersonModel, Long> builder) {

		return builder.types(
			"Person"
		).identifier(
			PersonModel::getPersonId
		).addDate(
			"birthDate", PersonModel::getBirthDate
		).addString(
			"address", PersonModel::getAddress
		).addString(
			"email", PersonModel::getEmail
		).addString(
			"familyName", PersonModel::getLastName
		).addString(
			"givenName", PersonModel::getFirstName
		).addString(
			"image", PersonModel::getAvatar
		).addString(
			"jobTitle", PersonModel::getJobTitle
		).addString(
			"name", PersonModel::getFullName
		).build();
	}

	private PersonModel _addPerson(PersonForm personForm) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		return PersonModel.addPerson(
			personForm.getAddress(), personForm.getImage(),
			personForm.getBirthDate(), personForm.getEmail(),
			personForm.getGivenName(), personForm.getJobTitle(),
			personForm.getFamilyName());
	}

	private void _deletePerson(Long personId) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		PersonModel.deletePerson(personId);
	}

	private PageItems<PersonModel> _getPageItems(Pagination pagination) {
		List<PersonModel> personModels = PersonModel.getPeople(
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = PersonModel.getPeopleCount();

		return new PageItems<>(personModels, count);
	}

	private PersonModel _getPerson(Long personId) {
		Optional<PersonModel> optional = PersonModel.getPerson(personId);

		return optional.orElseThrow(
			() -> new NotFoundException("Unable to get person " + personId));
	}

	private PersonModel _updatePerson(Long personId, PersonForm personForm) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		Optional<PersonModel> optional = PersonModel.updatePerson(
			personForm.getAddress(), personForm.getImage(),
			personForm.getBirthDate(), personForm.getEmail(),
			personForm.getGivenName(), personForm.getJobTitle(),
			personForm.getFamilyName(), personId);

		return optional.orElseThrow(
			() -> new NotFoundException("Unable to get person " + personId));
	}

}