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

package com.liferay.moduleadmin.portlet;

import aQute.libg.header.OSGiHeader;
import aQute.libg.version.Version;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.freemarker.FreeMarkerPortlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.startlevel.BundleStartLevel;

/**
 * @author Raymond Augé
 */
public class ModuleAdminPortlet extends FreeMarkerPortlet {

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		PortletContext portletContext = portletConfig.getPortletContext();

		_bundleContext = (BundleContext)portletContext.getAttribute(
			"osgi-bundlecontext");
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try {
			checkPermission();

			UploadPortletRequest uploadRequest =
				PortalUtil.getUploadPortletRequest(actionRequest);

			String cmd = ParamUtil.getString(uploadRequest, Constants.CMD);
			long bundleId = ParamUtil.getLong(uploadRequest, "bundleId");
			File file = uploadRequest.getFile("importBundle");
			String location = ParamUtil.getString(uploadRequest, "location");

			if (cmd.equals("install-from-upload")) {
				if (Validator.isNull(location)) {
					location = uploadRequest.getFullFileName("importBundle");
				}

				if ((file == null) || !file.exists()) {
					throw new IllegalArgumentException("file-does-not-exist");
				}

				InputStream inputStream = new BufferedInputStream(
					new FileInputStream(file));

				try {
					Bundle bundle = getBundle(_bundleContext, inputStream);

					if (bundle == null) {
						_bundleContext.installBundle(location, inputStream);
					}
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
			else if (cmd.equals("install-from-remote-location")) {
				_bundleContext.installBundle(location);
			}
			else if (cmd.equals("update-from-upload")) {
				if ((file == null) || !file.exists()) {
					throw new IllegalArgumentException("file-does-not-exist");
				}

				InputStream inputStream = new BufferedInputStream(
					new FileInputStream(file));

				try {
					Bundle bundle = getBundle(_bundleContext, inputStream);

					if (bundle != null) {
						bundle.update(inputStream);
					}
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
			else if (cmd.equals("update-from-remote-location")) {
				Bundle bundle = _bundleContext.getBundle(bundleId);

				if (bundle != null) {
					bundle.update();
				}
			}
			else if (cmd.equals("uninstall")) {
				Bundle bundle = _bundleContext.getBundle(bundleId);

				if (bundle != null) {
					bundle.uninstall();
				}
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if ((e instanceof BundleException) ||
				(e instanceof IllegalArgumentException) ||
				(e instanceof PrincipalException)) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute("bundleContext", _bundleContext);

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);
		long bundleId = ParamUtil.getLong(resourceRequest, "bundleId");

		resourceResponse.setContentType(ContentTypes.APPLICATION_JSON);

		String json = null;

		try {
			Bundle bundle = _bundleContext.getBundle(bundleId);

			if (cmd.equals("setBundleStartLevel")) {
				int startLevel = ParamUtil.getInteger(
					resourceRequest, "startLevel");

				BundleStartLevel bundleStartLevel = bundle.adapt(
					BundleStartLevel.class);

				bundleStartLevel.setStartLevel(startLevel);
			}
			else if (cmd.equals("startBundle")) {
				bundle.start();
			}
			else if (cmd.equals("stopBundle")) {
				bundle.stop();
			}
			else if (cmd.equals("uninstallBundle")) {
				bundle.uninstall();
			}
			else if (cmd.equals("updateBundle")) {
				bundle.update();
			}

			int state = bundle.getState();

			JSONObject jsonResult = JSONFactoryUtil.createJSONObject();

			if (state == Bundle.ACTIVE) {
				jsonResult.put("state", "active");
			}
			else if (state == Bundle.INSTALLED) {
				jsonResult.put("state", "installed");
			}
			else if (state == Bundle.RESOLVED) {
				jsonResult.put("state", "resolved");
			}
			else if (state == Bundle.STARTING) {
				jsonResult.put("state", "starting");
			}
			else if (state == Bundle.STOPPING) {
				jsonResult.put("state", "stopping");
			}
			else if (state == Bundle.UNINSTALLED) {
				jsonResult.put("state", "uninstalled");
			}
			else {
				jsonResult.put("state", StringPool.BLANK);
			}

			json = jsonResult.toString();
		}
		catch (BundleException be) {
			json = JSONFactoryUtil.serializeException(be);
		}

		PrintWriter writer = resourceResponse.getWriter();

		writer.print(json);
		writer.flush();
		writer.close();
	}

	private void checkPermission() throws PrincipalException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) || !permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}
	}

	private Bundle getBundle(
			BundleContext bundleContext, InputStream inputStream)
		throws PortalException {

		try {
			if (inputStream.markSupported()) {

				// 1Mb is a very large manifest file, should be enough

				inputStream.mark(1024 * 1024);
			}

			JarInputStream jarInputStream = new JarInputStream(inputStream);

			Manifest manifest = jarInputStream.getManifest();

			if (inputStream.markSupported()) {
				inputStream.reset();
			}

			Attributes attributes = manifest.getMainAttributes();

			String bundleSymbolicNameAttribute = attributes.getValue(
				org.osgi.framework.Constants.BUNDLE_SYMBOLICNAME);

			Map<String, Map<String, String>> bundleSymbolicNamesMap =
				OSGiHeader.parseHeader(bundleSymbolicNameAttribute);

			Set<String> bundleSymbolicNamesSet =
				bundleSymbolicNamesMap.keySet();

			Iterator<String> bundleSymbolicNamesIterator =
				bundleSymbolicNamesSet.iterator();

			String bundleSymbolicName = bundleSymbolicNamesIterator.next();

			String bundleVersionAttribute = attributes.getValue(
				org.osgi.framework.Constants.BUNDLE_VERSION);

			Version bundleVersion = Version.parseVersion(
				bundleVersionAttribute);

			for (Bundle bundle : bundleContext.getBundles()) {
				Version curBundleVersion = Version.parseVersion(
					bundle.getVersion().toString());

				if (bundleSymbolicName.equals(bundle.getSymbolicName()) &&
					bundleVersion.equals(curBundleVersion)) {

					return bundle;
				}
			}

			return null;
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	private BundleContext _bundleContext;

}