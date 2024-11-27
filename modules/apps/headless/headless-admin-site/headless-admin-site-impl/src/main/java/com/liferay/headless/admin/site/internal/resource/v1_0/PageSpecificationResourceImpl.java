/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.PageSpecificationResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-specification.properties",
	scope = ServiceScope.PROTOTYPE, service = PageSpecificationResource.class
)
public class PageSpecificationResourceImpl
	extends BasePageSpecificationResourceImpl {

	@Override
	public PageSpecification
			getSiteSiteByExternalReferenceCodePageSpecification(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode,
			GroupUtil.getGroupId(
				true, true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (!_isPageSpecificationSupported(layout)) {
			throw new UnsupportedOperationException();
		}

		return _pageSpecificationDTOConverter.toDTO(layout);
	}

	private boolean _isPageSpecificationSupported(Layout layout) {
		if (_isPublished(layout)) {
			if (!layout.isApproved() || !layout.isDraftLayout()) {
				return true;
			}

			return false;
		}

		if (layout.isDraftLayout()) {
			return true;
		}

		return false;
	}

	private boolean _isPublished(Layout layout) {
		if (!layout.isTypeAssetDisplay() && !layout.isTypeContent()) {
			return true;
		}

		if (layout.isDraftLayout()) {
			return GetterUtil.getBoolean(
				layout.getTypeSettingsProperty("published"));
		}

		Layout draftLayout = layout.fetchDraftLayout();

		return GetterUtil.getBoolean(
			draftLayout.getTypeSettingsProperty("published"));
	}

	@Reference
	private LayoutService _layoutService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageSpecificationDTOConverter)"
	)
	private DTOConverter<Layout, PageSpecification>
		_pageSpecificationDTOConverter;

}