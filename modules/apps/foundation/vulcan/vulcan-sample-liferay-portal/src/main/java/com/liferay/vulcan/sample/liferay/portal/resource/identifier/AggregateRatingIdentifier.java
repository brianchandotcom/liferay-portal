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

package com.liferay.vulcan.sample.liferay.portal.resource.identifier;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.vulcan.resource.identifier.Identifier;

/**
 * Represents an identifier for entities in an aggregate rating.
 *
 * <p>
 * This identifier should only be used to identify single items in {@link
 * com.liferay.vulcan.sample.liferay.portal.rating.AggregateRating}.
 * </p>
 *
 * @author Alejandro Hernández
 */
@ProviderType
public interface AggregateRatingIdentifier extends Identifier {

	/**
	 * Creates and returns a new aggregate rating identifier from a class name
	 * and class PK.
	 *
	 * @param  className the class name
	 * @param  classPK the class PK
	 * @return the new aggregate rating identifier
	 */
	public static AggregateRatingIdentifier create(
		String className, long classPK) {

		return new AggregateRatingIdentifier() {

			@Override
			public String getClassName() {
				return className;
			}

			@Override
			public long getClassPK() {
				return classPK;
			}

		};
	}

	/**
	 * Creates and returns a new aggregate rating identifier from a {@code
	 * ClassedModel}.
	 *
	 * @param  t the {@code ClassedModel}
	 * @return the new aggregate rating identifier
	 */
	public static <T extends ClassedModel> AggregateRatingIdentifier create(
		T t) {

		return create(t.getModelClassName(), (long)t.getPrimaryKeyObj());
	}

	/**
	 * Returns the aggregate rating identifier's class name.
	 *
	 * @return the aggregate rating identifier's class name
	 */
	public String getClassName();

	/**
	 * Returns the aggregate rating identifier's class PK.
	 *
	 * @return the aggregate rating identifier's class PK
	 */
	public long getClassPK();

}