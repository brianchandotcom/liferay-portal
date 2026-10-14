/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Daniel Sanz
 */
@ExtendedObjectClassDefinition(
	category = "adaptive-media",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.responsive.image.configuration.ResponsiveImageConfiguration",
	localization = "content/Language",
	name = "responsive-image-configuration-name"
)
public interface ResponsiveImageConfiguration {

	/**
	 * Every extra width is another distinct CDN cache object, while wider gaps
	 * between widths ship more pixels than needed. The default is a ladder of
	 * widths about 1.4 times apart, which caps the overshoot near twice the
	 * pixel count.
	 */
	@Meta.AD(
		deflt = "320|480|640|960|1280|1920|2560",
		description = "candidate-widths-help", name = "candidate-widths",
		required = false
	)
	public String[] candidateWidths();

	@Meta.AD(
		deflt = "", description = "cdn-host-help", name = "cdn-host",
		required = false
	)
	public String cdnHost();

	@Meta.AD(
		deflt = "disable=upscale", description = "default-transformations-help",
		name = "default-transformations", required = false
	)
	public String[] defaultTransformations();

	@Meta.AD(
		deflt = "false", description = "responsive-image-enabled-help",
		name = "enabled", required = false
	)
	public boolean enabled();

	/**
	 * Lists the media conditions that presets can use, typically a theme's
	 * breakpoints, as <code>&lt;name&gt;.query=&lt;media query&gt;</code>
	 * entries.
	 *
	 * <pre>
	 * narrow.query=(max-width: 767px)
	 * wide.query=(min-width: 768px)
	 * </pre>
	 */
	@Meta.AD(
		deflt = "", description = "media-conditions-help",
		name = "media-conditions", required = false
	)
	public String[] mediaConditions();

	/**
	 * Lists the presets as flat <code>key=value</code> entries.
	 *
	 * <pre>
	 * hero.narrow.sizes=100vw
	 * hero.narrow.transformations=crop=1:1
	 * hero.wide.sizes=100vw
	 * hero.wide.transformations=crop=16:9
	 * card.default.sizes=(min-width: 992px) 25vw, 100vw
	 * card.lazyLoading=true
	 * thumb.default.sizes=96px
	 * thumb.default.maxWidth=320
	 * </pre>
	 *
	 * <p>
	 * <code>&lt;preset&gt;.lazyLoading</code> says whether images in the preset
	 * are lazily loaded by default. <code>&lt;preset&gt;.&lt;media condition
	 * name&gt;.sizes</code>, <code>.transformations</code>, and
	 * <code>.maxWidth</code> describe what to generate under one media
	 * condition, which must be declared in {@link #mediaConditions()}.
	 * </p>
	 *
	 * <p>
	 * The <code>sizes</code> value is the <a
	 * href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLImageElement/sizes">
	 * sizes attribute</a> the image is rendered with, and it turns the width
	 * descriptors of the candidates into a choice. Browsers honor
	 * <code>auto</code> only on a lazily loaded image that already has an
	 * aspect ratio to measure. A preset that declares it must also declare
	 * <code>lazyLoading</code>, and the image must carry a width and a height
	 * or be sized by the page's style.
	 * </p>
	 *
	 * <p>
	 * A preset is named by the <code>responsive-image-preset-</code> language
	 * key that matches its name, which an administrator overrides like any
	 * other shipped string.
	 * </p>
	 */
	@Meta.AD(
		deflt = "", description = "presets-help", name = "presets",
		required = false
	)
	public String[] presets();

	@ExtendedAttributeDefinition(
		visibilityControllerKey = "responsive-image-url-transformer-name"
	)
	@Meta.AD(
		deflt = "fastlyImageOptimizer",
		description = "responsive-image-url-transformer-name-help",
		name = "responsive-image-url-transformer-name", required = false
	)
	public String responsiveImageURLTransformerName();

}