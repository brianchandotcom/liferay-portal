/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.constants.SearchResultsPortletKeys;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Gustavo Lima
 */
public class AssetURLUtil {

	public static String getAssetURLView(
		AssetRenderer<?> assetRenderer,
		AssetRendererFactory<?> assetRendererFactory, String entryClassName,
		long entryClassPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		try {
			PortletURL viewContentURL =
				PortletURLBuilder.createLiferayPortletURL(
					liferayPortletResponse,
					SearchResultsPortletKeys.SEARCH_RESULTS,
					PortletRequest.RENDER_PHASE
				).setRedirect(
					PortalUtil.getCurrentURL(liferayPortletRequest)
				).setPortletMode(
					PortletMode.VIEW
				).setWindowState(
					WindowState.MAXIMIZED
				).buildPortletURL();

			MutableRenderParameters mutableRenderParameters =
				viewContentURL.getRenderParameters();

			mutableRenderParameters.setValue("mvcPath", "/view_content.jsp");

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				entryClassName, entryClassPK);

			mutableRenderParameters.setValue(
				"assetEntryId", String.valueOf(assetEntry.getEntryId()));

			mutableRenderParameters.setValue(
				"type", assetRendererFactory.getType());

			String viewURL = null;

			if (assetRenderer != null) {
				viewURL = assetRenderer.getURLViewInContext(
					liferayPortletRequest, liferayPortletResponse,
					viewContentURL.toString());
			}

			if (Validator.isNull(viewURL)) {
				viewURL = viewContentURL.toString();
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return HttpComponentsUtil.setParameter(
				viewURL, "p_l_back_url", themeDisplay.getURLCurrent());
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to get view URL for class ", entryClassName,
					" with primary key ", entryClassPK),
				exception);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(AssetURLUtil.class);

}