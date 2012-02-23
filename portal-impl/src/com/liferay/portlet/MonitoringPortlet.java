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

package com.liferay.portlet;

import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.statistics.DataSampleThreadLocal;
import com.liferay.portal.monitoring.statistics.portlet.PortletRequestDataSample;
import com.liferay.portal.monitoring.statistics.portlet.PortletRequestType;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Michael C. Han
 * @author Karthik Sudarshan
 */
public class MonitoringPortlet implements InvokerPortlet {

	public static boolean isMonitoringPortletActionRequest() {
		return _monitoringPortletActionRequest;
	}

	public static boolean isMonitoringPortletEventRequest() {
		return _monitoringPortletEventRequest;
	}

	public static boolean isMonitoringPortletRenderRequest() {
		return _monitoringPortletRenderRequest;
	}

	public static boolean isMonitoringPortletResourceRequest() {
		return _monitoringPortletResourceRequest;
	}

	public static void setMonitoringPortletActionRequest(
		boolean monitoringPortletActionRequest) {

		_monitoringPortletActionRequest = monitoringPortletActionRequest;
	}

	public static void setMonitoringPortletEventRequest(
		boolean monitoringPortletEventRequest) {

		_monitoringPortletEventRequest = monitoringPortletEventRequest;
	}

	public static void setMonitoringPortletRenderRequest(
		boolean monitoringPortletRenderRequest) {

		_monitoringPortletRenderRequest = monitoringPortletRenderRequest;
	}

	public static void setMonitoringPortletResourceRequest(
		boolean monitoringPortletResourceRequest) {

		_monitoringPortletResourceRequest = monitoringPortletResourceRequest;
	}

	public MonitoringPortlet() {
	}

	public MonitoringPortlet(
		InvokerPortlet invokerPortlet,
		SingleDestinationMessageSender singleDestinationMessageSender) {

		_invokerPortlet = invokerPortlet;
		_singleDestinationMessageSender = singleDestinationMessageSender;
	}

	public void destroy() {
		_invokerPortlet.destroy();
	}

	public Integer getExpCache() {
		return _invokerPortlet.getExpCache();
	}

	public Portlet getPortlet() {
		return _invokerPortlet.getPortlet();
	}

	public ClassLoader getPortletClassLoader() {
		return _invokerPortlet.getPortletClassLoader();
	}

	public PortletConfig getPortletConfig() {
		return _invokerPortlet.getPortletConfig();
	}

	public PortletContext getPortletContext() {
		return _invokerPortlet.getPortletContext();
	}

	public Portlet getPortletInstance() {
		return _invokerPortlet.getPortletInstance();
	}

	public void init(PortletConfig portletConfig) throws PortletException {
		PortletConfigImpl portletConfigImpl = (PortletConfigImpl)portletConfig;

		_invokerPortlet.init(portletConfigImpl);

		com.liferay.portal.model.Portlet portletModel =
			portletConfigImpl.getPortlet();

		_actionTimeout = portletModel.getActionTimeout();
		_renderTimeout = portletModel.getRenderTimeout();
	}

	public boolean isCheckAuthToken() {
		return _invokerPortlet.isCheckAuthToken();
	}

	public boolean isDestroyable() {
		return _invokerPortlet.isDestroyable();
	}

	public boolean isFacesPortlet() {
		return _invokerPortlet.isFacesPortlet();
	}

	public boolean isStrutsBridgePortlet() {
		return _invokerPortlet.isStrutsBridgePortlet();
	}

	public boolean isStrutsPortlet() {
		return _invokerPortlet.isStrutsPortlet();
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		PortletRequestDataSample portletRequestDataSample = null;

		try {
			if (_monitoringPortletActionRequest) {
				portletRequestDataSample = new PortletRequestDataSample(
					PortletRequestType.ACTION, actionRequest, actionResponse);

				portletRequestDataSample.setTimeout(_actionTimeout);

				portletRequestDataSample.prepare();
			}

			_invokerPortlet.processAction(actionRequest, actionResponse);

			if (_monitoringPortletActionRequest) {
				portletRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_monitoringPortletActionRequest, portletRequestDataSample, e);
		}
		finally {
			if (portletRequestDataSample != null) {
				_singleDestinationMessageSender.send(portletRequestDataSample);

				DataSampleThreadLocal.addDataSample(portletRequestDataSample);
			}
		}
	}

	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		PortletRequestDataSample portletRequestDataSample = null;

