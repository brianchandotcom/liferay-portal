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

package com.liferay.talend.properties;

import com.liferay.talend.utils.StringUtils;

import java.net.URI;

import java.util.List;

import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.property.Property;

/**
 * @author Zoltán Takács
 */
public class ResourceProperty extends BaseContextAwareStringProperty {

	public ResourceProperty(String name) {
		super(name);
	}

	public String getResourceURL() {
		if (getValue() == null) {
			throw new IllegalStateException("No resource selected");
		}

		if ((_possibleNamedThingValues == null) ||
			_possibleNamedThingValues.isEmpty()) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"No possible values were specified. Constructing the " +
						"resource URL manually.");
			}

			return _constructResourceURL(getUriPrefix());
		}

		String resourceName = getValue();

		for (NamedThing namedThing : _possibleNamedThingValues) {
			if (resourceName.equals(namedThing.getName())) {
				return namedThing.getTitle();
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Unable to determine the resource's URL from the possible" +
					"values. Constructing the URL manually.");
		}

		return _constructResourceURL(getUriPrefix());
	}

	public String getSingleResourceOperationURL() {
		String host = getHost();
		String uriPrefix = getUriPrefix();

		if (uriPrefix.equals(host)) {
			return getResourceURL();
		}

		if (getValue() == null) {
			throw new IllegalStateException("No resource selected");
		}

		return _constructResourceURL(getHost());
	}

	public String getUriPrefix() {
		if (_uriPrefix == null) {
			throw new IllegalArgumentException("URI prefix is not set");
		}

		return _uriPrefix;
	}

	/**
	 * Expects a List of {@link NamedThing} for the possible values where the
	 * {@link NamedThing#getName()} and {@link NamedThing#getDisplayName()}
	 * returns with the name of the resource and the {@link
	 * NamedThing#getTitle()} returns with the actual resource's URL.
	 *
	 * @param  possibleNamedThingValues
	 * @return the actual instance of the ResourceProperty
	 */
	@Override
	public Property<String> setPossibleNamedThingValues(
		List<NamedThing> possibleNamedThingValues) {

		_possibleNamedThingValues = possibleNamedThingValues;

		super.setPossibleNamedThingValues(possibleNamedThingValues);

		return this;
	}

	public void setUriPrefix(String uriPrefix) {
		_uriPrefix = StringUtils.removeQuotes(uriPrefix);
	}

	private String _constructResourceURL(String uriPrefix) {
		UriBuilder uriBuilder = UriBuilder.fromPath(uriPrefix);

		URI resourceURI = uriBuilder.path(
			"{resource}"
		).build(
			getValue()
		);

		return resourceURI.toString();
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ResourceProperty.class);

	private List<NamedThing> _possibleNamedThingValues;
	private String _uriPrefix;

}