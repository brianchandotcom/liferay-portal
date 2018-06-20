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

import static com.liferay.commerce.data.integration.apio.internal.util.UserHelper.convertLongArrayToList;
import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.UserIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.UserCreatorForm;
import com.liferay.commerce.data.integration.apio.internal.form.UserUpdaterForm;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.UserWrapper;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class UserCollectionResource
	implements CollectionResource<UserWrapper, Long, UserIdentifier> {

	@Override
	public CollectionRoutes<UserWrapper, Long> collectionRoutes(
		CollectionRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addUser, Company.class, _hasPermission::forAdding,
			UserCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "users";
	}

	@Override
	public ItemRoutes<UserWrapper, Long> itemRoutes(
		ItemRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getUserWrapper
		).addRemover(
			idempotent(_userService::deleteUser),
			_hasPermission::forDeleting
		).addUpdater(
			this::_updateUser, _hasPermission::forUpdating,
			UserUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<UserWrapper> representor(
		Representor.Builder<UserWrapper, Long> builder) {

		return builder.types(
			"User"
		).identifier(
			User::getUserId
		).addDate(
			"birthDate", UserCollectionResource::_getBirthday
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
			"gender", UserCollectionResource::_getGender
		).addString(
			"givenName", User::getFirstName
		).addString(
			"name", User::getFullName
		).build();
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
			UserCreatorForm userCreatorForm, Company company)
		throws PortalException {

		User user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, company.getCompanyId(), false,
			userCreatorForm.getPassword1(), userCreatorForm.getPassword2(),
			userCreatorForm.hasAlternateName(),
			userCreatorForm.getAlternateName(), userCreatorForm.getEmail(), 0,
			StringPool.BLANK, LocaleUtil.getDefault(),
			userCreatorForm.getGivenName(), StringPool.BLANK,
			userCreatorForm.getFamilyName(), 0, 0, userCreatorForm.isMale(),
			userCreatorForm.getBirthdayMonth(),
			userCreatorForm.getBirthdayDay(), userCreatorForm.getBirthdayYear(),
			userCreatorForm.getJobTitle(), null,
			userCreatorForm.getAccountIds(), userCreatorForm.getRoleIds(), null,
			false, new ServiceContext());

		return new UserWrapper(user);
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
			Pagination pagination, Company company)
		throws PortalException {

		List<User> users = _userService.getCompanyUsers(
				company.getCompanyId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		List<UserWrapper> userWrappers = new ArrayList<>(users.size());
		for (User user : users) {
			userWrappers.add(new UserWrapper(user));
		}

		int total = _userService.getCompanyUsersCount(company.getCompanyId());

		return new PageItems<>(userWrappers, total);
	}

	private List<Long> _getRolesToAdd(
		List<Long> oldRoleIds, List<Long> newRoleIds) {

		List<Long> roleIdsToAdd = new ArrayList<>();

		for (Long roleId : newRoleIds) {
			if (!oldRoleIds.contains(roleId)) {
				roleIdsToAdd.add(roleId);
			}
		}

		return roleIdsToAdd;
	}

	private List<Long> _getRolesToRemove(
		List<Long> newRoleIds, List<Long> oldRoleIds) {

		List<Long> roleIdsToRemove = new ArrayList<>();

		for (Long roleId : oldRoleIds) {
			if (!newRoleIds.contains(roleId)) {
				roleIdsToRemove.add(roleId);
			}
		}

		return roleIdsToRemove;
	}

	private UserWrapper _getUserWrapper(long userId) throws PortalException {
		User user = _userService.getUserById(userId);

		return new UserWrapper(user);
	}

	private void _updateRoles(UserUpdaterForm userUpdaterForm, User user)
		throws PortalException {

		List<Long> newRoleIds = convertLongArrayToList(
			userUpdaterForm.getRoleIds());

		List<Long> oldRoleIds = convertLongArrayToList(user.getRoleIds());

		List<Long> roleIdsToRemove = _getRolesToRemove(newRoleIds, oldRoleIds);

		_roleService.unsetUserRoles(
			user.getUserId(), ArrayUtil.toLongArray(roleIdsToRemove));

		List<Long> roleIdsToAdd = _getRolesToAdd(oldRoleIds, newRoleIds);

		_roleService.addUserRoles(
			user.getUserId(), ArrayUtil.toLongArray(roleIdsToAdd));
	}

	private UserWrapper _updateUser(
			long userId, UserUpdaterForm userUpdaterForm)
		throws PortalException {

		User user = _userService.getUserById(userId);

		user.setPassword(userUpdaterForm.getPassword());
		user.setScreenName(userUpdaterForm.getAlternateName());
		user.setEmailAddress(userUpdaterForm.getEmail());
		user.setFirstName(userUpdaterForm.getGivenName());
		user.setLastName(userUpdaterForm.getFamilyName());
		user.setJobTitle(userUpdaterForm.getJobTitle());

		user = _userLocalService.updateUser(user);

		if (userUpdaterForm.getAccountIds() != null) {
			_userService.updateOrganizations(
				user.getUserId(), userUpdaterForm.getAccountIds(),
				new ServiceContext());
		}

		if (userUpdaterForm.getRoleIds() != null) {
			_updateRoles(userUpdaterForm, user);
		}

		return new UserWrapper(user);
	}

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private RoleService _roleService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.User)"
	)
	private HasPermission<Long> _hasPermission;

}