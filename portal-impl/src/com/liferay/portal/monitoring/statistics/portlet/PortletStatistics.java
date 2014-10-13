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

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.statistics.RequestStatistics;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Karthik Sudarshan
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortletStatistics
	implements DataSampleProcessor<PortletRequestDataSample> {

	public PortletStatistics(
		String portletId, String portletName, String displayName) {

		_portletId = portletId;
		_portletName = portletName;
		_displayName = displayName;
		_actionRequestStatistics = new RequestStatistics(portletId);
		_eventRequestStatistics = new RequestStatistics(portletId);
		_renderRequestStatistics = new RequestStatistics(portletId);
		_resourceRequestStatistics = new RequestStatistics(portletId);

		_requestStatistics.put(
			PortletRequestType.ACTION, _actionRequestStatistics);
		_requestStatistics.put(
			PortletRequestType.EVENT, _eventRequestStatistics);
		_requestStatistics.put(
			PortletRequestType.RENDER, _renderRequestStatistics);
		_requestStatistics.put(
			PortletRequestType.RESOURCE, _resourceRequestStatistics);
	}

	public RequestStatistics getActionRequestStatistics() {
		return _actionRequestStatistics;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public RequestStatistics getEventRequestStatistics() {
		return _eventRequestStatistics;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getPortletName() {
		return _portletName;
	}

	public RequestStatistics getRenderRequestStatistics() {
		return _renderRequestStatistics;
	}

	public RequestStatistics getResourceRequestStatistics() {
		return _resourceRequestStatistics;
	}

	@Override
	public void processDataSample(
			PortletRequestDataSample portletRequestDataSample)
		throws MonitoringException {

		if (!portletRequestDataSample.getPortletId().equals(_portletId)) {
			return;
		}

		PortletRequestType portletRequestType =
			portletRequestDataSample.getRequestType();

		RequestStatistics requestStatistics =_requestStatistics.get(
			portletRequestType);

		if (requestStatistics == null) {
			throw new MonitoringException(
				"No statistics found for " + portletRequestDataSample);
		}

		RequestStatus requestStatus =
			portletRequestDataSample.getRequestStatus();

		if (requestStatus.equals(RequestStatus.ERROR)) {
			requestStatistics.incrementError();
		}
		else if (requestStatus.equals(RequestStatus.SUCCESS)) {
			requestStatistics.incrementSuccessDuration(
				portletRequestDataSample.getDuration());
		}
		else if (requestStatus.equals(RequestStatus.TIMEOUT)) {
			requestStatistics.incrementTimeout();
		}
	}

	public void reset() {
		_actionRequestStatistics.reset();
		_eventRequestStatistics.reset();
		_renderRequestStatistics.reset();
		_resourceRequestStatistics.reset();
	}

	private final RequestStatistics _actionRequestStatistics;
	private final String _displayName;
	private final RequestStatistics _eventRequestStatistics;
	private final String _portletId;
	private final String _portletName;
	private final RequestStatistics _renderRequestStatistics;
	private final Map<PortletRequestType, RequestStatistics>
		_requestStatistics =
			new HashMap<PortletRequestType, RequestStatistics>();
	private final RequestStatistics _resourceRequestStatistics;

}