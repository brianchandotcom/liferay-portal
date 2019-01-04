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
 * This class is a wrapper for {@link ClusterGroup}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClusterGroup
 * @generated
 */
@ProviderType
public class ClusterGroupWrapper extends BaseModelWrapper<ClusterGroup>
	implements ClusterGroup, ModelWrapper<ClusterGroup> {
	public ClusterGroupWrapper(ClusterGroup clusterGroup) {
		super(clusterGroup);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("clusterGroupId", getClusterGroupId());
		attributes.put("name", getName());
		attributes.put("clusterNodeIds", getClusterNodeIds());
		attributes.put("wholeCluster", isWholeCluster());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	* Returns the cluster group ID of this cluster group.
	*
	* @return the cluster group ID of this cluster group
	*/
	@Override
	public long getClusterGroupId() {
		return model.getClusterGroupId();
	}

	/**
	* Returns the cluster node IDs of this cluster group.
	*
	* @return the cluster node IDs of this cluster group
	*/
	@Override
	public String getClusterNodeIds() {
		return model.getClusterNodeIds();
	}

	@Override
	public String[] getClusterNodeIdsArray() {
		return model.getClusterNodeIdsArray();
	}

	/**
	* Returns the mvcc version of this cluster group.
	*
	* @return the mvcc version of this cluster group
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the name of this cluster group.
	*
	* @return the name of this cluster group
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the primary key of this cluster group.
	*
	* @return the primary key of this cluster group
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the whole cluster of this cluster group.
	*
	* @return the whole cluster of this cluster group
	*/
	@Override
	public boolean getWholeCluster() {
		return model.getWholeCluster();
	}

	/**
	* Returns <code>true</code> if this cluster group is whole cluster.
	*
	* @return <code>true</code> if this cluster group is whole cluster; <code>false</code> otherwise
	*/
	@Override
	public boolean isWholeCluster() {
		return model.isWholeCluster();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the cluster group ID of this cluster group.
	*
	* @param clusterGroupId the cluster group ID of this cluster group
	*/
	@Override
	public void setClusterGroupId(long clusterGroupId) {
		model.setClusterGroupId(clusterGroupId);
	}

	/**
	* Sets the cluster node IDs of this cluster group.
	*
	* @param clusterNodeIds the cluster node IDs of this cluster group
	*/
	@Override
	public void setClusterNodeIds(String clusterNodeIds) {
		model.setClusterNodeIds(clusterNodeIds);
	}

	/**
	* Sets the mvcc version of this cluster group.
	*
	* @param mvccVersion the mvcc version of this cluster group
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this cluster group.
	*
	* @param name the name of this cluster group
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the primary key of this cluster group.
	*
	* @param primaryKey the primary key of this cluster group
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets whether this cluster group is whole cluster.
	*
	* @param wholeCluster the whole cluster of this cluster group
	*/
	@Override
	public void setWholeCluster(boolean wholeCluster) {
		model.setWholeCluster(wholeCluster);
	}

	@Override
	protected ClusterGroupWrapper wrap(ClusterGroup clusterGroup) {
		return new ClusterGroupWrapper(clusterGroup);
	}
}