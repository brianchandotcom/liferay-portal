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

package com.liferay.portal.messaging;

import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

/**
 * @author Mate Thurzo
 */
public class BlogsStatsUpdateRequest implements Serializable {

	public BlogsStatsUpdateRequest() {
	}

	public BlogsStatsUpdateRequest(long groupId, long userId, long entryId) {
		_groupId = groupId;
		_userId = userId;
		_entryId = entryId;
	}

	public boolean getAssetEntryVisibility() {
		return _assetEntryVisibility;
	}

	public long getEntryId() {
		return _entryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public int getSocialActivityActivityKey() {
		return _socialActivityActivityKey;
	}

	public String getSocialActivityExtraData() {
		return _socialActivityExtraData;
	}

	public long getSocialActivityReceiverUserId() {
		return _socialActivityReceiverUserId;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isAssetEntryUpdateNeeded() {
		return _assetEntryUpdateNeeded;
	}

	public boolean isSocialActivityUniqueActivity() {
		return _socialActivityUniqueActivity;
	}

	public boolean isSocialActivityUpdateNeeded() {
		return _socialActivityUpdateNeeded;
	}

	public void setAssetEntryUpdateNeeded(boolean assetEntryUpdateNeeded) {
		_assetEntryUpdateNeeded = assetEntryUpdateNeeded;
	}

	public void setAssetEntryVisibility(boolean assetEntryVisibility) {
		_assetEntryVisibility = assetEntryVisibility;
	}

	public void setEntryId(long entryId) {
		this._entryId = entryId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	public void setSocialActivityActivityKey(int socialActivityActivityKey) {
		_socialActivityActivityKey = socialActivityActivityKey;
	}

	public void setSocialActivityExtraData(String socialActivityExtraData) {
		_socialActivityExtraData = socialActivityExtraData;
	}

	public void setSocialActivityReceiverUserId(
		long socialActivityReceiverUserId) {

		_socialActivityReceiverUserId = socialActivityReceiverUserId;
	}

	public void setSocialActivityUniqueActivity(
		boolean socialActivityUniqueActivity) {

		_socialActivityUniqueActivity = socialActivityUniqueActivity;
	}

	public void setSocialActivityUpdateNeeded(
		boolean socialActivityUpdateNeeded) {

		_socialActivityUpdateNeeded = socialActivityUpdateNeeded;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private boolean _assetEntryUpdateNeeded;
	private boolean _assetEntryVisibility;
	private long _entryId;
	private long _groupId;
	private ServiceContext _serviceContext;
	private int _socialActivityActivityKey;
	private String _socialActivityExtraData;
	private long _socialActivityReceiverUserId;
	private boolean _socialActivityUniqueActivity;
	private boolean _socialActivityUpdateNeeded;
	private long _userId;

}