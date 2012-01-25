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

import java.util.Date;
import java.util.Map;

/**
 * @author Bruno Farache
 */
public class LayoutsRemotePublisherRequest
	extends LayoutsLocalPublisherRequest {

	public LayoutsRemotePublisherRequest() {
	}

	public LayoutsRemotePublisherRequest(
		long userId, long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, boolean secureConnection,
		long remoteGroupId, boolean remotePrivateLayout, Date startDate,
		Date endDate) {

		_userId = userId;
		_sourceGroupId = sourceGroupId;
		_privateLayout = privateLayout;
		_layoutIdMap = layoutIdMap;
		_parameterMap = parameterMap;
		_remoteAddress = remoteAddress;
		_remotePort = remotePort;
		_secureConnection = secureConnection;
		_remoteGroupId = remoteGroupId;
		_remotePrivateLayout = remotePrivateLayout;
		_startDate = startDate;
		_endDate = endDate;
	}

	@Override
	public String getCronText() {
		return _cronText;
	}

	@Override
	public void setCronText(String cronText) {
		_cronText = cronText;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	@Override
	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	@Override
	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	@Override
	public Map<Long, Boolean> getLayoutIdMap() {
		return _layoutIdMap;
	}

	@Override
	public void setLayoutIdMap(Map<Long, Boolean> layoutIdMap) {
		_layoutIdMap = layoutIdMap;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	@Override
	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	public String getRemoteAddress() {
		return _remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		_remoteAddress = remoteAddress;
	}

	public int getRemotePort() {
		return _remotePort;
	}

	public void setRemotePort(int remotePort) {
		_remotePort = remotePort;
	}

	public boolean isSecureConnection() {
		return _secureConnection;
	}

	public void setSecureConnection(boolean secureConnection) {
		_secureConnection = secureConnection;
	}

	public long getRemoteGroupId() {
		return _remoteGroupId;
	}

	public void setRemoteGroupId(long remoteGroupId) {
		_remoteGroupId = remoteGroupId;
	}

	public boolean isRemotePrivateLayout() {
		return _remotePrivateLayout;
	}

	public void setRemotePrivateLayout(boolean remotePrivateLayout) {
		_remotePrivateLayout = remotePrivateLayout;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@Override
	public Date getScheduledFireTime() {
		return _scheduledFireTime;
	}

	@Override
	public void setScheduledFireTime(Date scheduledFireTime) {
		_scheduledFireTime = scheduledFireTime;
	}

	private static final long serialVersionUID = -8270092763766057207L;

	private String _cronText;
	private Date _endDate;
	private Map<Long, Boolean> _layoutIdMap;
	private Map<String, String[]> _parameterMap;
	private boolean _privateLayout;
	private String _remoteAddress;
	private long _remoteGroupId;
	private int _remotePort;
	private boolean _remotePrivateLayout;
	private Date _scheduledFireTime;
	private boolean _secureConnection;
	private long _sourceGroupId;
	private Date _startDate;
	private long _userId;

}