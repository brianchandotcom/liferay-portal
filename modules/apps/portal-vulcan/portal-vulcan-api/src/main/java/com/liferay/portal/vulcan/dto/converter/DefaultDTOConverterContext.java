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

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.UriInfo;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
public class DefaultDTOConverterContext implements DTOConverterContext {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public DefaultDTOConverterContext(
		boolean acceptAllLanguages, DTOConverterRegistry dtoConverterRegistry,
		Object id, Locale locale, UriInfo uriInfo, User user) {

		this(
			acceptAllLanguages, new HashMap<>(), dtoConverterRegistry, id,
			locale, uriInfo, user);
	}

	public DefaultDTOConverterContext(
		boolean acceptAllLanguages, Map<String, Map<String, String>> actions,
		DTOConverterRegistry dtoConverterRegistry,
		HttpServletRequest httpServletRequest, Object id, Locale locale,
		UriInfo uriInfo, User user) {

		this(
			acceptAllLanguages, actions, new HashMap<>(), dtoConverterRegistry,
			httpServletRequest, id, locale, uriInfo, user);
	}

	public DefaultDTOConverterContext(
		boolean acceptAllLanguages, Map<String, Map<String, String>> actions,
		DTOConverterRegistry dtoConverterRegistry, Object id, Locale locale,
		UriInfo uriInfo, User user) {

		this(
			acceptAllLanguages, actions, dtoConverterRegistry, null, id, locale,
			uriInfo, user);
	}

	public DefaultDTOConverterContext(
		boolean acceptAllLanguages, Map<String, Map<String, String>> actions,
		Map<String, Object> attributes,
		DTOConverterRegistry dtoConverterRegistry,
		HttpServletRequest httpServletRequest, Object id, Locale locale,
		UriInfo uriInfo, User user) {

		_acceptAllLanguages = acceptAllLanguages;
		_actions = actions;
		_attributes = attributes;
		_dtoConverterRegistry = dtoConverterRegistry;
		_httpServletRequest = httpServletRequest;
		_id = id;
		_locale = locale;
		_uriInfo = uriInfo;
		_user = user;
	}

	public DefaultDTOConverterContext(
		DTOConverterRegistry dtoConverterRegistry, Object id, Locale locale,
		UriInfo uriInfo, User user) {

		this(
			false, new HashMap<>(), dtoConverterRegistry, id, locale, uriInfo,
			user);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public DefaultDTOConverterContext(Object id, Locale locale) {
		this(false, new HashMap<>(), null, id, locale, null, null);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public DefaultDTOConverterContext(
		Object id, Locale locale, UriInfo uriInfo) {

		this(false, new HashMap<>(), null, id, locale, uriInfo, null);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public DefaultDTOConverterContext(
		Object id, Locale locale, UriInfo uriInfo, User user) {

		this(false, new HashMap<>(), null, id, locale, uriInfo, user);
	}

	@Override
	public Map<String, Map<String, String>> getActions() {
		return _actions;
	}

	@Override
	public Object getAttribute(String name) {
		return _attributes.get(name);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return _attributes;
	}

	@Override
	public DTOConverterRegistry getDTOConverterRegistry() {
		return _dtoConverterRegistry;
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
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

	@Override
	public Object removeAttribute(String name) {
		return _attributes.remove(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		_attributes.put(name, value);
	}

	@Override
	public void setAttributes(Map<String, Serializable> attributes) {
		_attributes.putAll(attributes);
	}

	private final boolean _acceptAllLanguages;
	private final Map<String, Map<String, String>> _actions;
	private final Map<String, Object> _attributes;
	private final DTOConverterRegistry _dtoConverterRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final Object _id;
	private final Locale _locale;
	private final UriInfo _uriInfo;
	private final User _user;

}