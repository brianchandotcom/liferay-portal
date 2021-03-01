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

package com.liferay.analytics.reports.layout.display.page.internal.info.item.provider;

import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = AnalyticsReportsInfoItemObjectProvider.class)
public class
	LayoutDisplayPageObjectProviderAnalyticsReportsInfoItemObjectProvider
		implements AnalyticsReportsInfoItemObjectProvider
			<LayoutDisplayPageObjectProvider<?>> {

	@Override
	public LayoutDisplayPageObjectProvider<?> getAnalyticsReportsInfoItemObject(
		InfoItemReference infoItemReference) {

		if (infoItemReference.getInfoItemIdentifier() instanceof
				ClassNameClassPKInfoItemIdentifier) {

			ClassNameClassPKInfoItemIdentifier
				classNameClassPKInfoItemIdentifier =
					(ClassNameClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

			LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
				_layoutDisplayPageProviderTracker.
					getLayoutDisplayPageProviderByClassName(
						classNameClassPKInfoItemIdentifier.getClassName());

			return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					classNameClassPKInfoItemIdentifier.getClassName(),
					classNameClassPKInfoItemIdentifier.getClassPK()));
		}

		return null;
	}

	@Override
	public String getClassName() {
		return LayoutDisplayPageObjectProvider.class.getName();
	}

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

}