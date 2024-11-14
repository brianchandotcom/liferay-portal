/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.headless.admin.site.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.resource.v1_0.FriendlyUrlHistoryResource;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;

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
	public Page<FriendlyUrlHistory>
			getSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistoryPage(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		Layout layout = _layoutLocalService.getLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode, group.getGroupId());

		return Page.of(
			transform(
				_friendlyURLEntryLocalService.getFriendlyURLEntries(
					group.getGroupId(),
					_layoutFriendlyURLEntryHelper.getClassNameId(
						layout.isPrivateLayout()),
					layout.getPlid()),
				friendlyURLEntry -> _friendlyURLHistoryDTOConverter.toDTO(
					friendlyURLEntry)));
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.FriendlyURLHistoryDTOConverter)"
	)
	private DTOConverter<FriendlyURLEntry, FriendlyUrlHistory>
		_friendlyURLHistoryDTOConverter;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

}