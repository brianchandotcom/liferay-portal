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

package com.liferay.person.apio.internal.util;

import com.liferay.address.apio.architect.identifier.AddressIdentifier;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.email.apio.architect.identifier.EmailIdentifier;
import com.liferay.person.apio.internal.model.UserWrapper;
import com.liferay.phone.apio.architect.identifier.PhoneIdentifier;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeService;
import com.liferay.role.apio.identifier.RoleIdentifier;
import com.liferay.web.url.apio.architect.identifier.WebUrlIdentifier;

import java.util.Date;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Perez
 */
@Component(immediate = true)
public class UserAccountRepresentorBuilderHelperImpl
	implements UserAccountRepresentorBuilderHelper {

	@Override
	public Representor.FirstStep<UserWrapper> buildUserWrapperFirstStep(
		Representor.Builder<UserWrapper, Long> builder) {

		return builder.types(
			"Liferay:UserAccount", "Person"
		).identifier(
			User::getUserId
		).addDate(
			"birthDate", UserAccountRepresentorBuilderHelperImpl::_getBirthday
		).addLocalizedStringByLocale(
			"honorificPrefix", _getContactField(Contact::getPrefixId)
		).addLocalizedStringByLocale(
			"honorificSuffix", _getContactField(Contact::getSuffixId)
		).addNested(
			"contactInformation", this::_getContact,
			contactBuilder -> contactBuilder.types(
				"ContactInformation"
			).addString(
				"facebook", Contact::getFacebookSn
			).addString(
				"jabber", Contact::getJabberSn
			).addString(
				"skype", Contact::getSkypeSn
			).addString(
				"sms", Contact::getSmsSn
			).addString(
				"twitter", Contact::getTwitterSn
			).build()
		).addRelatedCollection(
			"roles", RoleIdentifier.class
		).addRelatedCollection(
			"address", AddressIdentifier.class
		).addRelatedCollection(
			"emails", EmailIdentifier.class
		).addRelatedCollection(
			"telephone", PhoneIdentifier.class
		).addRelatedCollection(
			"webUrl", WebUrlIdentifier.class
		).addRelativeURL(
			"image", UserWrapper::getPortraitURL
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
			"gender", UserAccountRepresentorBuilderHelperImpl::_getGender
		).addString(
			"givenName", User::getFirstName
		).addString(
			"jobTitle", User::getJobTitle
		).addString(
			"name", User::getFullName
		).addString(
			"profileURL", UserWrapper::getProfileURL
		);
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

	private Contact _getContact(UserWrapper userWrapper) {
		return Try.fromFallible(
			userWrapper::getContact
		).orElse(
			null
		);
	}

	private BiFunction<UserWrapper, Locale, String> _getContactField(
		Function<Contact, Long> function) {

		return (user, locale) -> Try.fromFallible(
			user::getContact
		).map(
			function::apply
		).map(
			_listTypeService::getListType
		).map(
			ListType::getName
		).map(
			name -> LanguageUtil.get(locale, name)
		).orElse(
			null
		);
	}

	@Reference
	private ListTypeService _listTypeService;

}