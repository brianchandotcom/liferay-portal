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

package com.liferay.layout.seo.web.internal.servlet.filter;

import com.liferay.layout.seo.web.internal.configuration.LayoutSEODynamicRenderingConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Jamie Sammons
 */
@Component(
	configurationPid = "com.liferay.layout.seo.web.internal.configuration.LayoutSEODynamicRenderingConfiguration",
	immediate = true,
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SEO Dynamic Rendering Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class LayoutSEODynamicRenderingFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			if (!_layoutSEODynamicRenderingConfiguration.enabled() ||
				!_validateUserAgent(
					StringUtil.toLowerCase(
						httpServletRequest.getHeader(HttpHeaders.USER_AGENT)),
					_layoutSEODynamicRenderingConfiguration.
						crawlerUserAgents())) {

				return false;
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			String requestURI = serviceContext.getCurrentURL();

			for (String extension :
					_layoutSEODynamicRenderingConfiguration.
						extensionIgnoreList()) {

				if (requestURI.endsWith(extension)) {
					return false;
				}
			}

			if (!_validatePath(
					requestURI,
					_layoutSEODynamicRenderingConfiguration.pathList())) {

				return false;
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return true;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_layoutSEODynamicRenderingConfiguration =
			ConfigurableUtil.createConfigurable(
				LayoutSEODynamicRenderingConfiguration.class, properties);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		_poolingClientConnectionManager =
			new PoolingHttpClientConnectionManager();

		_poolingClientConnectionManager.setDefaultMaxPerRoute(
			_layoutSEODynamicRenderingConfiguration.
				defaultMaxConnectionsPerRoute());

		SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();

		socketConfigBuilder.setSoTimeout(
			_layoutSEODynamicRenderingConfiguration.soTimeout());

		_poolingClientConnectionManager.setDefaultSocketConfig(
			socketConfigBuilder.build());

		_poolingClientConnectionManager.setMaxTotal(
			_layoutSEODynamicRenderingConfiguration.maxTotalConnections());

		httpClientBuilder.setConnectionManager(_poolingClientConnectionManager);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		requestConfigBuilder.setConnectTimeout(
			_layoutSEODynamicRenderingConfiguration.connectionManagerTimeout());

		requestConfigBuilder.setNormalizeUri(false);

		httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

		httpClientBuilder.setRetryHandler(
			new DefaultHttpRequestRetryHandler(0, false));

		_closeableHttpClient = httpClientBuilder.build();
	}

	@Deactivate
	protected void deactivate() {
		if (_closeableHttpClient != null) {
			try {
				_closeableHttpClient.close();
			}
			catch (IOException ioException) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioException);
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Shutting down " + getClass().getName());
		}

		if (_poolingClientConnectionManager == null) {
			return;
		}

		int retry = 0;

		while (retry < 10) {
			PoolStats poolStats =
				_poolingClientConnectionManager.getTotalStats();

			int availableConnections = poolStats.getAvailable();

			if (availableConnections <= 0) {
				break;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						getClass().getName(), " is waiting on ",
						availableConnections, " connections"));
			}

			_poolingClientConnectionManager.closeIdleConnections(
				200, TimeUnit.MILLISECONDS);

			try {
				Thread.sleep(500);
			}
			catch (InterruptedException interruptedException) {
				if (_log.isDebugEnabled()) {
					_log.debug(interruptedException);
				}
			}

			retry++;
		}

		_poolingClientConnectionManager.shutdown();

		_poolingClientConnectionManager = null;

		if (_log.isDebugEnabled()) {
			_log.debug(this + " was shut down");
		}
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		deactivate();

		activate(properties);
	}

	protected void processFilter(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FilterChain filterChain) {

		String serviceURL =
			_layoutSEODynamicRenderingConfiguration.serviceUrl();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		String requestURL =
			serviceContext.getPortalURL() + serviceContext.getCurrentURL();

		HttpGet httpGet = new HttpGet(
			serviceURL + StringPool.SLASH + requestURL);

		String serviceToken =
			_layoutSEODynamicRenderingConfiguration.serviceToken();

		if (!serviceToken.isEmpty()) {
			httpGet.addHeader("X-Prerender-Token", serviceToken);
		}

		CloseableHttpClient httpClient = _closeableHttpClient;

		try (CloseableHttpResponse closeableHttpResponse = httpClient.execute(
				httpGet)) {

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			httpServletResponse.setStatus(statusLine.getStatusCode());

			_getHtml(closeableHttpResponse, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
		finally {
			httpGet.releaseConnection();
		}
	}

	private void _getHtml(
		CloseableHttpResponse closeableHttpResponse,
		HttpServletResponse httpServletResponse) {

		httpServletResponse.setContentType("text/html");

		try (PrintWriter printWriter = httpServletResponse.getWriter()) {
			HttpEntity httpEntity = closeableHttpResponse.getEntity();

			printWriter.write(EntityUtils.toString(httpEntity));

			printWriter.flush();
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
	}

	private boolean _validatePath(String renderURI, String[] pathList) {
		boolean validPath = false;

		for (String path : pathList) {
			if (renderURI.contains(StringUtil.toLowerCase(path))) {
				validPath = true;

				break;
			}
		}

		return validPath;
	}

	private boolean _validateUserAgent(
		String requestUserAgent, String[] userAgents) {

		boolean validUserAgent = false;

		for (String userAgent : userAgents) {
			if (requestUserAgent.contains(StringUtil.toLowerCase(userAgent))) {
				validUserAgent = true;

				break;
			}
		}

		return validUserAgent;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEODynamicRenderingFilter.class);

	private CloseableHttpClient _closeableHttpClient;
	private LayoutSEODynamicRenderingConfiguration
		_layoutSEODynamicRenderingConfiguration;
	private PoolingHttpClientConnectionManager _poolingClientConnectionManager;

}