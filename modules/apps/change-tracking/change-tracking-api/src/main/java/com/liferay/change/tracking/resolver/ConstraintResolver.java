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

package com.liferay.change.tracking.resolver;

import com.liferay.change.tracking.resolver.helper.ConstraintResolverHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Preston Crary
 */
public interface ConstraintResolver<T extends CTModel<T>> {

	public String getConflictDescriptionKey();

	public Class<T> getModelClass();

	public String getResolutionDescriptionKey();

	public ResourceBundle getResourceBundle(Locale locale);

	public String[] getUniqueIndexColumnNames();

	public void resolveConflict(
			ConstraintResolverHelper<T> constraintResolverHelper)
		throws PortalException;

}