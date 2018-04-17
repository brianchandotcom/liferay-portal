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

package com.liferay.meris;

import aQute.bnd.annotation.ProviderType;

import java.util.Map;

/**
 * Represents a profile.
 *
 * @author Eduardo Garcia
 * @review
 */
@ProviderType
public interface MerisProfile {

	/**
	 * Returns the value of a profile attribute
	 *
	 * @param  key the key of the profile attribute
	 * @return the value of a profile attribute
	 * @review
	 */
	public Object getAttribute(String key);

	/**
	 * Returns the profile attributes
	 *
	 * @return the profile attributes
	 * @review
	 */
	public Map<String, Object> getAttributes();

	/**
	 * Returns the unique ID of the profile
	 *
	 * @return
	 * @review
	 */
	public String getMerisProfileId();

}