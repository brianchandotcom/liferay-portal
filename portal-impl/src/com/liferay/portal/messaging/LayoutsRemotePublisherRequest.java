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

package com.liferay.portal.messaging;

import java.util.Date;
import java.util.Map;

/**
 * @author Bruno Farache
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class LayoutsRemotePublisherRequest
	extends LayoutsLocalPublisherRequest {

	public LayoutsRemotePublisherRequest() {
	}

	public LayoutsRemotePublisherRequest(
		long userId, long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout, Date startDate, Date endDate) {

		_userId = userId;
		_sourceGroupId = sourceGroupId;
		_privateLayout = privateLayout;
		_layoutIdMap = layoutIdMap;
		_parameterMap = parameterMap;
		_remoteAddress = remoteAddress;
		_remotePort = remotePort;
		_remotePathContext = remotePathContext;
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
	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public Map<Long, Boolean> getLayoutIdMap() {
		return _layoutIdMap;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public String getRemoteAddress() {
		return _remoteAddress;
	}

	public long getRemoteGroupId() {
		return _remoteGroupId;
	}

	public String getRemotePathContext() {
		return _remotePathContext;
	}

	public int getRemotePort() {
		return _remotePort;
	}

	@Override
	public Date getScheduledFireTime() {
		return _scheduledFireTime;
	}

	@Override
	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public boolean isRemotePrivateLayout() {
		return _remotePrivateLayout;
	}

	public boolean isSecureConnection() {
		return _secureConnection;
	}

	@Override
	public void setCronText(String cronText) {
		_cronText = cronText;
	}

	@Override
	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@Override
	public void setLayoutIdMap(Map<Long, Boolean> layoutIdMap) {
		_layoutIdMap = layoutIdMap;
	}

	@Override
	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setRemoteAddress(String remoteAddress) {
		_remoteAddress = remoteAddress;
	}

	public void setRemoteGroupId(long remoteGroupId) {
		_remoteGroupId = remoteGroupId;
	}

	public void setRemotePathContext(String remotePathContext) {
		_remotePathContext = remotePathContext;
	}

	public void setRemotePort(int remotePort) {
		_remotePort = remotePort;
	}

	public void setRemotePrivateLayout(boolean remotePrivateLayout) {
		_remotePrivateLayout = remotePrivateLayout;
	}

	@Override
	public void setScheduledFireTime(Date scheduledFireTime) {
		_scheduledFireTime = scheduledFireTime;
	}

	public void setSecureConnection(boolean secureConnection) {
		_secureConnection = secureConnection;
	}

	@Override
	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	private static final long serialVersionUID = -8270092763766057207L;

	private String _cronText;
	private Date _endDate;
	private Map<Long, Boolean> _layoutIdMap;
	private Map<String, String[]> _parameterMap;
	private boolean _privateLayout;
	private String _remoteAddress;
	private long _remoteGroupId;
	private String _remotePathContext;
	private int _remotePort;
	private boolean _remotePrivateLayout;
	private Date _scheduledFireTime;
	private boolean _secureConnection;
	private long _sourceGroupId;
	private Date _startDate;
	private long _userId;

}