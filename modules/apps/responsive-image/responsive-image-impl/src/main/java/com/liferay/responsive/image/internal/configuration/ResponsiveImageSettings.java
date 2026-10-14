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
 * resolved to, which is why this class is separate from the configuration that
 * an administrator wrote. Resolving happens once per change rather than once
 * per image, and {@link ResponsiveImageSettingsProvider} decides when.
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

	public int getContentHash() {
		return _contentHash;
	}

	public Map<String, String> getDefaultTransformations() {
		return _defaultTransformations;
	}

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
	 * Enabling is explicit because a site inherits the instance configuration.
	 * Otherwise, every site would run the feature as soon as the instance holds
	 * any configuration.
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

		public String getName() {
			return _name;
		}

		public List<Variant> getVariants() {
			return _variants;
		}

		/**
		 * Returns whether an image in this placement is lazily loaded. An
		 * undeclared value means eager loading.
		 *
		 * <p>
		 * Eager loading costs bandwidth, while lazily loading the largest
		 * contentful image costs a Core Web Vital. Eager loading is the safer
		 * default because it cannot regress that vital.
		 * </p>
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
		 * candidate. A thumbnail rendered 96 pixels wide that advertises a
		 * candidate 2560 pixels wide ships seven URLs of markup for images that
		 * the browser never chooses.
		 * </p>
		 *
		 * <p>
		 * This is a soft limit. The smallest candidate at or above this width
		 * is still included because the browser needs it, and excluding it
		 * would leave nothing usable. Set it to the rendered width multiplied
		 * by the highest pixel density worth serving.
		 * </p>
		 */
		public Integer getMaxWidth() {
			return _maxWidth;
		}

		public String getQuery() {
			return _query;
		}

		public String getSizes() {
			return _sizes;
		}

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