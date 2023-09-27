/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.layout.display.page.internal.info.item.provider;

import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;

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
				_layoutDisplayPageProviderRegistry.
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
	private LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

}