/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.asset.display.page.portlet;

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = FriendlyURLResolver.class)
public class BlogsAssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_BLOGS_ENTRY;
	}

	@Override
	public String getKey() {
		return BlogsEntry.class.getName();
	}

	@Override
	public boolean isURLSeparatorConfigurable() {
		return FeatureFlagManagerUtil.isEnabled("LPD-11147");
	}

	@Override
	protected boolean isSameFriendlyURL(String url1, String url2) {
		return Objects.equals(
			_friendlyURLNormalizer.normalizeWithEncoding(url1),
			_friendlyURLNormalizer.normalizeWithEncoding(url2));
	}

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

}