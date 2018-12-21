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

package com.liferay.portal.workflow.reports.messaging;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rafael Praxedes
 */
public class WorkflowReportsMessage {

	public long getCompanyId() {
		return _companyId;
	}

	public String getEventId() {
		return _eventId;
	}

	public Map<String, Object> getProperties() {
		return Collections.unmodifiableMap(_properties);
	}

	public <T> T getProperty(String key) {
		return (T)_properties.get(key);
	}

	public long getUserId() {
		return _userId;
	}

	public static class Builder {

		public static Builder newBuilder(
			long companyId, String eventId, long userId) {

			return new Builder(companyId, eventId, userId);
		}

		public WorkflowReportsMessage build() {
			return _workflowReportsMessage;
		}

		public Builder withProperty(String key, Object value) {
			_workflowReportsMessage._properties.put(key, value);

			return this;
		}

		private Builder(long companyId, String eventId, long userId) {
			_workflowReportsMessage._companyId = companyId;
			_workflowReportsMessage._eventId = eventId;
			_workflowReportsMessage._userId = userId;
		}

		private final WorkflowReportsMessage _workflowReportsMessage =
			new WorkflowReportsMessage();

	}

	private WorkflowReportsMessage() {
	}

	private long _companyId;
	private String _eventId;
	private Map<String, Object> _properties = new ConcurrentHashMap<>();
	private long _userId;

}