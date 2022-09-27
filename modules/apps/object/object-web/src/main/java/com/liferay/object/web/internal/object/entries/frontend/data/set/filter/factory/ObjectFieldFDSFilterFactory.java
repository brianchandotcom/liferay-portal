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

package com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.object.model.ObjectViewFilterColumn;

import java.util.Locale;

/**
 * @author Feliphe Marinho
 */
public interface ObjectFieldFDSFilterFactory {

	public FDSFilter create(
			Locale locale, long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws Exception;

}