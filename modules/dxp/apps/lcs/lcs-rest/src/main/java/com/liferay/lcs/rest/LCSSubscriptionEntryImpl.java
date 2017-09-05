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
public class LCSSubscriptionEntryImpl implements LCSSubscriptionEntry {

	@Override
	public int getInstanceSize() {
		return _instanceSize;
	}

	@Override
	public long getLcsProjectId() {
		return _lcsProjectId;
	}

	@Override
	public long getLcsSubscriptionEntryId() {
		return _lcsSubscriptionEntryId;
	}

	@Override
	public String getPlatform() {
		return _platform;
	}

	@Override
	public int getPlatformVersion() {
		return _platformVersion;
	}

	@Override
	public int getProcessorCoresAllowed() {
		return _processorCoresAllowed;
	}

	@Override
	public String getProduct() {
		return _product;
	}

	@Override
	public int getProductVersion() {
		return _productVersion;
	}

	@Override
	public int getServersAllowed() {
		return _serversAllowed;
	}

	@Override
	public int getServersUsed() {
		return _serversUsed;
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@Override
	public void setInstanceSize(int instanceSize) {
		_instanceSize = instanceSize;
	}

	@Override
	public void setLcsProjectId(long lcsProjectId) {
		_lcsProjectId = lcsProjectId;
	}

	@Override
	public void setLcsSubscriptionEntryId(long lcsSubscriptionEntryId) {
		_lcsSubscriptionEntryId = lcsSubscriptionEntryId;
	}

	@Override
	public void setPlatform(String platform) {
		_platform = platform;
	}

	@Override
	public void setPlatformVersion(int platformVersion) {
		_platformVersion = platformVersion;
	}

	@Override
	public void setProcessorCoresAllowed(int processorCoresAllowed) {
		_processorCoresAllowed = processorCoresAllowed;
	}

	@Override
	public void setProduct(String product) {
		_product = product;
	}

	@Override
	public void setProductVersion(int productVersion) {
		_productVersion = productVersion;
	}

	@Override
	public void setServersAllowed(int serversAllowed) {
		_serversAllowed = serversAllowed;
	}

	@Override
	public void setServersUsed(int serversUsed) {
		_serversUsed = serversUsed;
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public void setSupportEndDate(Date supportEndDate) {
		_supportEndDate = supportEndDate;
	}

	@Override
	public void setSupportStartDate(Date supportStartDate) {
		_supportStartDate = supportStartDate;
	}

	@Override
	public void setType(String type) {
		_type = type;
	}

	private Date _endDate;
	private int _instanceSize;
	private long _lcsProjectId;
	private long _lcsSubscriptionEntryId;
	private String _platform;
	private int _platformVersion;
	private int _processorCoresAllowed;
	private String _product;
	private int _productVersion;
	private int _serversAllowed;
	private int _serversUsed;
	private Date _startDate;
	private Date _supportEndDate;
	private Date _supportStartDate;
	private String _type;

}