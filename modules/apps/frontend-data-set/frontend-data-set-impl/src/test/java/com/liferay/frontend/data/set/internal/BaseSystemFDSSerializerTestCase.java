/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MapUtil;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Sanz
 */
public abstract class BaseSystemFDSSerializerTestCase {

	@Before
	public void setUp() throws Exception {
		bundleContext = SystemBundleUtil.getBundleContext();

		systemFDSEntryserviceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, SystemFDSEntry.class, "frontend.data.set.name");

		ReflectionTestUtil.setFieldValue(
			systemFDSEntryRegistryImpl, "_serviceTrackerMap",
			systemFDSEntryserviceTrackerMap);
	}

	@After
	public void tearDown() {
		systemFDSEntryserviceTrackerMap.close();
	}

	protected ServiceRegistration<SystemFDSEntry> registerSystemFDSEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema) {

		return registerSystemFDSEntry(
			fdsName, restApplication, restEndpoint, restSchema, null);
	}

	protected ServiceRegistration<SystemFDSEntry> registerSystemFDSEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema, String additionalURLParameters) {

		return bundleContext.registerService(
			SystemFDSEntry.class,
			new SystemFDSEntry() {

				@Override
				public String getAdditionalAPIURLParameters() {
					return additionalURLParameters;
				}

				@Override
				public String getDescription() {
					return "";
				}

				@Override
				public String getName() {
					return fdsName;
				}

				@Override
				public String getRESTApplication() {
					return restApplication;
				}

				@Override
				public String getRESTEndpoint() {
					return restEndpoint;
				}

				@Override
				public String getRESTSchema() {
					return restSchema;
				}

				@Override
				public String getTitle() {
					return "";
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	protected static BundleContext bundleContext =
		SystemBundleUtil.getBundleContext();
	protected static final HttpServletRequest httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	protected static final SystemFDSEntryRegistryImpl
		systemFDSEntryRegistryImpl = new SystemFDSEntryRegistryImpl();
	protected static ServiceTrackerMap<String, SystemFDSEntry>
		systemFDSEntryserviceTrackerMap;

}