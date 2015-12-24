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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RecentLayoutRevision}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutRevision
 * @generated
 */
@ProviderType
public class RecentLayoutRevisionWrapper implements RecentLayoutRevision,
	ModelWrapper<RecentLayoutRevision> {
	public RecentLayoutRevisionWrapper(
		RecentLayoutRevision recentLayoutRevision) {
		_recentLayoutRevision = recentLayoutRevision;
	}

	@Override
	public Class<?> getModelClass() {
		return RecentLayoutRevision.class;
	}

	@Override
	public String getModelClassName() {
		return RecentLayoutRevision.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("recentLayoutRevisionId", getRecentLayoutRevisionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("layoutRevisionId", getLayoutRevisionId());
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

		Long recentLayoutRevisionId = (Long)attributes.get(
				"recentLayoutRevisionId");

		if (recentLayoutRevisionId != null) {
			setRecentLayoutRevisionId(recentLayoutRevisionId);
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

		Long layoutRevisionId = (Long)attributes.get("layoutRevisionId");

		if (layoutRevisionId != null) {
			setLayoutRevisionId(layoutRevisionId);
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
	public java.lang.Object clone() {
		return new RecentLayoutRevisionWrapper((RecentLayoutRevision)_recentLayoutRevision.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.RecentLayoutRevision recentLayoutRevision) {
		return _recentLayoutRevision.compareTo(recentLayoutRevision);
	}

	/**
	* Returns the company ID of this recent layout revision.
	*
	* @return the company ID of this recent layout revision
	*/
	@Override
	public long getCompanyId() {
		return _recentLayoutRevision.getCompanyId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _recentLayoutRevision.getExpandoBridge();
	}

	/**
	* Returns the group ID of this recent layout revision.
	*
	* @return the group ID of this recent layout revision
	*/
	@Override
	public long getGroupId() {
		return _recentLayoutRevision.getGroupId();
	}

	/**
	* Returns the layout revision ID of this recent layout revision.
	*
	* @return the layout revision ID of this recent layout revision
	*/
	@Override
	public long getLayoutRevisionId() {
		return _recentLayoutRevision.getLayoutRevisionId();
	}

	/**
	* Returns the layout set branch ID of this recent layout revision.
	*
	* @return the layout set branch ID of this recent layout revision
	*/
	@Override
	public long getLayoutSetBranchId() {
		return _recentLayoutRevision.getLayoutSetBranchId();
	}

	/**
	* Returns the mvcc version of this recent layout revision.
	*
	* @return the mvcc version of this recent layout revision
	*/
	@Override
	public long getMvccVersion() {
		return _recentLayoutRevision.getMvccVersion();
	}

	/**
	* Returns the plid of this recent layout revision.
	*
	* @return the plid of this recent layout revision
	*/
	@Override
	public long getPlid() {
		return _recentLayoutRevision.getPlid();
	}

	/**
	* Returns the primary key of this recent layout revision.
	*
	* @return the primary key of this recent layout revision
	*/
	@Override
	public long getPrimaryKey() {
		return _recentLayoutRevision.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _recentLayoutRevision.getPrimaryKeyObj();
	}

	/**
	* Returns the recent layout revision ID of this recent layout revision.
	*
	* @return the recent layout revision ID of this recent layout revision
	*/
	@Override
	public long getRecentLayoutRevisionId() {
		return _recentLayoutRevision.getRecentLayoutRevisionId();
	}

	/**
	* Returns the user ID of this recent layout revision.
	*
	* @return the user ID of this recent layout revision
	*/
	@Override
	public long getUserId() {
		return _recentLayoutRevision.getUserId();
	}

	/**
	* Returns the user uuid of this recent layout revision.
	*
	* @return the user uuid of this recent layout revision
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _recentLayoutRevision.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _recentLayoutRevision.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _recentLayoutRevision.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _recentLayoutRevision.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _recentLayoutRevision.isNew();
	}

	@Override
	public void persist() {
		_recentLayoutRevision.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_recentLayoutRevision.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this recent layout revision.
	*
	* @param companyId the company ID of this recent layout revision
	*/
	@Override
	public void setCompanyId(long companyId) {
		_recentLayoutRevision.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_recentLayoutRevision.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_recentLayoutRevision.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_recentLayoutRevision.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this recent layout revision.
	*
	* @param groupId the group ID of this recent layout revision
	*/
	@Override
	public void setGroupId(long groupId) {
		_recentLayoutRevision.setGroupId(groupId);
	}

	/**
	* Sets the layout revision ID of this recent layout revision.
	*
	* @param layoutRevisionId the layout revision ID of this recent layout revision
	*/
	@Override
	public void setLayoutRevisionId(long layoutRevisionId) {
		_recentLayoutRevision.setLayoutRevisionId(layoutRevisionId);
	}

	/**
	* Sets the layout set branch ID of this recent layout revision.
	*
	* @param layoutSetBranchId the layout set branch ID of this recent layout revision
	*/
	@Override
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_recentLayoutRevision.setLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Sets the mvcc version of this recent layout revision.
	*
	* @param mvccVersion the mvcc version of this recent layout revision
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_recentLayoutRevision.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_recentLayoutRevision.setNew(n);
	}

	/**
	* Sets the plid of this recent layout revision.
	*
	* @param plid the plid of this recent layout revision
	*/
	@Override
	public void setPlid(long plid) {
		_recentLayoutRevision.setPlid(plid);
	}

	/**
	* Sets the primary key of this recent layout revision.
	*
	* @param primaryKey the primary key of this recent layout revision
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_recentLayoutRevision.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_recentLayoutRevision.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the recent layout revision ID of this recent layout revision.
	*
	* @param recentLayoutRevisionId the recent layout revision ID of this recent layout revision
	*/
	@Override
	public void setRecentLayoutRevisionId(long recentLayoutRevisionId) {
		_recentLayoutRevision.setRecentLayoutRevisionId(recentLayoutRevisionId);
	}

	/**
	* Sets the user ID of this recent layout revision.
	*
	* @param userId the user ID of this recent layout revision
	*/
	@Override
	public void setUserId(long userId) {
		_recentLayoutRevision.setUserId(userId);
	}

	/**
	* Sets the user uuid of this recent layout revision.
	*
	* @param userUuid the user uuid of this recent layout revision
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_recentLayoutRevision.setUserUuid(userUuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.RecentLayoutRevision> toCacheModel() {
		return _recentLayoutRevision.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.RecentLayoutRevision toEscapedModel() {
		return new RecentLayoutRevisionWrapper(_recentLayoutRevision.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _recentLayoutRevision.toString();
	}

	@Override
	public com.liferay.portal.model.RecentLayoutRevision toUnescapedModel() {
		return new RecentLayoutRevisionWrapper(_recentLayoutRevision.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _recentLayoutRevision.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RecentLayoutRevisionWrapper)) {
			return false;
		}

		RecentLayoutRevisionWrapper recentLayoutRevisionWrapper = (RecentLayoutRevisionWrapper)obj;

		if (Validator.equals(_recentLayoutRevision,
					recentLayoutRevisionWrapper._recentLayoutRevision)) {
			return true;
		}

		return false;
	}

	@Override
	public RecentLayoutRevision getWrappedModel() {
		return _recentLayoutRevision;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _recentLayoutRevision.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _recentLayoutRevision.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_recentLayoutRevision.resetOriginalValues();
	}

	private final RecentLayoutRevision _recentLayoutRevision;
}