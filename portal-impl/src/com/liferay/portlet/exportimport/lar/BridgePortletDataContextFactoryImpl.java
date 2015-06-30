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

package com.liferay.portlet.exportimport.lar;

import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class BridgePortletDataContextFactoryImpl
	implements PortletDataContextFactory {

	public BridgePortletDataContextFactoryImpl() {
		this(new DummyPortletDataContextFactoryImpl());
	}

	public BridgePortletDataContextFactoryImpl(
		PortletDataContextFactory defaultPortletDataContextFactory) {

		_defaultPortletDataContextFactory = defaultPortletDataContextFactory;

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortletDataContextFactory.class);

		_serviceTracker.open();
	}

	@Override
	public PortletDataContext clonePortletDataContext(
		PortletDataContext portletDataContext) {

		return getPortletDataContextFactory().clonePortletDataContext(
			portletDataContext);
	}

	@Override
	public PortletDataContext createExportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate, ZipWriter zipWriter)
		throws PortletDataException {

		return getPortletDataContextFactory().createExportPortletDataContext(
			companyId, groupId, parameterMap, startDate, endDate, zipWriter);
	}

	@Override
	public PortletDataContext createImportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			UserIdStrategy userIdStrategy, ZipReader zipReader)
		throws PortletDataException {

		return getPortletDataContextFactory().createImportPortletDataContext(
			companyId, groupId, parameterMap, userIdStrategy, zipReader);
	}

	@Override
	public PortletDataContext createPreparePortletDataContext(
			long companyId, long groupId, Date startDate, Date endDate)
		throws PortletDataException {

		return getPortletDataContextFactory().createPreparePortletDataContext(
			companyId, groupId, startDate, endDate);
	}

	@Override
	public PortletDataContext createPreparePortletDataContext(
			ThemeDisplay themeDisplay, Date startDate, Date endDate)
		throws PortletDataException {

		return getPortletDataContextFactory().createPreparePortletDataContext(
			themeDisplay, startDate, endDate);
	}

	protected PortletDataContextFactory getPortletDataContextFactory() {
		if (_serviceTracker.isEmpty()) {
			return _defaultPortletDataContextFactory;
		}

		return _serviceTracker.getService();
	}

	private final PortletDataContextFactory _defaultPortletDataContextFactory;
	private final ServiceTracker
		<PortletDataContextFactory, PortletDataContextFactory> _serviceTracker;

}