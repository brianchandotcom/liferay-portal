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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link RecentLayoutBranch}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutBranch
 * @generated
 */
@ProviderType
public class RecentLayoutBranchWrapper implements RecentLayoutBranch,
	ModelWrapper<RecentLayoutBranch> {
	public RecentLayoutBranchWrapper(RecentLayoutBranch recentLayoutBranch) {
		_recentLayoutBranch = recentLayoutBranch;
	}

	@Override
	public Class<?> getModelClass() {
		return RecentLayoutBranch.class;
	}

	@Override
	public String getModelClassName() {
		return RecentLayoutBranch.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("recentLayoutBranchId", getRecentLayoutBranchId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("layoutBranchId", getLayoutBranchId());
		attributes.put("layoutSetBranchId", getLayoutSetBranchId());
		attributes.put("plid", getPlid());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long recentLayoutBranchId = (Long)attributes.get("recentLayoutBranchId");

		if (recentLayoutBranchId != null) {
			setRecentLayoutBranchId(recentLayoutBranchId);
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

		Long layoutBranchId = (Long)attributes.get("layoutBranchId");

		if (layoutBranchId != null) {
			setLayoutBranchId(layoutBranchId);
		}

		Long layoutSetBranchId = (Long)attributes.get("layoutSetBranchId");

		if (layoutSetBranchId != null) {
			setLayoutSetBranchId(layoutSetBranchId);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}
	}

	@Override
	public CacheModel<com.liferay.portal.kernel.model.RecentLayoutBranch> toCacheModel() {
		return _recentLayoutBranch.toCacheModel();
	}

	@Override
	public boolean isCachedModel() {
		return _recentLayoutBranch.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _recentLayoutBranch.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _recentLayoutBranch.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _recentLayoutBranch.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.RecentLayoutBranch toEscapedModel() {
		return new RecentLayoutBranchWrapper(_recentLayoutBranch.toEscapedModel());
	}

	@Override
	public com.liferay.portal.kernel.model.RecentLayoutBranch toUnescapedModel() {
		return new RecentLayoutBranchWrapper(_recentLayoutBranch.toUnescapedModel());
	}

	@Override
	public int compareTo(
		com.liferay.portal.kernel.model.RecentLayoutBranch recentLayoutBranch) {
		return _recentLayoutBranch.compareTo(recentLayoutBranch);
	}

	@Override
	public int hashCode() {
		return _recentLayoutBranch.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _recentLayoutBranch.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new RecentLayoutBranchWrapper((RecentLayoutBranch)_recentLayoutBranch.clone());
	}

	/**
	* Returns the user uuid of this recent layout branch.
	*
	* @return the user uuid of this recent layout branch
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _recentLayoutBranch.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _recentLayoutBranch.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _recentLayoutBranch.toXmlString();
	}

	/**
	* Returns the company ID of this recent layout branch.
	*
	* @return the company ID of this recent layout branch
	*/
	@Override
	public long getCompanyId() {
		return _recentLayoutBranch.getCompanyId();
	}

	/**
	* Returns the group ID of this recent layout branch.
	*
	* @return the group ID of this recent layout branch
	*/
	@Override
	public long getGroupId() {
		return _recentLayoutBranch.getGroupId();
	}

	/**
	* Returns the layout branch ID of this recent layout branch.
	*
	* @return the layout branch ID of this recent layout branch
	*/
	@Override
	public long getLayoutBranchId() {
		return _recentLayoutBranch.getLayoutBranchId();
	}

	/**
	* Returns the layout set branch ID of this recent layout branch.
	*
	* @return the layout set branch ID of this recent layout branch
	*/
	@Override
	public long getLayoutSetBranchId() {
		return _recentLayoutBranch.getLayoutSetBranchId();
	}

	/**
	* Returns the mvcc version of this recent layout branch.
	*
	* @return the mvcc version of this recent layout branch
	*/
	@Override
	public long getMvccVersion() {
		return _recentLayoutBranch.getMvccVersion();
	}

	/**
	* Returns the plid of this recent layout branch.
	*
	* @return the plid of this recent layout branch
	*/
	@Override
	public long getPlid() {
		return _recentLayoutBranch.getPlid();
	}

	/**
	* Returns the primary key of this recent layout branch.
	*
	* @return the primary key of this recent layout branch
	*/
	@Override
	public long getPrimaryKey() {
		return _recentLayoutBranch.getPrimaryKey();
	}

	/**
	* Returns the recent layout branch ID of this recent layout branch.
	*
	* @return the recent layout branch ID of this recent layout branch
	*/
	@Override
	public long getRecentLayoutBranchId() {
		return _recentLayoutBranch.getRecentLayoutBranchId();
	}

	/**
	* Returns the user ID of this recent layout branch.
	*
	* @return the user ID of this recent layout branch
	*/
	@Override
	public long getUserId() {
		return _recentLayoutBranch.getUserId();
	}

	@Override
	public void persist() {
		_recentLayoutBranch.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_recentLayoutBranch.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this recent layout branch.
	*
	* @param companyId the company ID of this recent layout branch
	*/
	@Override
	public void setCompanyId(long companyId) {
		_recentLayoutBranch.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_recentLayoutBranch.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_recentLayoutBranch.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_recentLayoutBranch.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this recent layout branch.
	*
	* @param groupId the group ID of this recent layout branch
	*/
	@Override
	public void setGroupId(long groupId) {
		_recentLayoutBranch.setGroupId(groupId);
	}

	/**
	* Sets the layout branch ID of this recent layout branch.
	*
	* @param layoutBranchId the layout branch ID of this recent layout branch
	*/
	@Override
	public void setLayoutBranchId(long layoutBranchId) {
		_recentLayoutBranch.setLayoutBranchId(layoutBranchId);
	}

	/**
	* Sets the layout set branch ID of this recent layout branch.
	*
	* @param layoutSetBranchId the layout set branch ID of this recent layout branch
	*/
	@Override
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_recentLayoutBranch.setLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Sets the mvcc version of this recent layout branch.
	*
	* @param mvccVersion the mvcc version of this recent layout branch
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_recentLayoutBranch.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_recentLayoutBranch.setNew(n);
	}

	/**
	* Sets the plid of this recent layout branch.
	*
	* @param plid the plid of this recent layout branch
	*/
	@Override
	public void setPlid(long plid) {
		_recentLayoutBranch.setPlid(plid);
	}

	/**
	* Sets the primary key of this recent layout branch.
	*
	* @param primaryKey the primary key of this recent layout branch
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_recentLayoutBranch.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_recentLayoutBranch.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the recent layout branch ID of this recent layout branch.
	*
	* @param recentLayoutBranchId the recent layout branch ID of this recent layout branch
	*/
	@Override
	public void setRecentLayoutBranchId(long recentLayoutBranchId) {
		_recentLayoutBranch.setRecentLayoutBranchId(recentLayoutBranchId);
	}

	/**
	* Sets the user ID of this recent layout branch.
	*
	* @param userId the user ID of this recent layout branch
	*/
	@Override
	public void setUserId(long userId) {
		_recentLayoutBranch.setUserId(userId);
	}

	/**
	* Sets the user uuid of this recent layout branch.
	*
	* @param userUuid the user uuid of this recent layout branch
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_recentLayoutBranch.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RecentLayoutBranchWrapper)) {
			return false;
		}

		RecentLayoutBranchWrapper recentLayoutBranchWrapper = (RecentLayoutBranchWrapper)obj;

		if (Objects.equals(_recentLayoutBranch,
					recentLayoutBranchWrapper._recentLayoutBranch)) {
			return true;
		}

		return false;
	}

	@Override
	public RecentLayoutBranch getWrappedModel() {
		return _recentLayoutBranch;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _recentLayoutBranch.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _recentLayoutBranch.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_recentLayoutBranch.resetOriginalValues();
	}

	private final RecentLayoutBranch _recentLayoutBranch;
}