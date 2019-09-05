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

package com.liferay.portal.monitoring.internal.portlet;

import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleFactory;
import com.liferay.portal.kernel.monitoring.DataSampleThreadLocal;
import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;
import com.liferay.portal.kernel.monitoring.PortletRequestType;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.portlet.InvokerFilterContainer;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.MimeResponse;
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
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

/**
 * @author Michael C. Han
 * @author Karthik Sudarshan
 * @author Raymond Augé
 * @author Philip Jones
 * @author Neil Griffin
 */
public class MonitoringInvokerPortlet
	implements InvokerFilterContainer, InvokerPortlet {

	public MonitoringInvokerPortlet(
		InvokerPortlet invokerPortlet, DataSampleFactory dataSampleFactory,
		PortletMonitoringControl portletMonitoringControl) {

		_invokerPortlet = invokerPortlet;
		_dataSampleFactory = dataSampleFactory;
		_portletMonitoringControl = portletMonitoringControl;
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
	public List<HeaderFilter> getHeaderFilters() {
		InvokerFilterContainer invokerFilterContainer =
			(InvokerFilterContainer)_invokerPortlet;

		return invokerFilterContainer.getHeaderFilters();
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

		com.liferay.portal.kernel.model.Portlet portletModel =
			liferayPortletConfig.getPortlet();

		_actionTimeout = portletModel.getActionTimeout();
		_headerTimeout = portletModel.getHeaderTimeout();
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
	public boolean isHeaderPortlet() {
		return _invokerPortlet.isHeaderPortlet();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isStrutsBridgePortlet() {
		return false;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
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
			if (_portletMonitoringControl.isMonitorPortletActionRequest()) {
				dataSample = _dataSampleFactory.createPortletRequestDataSample(
					PortletRequestType.ACTION, actionRequest, actionResponse);

				dataSample.setTimeout(_actionTimeout);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.processAction(actionRequest, actionResponse);

			if (_portletMonitoringControl.isMonitorPortletActionRequest() &&
				(dataSample != null)) {

				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_portletMonitoringControl.isMonitorPortletActionRequest(),
				dataSample, e);
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
			if (_portletMonitoringControl.isMonitorPortletEventRequest()) {
				dataSample = _dataSampleFactory.createPortletRequestDataSample(
					PortletRequestType.EVENT, eventRequest, eventResponse);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.processEvent(eventRequest, eventResponse);

			if (_portletMonitoringControl.isMonitorPortletEventRequest() &&
				(dataSample != null)) {

				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_portletMonitoringControl.isMonitorPortletEventRequest(),
				dataSample, e);
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

		_render(
			renderRequest, renderResponse,
			() -> _invokerPortlet.render(renderRequest, renderResponse),
			PortletRequestType.RENDER, _renderTimeout);
	}

	@Override
	public void renderHeaders(
			HeaderRequest headerRequest, HeaderResponse headerResponse)
		throws IOException, PortletException {

		_render(
			headerRequest, headerResponse,
			() -> _invokerPortlet.renderHeaders(headerRequest, headerResponse),
			PortletRequestType.HEADER, _headerTimeout);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		DataSample dataSample = null;

		try {
			if (_portletMonitoringControl.isMonitorPortletResourceRequest()) {
				dataSample = _dataSampleFactory.createPortletRequestDataSample(
					PortletRequestType.RESOURCE, resourceRequest,
					resourceResponse);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			_invokerPortlet.serveResource(resourceRequest, resourceResponse);

			if (_portletMonitoringControl.isMonitorPortletResourceRequest() &&
				(dataSample != null)) {

				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_portletMonitoringControl.isMonitorPortletResourceRequest(),
				dataSample, e);
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
			boolean monitorPortletRequest, DataSample dataSample, Exception e)
		throws IOException, PortletException {

		if (monitorPortletRequest && (dataSample != null)) {
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

	private void _render(
			RenderRequest renderRequest, MimeResponse mimeResponse,
			Renderable renderable, PortletRequestType portletRequestType,
			long timeout)
		throws IOException, PortletException {

		DataSample dataSample = null;

		try {
			if (_portletMonitoringControl.isMonitorPortletHeaderRequest() ||
				_portletMonitoringControl.isMonitorPortletRenderRequest()) {

				dataSample = _dataSampleFactory.createPortletRequestDataSample(
					portletRequestType, renderRequest, mimeResponse);

				dataSample.setTimeout(timeout);

				dataSample.prepare();

				DataSampleThreadLocal.initialize();
			}

			renderable.render();

			if ((_portletMonitoringControl.isMonitorPortletHeaderRequest() ||
				 _portletMonitoringControl.isMonitorPortletRenderRequest()) &&
				(dataSample != null)) {

				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_portletMonitoringControl.isMonitorPortletHeaderRequest() ||
				_portletMonitoringControl.isMonitorPortletRenderRequest(),
				dataSample, e);
		}
		finally {
			if (dataSample != null) {
				DataSampleThreadLocal.addDataSample(dataSample);
			}
		}
	}

	private long _actionTimeout;
	private final DataSampleFactory _dataSampleFactory;
	private long _headerTimeout;
	private InvokerPortlet _invokerPortlet;
	private final PortletMonitoringControl _portletMonitoringControl;
	private long _renderTimeout;

	@FunctionalInterface
	private interface Renderable {

		public void render() throws IOException, PortletException;

	}

}