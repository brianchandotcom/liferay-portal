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

import java.util.Comparator;
import java.util.List;

/**
 * Represents a profile manager.
 *
 * @author Eduardo Garcia
 * @review
 */
@ProviderType
public interface MerisProfileManager <P extends MerisProfile> {

	/**
	 * Returns a {@code MerisProfile}
	 *
	 * @param  merisProfileId the ID of the {@code MerisProfile}
	 * @return the {@code MerisProfile}
	 * @review
	 */
	public P getMerisProfile(String merisProfileId);

	/**
	 * Returns a range of {@code MerisProfile}
	 *
	 * @param  start the lower bound of the range of model instances
	 * @param  end the upper bound of the range of model instances (not
	 *         inclusive)
	 * @param  comparator the comparator to order the results by (optionally
	 *         {@code null})
	 * @return the range of {@code MerisProfile}
	 * @review
	 */
	public List<P> getMerisProfiles(
		int start, int end, Comparator<P> comparator);

}