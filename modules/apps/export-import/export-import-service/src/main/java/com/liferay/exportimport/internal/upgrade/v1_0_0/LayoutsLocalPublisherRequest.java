/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.upgrade.v1_0_0;

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

	public String getCronText() {
		return _cronText;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public Map<Long, Boolean> getLayoutIdMap() {
		return _layoutIdMap;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public Date getScheduledFireTime() {
		return _scheduledFireTime;
	}

	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public long getTargetGroupId() {
		return _targetGroupId;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setCommand(String command) {
		_command = command;
	}

	public void setCronText(String cronText) {
		_cronText = cronText;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setLayoutIdMap(Map<Long, Boolean> layoutIdMap) {
		_layoutIdMap = layoutIdMap;
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setScheduledFireTime(Date scheduledFireTime) {
		_scheduledFireTime = scheduledFireTime;
	}

	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public void setTargetGroupId(long targetGroupId) {
		_targetGroupId = targetGroupId;
	}

	public void setUserId(long userId) {
		_userId = userId;
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