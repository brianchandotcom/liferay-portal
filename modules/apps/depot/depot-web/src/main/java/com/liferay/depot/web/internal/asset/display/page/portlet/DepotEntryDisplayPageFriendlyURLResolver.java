/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.asset.display.page.portlet;

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = FriendlyURLResolver.class)
public class DepotEntryDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_ASSET_LIBRARY;
	}

	@Override
	public String getKey() {
		return DepotEntry.class.getName();
	}

	@Override
	public boolean isURLSeparatorConfigurable() {
		return true;
	}

	@Override
	protected LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
			String friendlyURL)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			return super.getLayoutDisplayPageProvider(friendlyURL);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return super.getLayoutDisplayPageProvider(friendlyURL);
		}

		String[] parts = StringUtil.split(
			StringUtil.removeFirst(friendlyURL, getURLSeparator()),
			CharPool.SLASH);

		if (parts.length == 1) {
			return super.getLayoutDisplayPageProvider(friendlyURL);
		}

		DepotEntry depotEntry = _depotEntryLocalService.fetchDepotEntry(
			GetterUtil.getLong(parts[0]));

		if (depotEntry == null) {
			return super.getLayoutDisplayPageProvider(friendlyURL);
		}

		return layoutDisplayPageProviderRegistry.
			getLayoutDisplayPageProviderByClassName(DepotEntry.class.getName());
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

}