		try {
			if (_monitoringPortletEventRequest) {
				portletRequestDataSample = new PortletRequestDataSample(
					PortletRequestType.EVENT, eventRequest, eventResponse);

				portletRequestDataSample.prepare();
			}

			_invokerPortlet.processEvent(eventRequest, eventResponse);

			if (_monitoringPortletEventRequest) {
				portletRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_monitoringPortletEventRequest, portletRequestDataSample, e);
		}
		finally {
			if (portletRequestDataSample != null) {
				_singleDestinationMessageSender.send(portletRequestDataSample);

				DataSampleThreadLocal.addDataSample(portletRequestDataSample);
			}
		}
	}

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletRequestDataSample portletRequestDataSample = null;

		try {
			if (_monitoringPortletRenderRequest) {
				portletRequestDataSample = new PortletRequestDataSample(
					PortletRequestType.RENDER, renderRequest, renderResponse);

				portletRequestDataSample.setTimeout(_renderTimeout);

				portletRequestDataSample.prepare();
			}

			_invokerPortlet.render(renderRequest, renderResponse);

			if (_monitoringPortletRenderRequest) {
				portletRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_monitoringPortletRenderRequest, portletRequestDataSample, e);
		}
		finally {
			if (portletRequestDataSample != null) {
				_singleDestinationMessageSender.send(portletRequestDataSample);

				DataSampleThreadLocal.addDataSample(portletRequestDataSample);
			}
		}
	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		PortletRequestDataSample portletRequestDataSample = null;

		try {
			if (_monitoringPortletResourceRequest) {
				portletRequestDataSample = new PortletRequestDataSample(
					PortletRequestType.RESOURCE, resourceRequest,
					resourceResponse);

				portletRequestDataSample.prepare();
			}

			_invokerPortlet.serveResource(resourceRequest, resourceResponse);

			if (_monitoringPortletResourceRequest) {
				portletRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_monitoringPortletResourceRequest, portletRequestDataSample, e);
		}
		finally {
			if (portletRequestDataSample != null) {
				_singleDestinationMessageSender.send(portletRequestDataSample);

				DataSampleThreadLocal.addDataSample(portletRequestDataSample);
			}
		}
	}

	public void setInvokerPortlet(InvokerPortlet invokerPortlet) {
		_invokerPortlet = invokerPortlet;
	}

	public void setPortletFilters() throws PortletException {
		_invokerPortlet.setPortletFilters();
	}

	public void setSingleDestinationMessageSender(
		SingleDestinationMessageSender singleDestinationMessageSender) {

		_singleDestinationMessageSender = singleDestinationMessageSender;
	}

	private void _processException(
			boolean monitoringPortletRequest,
			PortletRequestDataSample portletRequestDataSample, Exception e)
		throws IOException, PortletException {

		if (monitoringPortletRequest) {
			portletRequestDataSample.capture(RequestStatus.ERROR);
		}

		if (e instanceof IOException) {
			throw (IOException)e;
		}
		else if (e instanceof PortletException) {
			throw (PortletException)e;
		}
		else {
			throw new PortletException("Unable to process portlet", e);
		}
	}

	private static boolean _monitoringPortletActionRequest =
		PropsValues.MONITORING_PORTLET_ACTION_REQUEST;
	private static boolean _monitoringPortletEventRequest =
		PropsValues.MONITORING_PORTLET_EVENT_REQUEST;
	private static boolean _monitoringPortletRenderRequest =
		PropsValues.MONITORING_PORTLET_RENDER_REQUEST;
	private static boolean _monitoringPortletResourceRequest =
		PropsValues.MONITORING_PORTLET_RESOURCE_REQUEST;

	private long _actionTimeout;
	private InvokerPortlet _invokerPortlet;
	private long _renderTimeout;
	private SingleDestinationMessageSender _singleDestinationMessageSender;

}