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

package com.liferay.commerce.data.integration.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.commerce.data.integration.apio.internal.form.CommerceUserUpserterForm;
import com.liferay.external.reference.service.ERUserLocalService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserWrapper;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class CommerceUserCollectionResource
	implements CollectionResource<UserWrapper, Long, PersonIdentifier> {

	@Override
	public CollectionRoutes<UserWrapper, Long> collectionRoutes(
		CollectionRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class
		).addCreator(
			this::_addUser, ThemeDisplay.class, _hasPermission::forAdding,
			CommerceUserUpserterForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "person";
	}

	@Override
	public ItemRoutes<UserWrapper, Long> itemRoutes(
		ItemRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getUserWrapper, ThemeDisplay.class
		).addRemover(
			idempotent(_userService::deleteUser), _hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<UserWrapper> representor(
		Representor.Builder<UserWrapper, Long> builder) {

		Representor.FirstStep<UserWrapper> person = builder.types(
			"Person"
		).identifier(
			User::getUserId
		);

		person = _originalPersonResource(person);
		person = _commerceResource(person);

		return person.build();
	}

	private static Date _getBirthday(UserWrapper userWrapper) {
		return Try.fromFallible(
			userWrapper::getBirthday
		).orElse(
			null
		);
	}

	private static String _getGender(UserWrapper userWrapper) {
		return Try.fromFallible(
			userWrapper::isMale
		).map(
			male -> male ? "male" : "female"
		).orElse(
			null
		);
	}

	private UserWrapper _addUser(
			CommerceUserUpserterForm commerceUserUpserterForm,
			ThemeDisplay themeDisplay)
		throws PortalException {

		User user = _userLocalService.getUserById(
			PrincipalThreadLocal.getUserId());

		Company company = themeDisplay.getCompany();

		user = _erUserLocalService.addOrUpdateUser(
			commerceUserUpserterForm.getExternalReferenceCode(),
			user.getUserId(), company.getCompanyId(), false,
			commerceUserUpserterForm.getPassword1(),
			commerceUserUpserterForm.getPassword2(), false,
			commerceUserUpserterForm.getAlternateName(),
			commerceUserUpserterForm.getEmail(), LocaleUtil.getDefault(),
			commerceUserUpserterForm.getGivenName(), null,
			commerceUserUpserterForm.getFamilyName(), 0, 0,
			commerceUserUpserterForm.isMale(),
			commerceUserUpserterForm.getBirthdayMonth(),
			commerceUserUpserterForm.getBirthdayDay(),
			commerceUserUpserterForm.getBirthdayYear(),
			commerceUserUpserterForm.getJobTitle(), null,
			commerceUserUpserterForm.getCommerceAccountIds(),
			commerceUserUpserterForm.getRoleIds(), null, null, false,
			new ServiceContext());

		return new UserWrapper(user);
	}

	private Representor.FirstStep<UserWrapper> _commerceResource(
		Representor.FirstStep<UserWrapper> builder) {

		return builder.addNumberList(
			"commerceAccountIds", this::_getCommerceAccountIds
		).addNumberList(
			"roleIds", this::_getRoleIds
		);
	}

	private List<Number> _getCommerceAccountIds(UserWrapper userWrapper) {
		List<Number> commerceAccountIds = new ArrayList<>();

		try {
			for (long organizationId : userWrapper.getOrganizationIds()) {
				commerceAccountIds.add(organizationId);
			}
		}
		catch (PortalException pe) {
			_log.error("Unable to retrieve organizations", pe);
		}

		return commerceAccountIds;
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

		List<User> users = _userService.getCompanyUsers(
			themeDisplay.getCompanyId(), pagination.getStartPosition(),
			pagination.getEndPosition());

		List<UserWrapper> userWrappers = new ArrayList<>(users.size());

		for (User user : users) {
			userWrappers.add(new UserWrapper(user));
		}

		int total = _userService.getCompanyUsersCount(
			themeDisplay.getCompanyId());

		return new PageItems<>(userWrappers, total);
	}

	private List<Number> _getRoleIds(UserWrapper userWrapper) {
		List<Number> roleIds = new ArrayList<>();

		for (long id : userWrapper.getRoleIds()) {
			roleIds.add(id);
		}

		return roleIds;
	}

	private UserWrapper _getUserWrapper(long userId, ThemeDisplay themeDisplay)
		throws PortalException {

		if (themeDisplay.getDefaultUserId() == userId) {
			throw new NotFoundException();
		}

		User user = _userService.getUserById(userId);

		return new UserWrapper(user);
	}

	private Representor.FirstStep<UserWrapper> _originalPersonResource(
		Representor.FirstStep<UserWrapper> builder) {

		return builder.addDate(
			"birthDate", CommerceUserCollectionResource::_getBirthday
		).addLocalizedStringByLocale(
			"honorificPrefix", _getContactField(Contact::getPrefixId)
		).addLocalizedStringByLocale(
			"honorificSuffix", _getContactField(Contact::getSuffixId)
		).addString(
			"additionalName", User::getMiddleName
		).addString(
			"alternateName", User::getScreenName
		).addString(
			"email", User::getEmailAddress
		).addString(
			"familyName", User::getLastName
		).addString(
			"gender", CommerceUserCollectionResource::_getGender
		).addString(
			"givenName", User::getFirstName
		).addString(
			"jobTitle", User::getJobTitle
		).addString(
			"name", User::getFullName
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceUserCollectionResource.class);

	@Reference
	private ERUserLocalService _erUserLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.User)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private RoleService _roleService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}