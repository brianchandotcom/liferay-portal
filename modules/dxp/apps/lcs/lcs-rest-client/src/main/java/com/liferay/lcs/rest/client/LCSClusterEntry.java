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

package com.liferay.lcs.rest.client;

/**
 * @author Igor Beslic
 * @author Ivica Cardic
 */
public class LCSClusterEntry {

	public boolean getArchived() {
		return _archived;
	}

	public String getDescription() {
		return _description;
	}

	public boolean getElastic() {
		return _elastic;
	}

	public long getLcsClusterEntryId() {
		return _lcsClusterEntryId;
	}

	public long getLcsProjectId() {
		return _lcsProjectId;
	}

	public String getLocation() {
		return _location;
	}

	public String getName() {
		return _name;
	}

	public String getSubscriptionType() {
		return _subscriptionType;
	}

	public int getType() {
		return _type;
	}

	public boolean isArchived() {
		return _archived;
	}

	public boolean isElastic() {
		return _elastic;
	}

	public void setArchived(boolean archived) {
		_archived = archived;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setElastic(boolean elastic) {
		_elastic = elastic;
	}

	public void setLcsClusterEntryId(long lcsClusterEntryId) {
		_lcsClusterEntryId = lcsClusterEntryId;
	}

	public void setLcsProjectId(long lcsProjectId) {
		_lcsProjectId = lcsProjectId;
	}

	public void setLocation(String location) {
		_location = location;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSubscriptionType(String subscriptionType) {
		_subscriptionType = subscriptionType;
	}

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