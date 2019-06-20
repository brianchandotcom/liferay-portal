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

package com.liferay.segments.interest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides methods to obtain terms of interest from AC.
 *
 * @author Sarai Díaz
 * @review
 */
@ProviderType
public interface InterestTermsProvider {

	/**
	 * Returns the terms of interest given a user ID.
	 *
	 * @param  userId the ID of the user
	 * @return array of strings of interest terms
	 * @review
	 */
	public String[] getInterestTerms(String userId);

}