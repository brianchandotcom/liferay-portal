/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal.configuration;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.responsive.image.configuration.ResponsiveImageConfiguration;
import com.liferay.responsive.image.constants.ResponsiveImageConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds the presets a scope has, and hands one back by name.
 *
 * @author Daniel Sanz
 */
public class ResponsiveImageSettingsProvider {

	public ResponsiveImageSettingsProvider(
		ResponsiveImageConfigurationHelper responsiveImageConfigurationHelper) {

		_responsiveImageConfigurationHelper =
			responsiveImageConfigurationHelper;
	}

	/**
	 * Returns this scope's whole parsed configuration, reparsing only when it
	 * has actually changed, or <code>null</code> if the configuration could not
	 * be read.
	 */
	public ResponsiveImageSettings getResponsiveImageSettings(
		long companyId, long groupId) {

		ResponsiveImageConfiguration responsiveImageConfiguration =
			_responsiveImageConfigurationHelper.getResponsiveImageConfiguration(
				companyId, groupId);

		if (responsiveImageConfiguration == null) {
			return null;
		}

		String[] candidateWidths =
			responsiveImageConfiguration.candidateWidths();
		String cdnHost = responsiveImageConfiguration.cdnHost();
		String[] defaultTransformations =
			responsiveImageConfiguration.defaultTransformations();
		boolean enabled = responsiveImageConfiguration.enabled();
		String[] mediaConditions =
			responsiveImageConfiguration.mediaConditions();
		String[] presets = responsiveImageConfiguration.presets();
		String responsiveImageURLTransformerName =
			responsiveImageConfiguration.responsiveImageURLTransformerName();

		int contentHash = Objects.hash(
			Arrays.hashCode(candidateWidths), cdnHost,
			Arrays.hashCode(defaultTransformations), enabled,
			Arrays.hashCode(mediaConditions), Arrays.hashCode(presets),
			responsiveImageURLTransformerName);

		String scopeKey = _responsiveImageConfigurationHelper.getScopeKey(
			companyId, groupId);

		ResponsiveImageSettings responsiveImageSettings =
			_responsiveImageSettingsMap.get(scopeKey);

		if ((responsiveImageSettings != null) &&
			(responsiveImageSettings.getContentHash() == contentHash)) {

			return responsiveImageSettings;
		}

		responsiveImageSettings = new ResponsiveImageSettings(
			_toCandidateWidthsSet(candidateWidths), cdnHost, contentHash,
			_toTransformationsMap(defaultTransformations), enabled,
			_toPresetsMap(_toMediaConditionsMap(mediaConditions), presets),
			responsiveImageURLTransformerName);

		_responsiveImageSettingsMap.put(scopeKey, responsiveImageSettings);

		return responsiveImageSettings;
	}

	/**
	 * Reads one <code>name=value</code> transformation into the given map,
	 * ignoring an entry that is blank or carries no value.
	 */
	private static void _addTransformation(
		String transformation, Map<String, String> transformationsMap) {

		if (Validator.isBlank(transformation)) {
			return;
		}

		int index = transformation.indexOf(StringPool.EQUAL);

		if (index <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Ignoring malformed transformation " + transformation);
			}

			return;
		}

