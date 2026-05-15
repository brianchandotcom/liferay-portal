/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.analytics.asset.AssetAnalyticsAttributesProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Objects;

/**
 * Thin template-facing wrapper around {@link
 * AssetAnalyticsAttributesProvider} so custom Asset Publisher display
 * templates (FTL / VM) can render analytics attributes with a single call,
 * without having to construct the asset renderer themselves.
 *
 * @author Georgel Pop
 */
public class AssetAnalyticsAttributesHelper {

	public String buildAttributes(
		AssetEntry assetEntry, String action, String field, Locale locale) {

		if ((assetEntry == null) || !_isViewMode()) {
			return StringPool.BLANK;
		}

		AssetAnalyticsAttributesProvider assetAnalyticsAttributesProvider =
			new AssetAnalyticsAttributesProvider(
				assetEntry, _getAssetRenderer(assetEntry), locale);

		return assetAnalyticsAttributesProvider.buildAttributes(action, field);
	}

	private AssetRenderer<?> _getAssetRenderer(AssetEntry assetEntry) {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		if (assetRendererFactory == null) {
			return null;
		}

		try {
			return assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private boolean _isViewMode() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return false;
		}

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		if (httpServletRequest == null) {
			return false;
		}

		String viewMode = ParamUtil.getString(
			PortalUtil.getOriginalServletRequest(httpServletRequest),
			"p_l_mode", Constants.VIEW);

		return Objects.equals(viewMode, Constants.VIEW);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAnalyticsAttributesHelper.class);

}