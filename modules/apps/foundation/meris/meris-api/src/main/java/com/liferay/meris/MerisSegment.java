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

import java.util.Locale;

/**
 * Represents a segment.
 *
 * @author Eduardo Garcia
 * @review
 */
@ProviderType
public interface MerisSegment {

	/**
	 * Returns the segment description
	 *
	 * @param  locale the {@code Locale} of the language
	 * @return the segment description
	 * @review
	 */
	public String getDescription(Locale locale);

	/**
	 * Returns the unique ID of the segment
	 *
	 * @return the unique ID of the segment
	 * @review
	 */
	public String getMerisSegmentId();

	/**
	 * Returns the segment name
	 *
	 * @param  locale the {@code Locale} of the language
	 * @return the segment name
	 * @review
	 */
	public String getName(Locale locale);

	/**
	 * Returns the ID of the segment scope
	 *
	 * @return the ID of the segment scope
	 * @review
	 */
	public String getScopeId();

}