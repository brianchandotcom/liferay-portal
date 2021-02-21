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

package com.liferay.analytics.reports.web.internal.request.attributes.contributor;

import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsWebKeys;
import com.liferay.analytics.reports.web.internal.info.display.contributor.util.LayoutDisplayPageProviderUtil;
import com.liferay.info.display.request.attributes.contributor.InfoDisplayRequestAttributesContributor;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = InfoDisplayRequestAttributesContributor.class)
public class AnalyticsReportsInfoDisplayRequestAttributesContributor
	implements InfoDisplayRequestAttributesContributor {

	@Override
	public void addAttributes(HttpServletRequest httpServletRequest) {
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			LayoutDisplayPageProviderUtil.initLayoutDisplayPageObjectProvider(
				httpServletRequest, _layoutDisplayPageProviderTracker, _portal);

		ClassName className = _classNameLocalService.fetchClassName(
			layoutDisplayPageObjectProvider.getClassNameId());

		httpServletRequest.setAttribute(
			AnalyticsReportsWebKeys.INFO_ITEM_REFERENCE,
			new InfoItemReference(
				className.getClassName(),
				layoutDisplayPageObjectProvider.getClassPK()));
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

}