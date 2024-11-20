/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.FriendlyUrlHistoryResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/friendly-url-history.properties",
	scope = ServiceScope.PROTOTYPE, service = FriendlyUrlHistoryResource.class
)
public class FriendlyUrlHistoryResourceImpl
	extends BaseFriendlyUrlHistoryResourceImpl {

	@Override
	public FriendlyUrlHistory
			getSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _friendlyURLHistoryDTOConverter.toDTO(
			_layoutLocalService.getLayoutByExternalReferenceCode(
				sitePageExternalReferenceCode,
				GroupUtil.getGroupId(
					true, contextCompany.getCompanyId(),
					siteExternalReferenceCode)));
	}

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.FriendlyURLHistoryDTOConverter)"
	)
	private DTOConverter<Layout, FriendlyUrlHistory>
		_friendlyURLHistoryDTOConverter;

	@Reference
	private LayoutLocalService _layoutLocalService;

}