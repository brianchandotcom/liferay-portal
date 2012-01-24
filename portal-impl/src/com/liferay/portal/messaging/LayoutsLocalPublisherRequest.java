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

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Bruno Farache
 */
public class LayoutsLocalPublisherRequest implements Serializable {

	public static final String COMMAND_ALL_PAGES = "ALL_PAGES";

	public static final String COMMAND_SELECTED_PAGES = "SELECTED_PAGES";

	public LayoutsLocalPublisherRequest() {
	}

	public LayoutsLocalPublisherRequest(
		String command, long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, Map<Long, Boolean> layoutIdMap,
		Map<String, String[]> parameterMap, Date startDate, Date endDate) {

		_command = command;
		_userId = userId;
		_sourceGroupId = sourceGroupId;
		_targetGroupId = targetGroupId;
		_privateLayout = privateLayout;
		_layoutIdMap = layoutIdMap;
		_parameterMap = parameterMap;
		_startDate = startDate;
		_endDate = endDate;
	}

	public String getCommand() {
		return _command;
	}

	public void setCommand(String command) {
		_command = command;
	}

	public String getCronText() {
		return _cronText;
	}

	public void setCronText(String cronText) {
		_cronText = cronText;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	public long getTargetGroupId() {
		return _targetGroupId;
	}

	public void setTargetGroupId(long targetGroupId) {
		_targetGroupId = targetGroupId;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public Map<Long, Boolean> getLayoutIdMap() {
		return _layoutIdMap;
	}

	public void setLayoutIdMap(Map<Long, Boolean> layoutIdMap) {
		_layoutIdMap = layoutIdMap;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public Date getScheduledFireTime() {
		return _scheduledFireTime;
	}

	public void setScheduledFireTime(Date scheduledFireTime) {
		_scheduledFireTime = scheduledFireTime;
	}

	private static final long serialVersionUID = 5924338343434811142L;

	private String _command;
	private String _cronText;
	private Date _endDate;
	private Map<Long, Boolean> _layoutIdMap;
	private Map<String, String[]> _parameterMap;
	private boolean _privateLayout;
	private Date _scheduledFireTime;
	private long _sourceGroupId;
	private Date _startDate;
	private long _targetGroupId;
	private long _userId;

}