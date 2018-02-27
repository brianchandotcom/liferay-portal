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

package com.liferay.person.apio.internal.architect.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.person.apio.internal.architect.form.PersonCreatorForm;
import com.liferay.person.apio.internal.architect.form.PersonUpdaterForm;
import com.liferay.portal.apio.architect.context.auth.MockPermissions;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;
import java.util.List;

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
	implements CollectionResource<User, Long, PersonIdentifier> {

	@Override
	public CollectionRoutes<User> collectionRoutes(
		CollectionRoutes.Builder<User> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addUser, Company.class, MockPermissions::validPermission,
			PersonCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "people";
	}

	@Override
	public ItemRoutes<User, Long> itemRoutes(
		ItemRoutes.Builder<User, Long> builder) {

		return builder.addGetter(
			this::_getUser
		).addRemover(
			this::_deleteUser, MockPermissions::validPermission
		).addUpdater(
			this::_updateUser, MockPermissions::validPermission,
			PersonUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<User, Long> representor(
		Representor.Builder<User, Long> builder) {

		return builder.types(
			"Person"
		).identifier(
			User::getUserId
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
		PersonCreatorForm personCreatorForm, Company company) {

		try {
			return _userLocalService.addUser(
				UserConstants.USER_ID_DEFAULT, company.getCompanyId(), false,
				personCreatorForm.getPassword1(),
				personCreatorForm.getPassword2(),
				personCreatorForm.hasAlternateName(),
				personCreatorForm.getAlternateName(),
				personCreatorForm.getEmail(), 0, StringPool.BLANK,
				LocaleUtil.getDefault(), personCreatorForm.getGivenName(),
				StringPool.BLANK, personCreatorForm.getFamilyName(), 0, 0,
				personCreatorForm.isMale(),
				personCreatorForm.getBirthdayMonth(),
				personCreatorForm.getBirthdayDay(),
				personCreatorForm.getBirthdayYear(),
				personCreatorForm.getJobTitle(), null, null, null, null, false,
				new ServiceContext());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private void _deleteUser(Long userId) {
		try {
			_userLocalService.deleteUser(userId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<User> _getPageItems(
		Pagination pagination, Company company) {

		List<User> users = _userLocalService.getCompanyUsers(
			company.getCompanyId(), pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _userLocalService.getCompanyUsersCount(
			company.getCompanyId());

		return new PageItems<>(users, count);
	}

	private User _getUser(Long userId) {
		try {
			return _userLocalService.getUserById(userId);
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException("Unable to get user " + userId, e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private User _updateUser(Long userId, PersonUpdaterForm personUpdaterForm) {
		User user = _getUser(userId);

		user.setPassword(personUpdaterForm.getPassword());
		user.setScreenName(personUpdaterForm.getAlternateName());
		user.setEmailAddress(personUpdaterForm.getEmail());
		user.setFirstName(personUpdaterForm.getGivenName());
		user.setLastName(personUpdaterForm.getFamilyName());
		user.setJobTitle(personUpdaterForm.getJobTitle());

		Try<User> userTry = Try.fromFallible(
			() -> _userLocalService.updateUser(user));

		return userTry.getUnchecked();
	}

	@Reference
	private UserLocalService _userLocalService;

}