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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.web.servlet.JSPServletFactory;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Servlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Constants;
import org.osgi.framework.namespace.HostNamespace;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = JSPServletFactory.class)
public class JSPServletFactoryImpl implements JSPServletFactory {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.RESOLVED,
			new JspFragmentBundleTrackerCustomizer());

		_bundleTracker.open();
	}

	@Override
	public Servlet createJSPServlet() {
		return new JspServlet();
	}

	@Deactivate
	public void deactivate() {
		_bundleTracker.close();
	}

	private static final String _DIR_NAME_RESOURCES = "/META-INF/resources";

	private static final String _WORK_DIR = StringBundler.concat(
		PropsValues.LIFERAY_HOME, File.separator, "work", File.separator);

	private BundleTracker<List<String>> _bundleTracker;

	private class JspFragmentBundleTrackerCustomizer
		implements BundleTrackerCustomizer<List<String>> {

		@Override
		public List<String> addingBundle(Bundle bundle, BundleEvent event) {
			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			String fragmentHost = headers.get(Constants.FRAGMENT_HOST);

			if (fragmentHost == null) {
				return null;
			}

			Enumeration<URL> enumeration = bundle.findEntries(
				_DIR_NAME_RESOURCES, "*.jsp", true);

			if (enumeration == null) {
				return null;
			}

			List<String> paths = new ArrayList<>();

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				String pathString = url.getPath();

				pathString = pathString.substring(
					_DIR_NAME_RESOURCES.length() + 1, pathString.length() - 4);

				pathString = StringUtil.replace(
					pathString, CharPool.UNDERLINE, "_005f");

				paths.add(
					"/org/apache/jsp/".concat(pathString).concat("_jsp.class"));
			}

			_deleteJSPServletClasses(bundle, paths);

			return paths;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent event, List<String> paths) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent event, List<String> paths) {

			_deleteJSPServletClasses(bundle, paths);
		}

		private void _deleteJSPServletClasses(
			Bundle fragmentBundle, List<String> paths) {

			BundleWiring bundleWiring = fragmentBundle.adapt(
				BundleWiring.class);

			List<BundleWire> bundleWires = bundleWiring.getRequiredWires(
				HostNamespace.HOST_NAMESPACE);

			if ((bundleWires == null) || bundleWires.isEmpty()) {
				return;
			}

			for (BundleWire bundleWire : bundleWires) {
				BundleRevision hostBundleRevision = bundleWire.getProvider();

				StringBundler sb = new StringBundler(4);

				sb.append(_WORK_DIR);
				sb.append(hostBundleRevision.getSymbolicName());
				sb.append(StringPool.DASH);
				sb.append(hostBundleRevision.getVersion());

				String scratchDir = sb.toString();

				for (String path : paths) {
					File file = new File(scratchDir, path);

					file.delete();
				}
			}
		}

	}

}