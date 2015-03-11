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

package com.liferay.portlet;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.statistics.DataSample;
import com.liferay.portal.kernel.monitoring.statistics.DataSampleThreadLocal;
import com.liferay.portal.kernel.monitoring.statistics.PortletRequestType;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.monitoring.statistics.DataSampleFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.List;

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
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

/**
 * @author Michael C. Han
 * @author Karthik Sudarshan
 * @author Raymond Augé
 */
@ProviderType
public class MonitoringPortlet
	implements InvokerFilterContainer, InvokerPortlet {

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

	public MonitoringPortlet(InvokerPortlet invokerPortlet) {
		_invokerPortlet = invokerPortlet;
	}

	@Override
	public void destroy() {
		_invokerPortlet.destroy();
	}

	@Override
	public List<ActionFilter> getActionFilters() {
		InvokerFilterContainer invokerFilterContainer =
			(InvokerFilterContainer)_invokerPortlet;

		return invokerFilterContainer.getActionFilters();
	}

	@Override
	public List<EventFilter> getEventFilters() {
		InvokerFilterContainer invokerFilterContainer =
			(InvokerFilterContainer)_invokerPortlet;

		return invokerFilterContainer.getEventFilters();
	}

	@Override
	public Integer getExpCache() {
		return _invokerPortlet.getExpCache();
	}

	@Override
	public Portlet getPortlet() {
		return _invokerPortlet.getPortlet();
	}

	@Override
	public ClassLoader getPortletClassLoader() {
		return _invokerPortlet.getPortletClassLoader();
	}

	@Override
	public PortletConfig getPortletConfig() {
		return _invokerPortlet.getPortletConfig();
	}

	@Override
	public PortletContext getPortletContext() {
		return _invokerPortlet.getPortletContext();
	}

	@Override
	public Portlet getPortletInstance() {
		return _invokerPortlet.getPortletInstance();
	}

	@Override
	public List<RenderFilter> getRenderFilters() {
		InvokerFilterContainer invokerFilterContainer =
			(InvokerFilterContainer)_invokerPortlet;

		return invokerFilterContainer.getRenderFilters();
	}

	@Override
	public List<ResourceFilter> getResourceFilters() {
		InvokerFilterContainer invokerFilterContainer =
			(InvokerFilterContainer)_invokerPortlet;

		return invokerFilterContainer.getResourceFilters();
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)portletConfig;

		_invokerPortlet.init(liferayPortletConfig);

		com.liferay.portal.model.Portlet portletModel =
			liferayPortletConfig.getPortlet();

		_actionTimeout = portletModel.getActionTimeout();
		_renderTimeout = portletModel.getRenderTimeout();
	}

	@Override
	public boolean isCheckAuthToken() {
		return _invokerPortlet.isCheckAuthToken();
	}

	@Override
	public boolean isFacesPortlet() {
		return _invokerPortlet.isFacesPortlet();
	}

	@Override
	public boolean isStrutsBridgePortlet() {
		return _invokerPortlet.isStrutsBridgePortlet();
	}

	@Override
	public boolean isStrutsPortlet() {
		return _invokerPortlet.isStrutsPortlet();
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		DataSample dataSample = null;

		try {
			if (_monitoringPortletActionRequest) {
				dataSample =
					DataSampleFactoryUtil.createPortletRequestDataSample(
						PortletRequestType.ACTION, actionRequest,
						actionResponse);

				dataSample.setTimeout(_actionTimeout);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.processAction(actionRequest, actionResponse);

			if (_monitoringPortletActionRequest) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(_monitoringPortletActionRequest, dataSample, e);
		}
		finally {
			if (dataSample != null) {
				DataSampleThreadLocal.addDataSample(dataSample);
			}
		}
	}

	@Override
	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		DataSample dataSample = null;

		try {
			if (_monitoringPortletEventRequest) {
				dataSample =
					DataSampleFactoryUtil.createPortletRequestDataSample(
						PortletRequestType.EVENT, eventRequest, eventResponse);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.processEvent(eventRequest, eventResponse);

			if (_monitoringPortletEventRequest) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(_monitoringPortletEventRequest, dataSample, e);
		}
		finally {
			if (dataSample != null) {
				DataSampleThreadLocal.addDataSample(dataSample);
			}
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		DataSample dataSample = null;

		try {
			if (_monitoringPortletRenderRequest) {
				dataSample =
					DataSampleFactoryUtil.createPortletRequestDataSample(
						PortletRequestType.RENDER, renderRequest,
						renderResponse);

				dataSample.setTimeout(_renderTimeout);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.render(renderRequest, renderResponse);

			if (_monitoringPortletRenderRequest) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(_monitoringPortletRenderRequest, dataSample, e);
		}
		finally {
			if (dataSample != null) {
				DataSampleThreadLocal.addDataSample(dataSample);
			}
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		DataSample dataSample = null;

		try {
			if (_monitoringPortletResourceRequest) {
				dataSample =
					DataSampleFactoryUtil.createPortletRequestDataSample(
						PortletRequestType.RESOURCE, resourceRequest,
						resourceResponse);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.serveResource(resourceRequest, resourceResponse);

			if (_monitoringPortletResourceRequest) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(_monitoringPortletResourceRequest, dataSample, e);
		}
		finally {
			if (dataSample != null) {
				DataSampleThreadLocal.addDataSample(dataSample);
			}
		}
	}

	public void setInvokerPortlet(InvokerPortlet invokerPortlet) {
		_invokerPortlet = invokerPortlet;
	}

	@Override
	public void setPortletFilters() throws PortletException {
		_invokerPortlet.setPortletFilters();
	}

	private void _processException(
			boolean monitoringPortletRequest, DataSample dataSample,
			Exception e)
		throws IOException, PortletException {

		if (monitoringPortletRequest) {
			dataSample.capture(RequestStatus.ERROR);
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

}