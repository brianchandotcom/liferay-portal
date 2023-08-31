/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.portlet;

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
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
import com.liferay.portal.kernel.util.Tuple;

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

		Tuple tuple = _getPathParts(friendlyURL);

		String itemReference = (String)tuple.getObject(1);

		long[] split = StringUtil.split(itemReference, StringPool.SLASH, 0L);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(split[1]);

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			new InfoItemReference(
				_portal.getClassName(split[0]), classPKInfoItemIdentifier));
	}

	@Override
	protected Layout getLayoutDisplayPageObjectProviderLayout(
		long groupId, String friendlyURL,
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
		LayoutDisplayPageProvider<?> layoutDisplayPageProvider) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-183727")) {
			return null;
		}

		Tuple tuple = _getPathParts(friendlyURL);

		return layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, false, (String)tuple.getObject(0));
	}

	@Override
	protected LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
		String friendlyURL) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-183727")) {
			return null;
		}

		Tuple tuple = _getPathParts(friendlyURL);

		String itemReference = (String)tuple.getObject(1);

		String[] split = StringUtil.split(itemReference, StringPool.SLASH);

		return layoutDisplayPageProviderRegistry.
			getLayoutDisplayPageProviderByClassName(
				_portal.getClassName(GetterUtil.getLong(split[0])));
	}

	@Override
	protected boolean useOriginalFriendlyURL() {
		return false;
	}

	private Tuple _getPathParts(String path) {
		String urlSeparator = getURLSeparator();

		String urlInfo = path.substring(
			path.indexOf(urlSeparator) + urlSeparator.length() - 1);

		String friendlyURL = urlInfo.substring(
			0,
			urlInfo.lastIndexOf(
				StringPool.SLASH, urlInfo.lastIndexOf(StringPool.SLASH) - 1));

		String itemReference = urlInfo.substring(
			friendlyURL.indexOf(StringPool.SLASH) + friendlyURL.length() + 1);

		return new Tuple(friendlyURL, itemReference);
	}

	@Reference
	private Portal _portal;

}