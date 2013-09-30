/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public final class EmptyModuleFramework implements ModuleFramework {

	@Override
	public Object addBundle(String location) throws PortalException {
		return null;
	}

	@Override
	public Object addBundle(String location, InputStream inputStream)
			throws PortalException {

		return null;
	}

	@Override
	public Map<String, List<URL>> getExtraPackageMap() {
		return null;
	}

	@Override
	public List<URL> getExtraPackageURLs() {
		return null;
	}

	@Override
	public Object getFramework() {
		return null;
	}

	@Override
	public String getState(long bundleId) throws PortalException {
		return null;
	}

	@Override
	public void registerContext(Object context) {
	}

	@Override
	public void setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException {
	}

	@Override
	public void startBundle(long bundleId) throws PortalException {
	}

	@Override
	public void startBundle(long bundleId, int options) throws PortalException {
	}

	@Override
	public void startFramework() throws Exception {
	}

	@Override
	public void startRuntime() throws Exception {
	}

	@Override
	public void stopBundle(long bundleId) throws PortalException {
	}

	@Override
	public void stopBundle(long bundleId, int options) throws PortalException {
	}

	@Override
	public void stopFramework() throws Exception {
	}

	@Override
	public void stopRuntime() throws Exception {
	}

	@Override
	public void uninstallBundle(long bundleId) throws PortalException {
	}

	@Override
	public void updateBundle(long bundleId) throws PortalException {
	}

	@Override
	public void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {
	}

}