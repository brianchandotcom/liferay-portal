/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper.util;

import com.liferay.headless.delivery.dto.v1_0.CustomCSSViewport;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public class StyledLayoutStructureItemUtil {

	public static String[] getCssClasses(
		StyledLayoutStructureItem styledLayoutStructureItem) {

		Set<String> cssClasses = styledLayoutStructureItem.getCssClasses();

		if (SetUtil.isEmpty(cssClasses)) {
			return null;
		}

		return ArrayUtil.toStringArray(cssClasses);
	}

	public static String getCustomCSS(
		StyledLayoutStructureItem styledLayoutStructureItem) {

		String customCSS = styledLayoutStructureItem.getCustomCSS();

		if (Validator.isNotNull(customCSS)) {
			return customCSS;
		}

		return null;
	}

	public static CustomCSSViewport[] getCustomCSSViewports(
		StyledLayoutStructureItem styledLayoutStructureItem) {

		Map<String, String> customCSSViewportsMap =
			styledLayoutStructureItem.getCustomCSSViewports();

		if ((customCSSViewportsMap == null) ||
			customCSSViewportsMap.isEmpty()) {

			return null;
		}

		List<CustomCSSViewport> customCSSViewports = new ArrayList<>();

		String mobileLandscapeCustomCSS = customCSSViewportsMap.get(
			ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId());

		if (Validator.isNotNull(mobileLandscapeCustomCSS)) {
			customCSSViewports.add(
				new CustomCSSViewport() {
					{
						customCSS = mobileLandscapeCustomCSS;
						id = ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId();
					}
				});
		}

		String portraitMobileCustomCSS = customCSSViewportsMap.get(
			ViewportSize.PORTRAIT_MOBILE.getViewportSizeId());

		if (Validator.isNotNull(portraitMobileCustomCSS)) {
			customCSSViewports.add(
				new CustomCSSViewport() {
					{
						customCSS = portraitMobileCustomCSS;
						id = ViewportSize.PORTRAIT_MOBILE.getViewportSizeId();
					}
				});
		}

		String tabletCustomCSS = customCSSViewportsMap.get(
			ViewportSize.TABLET.getViewportSizeId());

		if (Validator.isNotNull(tabletCustomCSS)) {
			customCSSViewports.add(
				new CustomCSSViewport() {
					{
						customCSS = tabletCustomCSS;
						id = ViewportSize.TABLET.getViewportSizeId();
					}
				});
		}

		if (ListUtil.isEmpty(customCSSViewports)) {
			return null;
		}

		return customCSSViewports.toArray(new CustomCSSViewport[0]);
	}

}