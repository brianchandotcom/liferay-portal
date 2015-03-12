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

import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.statistics.DataSample;
import com.liferay.portal.kernel.monitoring.statistics.DataSampleThreadLocal;
import com.liferay.portal.kernel.monitoring.statistics.PortletRequestType;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.monitoring.statistics.DataSampleFactoryUtil;

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
	implements InvokerFilterContainer, InvokerPortlet,
	PortletMonitoringControl {

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

	public boolean isMonitorPortletActionRequest() {
		return _monitorPortletActionRequest;
	}

	public boolean isMonitorPortletEventRequest() {
		return _monitorPortletEventRequest;
	}

	public boolean isMonitorPortletRenderRequest() {
		return _monitorPortletRenderRequest;
	}

	public boolean isMonitorPortletResourceRequest() {
		return _monitorPortletResourceRequest;
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
			if (_monitorPortletActionRequest) {
				dataSample =
					DataSampleFactoryUtil.createPortletRequestDataSample(
						PortletRequestType.ACTION, actionRequest,
						actionResponse);

				dataSample.setTimeout(_actionTimeout);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.processAction(actionRequest, actionResponse);

			if (_monitorPortletActionRequest) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(_monitorPortletActionRequest, dataSample, e);
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
			if (_monitorPortletEventRequest) {
				dataSample =
					DataSampleFactoryUtil.createPortletRequestDataSample(
						PortletRequestType.EVENT, eventRequest, eventResponse);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.processEvent(eventRequest, eventResponse);

			if (_monitorPortletEventRequest) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(_monitorPortletEventRequest, dataSample, e);
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
			if (_monitorPortletRenderRequest) {
				dataSample =
					DataSampleFactoryUtil.createPortletRequestDataSample(
						PortletRequestType.RENDER, renderRequest,
						renderResponse);

				dataSample.setTimeout(_renderTimeout);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.render(renderRequest, renderResponse);

			if (_monitorPortletRenderRequest) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(_monitorPortletRenderRequest, dataSample, e);
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
			if (_monitorPortletResourceRequest) {
				dataSample =
					DataSampleFactoryUtil.createPortletRequestDataSample(
						PortletRequestType.RESOURCE, resourceRequest,
						resourceResponse);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.serveResource(resourceRequest, resourceResponse);

			if (_monitorPortletResourceRequest) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(_monitorPortletResourceRequest, dataSample, e);
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

	public void setMonitorPortletActionRequest(
		boolean monitorPortletActionRequest) {

		_monitorPortletActionRequest = monitorPortletActionRequest;
	}

	public void setMonitorPortletEventRequest(
		boolean monitorPortletEventRequest) {

		_monitorPortletEventRequest = monitorPortletEventRequest;
	}

	public void setMonitorPortletRenderRequest(
		boolean monitorPortletRenderRequest) {

		_monitorPortletRenderRequest = monitorPortletRenderRequest;
	}

	public void setMonitorPortletResourceRequest(
		boolean monitorPortletResourceRequest) {

		_monitorPortletResourceRequest = monitorPortletResourceRequest;
	}

	@Override
	public void setPortletFilters() throws PortletException {
		_invokerPortlet.setPortletFilters();
	}

	private void _processException(
			boolean monitorPortletRequest, DataSample dataSample, Exception e)
		throws IOException, PortletException {

		if (monitorPortletRequest) {
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

	private long _actionTimeout;
	private InvokerPortlet _invokerPortlet;
	private boolean _monitorPortletActionRequest;
	private boolean _monitorPortletEventRequest;
	private boolean _monitorPortletRenderRequest;
	private boolean _monitorPortletResourceRequest;
	private long _renderTimeout;

}