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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ResourceBlock}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlock
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public class ResourceBlockWrapper extends BaseModelWrapper<ResourceBlock>
	implements ResourceBlock, ModelWrapper<ResourceBlock> {
	public ResourceBlockWrapper(ResourceBlock resourceBlock) {
		super(resourceBlock);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("resourceBlockId", getResourceBlockId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("name", getName());
		attributes.put("permissionsHash", getPermissionsHash());
		attributes.put("referenceCount", getReferenceCount());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long resourceBlockId = (Long)attributes.get("resourceBlockId");

		if (resourceBlockId != null) {
			setResourceBlockId(resourceBlockId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String permissionsHash = (String)attributes.get("permissionsHash");

		if (permissionsHash != null) {
			setPermissionsHash(permissionsHash);
		}

		Long referenceCount = (Long)attributes.get("referenceCount");

		if (referenceCount != null) {
			setReferenceCount(referenceCount);
		}
	}

	/**
	* Returns the company ID of this resource block.
	*
	* @return the company ID of this resource block
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the group ID of this resource block.
	*
	* @return the group ID of this resource block
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the mvcc version of this resource block.
	*
	* @return the mvcc version of this resource block
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the name of this resource block.
	*
	* @return the name of this resource block
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the permissions hash of this resource block.
	*
	* @return the permissions hash of this resource block
	*/
	@Override
	public String getPermissionsHash() {
		return model.getPermissionsHash();
	}

	/**
	* Returns the primary key of this resource block.
	*
	* @return the primary key of this resource block
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the reference count of this resource block.
	*
	* @return the reference count of this resource block
	*/
	@Override
	public long getReferenceCount() {
		return model.getReferenceCount();
	}

	/**
	* Returns the resource block ID of this resource block.
	*
	* @return the resource block ID of this resource block
	*/
	@Override
	public long getResourceBlockId() {
		return model.getResourceBlockId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this resource block.
	*
	* @param companyId the company ID of this resource block
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the group ID of this resource block.
	*
	* @param groupId the group ID of this resource block
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the mvcc version of this resource block.
	*
	* @param mvccVersion the mvcc version of this resource block
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this resource block.
	*
	* @param name the name of this resource block
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the permissions hash of this resource block.
	*
	* @param permissionsHash the permissions hash of this resource block
	*/
	@Override
	public void setPermissionsHash(String permissionsHash) {
		model.setPermissionsHash(permissionsHash);
	}

	/**
	* Sets the primary key of this resource block.
	*
	* @param primaryKey the primary key of this resource block
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the reference count of this resource block.
	*
	* @param referenceCount the reference count of this resource block
	*/
	@Override
	public void setReferenceCount(long referenceCount) {
		model.setReferenceCount(referenceCount);
	}

	/**
	* Sets the resource block ID of this resource block.
	*
	* @param resourceBlockId the resource block ID of this resource block
	*/
	@Override
	public void setResourceBlockId(long resourceBlockId) {
		model.setResourceBlockId(resourceBlockId);
	}

	@Override
	protected ResourceBlockWrapper wrap(ResourceBlock resourceBlock) {
		return new ResourceBlockWrapper(resourceBlock);
	}
}