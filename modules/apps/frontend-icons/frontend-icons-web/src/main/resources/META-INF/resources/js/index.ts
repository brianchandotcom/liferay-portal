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

/**
 * These constants should be identical to the constants used at
 * `portal-kernel/src/com/liferay/portal/kernel/icons/IconsUtil.java`
 */
const BASE_PATH = '/o/icons';
const SYSTEM_ICON_PACK = 'clay';

export function getSpritemapPath(iconPack?: String) {
	const packOrSite = iconPack ? 'pack' : 'site';
	const iconPackOrSiteId = iconPack || Liferay.ThemeDisplay.getSiteGroupId();

	return `${BASE_PATH}/${packOrSite}/${iconPackOrSiteId}.svg`;
}

export function getSystemSpritemapPath() {
	return getSpritemapPath(SYSTEM_ICON_PACK);
}
