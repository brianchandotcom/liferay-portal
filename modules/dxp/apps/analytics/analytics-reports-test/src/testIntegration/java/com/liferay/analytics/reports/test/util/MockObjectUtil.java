/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.test.util;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.test.MockObject;
import com.liferay.analytics.reports.test.analytics.reports.info.item.MockAnalyticsReportsInfoItem;
import com.liferay.analytics.reports.test.info.display.contributor.MockInfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Cristina González
 */
public class MockObjectUtil {

	public static void testWithMockObject(
			ClassNameLocalService classNameLocalService,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		ClassName className = classNameLocalService.addClassName(
			MockObject.class.getName());

		Bundle bundle = FrameworkUtil.getBundle(MockObjectUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<AnalyticsReportsInfoItem<MockObject>>
			analyticsReportsInfoItemServiceRegistration = null;

		ServiceRegistration<InfoDisplayContributor<MockObject>>
			infoDisplayContributorServiceRegistration = null;

		try {
			analyticsReportsInfoItemServiceRegistration =
				bundleContext.registerService(
					(Class<AnalyticsReportsInfoItem<MockObject>>)
						(Class<?>)AnalyticsReportsInfoItem.class,
					new MockAnalyticsReportsInfoItem(),
					new HashMapDictionary<>());

			infoDisplayContributorServiceRegistration =
				bundleContext.registerService(
					(Class<InfoDisplayContributor<MockObject>>)
						(Class<?>)InfoDisplayContributor.class,
					new MockInfoDisplayContributor(classNameLocalService),
					new HashMapDictionary<>());

			unsafeRunnable.run();
		}
		finally {
			if (analyticsReportsInfoItemServiceRegistration != null) {
				analyticsReportsInfoItemServiceRegistration.unregister();
			}

			if (infoDisplayContributorServiceRegistration != null) {
				infoDisplayContributorServiceRegistration.unregister();
			}

			classNameLocalService.deleteClassName(className);
		}
	}

}