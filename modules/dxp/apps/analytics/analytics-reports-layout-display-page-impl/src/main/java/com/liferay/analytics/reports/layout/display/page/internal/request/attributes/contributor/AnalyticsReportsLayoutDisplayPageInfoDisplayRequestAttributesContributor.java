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

package com.liferay.analytics.reports.layout.display.page.internal.request.attributes.contributor;

import com.liferay.analytics.reports.constants.AnalyticsReportsWebKeys;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemRegistry;
import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.info.display.request.attributes.contributor.InfoDisplayRequestAttributesContributor;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = InfoDisplayRequestAttributesContributor.class)
public class
	AnalyticsReportsLayoutDisplayPageInfoDisplayRequestAttributesContributor
		implements InfoDisplayRequestAttributesContributor {

	@Override
	public void addAttributes(HttpServletRequest httpServletRequest) {
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider == null) {
			return;
		}

		InfoItemReference infoItemReference = new InfoItemReference(
			layoutDisplayPageObjectProvider.getClassName(),
			layoutDisplayPageObjectProvider.getClassPK());

		AnalyticsReportsInfoItem<?> analyticsReportsInfoItem =
			_analyticsReportsInfoItemRegistry.getAnalyticsReportsInfoItem(
				layoutDisplayPageObjectProvider.getClassName());

		if (analyticsReportsInfoItem == null) {
			infoItemReference = new InfoItemReference(
				LayoutDisplayPageObjectProvider.class.getName(),
				new ClassNameClassPKInfoItemIdentifier(
					layoutDisplayPageObjectProvider.getClassName(),
					layoutDisplayPageObjectProvider.getClassPK()));
		}

		httpServletRequest.setAttribute(
			AnalyticsReportsWebKeys.ANALYTICS_INFO_ITEM_REFERENCE,
			infoItemReference);
	}

	@Reference
	private AnalyticsReportsInfoItemRegistry _analyticsReportsInfoItemRegistry;

}