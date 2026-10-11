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
	 * Lists the full set of widths available to generate image candidates, in
	 * pixels.
	 *
	 * <p>
	 * Two costs pull in opposite directions: every extra width is another
	 * distinct CDN cache object, while wider gaps mean shipping more pixels
	 * than needed. The default is a roughly 1.4x ladder, which caps overshoot
	 * near 2x the pixel count.
	 * </p>
	 */
	@Meta.AD(
		deflt = "320|480|640|960|1280|1920|2560",
		description = "candidate-widths-help", name = "candidate-widths",
		required = false
	)
	public String[] candidateWidths();

	/**
	 * Names the host the transformed image URLs are served from, protocol
	 * included, such as <code>https://cdn.example.com</code>. Leave it blank to
	 * use the CDN host configured for the instance.
	 */
	@Meta.AD(
		deflt = "", description = "cdn-host-help", name = "cdn-host",
		required = false
	)
	public String cdnHost();

	/**
	 * Lists the transformations applied to every generated rendition, as
	 * <code>name=value</code> entries.
	 */
	@Meta.AD(
		deflt = "disable=upscale", description = "default-transformations-help",
		name = "default-transformations", required = false
	)
	public String[] defaultTransformations();

	/**
	 * Returns whether the feature is enabled in this scope.
	 */
	@Meta.AD(
		deflt = "false", description = "responsive-image-enabled-help",
		name = "enabled", required = false
	)
	public boolean enabled();

	/**
	 * Lists the media conditions available to presets, typically a theme's
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
	 * Lists the presets, as flat <code>key=value</code> entries in one of two
	 * forms:
	 *
	 * <p>
	 * <code>&lt;preset&gt;.lazyLoading</code> says whether images in this
	 * preset are lazily loaded by default.
	 * <code>&lt;preset&gt;.&lt;media condition name&gt;.sizes</code>,
	 * <code>.transformations</code> and <code>.maxWidth</code> describe what to
	 * generate under one media condition, which must be one declared in
	 * {@link #mediaConditions()}. The <code>sizes</code> value is the
	 * <a
	 * href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLImageElement/sizes">
	 * sizes attribute</a> the image is rendered with, which is what turns the
	 * candidates' width descriptors into a choice. Browsers honor
	 * <code>auto</code> there only on a lazily loaded image that already has
	 * an aspect ratio to measure, so a preset declaring it has to declare
	 * <code>lazyLoading</code> too, and the image has to carry a width and
	 * a height or be sized by the page's style. A preset is named by the
	 * <code>responsive-image-preset-</code> language key matching its name,
	 * which an administrator overrides the way every other shipped string is
	 * overridden.
	 * </p>
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
	 */
	@Meta.AD(
		deflt = "", description = "presets-help", name = "presets",
		required = false
	)
	public String[] presets();

	/**
	 * Names the image optimization service whose URL vocabulary is used. It
	 * must match the name reported by a deployed
	 * {@code ResponsiveImageURLTransformer}.
	 *
	 * <p>
	 * It defaults to the transformer the product ships, and is not asked for
	 * until a second one makes the name a choice.
	 * </p>
	 */
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