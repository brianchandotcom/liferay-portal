/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.constants;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * Holds the constants the responsive image framework exposes to callers.
 *
 * <p>
 * The <code>PRESET_</code> constants name the presets every installation has,
 * whatever is configured. <code>MEDIA_CONDITION_NAME_DEFAULT</code> names the
 * one media condition that needs no declaring, because it is the absence of
 * one.
 * The <code>PROPERTY_</code> constants name the keys a preset is described
 * with.
 * </p>
 *
 * @author Daniel Sanz
 */
public class ResponsiveImageConstants {

	/**
	 * Names the media condition that applies when no other one does, so a
	 * preset declaring only this renders an <code>&lt;img&gt;</code> rather
	 * than a <code>&lt;picture&gt;</code>.
	 */
	public static final String MEDIA_CONDITION_NAME_DEFAULT = "default";

	/**
	 * Names the preset used by asset publisher cards, navigation tiles and
	 * data set cards.
	 */
	public static final String PRESET_CARD = "card";

	/**
	 * Names the preset used by images placed inside web content.
	 */
	public static final String PRESET_CONTENT = "content";

	/**
	 * Names the preset a caller gets when it names none.
	 */
	public static final String PRESET_DEFAULT = "default";

	/**
	 * Names the preset used by full bleed banners.
	 */
	public static final String PRESET_FULL_WIDTH = "fullWidth";

	/**
	 * Names the preset used by the leading image of a content page.
	 */
	public static final String PRESET_HERO = "hero";

	/**
	 * Names the preset used by avatars and list thumbnails.
	 */
	public static final String PRESET_THUMBNAIL = "thumbnail";

	/**
	 * Names the key saying whether images in a placement are lazily loaded.
	 */
	public static final String PROPERTY_LAZY_LOADING = "lazyLoading";

	/**
	 * Names the key capping the widest rendition worth generating for a
	 * preset.
	 */
	public static final String PROPERTY_MAX_WIDTH = "maxWidth";

	/**
	 * Names the key holding the media query a media condition is declared
	 * with, such as <code>(max-width: 767px)</code>.
	 */
	public static final String PROPERTY_QUERY = "query";

	/**
	 * Names the key describing how wide an image renders, which is what turns
	 * the candidates' width descriptors into a selection.
	 */
	public static final String PROPERTY_SIZES = "sizes";

	/**
	 * Names the key listing the transformations applied to every rendition
	 * generated under one media condition.
	 */
	public static final String PROPERTY_TRANSFORMATIONS = "transformations";

	/**
	 * Lists the presets every installation has, as the same flat
	 * <code>key=value</code> entries the configuration is written in.
	 *
	 * <p>
	 * Configuring a preset of the same name overrides the entry of the same
	 * key and leaves every other key of that preset alone, so a system
	 * preset can be adjusted but never deleted.
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