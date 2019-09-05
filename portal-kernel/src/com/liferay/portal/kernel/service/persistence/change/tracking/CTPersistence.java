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

package com.liferay.portal.kernel.service.persistence.change.tracking;

import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.List;

/**
 * @author Preston Crary
 */
public interface CTPersistence<T extends CTModel<T>>
	extends BasePersistence<T> {

	public List<T> findByCTCollectionId(long ctCollectionId);

	public List<String[]> getUniqueIndexColumnNames();

	public T removeCTModel(T ctModel, boolean quiet);

	public T updateCTModel(T ctModel, boolean quiet);

}