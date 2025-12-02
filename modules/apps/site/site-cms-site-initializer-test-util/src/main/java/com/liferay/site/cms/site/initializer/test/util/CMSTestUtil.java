/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.test.util;

import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.io.File;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Roberto Díaz
 * @author Stefano Motta
 */
public class CMSTestUtil {

	public static Group getOrAddGroup(Class<?> clazz) throws Exception {
		Group group = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId(), GroupConstants.CMS);

		if (group != null) {
			return group;
		}

		group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0,
			GroupConstants.CMS);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String originalName = PrincipalThreadLocal.getName();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

			PrincipalThreadLocal.setName(TestPropsValues.getUserId());

			ServiceContextThreadLocal.pushServiceContext(
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setCompanyIdWithSafeCloseable(
						group.getCompanyId())) {

				// These tests require the instance to be created with the
				// feature flag LPD-17564 enabled. On CI, feature flags are
				// enabled on demand for each test, but not during instance
				// initialization. Until the feature flag LPD-17564 is removed,
				// run the instance lifecycle initializer manually so that the
				// role is created.

				SiteInitializerRegistry siteInitializerRegistry =
					_siteInitializerRegistrySnapshot.get();

				SiteInitializer siteInitializer =
					siteInitializerRegistry.getSiteInitializer(
						"com.liferay.site.initializer.cms");

				siteInitializer.initialize(group.getGroupId());

				BatchEngineUnitProcessor batchEngineUnitProcessor =
					_batchEngineUnitProcessorSnapshot.get();
				BatchEngineUnitReader batchEngineUnitReader =
					_batchEngineUnitReaderSnapshot.get();

				Bundle testBundle = FrameworkUtil.getBundle(clazz);

				BundleContext bundleContext = testBundle.getBundleContext();

				for (Bundle bundle : bundleContext.getBundles()) {
					if (Objects.equals(
							bundle.getSymbolicName(),
							"com.liferay.site.initializer.cms")) {

						_deleteFile(bundle, "00.list.type.definition");
						_deleteFile(bundle, "01.object.folder");
						_deleteFile(bundle, "02.object.definition");

						CompletableFuture<Void> completableFuture =
							batchEngineUnitProcessor.processBatchEngineUnits(
								batchEngineUnitReader.getBatchEngineUnits(
									bundle));

						completableFuture.join();
					}
				}
			}
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);

			ServiceContextThreadLocal.popServiceContext();
		}

		return group;
	}

	private static void _deleteFile(Bundle bundle, String fileName) {
		File file = bundle.getDataFile(
			".com.liferay.site.initializer.cms.internal.batch." + fileName +
				".batch.engine.data.json.0.processed");

		if ((file != null) && file.exists()) {
			file.delete();
		}
	}

	private static final Snapshot<BatchEngineUnitProcessor>
		_batchEngineUnitProcessorSnapshot = new Snapshot<>(
			CMSTestUtil.class, BatchEngineUnitProcessor.class);
	private static final Snapshot<BatchEngineUnitReader>
		_batchEngineUnitReaderSnapshot = new Snapshot<>(
			CMSTestUtil.class, BatchEngineUnitReader.class);
	private static final Snapshot<SiteInitializerRegistry>
		_siteInitializerRegistrySnapshot = new Snapshot<>(
			CMSTestUtil.class, SiteInitializerRegistry.class);

}