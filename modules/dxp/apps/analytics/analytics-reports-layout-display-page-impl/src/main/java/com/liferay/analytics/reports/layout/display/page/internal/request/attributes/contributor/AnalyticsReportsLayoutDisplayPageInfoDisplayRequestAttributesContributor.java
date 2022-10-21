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
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.info.display.request.attributes.contributor.InfoDisplayRequestAttributesContributor;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Optional;

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

		ClassName className = _classNameLocalService.fetchClassName(
			layoutDisplayPageObjectProvider.getClassNameId());

		httpServletRequest.setAttribute(
			AnalyticsReportsWebKeys.ANALYTICS_INFO_ITEM_REFERENCE,
			Optional.ofNullable(
				_analyticsReportsInfoItemTracker.getAnalyticsReportsInfoItem(
					className.getClassName())
			).map(
				analyticsReportsInfoItem -> new InfoItemReference(
					className.getClassName(),
					layoutDisplayPageObjectProvider.getClassPK())
			).orElseGet(
				() -> new InfoItemReference(
					LayoutDisplayPageObjectProvider.class.getName(),
					new ClassNameClassPKInfoItemIdentifier(
						className.getClassName(),
						layoutDisplayPageObjectProvider.getClassPK()))
			));
	}

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}