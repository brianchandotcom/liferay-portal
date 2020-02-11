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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.UriInfo;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
public class DefaultDTOConverterContext implements DTOConverterContext {

	public DefaultDTOConverterContext(
		boolean acceptAllLanguages, DTOConverterRegistry dtoConverterRegistry,
		Object id, Locale locale, UriInfo uriInfo, User user) {

		this(
			acceptAllLanguages, new HashMap<>(), dtoConverterRegistry, id,
			locale, uriInfo, user);
	}

	public DefaultDTOConverterContext(
		boolean acceptAllLanguages, Map<String, Map<String, String>> actions,
		DTOConverterRegistry dtoConverterRegistry, Object id, Locale locale,
		UriInfo uriInfo, User user) {

		_acceptAllLanguages = acceptAllLanguages;
		_actions = actions;
		_dtoConverterRegistry = dtoConverterRegistry;
		_id = id;
		_locale = locale;
		_uriInfo = uriInfo;
		_user = user;
	}

	public DefaultDTOConverterContext(
		DTOConverterRegistry dtoConverterRegistry, Object id, Locale locale,
		UriInfo uriInfo, User user) {

		this(false, dtoConverterRegistry, id, locale, uriInfo, user);
	}

	public DefaultDTOConverterContext(Object id, Locale locale) {
		this(id, locale, null, null);
	}

	public DefaultDTOConverterContext(
		Object id, Locale locale, UriInfo uriInfo) {

		this(id, locale, uriInfo, null);
	}

	public DefaultDTOConverterContext(
		Object id, Locale locale, UriInfo uriInfo, User user) {

		this(false, new HashMap<>(), null, id, locale, uriInfo, user);
	}

	@Override
	public Map<String, Map<String, String>> getActions() {
		return _actions;
	}

	@Override
	public DTOConverterRegistry getDTOConverterRegistry() {
		return _dtoConverterRegistry;
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
	private final Map<String, Map<String, String>> _actions;
	private final DTOConverterRegistry _dtoConverterRegistry;
	private final Object _id;
	private final Locale _locale;
	private UriInfo _uriInfo;
	private final User _user;

}