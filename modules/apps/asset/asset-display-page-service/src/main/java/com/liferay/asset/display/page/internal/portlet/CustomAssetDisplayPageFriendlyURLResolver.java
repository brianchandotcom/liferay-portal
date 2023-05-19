/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.display.page.internal.portlet;

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(enabled = false, service = FriendlyURLResolver.class)
public class CustomAssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getURLSeparator() {
		return "/display-page/";
	}

	@Override
	protected LayoutDisplayPageObjectProvider<?>
		getLayoutDisplayPageObjectProvider(
			LayoutDisplayPageProvider<?> layoutDisplayPageProvider,
			long groupId, String friendlyURL, Map<String, String[]> params) {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(
				GetterUtil.getLong(_getParam("classPK", params)));

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			new InfoItemReference(
				_getParam("className", params), classPKInfoItemIdentifier));
	}

	@Override
	protected Layout getLayoutDisplayPageObjectProviderLayout(
		long groupId,
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
		LayoutDisplayPageProvider<?> layoutDisplayPageProvider,
		Map<String, String[]> params) {

		return layoutLocalService.fetchLayout(
			GetterUtil.getLong(_getParam("selPlid", params)));
	}

	@Override
	protected LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
		String friendlyURL, Map<String, String[]> params) {

		return layoutDisplayPageProviderRegistry.
			getLayoutDisplayPageProviderByClassName(
				_getParam("className", params));
	}

	private String _getParam(String name, Map<String, String[]> params) {
		String[] param = params.get(name);

		if (ArrayUtil.isEmpty(param)) {
			return StringPool.BLANK;
		}

		return param[0];
	}

}