/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.portlet;

import com.liferay.portal.kernel.monitoring.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.monitoring.PortletRequestType;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.monitoring.internal.statistics.RequestStatistics;

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
		_headerRequestStatistics = new RequestStatistics(portletId);
		_renderRequestStatistics = new RequestStatistics(portletId);
		_resourceRequestStatistics = new RequestStatistics(portletId);

		_requestStatistics.put(
			PortletRequestType.ACTION, _actionRequestStatistics);
		_requestStatistics.put(
			PortletRequestType.EVENT, _eventRequestStatistics);
		_requestStatistics.put(
			PortletRequestType.HEADER, _headerRequestStatistics);
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

		String portletId = portletRequestDataSample.getPortletId();

		if (!portletId.equals(_portletId)) {
			return;
		}

		PortletRequestType portletRequestType =
			portletRequestDataSample.getRequestType();

		RequestStatistics requestStatistics = _requestStatistics.get(
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
		_headerRequestStatistics.reset();
		_renderRequestStatistics.reset();
		_resourceRequestStatistics.reset();
	}

	private final RequestStatistics _actionRequestStatistics;
	private final String _displayName;
	private final RequestStatistics _eventRequestStatistics;
	private final RequestStatistics _headerRequestStatistics;
	private final String _portletId;
	private final String _portletName;
	private final RequestStatistics _renderRequestStatistics;
	private final Map<PortletRequestType, RequestStatistics>
		_requestStatistics = new HashMap<>();
	private final RequestStatistics _resourceRequestStatistics;

}