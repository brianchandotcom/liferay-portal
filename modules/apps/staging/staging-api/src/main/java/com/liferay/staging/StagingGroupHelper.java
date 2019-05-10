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

package com.liferay.staging;

import com.liferay.portal.kernel.model.Group;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Akos Thurzo
 */
@ProviderType
public interface StagingGroupHelper {

	public Group fetchLiveGroup(Group group);

	public Group fetchLiveGroup(long groupId);

	public Group fetchLocalLiveGroup(Group group);

	public Group fetchLocalLiveGroup(long groupId);

	public Group fetchLocalStagingGroup(Group group);

	public Group fetchLocalStagingGroup(long groupId);

	public Group fetchRemoteLiveGroup(Group group);

	public Group fetchRemoteLiveGroup(long groupId);

	public boolean isLiveGroup(Group group);

	public boolean isLiveGroup(long groupId);

	public boolean isLocalLiveGroup(Group group);

	public boolean isLocalLiveGroup(long groupId);

	public boolean isLocalStagingGroup(Group group);

	public boolean isLocalStagingGroup(long groupId);

	public boolean isLocalStagingOrLocalLiveGroup(Group group);

	public boolean isLocalStagingOrLocalLiveGroup(long groupId);

	public boolean isRemoteLiveGroup(Group group);

	public boolean isRemoteLiveGroup(long groupId);

	public boolean isRemoteStagingGroup(Group group);

	public boolean isRemoteStagingGroup(long groupId);

	public boolean isRemoteStagingOrRemoteLiveGroup(Group group);

	public boolean isRemoteStagingOrRemoteLiveGroup(long groupId);

	public boolean isStagedPortlet(Group group, String portletId);

	public boolean isStagedPortlet(long groupId, String portletId);

	public boolean isStagedPortletData(long groupId, String className);

	public boolean isStagingGroup(Group group);

	public boolean isStagingGroup(long groupId);

	public boolean isStagingOrLiveGroup(Group group);

	public boolean isStagingOrLiveGroup(long groupId);

}