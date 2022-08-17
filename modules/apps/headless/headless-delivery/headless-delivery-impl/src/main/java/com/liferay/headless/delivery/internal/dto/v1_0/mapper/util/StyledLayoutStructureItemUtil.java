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
						setCustomCSS(mobileLandscapeCustomCSS);
						setId(
							ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId());
					}
				});
		}

		String portraitMobileCustomCSS = customCSSViewportsMap.get(
			ViewportSize.PORTRAIT_MOBILE.getViewportSizeId());

		if (Validator.isNotNull(portraitMobileCustomCSS)) {
			customCSSViewports.add(
				new CustomCSSViewport() {
					{
						setCustomCSS(portraitMobileCustomCSS);
						setId(ViewportSize.PORTRAIT_MOBILE.getViewportSizeId());
					}
				});
		}

		String tabletCustomCSS = customCSSViewportsMap.get(
			ViewportSize.TABLET.getViewportSizeId());

		if (Validator.isNotNull(tabletCustomCSS)) {
			customCSSViewports.add(
				new CustomCSSViewport() {
					{
						setCustomCSS(tabletCustomCSS);
						setId(ViewportSize.TABLET.getViewportSizeId());
					}
				});
		}

		if (ListUtil.isEmpty(customCSSViewports)) {
			return null;
		}

		return customCSSViewports.toArray(new CustomCSSViewport[0]);
	}

}