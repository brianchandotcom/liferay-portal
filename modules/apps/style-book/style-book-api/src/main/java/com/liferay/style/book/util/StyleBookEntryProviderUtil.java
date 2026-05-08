/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util;

import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryServiceUtil;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

import java.util.List;

/**
 * @author Gabriel Lima
 */
public class StyleBookEntryProviderUtil {

	public static List<StyleBookEntry> getStyleBookEntries(
			long companyId, long groupId)
		throws PortalException {

		return StyleBookEntryLocalServiceUtil.getStyleBookEntries(
			_resolveGroupIds(companyId, groupId), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public static List<StyleBookEntry> getStyleBookEntries(
			long companyId, long groupId, String themeId)
		throws PortalException {

		return StyleBookEntryLocalServiceUtil.getStyleBookEntries(
			_resolveGroupIds(companyId, groupId), themeId);
	}

	private static long[] _resolveGroupIds(long companyId, long groupId)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-57283")) {
			return new long[] {groupId};
		}

		SiteConnectedGroupGroupProvider siteConnectedGroupGroupProvider =
			_siteConnectedGroupGroupProviderSnapshot.get();

		if (siteConnectedGroupGroupProvider == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"SiteConnectedGroupGroupProvider is not available; " +
						"falling back to site-only Style Book entries");
			}

			return new long[] {groupId};
		}

		return siteConnectedGroupGroupProvider.
			getCurrentAndAncestorSiteAndDepotGroupIds(groupId);
	}

	     private static final Log _log = LogFactoryUtil.getLog(
             StyleBookEntryProviderUtil.class);

	private static final Snapshot<SiteConnectedGroupGroupProvider>
		_siteConnectedGroupGroupProviderSnapshot = new Snapshot<>(
			StyleBookEntryProviderUtil.class,
			SiteConnectedGroupGroupProvider.class);

}