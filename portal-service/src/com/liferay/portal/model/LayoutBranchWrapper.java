/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutBranch}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranch
 * @generated
 */
@ProviderType
public class LayoutBranchWrapper implements LayoutBranch,
	ModelWrapper<LayoutBranch> {
	public LayoutBranchWrapper(LayoutBranch layoutBranch) {
		_layoutBranch = layoutBranch;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutBranch.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutBranch.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("LayoutBranchId", getLayoutBranchId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("layoutSetBranchId", getLayoutSetBranchId());
		attributes.put("plid", getPlid());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("master", getMaster());
		attributes.put("mvccVersion", getMvccVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long LayoutBranchId = (Long)attributes.get("LayoutBranchId");

		if (LayoutBranchId != null) {
			setLayoutBranchId(LayoutBranchId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Long layoutSetBranchId = (Long)attributes.get("layoutSetBranchId");

		if (layoutSetBranchId != null) {
			setLayoutSetBranchId(layoutSetBranchId);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean master = (Boolean)attributes.get("master");

		if (master != null) {
			setMaster(master);
		}

		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}
	}

	/**
	* Returns the primary key of this layout branch.
	*
	* @return the primary key of this layout branch
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutBranch.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout branch.
	*
	* @param primaryKey the primary key of this layout branch
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutBranch.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the layout branch ID of this layout branch.
	*
	* @return the layout branch ID of this layout branch
	*/
	@Override
	public long getLayoutBranchId() {
		return _layoutBranch.getLayoutBranchId();
	}

	/**
	* Sets the layout branch ID of this layout branch.
	*
	* @param LayoutBranchId the layout branch ID of this layout branch
	*/
	@Override
	public void setLayoutBranchId(long LayoutBranchId) {
		_layoutBranch.setLayoutBranchId(LayoutBranchId);
	}

	/**
	* Returns the group ID of this layout branch.
	*
	* @return the group ID of this layout branch
	*/
	@Override
	public long getGroupId() {
		return _layoutBranch.getGroupId();
	}

	/**
	* Sets the group ID of this layout branch.
	*
	* @param groupId the group ID of this layout branch
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutBranch.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this layout branch.
	*
	* @return the company ID of this layout branch
	*/
	@Override
	public long getCompanyId() {
		return _layoutBranch.getCompanyId();
	}

	/**
	* Sets the company ID of this layout branch.
	*
	* @param companyId the company ID of this layout branch
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutBranch.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this layout branch.
	*
	* @return the user ID of this layout branch
	*/
	@Override
	public long getUserId() {
		return _layoutBranch.getUserId();
	}

	/**
	* Sets the user ID of this layout branch.
	*
	* @param userId the user ID of this layout branch
	*/
	@Override
	public void setUserId(long userId) {
		_layoutBranch.setUserId(userId);
	}

	/**
	* Returns the user uuid of this layout branch.
	*
	* @return the user uuid of this layout branch
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutBranch.getUserUuid();
	}

	/**
	* Sets the user uuid of this layout branch.
	*
	* @param userUuid the user uuid of this layout branch
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutBranch.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this layout branch.
	*
	* @return the user name of this layout branch
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutBranch.getUserName();
	}

	/**
	* Sets the user name of this layout branch.
	*
	* @param userName the user name of this layout branch
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutBranch.setUserName(userName);
	}

	/**
	* Returns the layout set branch ID of this layout branch.
	*
	* @return the layout set branch ID of this layout branch
	*/
	@Override
	public long getLayoutSetBranchId() {
		return _layoutBranch.getLayoutSetBranchId();
	}

	/**
	* Sets the layout set branch ID of this layout branch.
	*
	* @param layoutSetBranchId the layout set branch ID of this layout branch
	*/
	@Override
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutBranch.setLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns the plid of this layout branch.
	*
	* @return the plid of this layout branch
	*/
	@Override
	public long getPlid() {
		return _layoutBranch.getPlid();
	}

	/**
	* Sets the plid of this layout branch.
	*
	* @param plid the plid of this layout branch
	*/
	@Override
	public void setPlid(long plid) {
		_layoutBranch.setPlid(plid);
	}

	/**
	* Returns the name of this layout branch.
	*
	* @return the name of this layout branch
	*/
	@Override
	public java.lang.String getName() {
		return _layoutBranch.getName();
	}

	/**
	* Sets the name of this layout branch.
	*
	* @param name the name of this layout branch
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutBranch.setName(name);
	}

	/**
	* Returns the description of this layout branch.
	*
	* @return the description of this layout branch
	*/
	@Override
	public java.lang.String getDescription() {
		return _layoutBranch.getDescription();
	}

	/**
	* Sets the description of this layout branch.
	*
	* @param description the description of this layout branch
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_layoutBranch.setDescription(description);
	}

	/**
	* Returns the master of this layout branch.
	*
	* @return the master of this layout branch
	*/
	@Override
	public boolean getMaster() {
		return _layoutBranch.getMaster();
	}

	/**
	* Returns <code>true</code> if this layout branch is master.
	*
	* @return <code>true</code> if this layout branch is master; <code>false</code> otherwise
	*/
	@Override
	public boolean isMaster() {
		return _layoutBranch.isMaster();
	}

	/**
	* Sets whether this layout branch is master.
	*
	* @param master the master of this layout branch
	*/
	@Override
	public void setMaster(boolean master) {
		_layoutBranch.setMaster(master);
	}

	/**
	* Returns the mvcc version of this layout branch.
	*
	* @return the mvcc version of this layout branch
	*/
	@Override
	public long getMvccVersion() {
		return _layoutBranch.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this layout branch.
	*
	* @param mvccVersion the mvcc version of this layout branch
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_layoutBranch.setMvccVersion(mvccVersion);
	}

	@Override
	public boolean isNew() {
		return _layoutBranch.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_layoutBranch.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _layoutBranch.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutBranch.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutBranch.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutBranch.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutBranch.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutBranch.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_layoutBranch.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_layoutBranch.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutBranch.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutBranchWrapper((LayoutBranch)_layoutBranch.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.LayoutBranch layoutBranch) {
		return _layoutBranch.compareTo(layoutBranch);
	}

	@Override
	public int hashCode() {
		return _layoutBranch.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.LayoutBranch> toCacheModel() {
		return _layoutBranch.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.LayoutBranch toEscapedModel() {
		return new LayoutBranchWrapper(_layoutBranch.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.LayoutBranch toUnescapedModel() {
		return new LayoutBranchWrapper(_layoutBranch.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutBranch.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutBranch.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutBranch.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutBranchWrapper)) {
			return false;
		}

		LayoutBranchWrapper layoutBranchWrapper = (LayoutBranchWrapper)obj;

		if (Validator.equals(_layoutBranch, layoutBranchWrapper._layoutBranch)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public LayoutBranch getWrappedLayoutBranch() {
		return _layoutBranch;
	}

	@Override
	public LayoutBranch getWrappedModel() {
		return _layoutBranch;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutBranch.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutBranch.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutBranch.resetOriginalValues();
	}

	private LayoutBranch _layoutBranch;
}