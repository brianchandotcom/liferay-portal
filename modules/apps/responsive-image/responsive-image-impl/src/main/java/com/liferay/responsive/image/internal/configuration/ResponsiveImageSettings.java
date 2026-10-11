/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal.configuration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.responsive.image.constants.ResponsiveImageConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Holds the responsive image settings that apply to one company or one site,
 * already parsed.
 *
 * <p>
 * These are the values that reading {@link ResponsiveImageConfiguration}
 * resolved to, so this is deliberately separate from the configuration, which
 * is what an administrator wrote. Resolving happens once per change rather
 * than once per image, and {@link ResponsiveImageSettingsProvider}
 * decides when.
 * </p>
 *
 * @author Daniel Sanz
 */
public class ResponsiveImageSettings {

	public ResponsiveImageSettings(
		TreeSet<Integer> candidateWidths, String cdnHost, int contentHash,
		Map<String, String> defaultTransformations, boolean enabled,
		Map<String, Preset> presetsMap,
		String responsiveImageURLTransformerName) {

		_candidateWidths = candidateWidths;
		_cdnHost = cdnHost;
		_contentHash = contentHash;
		_defaultTransformations = defaultTransformations;
		_enabled = enabled;
		_presetsMap = presetsMap;
		_responsiveImageURLTransformerName = responsiveImageURLTransformerName;
	}

	public String getCDNHost() {
		return _cdnHost;
	}

	public TreeSet<Integer> getCandidateWidths() {
		return _candidateWidths;
	}

	/**
	 * Returns a hash of the configuration this was parsed from, so the registry
	 * can tell whether reparsing is needed.
	 *
	 * @return the content hash
	 */
	public int getContentHash() {
		return _contentHash;
	}

	public Map<String, String> getDefaultTransformations() {
		return _defaultTransformations;
	}

	/**
	 * Returns the preset of the given name, falling back to the default preset
	 * when the name is blank or names nothing.
	 *
	 * @param  presetName the preset name, or <code>null</code> for the default
	 * @return the preset definition
	 */
	public Preset getPreset(String presetName) {
		if (Validator.isBlank(presetName)) {
			presetName = ResponsiveImageConstants.PRESET_DEFAULT;
		}

		Preset preset = _presetsMap.get(presetName);

		if (preset != null) {
			return preset;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("No preset named " + presetName + ", using the default");
		}

		return _presetsMap.get(ResponsiveImageConstants.PRESET_DEFAULT);
	}

	public String getResponsiveImageURLTransformerName() {
		return _responsiveImageURLTransformerName;
	}

	/**
	 * Returns whether the responsive image feature is enabled in this scope.
	 *
	 * <p>
	 * Enabling is explicit because a site inherits the instance configuration,
	 * so every site would otherwise operate the feature the moment the
	 * instance holds a configuration at all.
	 * </p>
	 *
	 * @return whether the responsive image feature is enabled
	 */
	public boolean isEnabled() {
		return _enabled;
	}

	public static final class Preset {

		public Preset(
			Boolean lazyLoading, String name, List<Variant> variants) {

			_lazyLoading = lazyLoading;
			_name = name;
			_variants = Collections.unmodifiableList(variants);
		}

		/**
		 * Returns the name callers use to request this preset.
		 *
		 * @return the name
		 */
		public String getName() {
			return _name;
		}

		/**
		 * Returns the variants this preset defines under each media condition,
		 * in the order they must be rendered.
		 *
		 * @return this preset's variants
		 */
		public List<Variant> getVariants() {
			return _variants;
		}

		/**
		 * Returns whether an image in this placement is lazily loaded.
		 *
		 * <p>
		 * Undeclared means eager. Loading eagerly costs bandwidth; loading the
		 * largest contentful image lazily costs a Core Web Vital, so the safer
		 * default is the one that cannot regress it.
		 * </p>
		 *
		 * @return whether to lazily load
		 */
		public boolean isLazyLoading() {
			return GetterUtil.getBoolean(_lazyLoading);
		}

		private final Boolean _lazyLoading;
		private final String _name;
		private final List<Variant> _variants;

	}

	public static final class Variant {

		public Variant(
			Integer maxWidth, String query, String sizes,
			Map<String, String> transformations) {

			_maxWidth = maxWidth;
			_query = query;
			_sizes = sizes;
			_transformations = Collections.unmodifiableMap(transformations);
		}

		/**
		 * Returns the widest rendition worth generating for this variant, or
		 * <code>null</code> for no limit.
		 *
		 * <p>
		 * A variant that renders small has no use for the widest configured
		 * candidate: a 96 pixel thumbnail advertising a 2560 pixel candidate
		 * ships 7 URLs of markup for an image the browser will never choose.
		 * </p>
		 *
		 * <p>
		 * This is a <b>soft</b> limit: the smallest candidate at or above this
		 * width is still included, because that is the one the browser needs,
		 * and excluding it would leave nothing usable. Set it to the rendered
		 * width multiplied by the highest pixel density worth serving.
		 * </p>
		 *
		 * @return the maximum width in pixels, or <code>null</code>
		 */
		public Integer getMaxWidth() {
			return _maxWidth;
		}

		/**
		 * Returns the query this applies under, resolved from its media
		 * condition declaration, or <code>null</code> when it applies
		 * unconditionally.
		 */
		public String getQuery() {
			return _query;
		}

		/**
		 * Returns the <code>sizes</code> attribute describing how wide the
		 * image renders under this variant, or <code>null</code>.
		 */
		public String getSizes() {
			return _sizes;
		}

		/**
		 * Returns the transformations to apply to every candidate generated for
		 * this variant, such as a crop that differs between viewports.
		 *
		 * @return the transformations
		 */
		public Map<String, String> getTransformations() {
			return _transformations;
		}

		private final Integer _maxWidth;
		private final String _query;
		private final String _sizes;
		private final Map<String, String> _transformations;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResponsiveImageSettings.class);

	private final TreeSet<Integer> _candidateWidths;
	private final String _cdnHost;
	private final int _contentHash;
	private final Map<String, String> _defaultTransformations;
	private final boolean _enabled;
	private final Map<String, Preset> _presetsMap;
	private final String _responsiveImageURLTransformerName;

}