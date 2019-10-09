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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.web.servlet.JSPServletFactory;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;

import javax.servlet.Servlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Constants;
import org.osgi.framework.VersionRange;
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
			new JspFragmentBundleTrackerCustomizer(bundleContext));

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

	private static final Log _log = LogFactoryUtil.getLog(
		JSPServletFactoryImpl.class);

	private BundleTracker<Tracked> _bundleTracker;

	private static class JspFragmentBundleTrackerCustomizer
		implements BundleTrackerCustomizer<Tracked> {

		@Override
		public Tracked addingBundle(Bundle bundle, BundleEvent event) {
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

			String[] fragmentHostParts = StringUtil.split(
				fragmentHost, CharPool.SEMICOLON);

			String symbolicName = StringUtil.trim(fragmentHostParts[0]);

			VersionRange versionRange = null;

			if (fragmentHostParts.length > 1) {
				String[] versionParts = StringUtil.split(
					fragmentHostParts[1], CharPool.EQUAL);

				versionRange = new VersionRange(
					StringUtil.unquote(StringUtil.trim(versionParts[1])));
			}

			Tracked tracked = new Tracked(symbolicName, versionRange);

			_deleteJSPServletClasses(tracked);

			return tracked;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent event, Tracked tracked) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent event, Tracked tracked) {

			_deleteJSPServletClasses(tracked);
		}

		private JspFragmentBundleTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private void _deleteJSPServletClasses(Tracked tracked) {
			for (Bundle bundle : _bundleContext.getBundles()) {
				if (!tracked.match(bundle)) {
					continue;
				}

				StringBundler sb = new StringBundler(4);

				sb.append(_WORK_DIR);
				sb.append(bundle.getSymbolicName());
				sb.append(StringPool.DASH);
				sb.append(bundle.getVersion());

				String scratchDir = sb.toString();

				if (PropsValues.WORK_DIR_OVERRIDE_ENABLED &&
					_log.isInfoEnabled()) {

					_log.info(
						"Deleting JSP class files from ".concat(scratchDir));
				}
				else if (_log.isDebugEnabled()) {
					_log.debug(
						"Deleting JSP class files from ".concat(scratchDir));
				}

				FileUtil.deltree(new File(scratchDir));
			}
		}

		private final BundleContext _bundleContext;

	}

	private static class Tracked {

		public boolean match(Bundle bundle) {
			if (_symbolicName.equals(bundle.getSymbolicName()) &&
				((_versionRange == null) ||
				 _versionRange.includes(bundle.getVersion()))) {

				return true;
			}

			return false;
		}

		private Tracked(String symbolicName, VersionRange versionRange) {
			_symbolicName = symbolicName;
			_versionRange = versionRange;
		}

		private final String _symbolicName;
		private final VersionRange _versionRange;

	}

}