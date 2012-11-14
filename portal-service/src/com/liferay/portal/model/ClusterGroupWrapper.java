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
 * This class is a wrapper for {@link ClusterGroup}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClusterGroup
 * @generated
 */
public class ClusterGroupWrapper implements ClusterGroup,
	ModelWrapper<ClusterGroup> {
	public ClusterGroupWrapper(ClusterGroup clusterGroup) {
		_clusterGroup = clusterGroup;
	}

	public Class<?> getModelClass() {
		return ClusterGroup.class;
	}

	public String getModelClassName() {
		return ClusterGroup.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("clusterGroupId", getClusterGroupId());
		attributes.put("name", getName());
		attributes.put("clusterNodeIds", getClusterNodeIds());
		attributes.put("wholeCluster", getWholeCluster());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long clusterGroupId = (Long)attributes.get("clusterGroupId");

		if (clusterGroupId != null) {
			setClusterGroupId(clusterGroupId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String clusterNodeIds = (String)attributes.get("clusterNodeIds");

		if (clusterNodeIds != null) {
			setClusterNodeIds(clusterNodeIds);
		}

		Boolean wholeCluster = (Boolean)attributes.get("wholeCluster");

		if (wholeCluster != null) {
			setWholeCluster(wholeCluster);
		}
	}

	/**
	* Returns the primary key of this cluster group.
	*
	* @return the primary key of this cluster group
	*/
	public long getPrimaryKey() {
		return _clusterGroup.getPrimaryKey();
	}

	/**
	* Sets the primary key of this cluster group.
	*
	* @param primaryKey the primary key of this cluster group
	*/
	public void setPrimaryKey(long primaryKey) {
		_clusterGroup.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the cluster group ID of this cluster group.
	*
	* @return the cluster group ID of this cluster group
	*/
	public long getClusterGroupId() {
		return _clusterGroup.getClusterGroupId();
	}

	/**
	* Sets the cluster group ID of this cluster group.
	*
	* @param clusterGroupId the cluster group ID of this cluster group
	*/
	public void setClusterGroupId(long clusterGroupId) {
		_clusterGroup.setClusterGroupId(clusterGroupId);
	}

	/**
	* Returns the name of this cluster group.
	*
	* @return the name of this cluster group
	*/
	public java.lang.String getName() {
		return _clusterGroup.getName();
	}

	/**
	* Sets the name of this cluster group.
	*
	* @param name the name of this cluster group
	*/
	public void setName(java.lang.String name) {
		_clusterGroup.setName(name);
	}

	/**
	* Returns the cluster node IDs of this cluster group.
	*
	* @return the cluster node IDs of this cluster group
	*/
	public java.lang.String getClusterNodeIds() {
		return _clusterGroup.getClusterNodeIds();
	}

	/**
	* Sets the cluster node IDs of this cluster group.
	*
	* @param clusterNodeIds the cluster node IDs of this cluster group
	*/
	public void setClusterNodeIds(java.lang.String clusterNodeIds) {
		_clusterGroup.setClusterNodeIds(clusterNodeIds);
	}

	/**
	* Returns the whole cluster of this cluster group.
	*
	* @return the whole cluster of this cluster group
	*/
	public boolean getWholeCluster() {
		return _clusterGroup.getWholeCluster();
	}

	/**
	* Returns <code>true</code> if this cluster group is whole cluster.
	*
	* @return <code>true</code> if this cluster group is whole cluster; <code>false</code> otherwise
	*/
	public boolean isWholeCluster() {
		return _clusterGroup.isWholeCluster();
	}

	/**
	* Sets whether this cluster group is whole cluster.
	*
	* @param wholeCluster the whole cluster of this cluster group
	*/
	public void setWholeCluster(boolean wholeCluster) {
		_clusterGroup.setWholeCluster(wholeCluster);
	}

	public boolean isNew() {
		return _clusterGroup.isNew();
	}

	public void setNew(boolean n) {
		_clusterGroup.setNew(n);
	}

	public boolean isCachedModel() {
		return _clusterGroup.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_clusterGroup.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _clusterGroup.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _clusterGroup.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_clusterGroup.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _clusterGroup.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_clusterGroup.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ClusterGroupWrapper((ClusterGroup)_clusterGroup.clone());
	}

	public int compareTo(com.liferay.portal.model.ClusterGroup clusterGroup) {
		return _clusterGroup.compareTo(clusterGroup);
	}

	@Override
	public int hashCode() {
		return _clusterGroup.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ClusterGroup> toCacheModel() {
		return _clusterGroup.toCacheModel();
	}

	public com.liferay.portal.model.ClusterGroup toEscapedModel() {
		return new ClusterGroupWrapper(_clusterGroup.toEscapedModel());
	}

	public com.liferay.portal.model.ClusterGroup toUnescapedModel() {
		return new ClusterGroupWrapper(_clusterGroup.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _clusterGroup.toString();
	}

	public java.lang.String toXmlString() {
		return _clusterGroup.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_clusterGroup.persist();
	}

	public java.lang.String[] getClusterNodeIdsArray() {
		return _clusterGroup.getClusterNodeIdsArray();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public ClusterGroup getWrappedClusterGroup() {
		return _clusterGroup;
	}

	public ClusterGroup getWrappedModel() {
		return _clusterGroup;
	}

	public void resetOriginalValues() {
		_clusterGroup.resetOriginalValues();
	}

	private ClusterGroup _clusterGroup;
}