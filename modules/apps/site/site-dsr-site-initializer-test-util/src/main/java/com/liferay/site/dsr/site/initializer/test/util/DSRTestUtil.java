/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.test.util;

import com.liferay.batch.engine.test.util.BatchEngineTestUtil;
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
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

/**
 * @author Stefano Motta
 */
public class DSRTestUtil {

	public static Group getOrAddGroup(Class<?> clazz) throws Exception {
		Group group = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId(), GroupConstants.DSR);

		if (group != null) {
			return group;
		}

		group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0,
			GroupConstants.DSR);

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
				// feature flag LPD-66359 enabled. On CI, feature flags are
				// enabled on demand for each test, but not during instance
				// initialization. Until the feature flag LPD-66359 is removed,
				// run the instance lifecycle initializer manually so that the
				// role is created.

				SiteInitializerRegistry siteInitializerRegistry =
					_siteInitializerRegistrySnapshot.get();

				SiteInitializer siteInitializer =
					siteInitializerRegistry.getSiteInitializer(
						_BUNDLE_SYMBOLIC_NAME);

				siteInitializer.initialize(group.getGroupId());

				BatchEngineTestUtil.processBatchEngineUnits(
					_BUNDLE_SYMBOLIC_NAME, clazz,
					new String[] {
						"." + _BUNDLE_SYMBOLIC_NAME +
							".internal.batch.01.object.folder",
						"." + _BUNDLE_SYMBOLIC_NAME +
							".internal.batch.02.object.definition",
						"." + _BUNDLE_SYMBOLIC_NAME +
							".internal.batch.03.notification.template"
					});
			}
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);

			ServiceContextThreadLocal.popServiceContext();
		}

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-66359");

		return group;
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.site.initializer.dsr";

	private static final Snapshot<SiteInitializerRegistry>
		_siteInitializerRegistrySnapshot = new Snapshot<>(
			DSRTestUtil.class, SiteInitializerRegistry.class);

}