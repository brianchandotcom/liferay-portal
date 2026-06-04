/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.interpreter;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.renderer.SharingEntryViewRenderer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class CMSAssetRendererSharingEntryInterpreter
	implements SharingEntryInterpreter {

	public CMSAssetRendererSharingEntryInterpreter(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	public String getAssetTypeTitle(SharingEntry sharingEntry, Locale locale)
		throws PortalException {

		AssetRenderer<?> assetRenderer = _getAssetRenderer(sharingEntry);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getTypeName(locale);
	}

	@Override
	public SharingEntryEditRenderer getSharingEntryEditRenderer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SharingEntryViewRenderer getSharingEntryViewRenderer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTitle(SharingEntry sharingEntry, Locale locale) {
		AssetEntry assetEntry = _getAssetEntry(sharingEntry);

		if (assetEntry == null) {
			return StringPool.BLANK;
		}

		return assetEntry.getTitle(locale);
	}

	@Override
	public Map<Locale, String> getTitleMap(SharingEntry sharingEntry) {
		AssetEntry assetEntry = _getAssetEntry(sharingEntry);

		if (assetEntry == null) {
			return new HashMap<>();
		}

		return assetEntry.getTitleMap();
	}

	@Override
	public boolean isVisible(SharingEntry sharingEntry) throws PortalException {
		AssetRenderer<?> assetRenderer = _getAssetRenderer(sharingEntry);

		if ((assetRenderer == null) || !assetRenderer.isDisplayable()) {
			return false;
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			sharingEntry.getClassNameId(), sharingEntry.getClassPK());

		if ((assetEntry == null) || !assetEntry.isVisible()) {
			return false;
		}

		return true;
	}

	private AssetEntry _getAssetEntry(SharingEntry sharingEntry) {
		try {
			AssetRenderer<?> assetRenderer = _getAssetRenderer(sharingEntry);

			if (assetRenderer == null) {
				return null;
			}

			AssetRendererFactory<?> assetRendererFactory =
				assetRenderer.getAssetRendererFactory();

			return assetRendererFactory.getAssetEntry(
				assetRendererFactory.getClassName(),
				assetRenderer.getClassPK());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	private AssetRenderer<?> _getAssetRenderer(SharingEntry sharingEntry)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				sharingEntry.getClassName());

		if (assetRendererFactory == null) {
			return null;
		}

		return assetRendererFactory.getAssetRenderer(sharingEntry.getClassPK());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMSAssetRendererSharingEntryInterpreter.class);

	private final AssetEntryLocalService _assetEntryLocalService;

}