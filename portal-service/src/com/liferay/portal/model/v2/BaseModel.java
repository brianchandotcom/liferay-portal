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

package com.liferay.portal.model.v2;

import aQute.bnd.annotation.ProviderType;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

/**
 * The base interface for all model classes. This interface should never need to
 * be used directly.
 *
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.model.impl.BaseModelImpl
 */
@ProviderType
public interface BaseModel<T>
	extends Cloneable, Comparable<T>, Serializable {

	/**
	 * Creates a shallow clone of this model instance.
	 *
	 * @return the shallow clone of this model instance
	 */
	public Object clone();

	/**
	 * Returns the primary key of this model instance.
	 *
	 * @return the primary key of this model instance
	 */
	public Serializable getPrimaryKeyObj();

	/**
	 * Returns <code>true</code> if this model instance does not yet exist in
	 * the database.
	 *
	 * @return <code>true</code> if this model instance does not yet exist in
	 *         the database; <code>false</code> otherwise
	 */
	public boolean isNew();

	/**
	 * Reset all original fields to current values.
	 */
	public void resetOriginalValues();

	/**
	 * Sets the primary key of this model instance.
	 *
	 * @param primaryKeyObj the primary key of this model instance
	 */
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public Set<ConstraintViolation<T>> validate();

}