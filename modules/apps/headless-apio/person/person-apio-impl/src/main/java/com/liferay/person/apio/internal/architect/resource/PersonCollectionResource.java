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

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

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
import com.liferay.person.apio.internal.wrapper.UserWrapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Person">Person </a> resources through a web API. The
 * resources are mapped from the internal model {@link UserWrapper}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class PersonCollectionResource
	implements CollectionResource<UserWrapper, Long, PersonIdentifier> {

	@Override
	public CollectionRoutes<UserWrapper> collectionRoutes(
		CollectionRoutes.Builder<UserWrapper> builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class
		).addCreator(
			this::_addUser, ThemeDisplay.class, _hasPermission::forAddingUsers,
			PersonCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "people";
	}

	@Override
	public ItemRoutes<UserWrapper, Long> itemRoutes(
		ItemRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getUserWrapper, ThemeDisplay.class
		).addRemover(
			idempotent(_userService::deleteUser),
			_hasPermission.forDeleting(User.class)
		).addUpdater(
			this::_updateUser, ThemeDisplay.class,
			_hasPermission.forUpdating(User.class), PersonUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<UserWrapper> representor(
		Representor.Builder<UserWrapper, Long> builder) {

		return builder.types(
			"Person"
		).identifier(
			User::getUserId
		).addDate(
			"birthDate", PersonCollectionResource::_getBirthday
		).addLocalizedStringByLocale(
			"honorificPrefix", _getContactField(Contact::getPrefixId)
		).addLocalizedStringByLocale(
			"honorificSuffix", _getContactField(Contact::getSuffixId)
		).addString(
			"additionalName", User::getMiddleName
		).addString(
			"alternateName", User::getScreenName
		).addString(
			"dashboardURL", UserWrapper::getDashboardURL
		).addString(
			"email", User::getEmailAddress
		).addString(
			"familyName", User::getLastName
		).addString(
			"gender", PersonCollectionResource::_getGender
		).addString(
			"givenName", User::getFirstName
		).addString(
			"image", UserWrapper::getPortraitURL
		).addString(
			"jobTitle", User::getJobTitle
		).addString(
			"name", User::getFullName
		).addString(
			"profileURL", UserWrapper::getProfileURL
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

	private UserWrapper _addUser(
			PersonCreatorForm personCreatorForm, ThemeDisplay themeDisplay)
		throws PortalException {

		User user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, themeDisplay.getCompanyId(), false,
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

		return new UserWrapper(user, themeDisplay);
	}

	private BiFunction<UserWrapper, Locale, String> _getContactField(
		Function<Contact, Long> function) {

		return (user, locale) -> Try.fromFallible(
			user::getContact
		).map(
			function::apply
		).map(
			_listTypeLocalService::getListType
		).map(
			ListType::getName
		).map(
			name -> LanguageUtil.get(locale, name)
		).orElse(
			null
		);
	}

	private PageItems<UserWrapper> _getPageItems(
			Pagination pagination, ThemeDisplay themeDisplay)
		throws PortalException {

		List<UserWrapper> userWrappers = Stream.of(
			_userService.getCompanyUsers(
				themeDisplay.getCompanyId(), pagination.getStartPosition(),
				pagination.getEndPosition())
		).flatMap(
			List::stream
		).map(
			user -> new UserWrapper(user, themeDisplay)
		).collect(
			Collectors.toList()
		);

		int count = _userService.getCompanyUsersCount(
			themeDisplay.getCompanyId());

		return new PageItems<>(userWrappers, count);
	}

	private UserWrapper _getUserWrapper(long userId, ThemeDisplay themeDisplay)
		throws PortalException {

		User user = _userService.getUserById(userId);

		return new UserWrapper(user, themeDisplay);
	}

	private UserWrapper _updateUser(
			Long userId, PersonUpdaterForm personUpdaterForm,
			ThemeDisplay themeDisplay)
		throws PortalException {

		User user = _userService.getUserById(userId);

		user.setPassword(personUpdaterForm.getPassword());
		user.setScreenName(personUpdaterForm.getAlternateName());
		user.setEmailAddress(personUpdaterForm.getEmail());
		user.setFirstName(personUpdaterForm.getGivenName());
		user.setLastName(personUpdaterForm.getFamilyName());
		user.setJobTitle(personUpdaterForm.getJobTitle());

		user = _userLocalService.updateUser(user);

		return new UserWrapper(user, themeDisplay);
	}

	@Reference
	private HasPermission _hasPermission;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}