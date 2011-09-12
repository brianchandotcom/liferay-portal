/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.osgiadmin.service.impl;

import com.liferay.portal.adaptor.osgi.OSGiAdaptor;
import com.liferay.portal.kernel.adaptor.Adaptor;
import com.liferay.portal.kernel.adaptor.AdaptorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.osgiadmin.OSGiException;
import com.liferay.portlet.osgiadmin.service.base.OSGiServiceBaseImpl;

import java.io.InputStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.startlevel.BundleStartLevel;

/**
 * The implementation of the o s gi remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.osgiadmin.service.OSGiService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.osgiadmin.service.base.OSGiServiceBaseImpl
 * @see com.liferay.portlet.osgiadmin.service.OSGiServiceUtil
 */
public class OSGiServiceImpl extends OSGiServiceBaseImpl {

	public Object addBundle(String location)
		throws PortalException, SystemException {

		return addBundle(location, null);
	}

	public Object addBundle(String location, InputStream inputStream)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		BundleContext bundleContext = (BundleContext)getBundleContext();

		try {
			return bundleContext.installBundle(location, inputStream);
		}
		catch (BundleException be) {
			_log.error(be);

			throw new OSGiException(be);
		}
	}

	public Object getBundleContext() {
		Framework framework = (Framework)getFramework();

		return framework.getBundleContext();
	}

	public Object getFramework() {
		if (_framework == null) {
			try {
				Adaptor adaptor = AdaptorUtil.getAdaptor();

				if ((adaptor != null) && (adaptor instanceof OSGiAdaptor)) {
					OSGiAdaptor osgiAdaptor = (OSGiAdaptor)adaptor;

					_framework = osgiAdaptor.getFramework();
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _framework;
	}

	public String getState(long bundleId)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		String state = StringPool.BLANK;

		if (bundle.getState() == Bundle.ACTIVE) {
			state = "active";
		}
		else if (bundle.getState() == Bundle.INSTALLED) {
			state = "installed";
		}
		else if (bundle.getState() == Bundle.RESOLVED) {
			state = "resolved";
		}
		else if (bundle.getState() == Bundle.STARTING) {
			state = "starting";
		}
		else if (bundle.getState() == Bundle.STOPPING) {
			state = "stopping";
		}
		else if (bundle.getState() == Bundle.UNINSTALLED) {
			state = "uninstalled";
		}

		return state;
	}

	public void setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		BundleStartLevel bundleStartLevel = (BundleStartLevel)bundle;

		bundleStartLevel.setStartLevel(startLevel);
	}

	public void startBundle(long bundleId)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		try {
			bundle.start();
		}
		catch (BundleException be) {
			_log.error(be);

			throw new OSGiException(be);
		}
	}

	public void startBundle(long bundleId, int options)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		try {
			bundle.start(options);
		}
		catch (BundleException be) {
			_log.error(be);

			throw new OSGiException(be);
		}
	}

	public void stopBundle(long bundleId)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		try {
			bundle.stop();
		}
		catch (BundleException be) {
			_log.error(be);

			throw new OSGiException(be);
		}
	}

	public void stopBundle(long bundleId, int options)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		try {
			bundle.stop(options);
		}
		catch (BundleException be) {
			_log.error(be);

			throw new OSGiException(be);
		}
	}

	public void uninstallBundle(long bundleId)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		try {
			bundle.uninstall();
		}
		catch (BundleException be) {
			_log.error(be);

			throw new OSGiException(be);
		}
	}

	public void updateBundle(long bundleId)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		try {
			bundle.update();
		}
		catch (BundleException be) {
			_log.error(be);

			throw new OSGiException(be);
		}
	}

	public void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("no such bundle " + bundleId);
		}

		try {
			bundle.update(inputStream);
		}
		catch (BundleException be) {
			_log.error(be);

			throw new OSGiException(be);
		}
	}

	protected Bundle getBundle(long bundleId) {
		BundleContext bundleContext = (BundleContext)getBundleContext();

		return bundleContext.getBundle(bundleId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OSGiServiceImpl.class);

	private Framework _framework;

}