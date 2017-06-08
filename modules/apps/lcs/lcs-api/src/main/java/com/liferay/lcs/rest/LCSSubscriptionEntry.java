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

import java.util.Date;

/**
 * @author Igor Beslic
 */
public interface LCSSubscriptionEntry {

	public int getInstanceSize();

	public long getLcsProjectId();

	public long getLcsSubscriptionEntryId();

	public String getPlatform();

	public int getPlatformVersion();

	public int getProcessorCoresAllowed();

	public String getProduct();

	public int getProductVersion();

	public int getServersAllowed();

	public int getServersUsed();

	public String getType();

	public void setEndDate(Date endDate);

	public void setInstanceSize(int instanceSize);

	public void setLcsProjectId(long lcsClusterEntryId);

	public void setLcsSubscriptionEntryId(long lcsSubscriptionEntryId);

	public void setPlatform(String platform);

	public void setPlatformVersion(int platformVersion);

	public void setProcessorCoresAllowed(int processorCoresAllowed);

	public void setProduct(String product);

	public void setProductVersion(int productVersion);

	public void setServersAllowed(int serversAllowed);

	public void setServersUsed(int serversUsed);

	public void setStartDate(Date startDate);

	public void setSupportEndDate(Date supportEndDate);

	public void setSupportStartDate(Date supportStartDate);

	public void setType(String type);

}