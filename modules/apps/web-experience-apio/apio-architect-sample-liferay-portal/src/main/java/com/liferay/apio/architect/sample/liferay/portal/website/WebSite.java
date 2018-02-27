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

package com.liferay.apio.architect.sample.liferay.portal.website;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.language.Language;

/**
 * Represents a website.
 *
 * <p>
 * Conforms to the <a href="http://schema.org/WebSite">WebSite </a> type from
 * schema.org.
 * </p>
 *
 * @author Victor Oliveira
 * @author Alejandro Hernández
 */
@ProviderType
public interface WebSite {

	/**
	 * Returns the website's description.
	 *
	 * @return the website's description
	 */
	public String getDescription();

	/**
	 * Returns the website's name.
	 *
	 * @param  language the requested language
	 * @return the website's name
	 */
	public String getName(Language language);

	/**
	 * Returns the website's identifier.
	 *
	 * @return the website's identifier
	 */
	public Long getWebSiteId();

}