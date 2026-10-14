/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.constants;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageConstants {

	/**
	 * Names the media condition that applies when no other one does. A preset
	 * that declares only this condition renders an <code>&lt;img&gt;</code>
	 * rather than a <code>&lt;picture&gt;</code>.
	 */
	public static final String MEDIA_CONDITION_NAME_DEFAULT = "default";

	public static final String PRESET_CARD = "card";

	public static final String PRESET_CONTENT = "content";

	public static final String PRESET_DEFAULT = "default";

	public static final String PRESET_FULL_WIDTH = "fullWidth";

	public static final String PRESET_HERO = "hero";

	public static final String PRESET_THUMBNAIL = "thumbnail";

	public static final String PROPERTY_LAZY_LOADING = "lazyLoading";

	public static final String PROPERTY_MAX_WIDTH = "maxWidth";

	public static final String PROPERTY_QUERY = "query";

	public static final String PROPERTY_SIZES = "sizes";

	public static final String PROPERTY_TRANSFORMATIONS = "transformations";

	/**
	 * Lists the presets every installation has, as the same flat
	 * <code>key=value</code> entries that the configuration is written in.
	 *
	 * <p>
	 * A configured preset of the same name overrides the entry of the same key
	 * and leaves every other key of that preset alone. A system preset can
	 * therefore be adjusted but never deleted.
	 * </p>
	 */
	public static final String[] SYSTEM_PRESETS = {
		StringBundler.concat(
			PRESET_CARD, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_MAX_WIDTH, "=960"),
		StringBundler.concat(
			PRESET_CARD, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_SIZES,
			"=(min-width: 992px) 25vw, 100vw"),
		StringBundler.concat(
			PRESET_CARD, StringPool.PERIOD, PROPERTY_LAZY_LOADING, "=true"),
		StringBundler.concat(
			PRESET_CONTENT, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_MAX_WIDTH, "=1440"),
		StringBundler.concat(
			PRESET_CONTENT, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_SIZES, "=100vw"),
		StringBundler.concat(
			PRESET_CONTENT, StringPool.PERIOD, PROPERTY_LAZY_LOADING, "=true"),
		StringBundler.concat(
			PRESET_DEFAULT, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_SIZES, "=100vw"),
		StringBundler.concat(
			PRESET_FULL_WIDTH, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_MAX_WIDTH, "=2880"),
		StringBundler.concat(
			PRESET_FULL_WIDTH, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_SIZES, "=100vw"),
		StringBundler.concat(
			PRESET_HERO, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_MAX_WIDTH, "=2160"),
		StringBundler.concat(
			PRESET_HERO, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_SIZES, "=100vw"),
		StringBundler.concat(
			PRESET_THUMBNAIL, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_MAX_WIDTH, "=320"),
		StringBundler.concat(
			PRESET_THUMBNAIL, StringPool.PERIOD, MEDIA_CONDITION_NAME_DEFAULT,
			StringPool.PERIOD, PROPERTY_SIZES, "=96px"),
		StringBundler.concat(
			PRESET_THUMBNAIL, StringPool.PERIOD, PROPERTY_LAZY_LOADING, "=true")
	};

}