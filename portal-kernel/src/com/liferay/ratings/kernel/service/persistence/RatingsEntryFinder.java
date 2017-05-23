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

package com.liferay.ratings.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface RatingsEntryFinder {
	public java.util.Map<java.io.Serializable, com.liferay.ratings.kernel.model.RatingsEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	public java.util.List<com.liferay.ratings.kernel.model.RatingsEntry> findByU_C_C(
		long userId, long classNameId, java.util.List<java.lang.Long> classPKs);
}