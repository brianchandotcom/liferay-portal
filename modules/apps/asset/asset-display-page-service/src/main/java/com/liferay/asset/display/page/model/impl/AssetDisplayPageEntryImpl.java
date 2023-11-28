/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.model.impl;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

/**
 * @author Rubén Pulido
 */
public class AssetDisplayPageEntryImpl extends AssetDisplayPageEntryBaseImpl {

	@Override
	public long getPlid() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntry(getLayoutPageTemplateEntryId());

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry.getPlid();
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		AssetEntry assetEntry = null;

		if (assetRendererFactory != null) {
			try {
				assetEntry = assetRendererFactory.getAssetEntry(
					getClassName(), getClassPK());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException);
				}
			}
		}
		else {
			assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				getClassName(), getClassPK());
		}

		if (assetEntry == null) {
			return LayoutConstants.DEFAULT_PLID;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			assetEntry.getLayoutUuid(), assetEntry.getGroupId(), false);

		if (layout != null) {
			return layout.getPlid();
		}

		layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			assetEntry.getLayoutUuid(), assetEntry.getGroupId(), true);

		if (layout != null) {
			return layout.getPlid();
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageEntryImpl.class);

}