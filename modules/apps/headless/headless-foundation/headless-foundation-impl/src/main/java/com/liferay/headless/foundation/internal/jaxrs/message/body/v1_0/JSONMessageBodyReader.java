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

package com.liferay.headless.foundation.internal.jaxrs.message.body.v1_0;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.dto.v1_0.ContactInformation;
import com.liferay.headless.foundation.dto.v1_0.Creator;
import com.liferay.headless.foundation.dto.v1_0.Email;
import com.liferay.headless.foundation.dto.v1_0.HoursAvailable;
import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.headless.foundation.dto.v1_0.Location;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.dto.v1_0.Role;
import com.liferay.headless.foundation.dto.v1_0.Services;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.dto.v1_0.WebUrl;
import com.liferay.headless.foundation.internal.dto.v1_0.CategoryImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.ContactInformationImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.CreatorImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.EmailImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.HoursAvailableImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.KeywordImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.LocationImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.OrganizationImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.PhoneImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.PostalAddressImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.RoleImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.ServicesImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.UserAccountImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.VocabularyImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.WebUrlImpl;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Headless.Foundation)",
		"osgi.jaxrs.name=Liferay.Headless.Foundation.v1_0.JSONMessageBodyReader"
	},
	service = MessageBodyReader.class
)
@Consumes(MediaType.APPLICATION_JSON)
@Generated("")
@Provider
public class JSONMessageBodyReader implements MessageBodyReader<Object> {

	@Override
	public boolean isReadable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

			if (clazz.equals(Category.class)) {
				return true;
	}
			if (clazz.equals(ContactInformation.class)) {
				return true;
	}
			if (clazz.equals(Creator.class)) {
				return true;
	}
			if (clazz.equals(Email.class)) {
				return true;
	}
			if (clazz.equals(HoursAvailable.class)) {
				return true;
	}
			if (clazz.equals(Keyword.class)) {
				return true;
	}
			if (clazz.equals(Location.class)) {
				return true;
	}
			if (clazz.equals(Organization.class)) {
				return true;
	}
			if (clazz.equals(Phone.class)) {
				return true;
	}
			if (clazz.equals(PostalAddress.class)) {
				return true;
	}
			if (clazz.equals(Role.class)) {
				return true;
	}
			if (clazz.equals(Services.class)) {
				return true;
	}
			if (clazz.equals(UserAccount.class)) {
				return true;
	}
			if (clazz.equals(Vocabulary.class)) {
				return true;
	}
			if (clazz.equals(WebUrl.class)) {
				return true;
	}

		return false;
	}

	@Override
	public Object readFrom(
			Class<Object> clazz, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> multivaluedMap,
			InputStream inputStream)
		throws IOException, WebApplicationException {

		return _objectMapper.readValue(inputStream, clazz);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			SimpleModule simpleModule =
				new SimpleModule("Liferay.Headless.Foundation",
					Version.unknownVersion());

			SimpleAbstractTypeResolver simpleAbstractTypeResolver =
				new SimpleAbstractTypeResolver();

			simpleAbstractTypeResolver.addMapping(
				Category.class, CategoryImpl.class);
			simpleAbstractTypeResolver.addMapping(
				ContactInformation.class, ContactInformationImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Creator.class, CreatorImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Email.class, EmailImpl.class);
			simpleAbstractTypeResolver.addMapping(
				HoursAvailable.class, HoursAvailableImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Keyword.class, KeywordImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Location.class, LocationImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Organization.class, OrganizationImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Phone.class, PhoneImpl.class);
			simpleAbstractTypeResolver.addMapping(
				PostalAddress.class, PostalAddressImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Role.class, RoleImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Services.class, ServicesImpl.class);
			simpleAbstractTypeResolver.addMapping(
				UserAccount.class, UserAccountImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Vocabulary.class, VocabularyImpl.class);
			simpleAbstractTypeResolver.addMapping(
				WebUrl.class, WebUrlImpl.class);

			simpleModule.setAbstractTypes(simpleAbstractTypeResolver);

			registerModule(simpleModule);

			setDateFormat(new ISO8601DateFormat());
	}
	};

}