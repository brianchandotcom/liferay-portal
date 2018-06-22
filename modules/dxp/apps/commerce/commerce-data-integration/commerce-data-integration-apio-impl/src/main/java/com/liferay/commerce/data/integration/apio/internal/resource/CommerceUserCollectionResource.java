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
import com.liferay.commerce.data.integration.apio.identifiers.CommerceUserIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.CommerceUserCreatorForm;
import com.liferay.commerce.data.integration.apio.internal.form.CommerceUserUpdaterForm;
import com.liferay.commerce.data.integration.apio.internal.util.ServiceContextHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.UserWrapper;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class CommerceUserCollectionResource
	implements CollectionResource<UserWrapper, Long, CommerceUserIdentifier> {

	@Override
	public CollectionRoutes<UserWrapper, Long> collectionRoutes(
		CollectionRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addUser, Company.class, _hasPermission::forAdding,
			CommerceUserCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "commerce-user";
	}

	@Override
	public ItemRoutes<UserWrapper, Long> itemRoutes(
		ItemRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getUserWrapper
		).addRemover(
			idempotent(_userService::deleteUser), _hasPermission::forDeleting
		).addUpdater(
			this::_updateUser, _hasPermission::forUpdating,
			CommerceUserUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<UserWrapper> representor(
		Representor.Builder<UserWrapper, Long> builder) {

		return builder.types(
			"CommerceUser"
		).identifier(
			User::getUserId
		).addDate(
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
			CommerceUserCreatorForm commerceUserCreatorForm, Company company)
		throws PortalException {

		User user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, company.getCompanyId(), false,
			commerceUserCreatorForm.getPassword1(),
			commerceUserCreatorForm.getPassword2(),
			commerceUserCreatorForm.hasAlternateName(),
			commerceUserCreatorForm.getAlternateName(),
			commerceUserCreatorForm.getEmail(), 0, StringPool.BLANK,
			LocaleUtil.getDefault(), commerceUserCreatorForm.getGivenName(),
			StringPool.BLANK, commerceUserCreatorForm.getFamilyName(), 0, 0,
			commerceUserCreatorForm.isMale(),
			commerceUserCreatorForm.getBirthdayMonth(),
			commerceUserCreatorForm.getBirthdayDay(),
			commerceUserCreatorForm.getBirthdayYear(),
			commerceUserCreatorForm.getJobTitle(), null,
			commerceUserCreatorForm.getCommerceAccountIds(),
			commerceUserCreatorForm.getRoleIds(), null, false,
			new ServiceContext());

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

	private void _updateRoles(
			CommerceUserUpdaterForm commerceUserUpdaterForm, User user)
		throws PortalException {

		List<Long> newRoleIds = ListUtil.toList(
			commerceUserUpdaterForm.getRoleIds());

		List<Long> oldRoleIds = ListUtil.toList(user.getRoleIds());

		List<Long> roleIdsToRemove = _getRolesToRemove(newRoleIds, oldRoleIds);

		_roleService.unsetUserRoles(
			user.getUserId(), ArrayUtil.toLongArray(roleIdsToRemove));

		List<Long> roleIdsToAdd = _getRolesToAdd(oldRoleIds, newRoleIds);

		_roleService.addUserRoles(
			user.getUserId(), ArrayUtil.toLongArray(roleIdsToAdd));
	}

	private UserWrapper _updateUser(
			long userId, CommerceUserUpdaterForm commerceUserUpdaterForm)
		throws PortalException {

		User user = _userService.getUserById(userId);

		long prefixId = 0;
		long suffixId = 0;
		String facebookSn = null;
		String jabberSn = null;
		String skypeSn = null;
		String smsSn = null;
		String twitterSn = null;

		Contact contact = user.getContact();

		if (contact != null) {
			facebookSn = contact.getFacebookSn();
			jabberSn = contact.getJabberSn();
			prefixId = contact.getPrefixId();
			skypeSn = contact.getSkypeSn();
			smsSn = contact.getSmsSn();
			suffixId = contact.getSuffixId();
			twitterSn = contact.getTwitterSn();
		}

		int birthdayMonth = 0;
		int birthdayDay = 0;
		int birthdayYear = 0;

		Date birthday = user.getBirthday();

		if (birthday != null) {
			Calendar calendarBirthday = CalendarFactoryUtil.getCalendar(
				birthday.getTime());

			birthdayDay = calendarBirthday.get(Calendar.DAY_OF_MONTH);
			birthdayMonth = calendarBirthday.get(Calendar.MONTH);
			birthdayYear = calendarBirthday.get(Calendar.YEAR);
		}

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRoles(userId);

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		user = _userLocalService.updateUser(
			user.getUserId(), user.getPassword(), null, null, false,
			user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
			commerceUserUpdaterForm.getAlternateName(),
			commerceUserUpdaterForm.getEmail(), user.getFacebookId(),
			user.getOpenId(), false, null, user.getLanguageId(),
			user.getTimeZoneId(), user.getGreeting(), user.getComments(),
			commerceUserUpdaterForm.getGivenName(), user.getMiddleName(),
			commerceUserUpdaterForm.getFamilyName(), prefixId, suffixId,
			user.getMale(), birthdayMonth, birthdayDay, birthdayYear, smsSn,
			facebookSn, jabberSn, skypeSn, twitterSn,
			commerceUserUpdaterForm.getJobTitle(), user.getGroupIds(),
			commerceUserUpdaterForm.getCommerceAccountIds(),
			commerceUserUpdaterForm.getRoleIds(), userGroupRoles,
			user.getUserGroupIds(), serviceContext);

		return new UserWrapper(user);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.User)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private RoleService _roleService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}