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

package com.liferay.apio.architect.sample.liferay.portal.internal.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.identifier.RootIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.Routes;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Person">Person </a> resources through a web API. The
 * resources are mapped from the internal model {@code User}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class PersonCollectionResource
	implements CollectionResource<User, LongIdentifier> {

	@Override
	public String getName() {
		return "people";
	}

	@Override
	public Representor<User, LongIdentifier> representor(
		Representor.Builder<User, LongIdentifier> builder) {

		return builder.types(
			"Person"
		).identifier(
			user -> user::getUserId
		).addDate(
			"birthDate", PersonCollectionResource::_getBirthday
		).addString(
			"additionalName", User::getMiddleName
		).addString(
			"alternateName", User::getScreenName
		).addString(
			"email", User::getEmailAddress
		).addString(
			"familyName", User::getLastName
		).addString(
			"gender", PersonCollectionResource::_getGender
		).addString(
			"givenName", User::getFirstName
		).addString(
			"jobTitle", User::getJobTitle
		).addString(
			"name", User::getFullName
		).build();
	}

	@Override
	public Routes<User> routes(Routes.Builder<User, LongIdentifier> builder) {
		return builder.addCollectionPageGetter(
			this::_getPageItems, RootIdentifier.class, Company.class
		).addCollectionPageItemCreator(
			this::_addUser, RootIdentifier.class, Company.class
		).addCollectionPageItemGetter(
			this::_getUser
		).addCollectionPageItemRemover(
			this::_deleteUser
		).addCollectionPageItemUpdater(
			this::_updateUser
		).build();
	}

	private static Date _getBirthday(User user) {
		Try<Date> dateTry = Try.fromFallible(user::getBirthday);

		return dateTry.orElse(null);
	}

	private static String _getGender(User user) {
		Try<Boolean> booleanTry = Try.fromFallible(user::isMale);

		return booleanTry.map(
			male -> male ? "male" : "female"
		).orElse(
			null
		);
	}

	private User _addUser(
		RootIdentifier rootIdentifier, Map<String, Object> body,
		Company company) {

		String password1 = (String)body.get("password1");
		String password2 = (String)body.get("password2");
		String screenName = (String)body.get("alternateName");
		String emailAddress = (String)body.get("email");
		String firstName = (String)body.get("givenName");
		String lastName = (String)body.get("familyName");
		boolean male = (boolean)body.get("male");
		String birthDateString = (String)body.get("birthDate");

		Supplier<BadRequestException> invalidBodyExceptionSupplier =
			() -> new BadRequestException("Invalid body");

		if (Validator.isNull(screenName) || Validator.isNull(emailAddress) ||
			Validator.isNull(firstName) || Validator.isNull(lastName) ||
			Validator.isNull(birthDateString)) {

			throw invalidBodyExceptionSupplier.get();
		}

		Calendar calendar = Calendar.getInstance();

		Try<DateFormat> dateFormatTry = Try.success(
			DateUtil.getISO8601Format());

		Date birthDate = dateFormatTry.map(
			dateFormat -> dateFormat.parse(birthDateString)
		).mapFailMatching(
			ParseException.class, invalidBodyExceptionSupplier
		).getUnchecked();

		calendar.setTime(birthDate);

		int birthdayMonth = calendar.get(Calendar.MONTH);
		int birthdayDay = calendar.get(Calendar.DATE);
		int birthdayYear = calendar.get(Calendar.YEAR);

		String jobTitle = (String)body.get("jobTitle");

		if (Validator.isNull(jobTitle)) {
			throw invalidBodyExceptionSupplier.get();
		}

		Try<User> userTry = Try.fromFallible(
			() -> _userLocalService.addUser(
				UserConstants.USER_ID_DEFAULT, company.getCompanyId(), false,
				password1, password2, Validator.isNull(screenName), screenName,
				emailAddress, 0, StringPool.BLANK, LocaleUtil.getDefault(),
				firstName, StringPool.BLANK, lastName, 0, 0, male,
				birthdayMonth, birthdayDay, birthdayYear, jobTitle, null, null,
				null, null, false, new ServiceContext()));

		return userTry.getUnchecked();
	}

	private void _deleteUser(LongIdentifier userLongIdentifier) {
		try {
			_userLocalService.deleteUser(userLongIdentifier.getId());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<User> _getPageItems(
		Pagination pagination, RootIdentifier rootIdentifier, Company company) {

		List<User> users = _userLocalService.getCompanyUsers(
			company.getCompanyId(), pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _userLocalService.getCompanyUsersCount(
			company.getCompanyId());

		return new PageItems<>(users, count);
	}

	private User _getUser(LongIdentifier userLongIdentifier) {
		try {
			return _userLocalService.getUserById(userLongIdentifier.getId());
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get user " + userLongIdentifier.getId(), e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private User _updateUser(
		LongIdentifier userLongIdentifier, Map<String, Object> body) {

		User user = _getUser(userLongIdentifier);

		String password = (String)body.get("password");
		String screenName = (String)body.get("alternateName");
		String emailAddress = (String)body.get("email");
		String firstName = (String)body.get("givenName");
		String lastName = (String)body.get("familyName");

		Supplier<BadRequestException> invalidBodyExceptionSupplier =
			() -> new BadRequestException("Invalid body");

		if (Validator.isNull(screenName) || Validator.isNull(emailAddress) ||
			Validator.isNull(firstName) || Validator.isNull(lastName)) {

			throw invalidBodyExceptionSupplier.get();
		}

		String jobTitle = (String)body.get("jobTitle");

		if (Validator.isNull(jobTitle)) {
			throw invalidBodyExceptionSupplier.get();
		}

		user.setPassword(password);
		user.setScreenName(screenName);
		user.setEmailAddress(emailAddress);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setJobTitle(jobTitle);

		Try<User> userTry = Try.fromFallible(
			() -> _userLocalService.updateUser(user));

		return userTry.getUnchecked();
	}

	@Reference
	private UserLocalService _userLocalService;

}