		transformationsMap.put(
			StringUtil.trim(transformation.substring(0, index)),
			StringUtil.trim(transformation.substring(index + 1)));
	}

	private static Map<String, String> _toTransformationsMap(
		String transformationsString) {

		if (Validator.isBlank(transformationsString)) {
			return Collections.emptyMap();
		}

		Map<String, String> transformationsMap = new LinkedHashMap<>();

		for (String transformation :
				StringUtil.split(transformationsString, StringPool.COMMA)) {

			_addTransformation(transformation, transformationsMap);
		}

		return transformationsMap;
	}

	private static Map<String, String> _toTransformationsMap(
		String[] transformations) {

		if (transformations == null) {
			return Collections.emptyMap();
		}

		Map<String, String> transformationsMap = new LinkedHashMap<>();

		for (String transformation : transformations) {
			_addTransformation(transformation, transformationsMap);
		}

		return transformationsMap;
	}

	/**
	 * Returns what a preset declares under one media condition, or
	 * <code>null</code> when it declares nothing there.
	 *
	 * <p>
	 * The unconditional media condition is the exception, answering with
	 * nothing declared rather than with nothing at all. A preset that names
	 * conditions but nothing unconditional still needs the unconditional
	 * variant, because that is what the <code>&lt;img&gt;</code> inside the
	 * <code>&lt;picture&gt;</code> renders when no condition matches, and
	 * declaring nothing there leaves it the whole candidate ladder and the
	 * default transformations.
	 * </p>
	 */
	private Map<String, String> _getVariantProperties(
		String mediaConditionName,
		Map<String, Map<String, String>> variantPropertiesMap) {

		Map<String, String> variantProperties = variantPropertiesMap.get(
			mediaConditionName);

		if (variantProperties != null) {
			return variantProperties;
		}

		if (Objects.equals(
				mediaConditionName,
				ResponsiveImageConstants.MEDIA_CONDITION_NAME_DEFAULT)) {

			return Collections.emptyMap();
		}

		return null;
	}

	/**
	 * Returns the configured presets with the system ones ahead of them, so
	 * that a configured preset overrides the system preset of the same key
	 * and leaves every other key of that preset alone.
	 */
	private String[] _prependSystemPresets(String[] presets) {
		if (presets == null) {
			return ResponsiveImageConstants.SYSTEM_PRESETS;
		}

		return ArrayUtil.append(
			ResponsiveImageConstants.SYSTEM_PRESETS, presets);
	}

	/**
	 * Returns the configured candidate widths as a sorted set, ignoring any
	 * that does not read as a positive number.
	 *
	 * <p>
	 * An unusable width is warned about rather than refused, so that one typo
	 * leaves the rest of the widths generating.
	 * </p>
	 */
	private TreeSet<Integer> _toCandidateWidthsSet(String[] candidateWidths) {
		TreeSet<Integer> candidateWidthsSet = new TreeSet<>();

		if (candidateWidths == null) {
			return candidateWidthsSet;
		}

		for (String candidateWidth : candidateWidths) {
			int width = GetterUtil.getInteger(candidateWidth);

			if (width > 0) {
				candidateWidthsSet.add(width);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Ignoring invalid candidate width " + candidateWidth);
			}
		}

		return candidateWidthsSet;
	}

	private Integer _toMaxWidth(String maxWidthString) {
		if (Validator.isBlank(maxWidthString)) {
			return null;
		}

		int maxWidth = GetterUtil.getInteger(maxWidthString);

		if (maxWidth > 0) {
			return maxWidth;
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Ignoring invalid maximum width " + maxWidthString);
		}

		return null;
	}

	private Map<String, String> _toMediaConditionsMap(
		String[] mediaConditions) {

		Map<String, String> mediaConditionsMap = new LinkedHashMap<>();

		if (mediaConditions == null) {
			return mediaConditionsMap;
		}

		for (String mediaCondition : mediaConditions) {
			if (Validator.isBlank(mediaCondition)) {
				continue;
			}

			int index = mediaCondition.indexOf(StringPool.EQUAL);

			if (index <= 0) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring malformed media condition " + mediaCondition);
				}

				continue;
			}

			String key = StringUtil.trim(mediaCondition.substring(0, index));

			String[] keyParts = StringUtil.split(key, StringPool.PERIOD);

			if ((keyParts.length != 2) ||
				!Objects.equals(
					keyParts[1], ResponsiveImageConstants.PROPERTY_QUERY)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring unrecognized media condition key " + key);
				}

				continue;
			}

			// The unconditional media condition is the absence of one, so
			// declaring it says nothing. Honoring the declaration would also
			// apply the unconditional settings twice, once under the declared
			// query and once as the catch all they are.

			if (Objects.equals(
					keyParts[0],
					ResponsiveImageConstants.MEDIA_CONDITION_NAME_DEFAULT)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Ignoring media condition key ", key,
							" because the unconditional media condition needs ",
							"no declaring"));
				}

				continue;
			}

			mediaConditionsMap.put(
				keyParts[0],
				StringUtil.trim(mediaCondition.substring(index + 1)));
		}

		return mediaConditionsMap;
	}

	private Map<String, ResponsiveImageSettings.Preset> _toPresetsMap(
		Map<String, String> mediaConditionsMap, String[] presets) {

		Map<String, Boolean> lazyLoadingValuesMap = new LinkedHashMap<>();
		Map<String, Map<String, Map<String, String>>> presetPropertiesMap =
			new LinkedHashMap<>();

		for (String presetString : _prependSystemPresets(presets)) {
			if (Validator.isBlank(presetString)) {
				continue;
			}

			int index = presetString.indexOf(StringPool.EQUAL);

			if (index <= 0) {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignoring malformed preset " + presetString);
				}

				continue;
			}

			String key = StringUtil.trim(presetString.substring(0, index));
			String value = StringUtil.trim(presetString.substring(index + 1));

			String[] keyParts = StringUtil.split(key, StringPool.PERIOD);

			String presetName = keyParts[0];

			if ((keyParts.length == 2) &&
				Objects.equals(
					keyParts[1],
					ResponsiveImageConstants.PROPERTY_LAZY_LOADING)) {

				lazyLoadingValuesMap.put(
					presetName, GetterUtil.getBoolean(value));

				continue;
			}

			if (keyParts.length != 3) {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignoring unrecognized preset key " + key);
				}

				continue;
			}

			String mediaConditionName = keyParts[1];

			if (!ResponsiveImageConstants.MEDIA_CONDITION_NAME_DEFAULT.equals(
					mediaConditionName) &&
				!mediaConditionsMap.containsKey(mediaConditionName)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Ignoring preset key ", key,
							" because no media condition named ",
							mediaConditionName, " is declared"));
				}

				continue;
			}

			Map<String, Map<String, String>> variantPropertiesMap =
				presetPropertiesMap.computeIfAbsent(
					presetName, name -> new LinkedHashMap<>());

			Map<String, String> variantProperties =
				variantPropertiesMap.computeIfAbsent(
					mediaConditionName, name -> new LinkedHashMap<>());

			variantProperties.put(keyParts[2], value);
		}

		Map<String, ResponsiveImageSettings.Preset> presetsMap =
			new LinkedHashMap<>();

		for (Map.Entry<String, Map<String, Map<String, String>>> entry :
				presetPropertiesMap.entrySet()) {

			presetsMap.put(
				entry.getKey(),
				new ResponsiveImageSettings.Preset(
					lazyLoadingValuesMap.get(entry.getKey()), entry.getKey(),
					_toVariants(entry.getValue(), mediaConditionsMap)));
		}

		return presetsMap;
	}

	/**
	 * Returns one variant, built from what a preset declares under one
	 * media condition.
	 */
	private ResponsiveImageSettings.Variant _toVariant(
		Map<String, String> variantProperties, String query) {

		if (variantProperties == null) {
			return null;
		}

		return new ResponsiveImageSettings.Variant(
			_toMaxWidth(
				variantProperties.get(
					ResponsiveImageConstants.PROPERTY_MAX_WIDTH)),
			query,
			variantProperties.get(ResponsiveImageConstants.PROPERTY_SIZES),
			_toTransformationsMap(
				variantProperties.get(
					ResponsiveImageConstants.PROPERTY_TRANSFORMATIONS)));
	}

	/**
	 * Returns a preset's variants, ordered by the order media conditions
	 * are declared rather than by the order the preset's own keys appear, so
	 * ordering is decided once for the whole installation. The unconditional
	 * media condition always sorts last, being the catch all.
	 */
	private List<ResponsiveImageSettings.Variant> _toVariants(
		Map<String, Map<String, String>> variantPropertiesMap,
		Map<String, String> mediaConditionsMap) {

		// The unconditional media condition is never a declaration, whoever
		// filtered the declarations of it, so it is taken out of them here.
		// That is what both sorts it last and leaves it without a query of
		// its own, since the names come from the declarations and so does
		// each query.

		Map<String, String> declaredMediaConditionsMap = new LinkedHashMap<>(
			mediaConditionsMap);

		declaredMediaConditionsMap.remove(
			ResponsiveImageConstants.MEDIA_CONDITION_NAME_DEFAULT);

		Set<String> mediaConditionNames = new LinkedHashSet<>(
			declaredMediaConditionsMap.keySet());

		mediaConditionNames.add(
			ResponsiveImageConstants.MEDIA_CONDITION_NAME_DEFAULT);

		return TransformUtil.transform(
			mediaConditionNames,
			mediaConditionName -> _toVariant(
				_getVariantProperties(mediaConditionName, variantPropertiesMap),
				declaredMediaConditionsMap.get(mediaConditionName)));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResponsiveImageSettingsProvider.class);

	private final ResponsiveImageConfigurationHelper
		_responsiveImageConfigurationHelper;
	private final Map<String, ResponsiveImageSettings>
		_responsiveImageSettingsMap = new ConcurrentHashMap<>();

}