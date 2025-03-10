/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.portlet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletAsyncContext;
import javax.portlet.PortletContext;
import javax.portlet.ResourceParameters;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.portlet.ResourceURL;
import javax.servlet.DispatcherType;

/**
 * @author Dante Wang
 */
public class MockResourceRequest
	extends MockClientDataRequest implements ResourceRequest {

	public MockResourceRequest() {
	}

	public MockResourceRequest(ResourceURL resourceURL) {
		_resourceID = resourceURL.getResourceID();
		_cacheability = resourceURL.getCacheability();
	}

	public MockResourceRequest(
		PortalContext portalContext, PortletContext portletContext) {

		super(portalContext, portletContext);
	}

	public MockResourceRequest(PortletContext portletContext) {
		super(portletContext);
	}

	public MockResourceRequest(String resourceID) {
		_resourceID = resourceID;
	}

	public void addPrivateRenderParameter(String key, String value) {
		_privateRenderParameters.put(key, new String[] {value});
	}

	public void addPrivateRenderParameter(String key, String[] values) {
		_privateRenderParameters.put(key, values);
	}

	@Override
	public String getCacheability() {
		return _cacheability;
	}

	@Override
	public DispatcherType getDispatcherType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getETag() {
		return getProperty("portlet.ETag");
	}

	@Override
	public PortletAsyncContext getPortletAsyncContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getPrivateRenderParameterMap() {
		return Collections.unmodifiableMap(_privateRenderParameters);
	}

	@Override
	public String getResourceID() {
		return _resourceID;
	}

	@Override
	public ResourceParameters getResourceParameters() {
		if (_resourceParameters == null) {
			_resourceParameters = new MockResourceParameters();
		}

		return _resourceParameters;
	}

	@Override
	public boolean isAsyncStarted() {
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		return false;
	}

	public void setCacheability(String cacheLevel) {
		_cacheability = cacheLevel;
	}

	public void setResourceID(String resourceID) {
		_resourceID = resourceID;
	}

	@Override
	public PortletAsyncContext startPortletAsync()
		throws IllegalStateException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PortletAsyncContext startPortletAsync(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IllegalStateException {

		throw new UnsupportedOperationException();
	}

	@Override
	protected String getLifecyclePhase() {
		return "RESOURCE_PHASE";
	}

	private String _cacheability;
	private final Map<String, String[]> _privateRenderParameters =
		new LinkedHashMap<>();
	private String _resourceID;
	private ResourceParameters _resourceParameters;

}