/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.search.test;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Luan Maoski
 */
public class ExportImportFixture {

	public ExportImportFixture(Group group) {
		_group = group;
	}

	public ExportImportConfiguration createExportImport() throws Exception {
		return createExportImport(RandomTestUtil.randomString());
	}

	public ExportImportConfiguration createExportImport(String name)
		throws Exception {

		long[] layoutIds = ExportImportHelperUtil.getAllLayoutIds(
			_group.getGroupId(), false);

		User user = TestPropsValues.getUser();

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					TestPropsValues.getUserId(), _group.getGroupId(), false,
					layoutIds, new HashMap<>(), user.getLocale(),
					user.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					TestPropsValues.getUserId(), _group.getGroupId(), name,
					RandomTestUtil.randomString(),
					ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET,
					settingsMap, getServiceContext());

		_exportImportConfigurations.add(exportImportConfiguration);

		return exportImportConfiguration;
	}

	public List<ExportImportConfiguration> getExportImportConfigurations() {
		return _exportImportConfigurations;
	}

	public ServiceContext getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), getUserId());
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	protected long getUserId() throws Exception {
		return TestPropsValues.getUserId();
	}

	private final List<ExportImportConfiguration> _exportImportConfigurations =
		new ArrayList<>();
	private final Group _group;

}