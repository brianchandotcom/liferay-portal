/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Shard}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Shard
 * @generated
 */
public class ShardWrapper implements Shard, ModelWrapper<Shard> {
	public ShardWrapper(Shard shard) {
		_shard = shard;
	}

	public Class<?> getModelClass() {
		return Shard.class;
	}

	public String getModelClassName() {
		return Shard.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("shardId", getShardId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("name", getName());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long shardId = (Long)attributes.get("shardId");

		if (shardId != null) {
			setShardId(shardId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	/**
	* Returns the primary key of this shard.
	*
	* @return the primary key of this shard
	*/
	public long getPrimaryKey() {
		return _shard.getPrimaryKey();
	}

	/**
	* Sets the primary key of this shard.
	*
	* @param primaryKey the primary key of this shard
	*/
	public void setPrimaryKey(long primaryKey) {
		_shard.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the shard ID of this shard.
	*
	* @return the shard ID of this shard
	*/
	public long getShardId() {
		return _shard.getShardId();
	}

	/**
	* Sets the shard ID of this shard.
	*
	* @param shardId the shard ID of this shard
	*/
	public void setShardId(long shardId) {
		_shard.setShardId(shardId);
	}

	/**
	* Returns the fully qualified class name of this shard.
	*
	* @return the fully qualified class name of this shard
	*/
	public java.lang.String getClassName() {
		return _shard.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_shard.setClassName(className);
	}

	/**
	* Returns the class name ID of this shard.
	*
	* @return the class name ID of this shard
	*/
	public long getClassNameId() {
		return _shard.getClassNameId();
	}

	/**
	* Sets the class name ID of this shard.
	*
	* @param classNameId the class name ID of this shard
	*/
	public void setClassNameId(long classNameId) {
		_shard.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this shard.
	*
	* @return the class p k of this shard
	*/
	public long getClassPK() {
		return _shard.getClassPK();
	}

	/**
	* Sets the class p k of this shard.
	*
	* @param classPK the class p k of this shard
	*/
	public void setClassPK(long classPK) {
		_shard.setClassPK(classPK);
	}

	/**
	* Returns the name of this shard.
	*
	* @return the name of this shard
	*/
	public java.lang.String getName() {
		return _shard.getName();
	}

	/**
	* Sets the name of this shard.
	*
	* @param name the name of this shard
	*/
	public void setName(java.lang.String name) {
		_shard.setName(name);
	}

	public boolean isNew() {
		return _shard.isNew();
	}

	public void setNew(boolean n) {
		_shard.setNew(n);
	}

	public boolean isCachedModel() {
		return _shard.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shard.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shard.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shard.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shard.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shard.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_shard.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_shard.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shard.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ShardWrapper((Shard)_shard.clone());
	}

	public int compareTo(com.liferay.portal.model.Shard shard) {
		return _shard.compareTo(shard);
	}

	@Override
	public int hashCode() {
		return _shard.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Shard> toCacheModel() {
		return _shard.toCacheModel();
	}

	public com.liferay.portal.model.Shard toEscapedModel() {
		return new ShardWrapper(_shard.toEscapedModel());
	}

	public com.liferay.portal.model.Shard toUnescapedModel() {
		return new ShardWrapper(_shard.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _shard.toString();
	}

	public java.lang.String toXmlString() {
		return _shard.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_shard.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Shard getWrappedShard() {
		return _shard;
	}

	public Shard getWrappedModel() {
		return _shard;
	}

	public void resetOriginalValues() {
		_shard.resetOriginalValues();
	}

	private Shard _shard;
}