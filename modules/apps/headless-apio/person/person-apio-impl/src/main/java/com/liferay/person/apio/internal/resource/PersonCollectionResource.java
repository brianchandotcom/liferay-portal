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

package com.liferay.person.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.person.apio.internal.form.PersonCreatorForm;
import com.liferay.person.apio.internal.form.PersonUpdaterForm;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Date;
import java.util.List;

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
			this::_addUser, Company.class, _hasPermission::forAddingUsers,
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
			_userService::getUserById
		).addRemover(
			idempotent(_userService::deleteUser),
			_hasPermission.forDeleting(User.class)
		).addUpdater(
			this::_updateUser, _hasPermission.forUpdating(User.class),
			PersonUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<User> representor(
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
		return Try.fromFallible(
			user::getBirthday
		).orElse(
			null
		);
	}

	private static String _getGender(User user) {
		return Try.fromFallible(
			user::isMale
		).map(
			male -> male ? "male" : "female"
		).orElse(
			null
		);
	}

	private User _addUser(PersonCreatorForm personCreatorForm, Company company)
		throws PortalException {

		return _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, company.getCompanyId(), false,
			personCreatorForm.getPassword1(), personCreatorForm.getPassword2(),
			personCreatorForm.hasAlternateName(),
			personCreatorForm.getAlternateName(), personCreatorForm.getEmail(),
			0, StringPool.BLANK, LocaleUtil.getDefault(),
			personCreatorForm.getGivenName(), StringPool.BLANK,
			personCreatorForm.getFamilyName(), 0, 0, personCreatorForm.isMale(),
			personCreatorForm.getBirthdayMonth(),
			personCreatorForm.getBirthdayDay(),
			personCreatorForm.getBirthdayYear(),
			personCreatorForm.getJobTitle(), null, null, null, null, false,
			new ServiceContext());
	}

	private PageItems<User> _getPageItems(
			Pagination pagination, Company company)
		throws PortalException {

		List<User> users = _userService.getCompanyUsers(
			company.getCompanyId(), pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _userService.getCompanyUsersCount(company.getCompanyId());

		return new PageItems<>(users, count);
	}

	private User _updateUser(Long userId, PersonUpdaterForm personUpdaterForm)
		throws PortalException {

		User user = _userService.getUserById(userId);

		user.setPassword(personUpdaterForm.getPassword());
		user.setScreenName(personUpdaterForm.getAlternateName());
		user.setEmailAddress(personUpdaterForm.getEmail());
		user.setFirstName(personUpdaterForm.getGivenName());
		user.setLastName(personUpdaterForm.getFamilyName());
		user.setJobTitle(personUpdaterForm.getJobTitle());

		return _userLocalService.updateUser(user);
	}

	@Reference
	private HasPermission _hasPermission;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}