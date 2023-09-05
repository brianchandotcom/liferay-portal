/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.portlet;

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = FriendlyURLResolver.class)
public class CustomAssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getURLSeparator() {
		return "/e/";
	}

	@Override
	protected LayoutDisplayPageObjectProvider<?>
		getLayoutDisplayPageObjectProvider(
			LayoutDisplayPageProvider<?> layoutDisplayPageProvider,
			long groupId, String friendlyURL, Map<String, String[]> params) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-183727")) {
			return null;
		}

		String[] parts = _getPathParts(friendlyURL);

		if (parts.length < 4) {
			return null;
		}

		InfoItemIdentifier infoItemIdentifier = null;

		if (Validator.isNumber(parts[3])) {
			infoItemIdentifier = new ClassPKInfoItemIdentifier(
				GetterUtil.getLong(parts[3]));
		}
		else {
			infoItemIdentifier = new ERCInfoItemIdentifier(parts[3]);
		}

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			new InfoItemReference(
				_portal.getClassName(GetterUtil.getLong(parts[2])),
				infoItemIdentifier));
	}

	@Override
	protected Layout getLayoutDisplayPageObjectProviderLayout(
		long groupId, String friendlyURL,
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
		LayoutDisplayPageProvider<?> layoutDisplayPageProvider) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-183727")) {
			return null;
		}

		String[] parts = _getPathParts(friendlyURL);

		if (parts.length < 4) {
			return null;
		}

		return layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, false, StringPool.SLASH + parts[1]);
	}

	@Override
	protected LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
		String friendlyURL) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-183727")) {
			return null;
		}

		String[] parts = _getPathParts(friendlyURL);

		if (parts.length < 4) {
			return null;
		}

		return layoutDisplayPageProviderRegistry.
			getLayoutDisplayPageProviderByClassName(
				_portal.getClassName(GetterUtil.getLong(parts[2])));
	}

	@Override
	protected boolean useOriginalFriendlyURL() {
		return false;
	}

	private String[] _getPathParts(String path) {
		String urlSeparator = getURLSeparator();

		String urlInfo = path.substring(
			path.indexOf(urlSeparator) + urlSeparator.length() - 1);

		return StringUtil.split(urlInfo, StringPool.SLASH);
	}

	@Reference
	private Portal _portal;

}