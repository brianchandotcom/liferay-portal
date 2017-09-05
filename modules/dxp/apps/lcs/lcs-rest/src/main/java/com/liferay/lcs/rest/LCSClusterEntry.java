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

/**
 * @author Ivica Cardic
 */
public interface LCSClusterEntry {

	public boolean getArchived();

	public String getDescription();

	public boolean getElastic();

	public long getLcsClusterEntryId();

	public long getLcsProjectId();

	public String getLocation();

	public String getName();

	public String getSubscriptionType();

	public int getType();

	public boolean isArchived();

	public boolean isCluster();

	public boolean isElastic();

	public boolean isEnvironment();

	public void setArchived(boolean archived);

	public void setDescription(String description);

	public void setElastic(boolean elastic);

	public void setLcsClusterEntryId(long lcsClusterEntryId);

	public void setLcsProjectId(long lcsProjectId);

	public void setLocation(String location);

	public void setName(String name);

	public void setSubscriptionType(String subscriptionType);

	public void setType(int type);

}