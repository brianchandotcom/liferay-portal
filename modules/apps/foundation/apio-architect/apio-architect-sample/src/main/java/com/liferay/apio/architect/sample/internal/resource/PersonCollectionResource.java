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

import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.identifier.RootIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.Routes;
import com.liferay.apio.architect.sample.internal.model.Person;

import java.time.Instant;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the information necessary to expose <a
 * href="http://schema.org/Person">Person </a> resources through a web API. The
 * resources are mapped from the internal {@link Person} model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PersonCollectionResource
	implements CollectionResource<Person, LongIdentifier> {

	@Override
	public String getName() {
		return "people";
	}

	@Override
	public Representor<Person, LongIdentifier> representor(
		Representor.Builder<Person, LongIdentifier> builder) {

		return builder.types(
			"Person"
		).identifier(
			person -> person::getPersonId
		).addDate(
			"birthDate", Person::getBirthDate
		).addString(
			"address", Person::getAddress
		).addString(
			"email", Person::getEmail
		).addString(
			"familyName", Person::getLastName
		).addString(
			"givenName", Person::getFirstName
		).addString(
			"image", Person::getAvatar
		).addString(
			"jobTitle", Person::getJobTitle
		).addString(
			"name", Person::getFullName
		).build();
	}

	@Override
	public Routes<Person> routes(
		Routes.Builder<Person, LongIdentifier> builder) {

		return builder.addCollectionPageGetter(
			this::_getPageItems, RootIdentifier.class
		).addCollectionPageItemCreator(
			this::_addPerson, RootIdentifier.class
		).addCollectionPageItemGetter(
			this::_getPerson
		).addCollectionPageItemRemover(
			this::_deletePerson
		).addCollectionPageItemUpdater(
			this::_updatePerson
		).build();
	}

	private Person _addPerson(
		RootIdentifier rootIdentifier, Map<String, Object> body) {

		String address = (String)body.get("address");
		String avatar = (String)body.get("image");

		String birthDateString = (String)body.get("birthDate");

		Date birthDate = Date.from(Instant.parse(birthDateString));

		String email = (String)body.get("email");
		String firstName = (String)body.get("givenName");
		String jobTitle = (String)body.get("jobTitle");
		String lastName = (String)body.get("familyName");

		return Person.addPerson(
			address, avatar, birthDate, email, firstName, jobTitle, lastName);
	}

	private void _deletePerson(LongIdentifier personLongIdentifier) {
		Person.deletePerson(personLongIdentifier.getId());
	}

	private PageItems<Person> _getPageItems(
		Pagination pagination, RootIdentifier rootIdentifier) {

		List<Person> persons = Person.getPeople(
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = Person.getPeopleCount();

		return new PageItems<>(persons, count);
	}

	private Person _getPerson(LongIdentifier personLongIdentifier) {
		Optional<Person> optional = Person.getPerson(
			personLongIdentifier.getId());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get person " + personLongIdentifier.getId()));
	}

	private Person _updatePerson(
		LongIdentifier personLongIdentifier, Map<String, Object> body) {

		String address = (String)body.get("address");
		String avatar = (String)body.get("image");

		String birthDateString = (String)body.get("birthDate");

		Date birthDate = Date.from(Instant.parse(birthDateString));

		String email = (String)body.get("email");
		String firstName = (String)body.get("givenName");
		String jobTitle = (String)body.get("jobTitle");
		String lastName = (String)body.get("familyName");

		Optional<Person> optional = Person.updatePerson(
			address, avatar, birthDate, email, firstName, jobTitle, lastName,
			personLongIdentifier.getId());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get person " + personLongIdentifier.getId()));
	}

}