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

package com.liferay.vulcan.sample.internal.resource;

import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.CollectionResource;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.resource.identifier.RootIdentifier;
import com.liferay.vulcan.sample.internal.model.User;

import java.time.Instant;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the necessary information to expose <a
 * href="http://schema.org/Person">Person</a> resources through a web API.
 *
 * The resources are mapped from the internal {@link User} model.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(immediate = true)
public class PersonCollectionResource
	implements CollectionResource<User, LongIdentifier> {

	@Override
	public Representor<User, LongIdentifier> buildRepresentor(
		RepresentorBuilder<User, LongIdentifier> representorBuilder) {

		return representorBuilder.identifier(
			blogsEntry -> blogsEntry::getId
		).addDate(
			"birthDate", User::getBirthDate
		).addString(
			"image", User::getAvatar
		).addString(
			"address", User::getAddress
		).addString(
			"email", User::getEmail
		).addString(
			"familyName", User::getLastName
		).addString(
			"givenName", User::getFirstName
		).addString(
			"name", User::getFullName
		).addString(
			"jobTitle", User::getJobTitle
		).addType(
			"Person"
		).build();
	}

	@Override
	public String getName() {
		return "people";
	}

	@Override
	public Routes<User> routes(
		RoutesBuilder<User, LongIdentifier> routesBuilder) {

		return routesBuilder.addCollectionPageGetter(
			this::_getPageItems, RootIdentifier.class
		).addCollectionPageItemCreator(
			this::_addUser, RootIdentifier.class
		).addCollectionPageItemGetter(
			this::_getUser
		).addCollectionPageItemRemover(
			this::_deleteUser
		).addCollectionPageItemUpdater(
			this::_updateUser
		).build();
	}

	private User _addUser(
		RootIdentifier rootIdentifier, Map<String, Object> body) {

		String firstName = (String)body.get("givenName");
		String lastName = (String)body.get("familyName");
		String email = (String)body.get("email");
		String address = (String)body.get("address");
		String jobTitle = (String)body.get("jobTitle");
		String birthDateString = (String)body.get("birthDate");
		String avatar = (String)body.get("image");

		Date birthDate = Date.from(Instant.parse(birthDateString));

		return User.addUser(
			firstName, lastName, email, address, jobTitle, birthDate, avatar);
	}

	private void _deleteUser(LongIdentifier personLongIdentifier) {
		User.deleteUser(personLongIdentifier.getId());
	}

	private PageItems<User> _getPageItems(
		Pagination pagination, RootIdentifier rootIdentifier) {

		List<User> people = User.getUsers(
			pagination.getStartPosition(), pagination.getEndPosition());

		int count = User.getUsersCount();

		return new PageItems<>(people, count);
	}

	private User _getUser(LongIdentifier personLongIdentifier) {
		Optional<User> optional = User.getUser(personLongIdentifier.getId());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get User " + personLongIdentifier.getId()));
	}

	private User _updateUser(
		LongIdentifier personLongIdentifier, Map<String, Object> body) {

		String firstName = (String)body.get("givenName");
		String lastName = (String)body.get("familyName");
		String email = (String)body.get("email");
		String address = (String)body.get("address");
		String jobTitle = (String)body.get("jobTitle");
		String birthDateString = (String)body.get("birthDate");
		String avatar = (String)body.get("image");

		Date birthDate = Date.from(Instant.parse(birthDateString));

		Optional<User> optional = User.updateUser(
			personLongIdentifier.getId(), firstName, lastName, email, address,
			jobTitle, birthDate, avatar);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get User " + personLongIdentifier.getId()));
	}

}