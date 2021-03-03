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
import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.analytics.reports.test.MockObject;
import com.liferay.analytics.reports.test.analytics.reports.info.item.MockAnalyticsReportsInfoItem;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.util.HashMapDictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Cristina González
 */
public class MockContextUtil {

	public static void testWithMockContext(
			MockContext mockContext, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(MockContextUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<AnalyticsReportsInfoItemObjectProvider>
			analyticsReportsInfoItemObjectProviderServiceRegistration = null;
		ServiceRegistration<AnalyticsReportsInfoItem<MockObject>>
			analyticsReportsInfoItemServiceRegistration = null;

		try {
			analyticsReportsInfoItemObjectProviderServiceRegistration =
				bundleContext.registerService(
					AnalyticsReportsInfoItemObjectProvider.class,
					new AnalyticsReportsInfoItemObjectProvider<MockObject>() {

						@Override
						public MockObject getAnalyticsReportsInfoItemObject(
							InfoItemReference infoItemReference) {

							return new MockObject();
						}

						@Override
						public String getClassName() {
							return MockObject.class.getName();
						}

					},
					new HashMapDictionary<>());
			analyticsReportsInfoItemServiceRegistration =
				bundleContext.registerService(
					(Class<AnalyticsReportsInfoItem<MockObject>>)
						(Class<?>)AnalyticsReportsInfoItem.class,
					mockContext.getAnalyticsReportsInfoItem(),
					new HashMapDictionary<>());
			unsafeRunnable.run();
		}
		finally {
			if (analyticsReportsInfoItemObjectProviderServiceRegistration !=
					null) {

				analyticsReportsInfoItemObjectProviderServiceRegistration.
					unregister();
			}

			if (analyticsReportsInfoItemServiceRegistration != null) {
				analyticsReportsInfoItemServiceRegistration.unregister();
			}
		}
	}

	public static class MockContext {

		public static Builder builder() {
			return new Builder();
		}

		public AnalyticsReportsInfoItem<MockObject>
			getAnalyticsReportsInfoItem() {

			return _analyticsReportsInfoItem;
		}

		public static class Builder {

			public Builder() {
			}

			public Builder analyticsReportsInfoItem(
				AnalyticsReportsInfoItem<MockObject> analyticsReportsInfoItem) {

				_analyticsReportsInfoItem = analyticsReportsInfoItem;

				return this;
			}

			public MockContext build() {
				return new MockContext(_analyticsReportsInfoItem);
			}

			private AnalyticsReportsInfoItem<MockObject>
				_analyticsReportsInfoItem;

		}

		private MockContext(
			AnalyticsReportsInfoItem<MockObject> analyticsReportsInfoItem) {

			if (analyticsReportsInfoItem == null) {
				_analyticsReportsInfoItem =
					MockAnalyticsReportsInfoItem.builder(
					).build();
			}
			else {
				_analyticsReportsInfoItem = analyticsReportsInfoItem;
			}
		}

		private final AnalyticsReportsInfoItem<MockObject>
			_analyticsReportsInfoItem;

	}

}