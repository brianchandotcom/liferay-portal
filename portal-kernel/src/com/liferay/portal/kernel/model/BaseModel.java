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

package com.liferay.portal.kernel.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Map;

/**
 * The base interface for all model classes. This interface should never need to
 * be used directly.
 *
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.kernel.model.impl.BaseModelImpl
 */
public interface BaseModel<T>
	extends ClassedModel, Cloneable, Comparable<T>, Serializable {

	/**
	 * Creates a shallow clone of this model instance.
	 *
	 * @return the shallow clone of this model instance
	 */
	public Object clone();

	/**
	 * Returns the expando bridge for this model instance.
	 *
	 * @return the expando bridge for this model instance
	 */
	@Override
	public ExpandoBridge getExpandoBridge();

	public Map<String, Object> getModelAttributes();

	/**
	 * Returns the primary key of this model instance.
	 *
	 * @return the primary key of this model instance
	 */
	@Override
	public Serializable getPrimaryKeyObj();

	/**
	 * Returns <code>true</code> if this model instance was retrieved from the
	 * entity cache.
	 *
	 * @return <code>true</code> if this model instance was retrieved from the
	 *         entity cache; <code>false</code> otherwise
	 * @see    #setCachedModel(boolean)
	 */
	public boolean isCachedModel();

	/**
	 * Returns <code>true</code> if this model's entity cache is enabled.
	 *
	 * @return <code>true</code> if this model's entity cache is enabled;
	 *         <code>false</code> otherwise
	 */
	public boolean isEntityCacheEnabled();

	/**
	 * Returns <code>true</code> if this model instance is escaped.
	 *
	 * @return <code>true</code> if this model instance is escaped;
	 *         <code>false</code> otherwise
	 */
	public boolean isEscapedModel();

	/**
	 * Returns <code>true</code> if this model's finder cache is enabled.
	 *
	 * @return <code>true</code> if this model's finder cache is enabled;
	 *         <code>false</code> otherwise
	 */
	public boolean isFinderCacheEnabled();

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
	 * Sets whether this model instance was retrieved from the entity cache.
	 *
	 * @param cachedModel whether this model instance was retrieved from the
	 *        entity cache
	 * @see   com.liferay.portal.kernel.dao.orm.EntityCache
	 */
	public void setCachedModel(boolean cachedModel);

	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	/**
	 * Sets the expando bridge attributes for this model instance to the
	 * attributes stored in the service context.
	 *
	 * @param serviceContext the service context to be applied
	 * @see   ServiceContext#getExpandoBridgeAttributes()
	 */
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public void setModelAttributes(Map<String, Object> attributes);

	/**
	 * Sets whether this model instance does not yet exist in the database.
	 *
	 * @param n whether this model instance does not yet exist in the database
	 */
	public void setNew(boolean n);

	/**
	 * Sets the primary key of this model instance.
	 *
	 * @param primaryKeyObj the primary key of this model instance
	 */
	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	/**
	 * Returns a cache model object for this entity used by entity cache.
	 *
	 * @return the cache model object
	 */
	public CacheModel<T> toCacheModel();

	/**
	 * Returns a copy of this entity as an escaped model instance by wrapping it
	 * with an {@link com.liferay.portal.kernel.bean.AutoEscapeBeanHandler}.
	 *
	 * @return the escaped model instance
	 * @see    com.liferay.portal.kernel.bean.AutoEscapeBeanHandler
	 */
	public T toEscapedModel();

	public T toUnescapedModel();

	/**
	 * Returns the XML representation of this model instance.
	 *
	 * @return the XML representation of this model instance
	 */
	public String toXmlString();

}