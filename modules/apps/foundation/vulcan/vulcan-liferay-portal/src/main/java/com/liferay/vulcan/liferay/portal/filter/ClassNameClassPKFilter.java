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

package com.liferay.vulcan.liferay.portal.filter;

import com.liferay.vulcan.filter.TypeIdFilter;

/**
 * Instances of this class represent a className-classPK filter.
 *
 * <p>
 * To use this class, add it as a parameter in {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder}
 * <code>filteredCollectionPage</code> methods.
 * </p>
 *
 * @author Alejandro Hernández
 */
public interface ClassNameClassPKFilter extends TypeIdFilter<Long> {

	/**
	 * Returns the className to filter.
	 *
	 * @return className to filter.
	 */
	public String getClassName();

	/**
	 * Returns the classPK to filter.
	 *
	 * @return classPK to filter.
	 */
	public Long getClassPK();

}