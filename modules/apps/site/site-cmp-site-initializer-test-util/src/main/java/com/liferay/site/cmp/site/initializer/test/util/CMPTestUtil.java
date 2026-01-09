/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.test.util;

import com.liferay.batch.engine.test.util.BatchEngineTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

/**
 * @author Carolina Barbosa
 */
public class CMPTestUtil {

	public static Group getOrAddGroup(Class<?> clazz) throws Exception {
		Group group = CMSTestUtil.getOrAddGroup(clazz);

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_CMP_PROJECT", TestPropsValues.getCompanyId());

		if (objectDefinition != null) {
			return group;
		}

		try {
			ServiceContextThreadLocal.pushServiceContext(
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setCompanyIdWithSafeCloseable(
						group.getCompanyId())) {

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
							".internal.batch.00.list.type.definition",
						"." + _BUNDLE_SYMBOLIC_NAME +
							".internal.batch.01.object.folder",
						"." + _BUNDLE_SYMBOLIC_NAME +
							".internal.batch.02.object.definition"
					});
			}
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		return group;
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.site.initializer.cmp";

	private static final Snapshot<SiteInitializerRegistry>
		_siteInitializerRegistrySnapshot = new Snapshot<>(
			CMPTestUtil.class, SiteInitializerRegistry.class);

}