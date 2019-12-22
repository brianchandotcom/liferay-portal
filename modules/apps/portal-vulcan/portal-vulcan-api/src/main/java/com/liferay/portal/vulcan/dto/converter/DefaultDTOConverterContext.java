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

package com.liferay.portal.vulcan.dto.converter;

import com.liferay.portal.kernel.model.User;

import java.util.Locale;
import java.util.Optional;

import javax.ws.rs.core.UriInfo;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
public class DefaultDTOConverterContext implements DTOConverterContext {

	public DefaultDTOConverterContext(
		boolean acceptAllLanguages, Locale locale, Object id, UriInfo uriInfo,
		User user) {

		_acceptAllLanguages = acceptAllLanguages;
		_locale = locale;
		_id = id;
		_uriInfo = uriInfo;
		_user = user;
	}

	public DefaultDTOConverterContext(Locale locale, Object id) {
		this(locale, id, null, null);
	}

	public DefaultDTOConverterContext(
		Locale locale, Object id, UriInfo uriInfo) {

		this(locale, id, uriInfo, null);
	}

	public DefaultDTOConverterContext(
		Locale locale, Object id, UriInfo uriInfo, User user) {

		this(false, locale, id, uriInfo, user);
	}

	@Override
	public Object getId() {
		return _id;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public Optional<UriInfo> getUriInfoOptional() {
		return Optional.ofNullable(_uriInfo);
	}

	@Override
	public User getUser() {
		return _user;
	}

	@Override
	public long getUserId() {
		if (_user != null) {
			return _user.getUserId();
		}

		return 0;
	}

	@Override
	public boolean isAcceptAllLanguages() {
		return _acceptAllLanguages;
	}

	private final boolean _acceptAllLanguages;
	private final Object _id;
	private final Locale _locale;
	private UriInfo _uriInfo;
	private final User _user;

}