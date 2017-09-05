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

package com.liferay.lcs.rest;

import com.liferay.lcs.util.LCSConstants;

/**
 * @author Igor Beslic
 * @author Ivica Cardic
 */
public class LCSClusterEntryImpl implements LCSClusterEntry {

	@Override
	public boolean getArchived() {
		return _archived;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public boolean getElastic() {
		return _elastic;
	}

	@Override
	public long getLcsClusterEntryId() {
		return _lcsClusterEntryId;
	}

	@Override
	public long getLcsProjectId() {
		return _lcsProjectId;
	}

	@Override
	public String getLocation() {
		return _location;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getSubscriptionType() {
		return _subscriptionType;
	}

	@Override
	public int getType() {
		return _type;
	}

	@Override
	public boolean isArchived() {
		return _archived;
	}

	@Override
	public boolean isCluster() {
		if (getType() == LCSConstants.LCS_CLUSTER_ENTRY_TYPE_CLUSTER) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isElastic() {
		return _elastic;
	}

	@Override
	public boolean isEnvironment() {
		if (getType() == LCSConstants.LCS_CLUSTER_ENTRY_TYPE_ENVIRONMENT) {
			return true;
		}

		return false;
	}

	@Override
	public void setArchived(boolean archived) {
		_archived = archived;
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public void setElastic(boolean elastic) {
		_elastic = elastic;
	}

	@Override
	public void setLcsClusterEntryId(long lcsClusterEntryId) {
		_lcsClusterEntryId = lcsClusterEntryId;
	}

	@Override
	public void setLcsProjectId(long lcsProjectId) {
		_lcsProjectId = lcsProjectId;
	}

	@Override
	public void setLocation(String location) {
		_location = location;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setSubscriptionType(String subscriptionType) {
		_subscriptionType = subscriptionType;
	}

	@Override
	public void setType(int type) {
		_type = type;
	}

	private boolean _archived;
	private String _description;
	private boolean _elastic;
	private long _lcsClusterEntryId;
	private long _lcsProjectId;
	private String _location;
	private String _name;
	private String _subscriptionType;
	private int _type;

}