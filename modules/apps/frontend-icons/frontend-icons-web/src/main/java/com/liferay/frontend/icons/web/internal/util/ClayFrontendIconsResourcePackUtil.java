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

package com.liferay.frontend.icons.web.internal.util;

import com.liferay.frontend.icons.web.internal.model.FrontendIconsResource;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResourcePack;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Víctor Galán
 */
public class ClayFrontendIconsResourcePackUtil {

	public static final String CLAY_FRONTEND_ICONS_PACK_NAME = "clay";

	public static FrontendIconsResourcePack getFrontendIconResourcePack() {
		if (_frontendIconsResourcePack != null) {
			return _frontendIconsResourcePack;
		}

		List<FrontendIconsResource> frontendIconsResources =
			SVGUtil.getFrontendIconsResources(getSpritemap());

		FrontendIconsResourcePack frontendIconsResourcePack =
			new FrontendIconsResourcePack(false, CLAY_FRONTEND_ICONS_PACK_NAME);

		frontendIconsResourcePack.addFrontendIconsResources(
			frontendIconsResources);

		_frontendIconsResourcePack = frontendIconsResourcePack;

		return _frontendIconsResourcePack;
	}

	public static String getSpritemap() {
		if (_spriteMap != null) {
			return _spriteMap;
		}

		_spriteMap = StringUtil.read(
			ClayFrontendIconsResourcePackUtil.class,
			"/META-INF/resources/images/icons.svg");

		return _spriteMap;
	}

	private static FrontendIconsResourcePack _frontendIconsResourcePack;
	private static String _spriteMap;

}