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

package com.liferay.arquillian.bundle.manager;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleReference;
import org.osgi.framework.Filter;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 * @author Miguel Pastor
 */
@Component (
	property = {
		"servletName=arquillian-bundle-manager",
		"urlPattern=/arquillian-bundle-manager"
	},
	service = Servlet.class
)
public class BundleManagerServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		ServletContext servletContext = servletConfig.getServletContext();

		if (servletContext instanceof BundleReference) {
			BundleReference bundleReference = (BundleReference)servletContext;

			_bundle = bundleReference.getBundle();
		}

		_contextPathHeader = GetterUtil.getString(
			servletConfig.getInitParameter("contextPathHeader"),
			"Bundle-Context-Path");
		_location = GetterUtil.getString(
			servletConfig.getInitParameter("location"), _LOCATION);
		_timeout = GetterUtil.getLong(
			servletConfig.getInitParameter("timeout"), _TIMEOUT);
	}

	@Override
	protected void doDelete(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException {

		BundleContext bundleContext = _bundle.getBundleContext();

		try {
			Bundle bundle = bundleContext.getBundle(_LOCATION);

			bundle.stop();

			bundle.uninstall();
		}
		catch (BundleException be) {
			throw new ServletException(be);
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		ServiceTracker<ServletContext, ServletContext> serviceTracker = null;

		try {
			BundleContext bundleContext = _bundle.getBundleContext();

			InputStream inputStream = getBundleArchive(request);

			Bundle bundle = bundleContext.installBundle(_location, inputStream);

			bundle.start();

			Filter filter = bundleContext.createFilter(
				"(&(bundle.id=" + bundle.getBundleId() +
					")(objectClass=javax.servlet.ServletContext))");

			serviceTracker = new ServiceTracker<>(bundleContext, filter, null);

			serviceTracker.open();

			ServletContext servletContext = serviceTracker.waitForService(
				_timeout);

			Servlet servlet = waitForServlet(
				servletContext, "ArquillianServletRunner", _timeout);

			if (servlet == null) {
				throw new TimeoutException(
					"The Arquillian servlet runner is taking more than " +
						_timeout + " ms to deploy");
			}

			response.setContentType("text/text");
			response.setHeader(
				_contextPathHeader, servletContext.getContextPath());
			response.setStatus(HttpServletResponse.SC_OK);
		}
		catch (Exception e) {
			sendError(e, response);
		}
		finally {
			if (serviceTracker != null) {
				serviceTracker.close();
			}

			ServletOutputStream servletOutputStream =
				response.getOutputStream();

			servletOutputStream.flush();
		}
	}

	protected InputStream getBundleArchive(HttpServletRequest request)
		throws FileUploadException, IOException {

		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

		ServletConfig servletConfig = getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		File file = (File)servletContext.getAttribute(
			"javax.servlet.context.tempdir");

		diskFileItemFactory.setRepository(file);

		ServletFileUpload servletFileUpload = new ServletFileUpload(
			diskFileItemFactory);

		List<FileItem> fileItems = servletFileUpload.parseRequest(request);

		FileItem fileItem = fileItems.get(0);

		return fileItem.getInputStream();
	}

	protected void sendError(
		Throwable throwable, HttpServletResponse response) {

		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		try {
			ServletOutputStream outputStream = response.getOutputStream();

			response.setContentType(StringPool.UTF8);

			PrintWriter printWriter = new PrintWriter(outputStream);

			throwable.printStackTrace(printWriter);

			printWriter.flush();
		}
		catch (IOException ioe) {
			_log.error("Unable to send error " + throwable.getMessage(), ioe);
		}
	}

	@SuppressWarnings("deprecation")
	protected Servlet waitForServlet(
		ServletContext servletContext, String servletName, long timeout) {

		long attempts = 10;
		long time = 0;

		Servlet servlet = null;

		while ((servlet == null) && (time < timeout)) {
			try {
				Thread.sleep(attempts);
			}
			catch (InterruptedException ie) {
				break;
			}

			try {

				// Our current HTTP service implementation returns a valid
				// servlet. This will be swapped out for a nondeprecated
				// implementation in the future.

				servlet = servletContext.getServlet(servletName);
			}
			catch (ServletException se) {
			}

			time += attempts;
		}

		return servlet;
	}

	private static final String _LOCATION = "BundleManagerServlet";

	private static final long _TIMEOUT = 10000;

	private static final Log _log = LogFactoryUtil.getLog(
		BundleManagerServlet.class);

	private Bundle _bundle;
	private String _contextPathHeader;
	private String _location;
	private long _timeout;

}