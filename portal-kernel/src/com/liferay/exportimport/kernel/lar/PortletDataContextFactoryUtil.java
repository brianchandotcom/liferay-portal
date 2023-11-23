/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.util.Date;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class PortletDataContextFactoryUtil {

	public static PortletDataContext clonePortletDataContext(
		PortletDataContext portletDataContext) {

		PortletDataContextFactory portletDataContextFactory =
			_getPortletDataContextFactory();

		return portletDataContextFactory.clonePortletDataContext(
			portletDataContext);
	}

	public static PortletDataContext createExportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate, ZipWriter zipWriter)
		throws PortletDataException {

		PortletDataContextFactory portletDataContextFactory =
			_getPortletDataContextFactory();

		return portletDataContextFactory.createExportPortletDataContext(
			companyId, groupId, parameterMap, startDate, endDate, zipWriter);
	}

	public static PortletDataContext createImportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			UserIdStrategy userIdStrategy, ZipReader zipReader)
		throws PortletDataException {

		PortletDataContextFactory portletDataContextFactory =
			_getPortletDataContextFactory();

		return portletDataContextFactory.createImportPortletDataContext(
			companyId, groupId, parameterMap, userIdStrategy, zipReader);
	}

	public static PortletDataContext createPreparePortletDataContext(
			long companyId, long groupId, Date startDate, Date endDate)
		throws PortletDataException {

		PortletDataContextFactory portletDataContextFactory =
			_getPortletDataContextFactory();

		return portletDataContextFactory.createPreparePortletDataContext(
			companyId, groupId, startDate, endDate);
	}

	public static PortletDataContext createPreparePortletDataContext(
			long companyId, long groupId, String range, Date startDate,
			Date endDate)
		throws PortletDataException {

		PortletDataContextFactory portletDataContextFactory =
			_getPortletDataContextFactory();

		return portletDataContextFactory.createPreparePortletDataContext(
			companyId, groupId, range, startDate, endDate);
	}

	public static PortletDataContext createPreparePortletDataContext(
			ThemeDisplay themeDisplay, Date startDate, Date endDate)
		throws PortletDataException {

		PortletDataContextFactory portletDataContextFactory =
			_getPortletDataContextFactory();

		return portletDataContextFactory.createPreparePortletDataContext(
			themeDisplay, startDate, endDate);
	}

	private static PortletDataContextFactory _getPortletDataContextFactory() {
		PortletDataContextFactory portletDataContextFactory =
			_portletDataContextFactorySnapshot.get();

		if (portletDataContextFactory == null) {
			return DummyPortletDataContextFactoryHolder.
				_dummyPortletDataContextFactory;
		}

		return portletDataContextFactory;
	}

	private static final Snapshot<PortletDataContextFactory>
		_portletDataContextFactorySnapshot = new Snapshot<>(
			PortletDataContextFactoryUtil.class,
			PortletDataContextFactory.class);

	private static class DummyPortletDataContextFactoryHolder {

		private static final PortletDataContextFactory
			_dummyPortletDataContextFactory = ProxyFactory.newDummyInstance(
				PortletDataContextFactory.class);

	}

}