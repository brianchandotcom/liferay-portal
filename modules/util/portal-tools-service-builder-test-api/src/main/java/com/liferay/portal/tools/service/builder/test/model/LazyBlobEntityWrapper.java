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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.sql.Blob;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LazyBlobEntity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntity
 * @generated
 */
public class LazyBlobEntityWrapper
	extends BaseModelWrapper<LazyBlobEntity>
	implements LazyBlobEntity, ModelWrapper<LazyBlobEntity> {

	public LazyBlobEntityWrapper(LazyBlobEntity lazyBlobEntity) {
		super(lazyBlobEntity);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("lazyBlobEntityId", getLazyBlobEntityId());
		attributes.put("groupId", getGroupId());
		attributes.put("blob1", getBlob1());
		attributes.put("blob2", getBlob2());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long lazyBlobEntityId = (Long)attributes.get("lazyBlobEntityId");

		if (lazyBlobEntityId != null) {
			setLazyBlobEntityId(lazyBlobEntityId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Blob blob1 = (Blob)attributes.get("blob1");

		if (blob1 != null) {
			setBlob1(blob1);
		}

		Blob blob2 = (Blob)attributes.get("blob2");

		if (blob2 != null) {
			setBlob2(blob2);
		}
	}

	/**
	 * Returns the blob1 of this lazy blob entity.
	 *
	 * @return the blob1 of this lazy blob entity
	 */
	@Override
	public Blob getBlob1() {
		return model.getBlob1();
	}

	/**
	 * Returns the blob2 of this lazy blob entity.
	 *
	 * @return the blob2 of this lazy blob entity
	 */
	@Override
	public Blob getBlob2() {
		return model.getBlob2();
	}

	/**
	 * Returns the group ID of this lazy blob entity.
	 *
	 * @return the group ID of this lazy blob entity
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the lazy blob entity ID of this lazy blob entity.
	 *
	 * @return the lazy blob entity ID of this lazy blob entity
	 */
	@Override
	public long getLazyBlobEntityId() {
		return model.getLazyBlobEntityId();
	}

	/**
	 * Returns the primary key of this lazy blob entity.
	 *
	 * @return the primary key of this lazy blob entity
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this lazy blob entity.
	 *
	 * @return the uuid of this lazy blob entity
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a lazy blob entity model instance should use the <code>LazyBlobEntity</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the blob1 of this lazy blob entity.
	 *
	 * @param blob1 the blob1 of this lazy blob entity
	 */
	@Override
	public void setBlob1(Blob blob1) {
		model.setBlob1(blob1);
	}

	/**
	 * Sets the blob2 of this lazy blob entity.
	 *
	 * @param blob2 the blob2 of this lazy blob entity
	 */
	@Override
	public void setBlob2(Blob blob2) {
		model.setBlob2(blob2);
	}

	/**
	 * Sets the group ID of this lazy blob entity.
	 *
	 * @param groupId the group ID of this lazy blob entity
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the lazy blob entity ID of this lazy blob entity.
	 *
	 * @param lazyBlobEntityId the lazy blob entity ID of this lazy blob entity
	 */
	@Override
	public void setLazyBlobEntityId(long lazyBlobEntityId) {
		model.setLazyBlobEntityId(lazyBlobEntityId);
	}

	/**
	 * Sets the primary key of this lazy blob entity.
	 *
	 * @param primaryKey the primary key of this lazy blob entity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this lazy blob entity.
	 *
	 * @param uuid the uuid of this lazy blob entity
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected LazyBlobEntityWrapper wrap(LazyBlobEntity lazyBlobEntity) {
		return new LazyBlobEntityWrapper(lazyBlobEntity);
	}

}