/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.asset;

import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.item.InfoItemServiceRegistryUtil;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Georgel Pop
 */
public class AssetAnalyticsAttributesProvider {

	public static final String ACTION_IMPRESSION = "impression";

	public static final String ACTION_VIEW = "view";

	public static final String FIELD_AUTHOR = "author";

	public static final String FIELD_CONTENT = "content";

	public static final String FIELD_DATE = "date";

	public static final String FIELD_TITLE = "title";

	public static final String HAS_ANALYTICS_ATTRIBUTES =
		"HAS_ANALYTICS_ATTRIBUTES";

	public AssetAnalyticsAttributesProvider(
		AssetEntry assetEntry, AssetRenderer<?> assetRenderer, Locale locale,
		boolean shouldTrack) {

		_assetEntry = assetEntry;
		_assetRenderer = assetRenderer;
		_locale = locale;
		_shouldTrack = shouldTrack;
	}

	public String buildAttributes(String action, String field) {
		if ((_assetEntry == null) || !_shouldTrack ||
			!FeatureFlagManagerUtil.isEnabled(
				_assetEntry.getCompanyId(), "LPD-81914") ||
			!_isAnalyticsEnabled()) {

			return StringPool.BLANK;
		}

		Map<String, String> analyticsAttributes = getAttributes(action, field);

		StringBundler sb = new StringBundler(analyticsAttributes.size() * 4);

		for (Map.Entry<String, String> entry : analyticsAttributes.entrySet()) {
			if (Validator.isNull(entry.getValue())) {
				continue;
			}

			sb.append(entry.getKey());
			sb.append("=\"");
			sb.append(HtmlUtil.escapeAttribute(entry.getValue()));
			sb.append("\" ");
		}

		return sb.toString(
		).trim();
	}

	public Map<String, String> getAttributes(String action, String field) {
		if ((_assetEntry == null) || !_isAnalyticsEnabled()) {
			return Collections.emptyMap();
		}

		return Collections.unmodifiableMap(
			LinkedHashMapBuilder.put(
				"data-analytics-asset-action", action
			).put(
				"data-analytics-asset-cmsversion", _getAnalyticsCMSVersion()
			).put(
				"data-analytics-asset-field", field
			).put(
				"data-analytics-asset-id",
				String.valueOf(_assetEntry.getClassPK())
			).put(
				"data-analytics-asset-subtype", _getAnalyticsSubtype()
			).put(
				"data-analytics-asset-title",
				((_assetRenderer != null) && (_locale != null)) ?
					_assetRenderer.getTitle(_locale) : null
			).put(
				"data-analytics-asset-type", _getAnalyticsType()
			).put(
				"data-analytics-external-reference-code",
				_getAnalyticsExternalReferenceCode()
			).build());
	}

	private String _getAnalyticsCMSVersion() {
		String className = _assetEntry.getClassName();

		if (!className.startsWith(_CLASS_NAME_OBJECT_DEFINITION)) {
			return "1.0";
		}

		ObjectDefinitionLocalService objectDefinitionLocalService =
			_objectDefinitionLocalServiceSnapshot.get();

		if (objectDefinitionLocalService == null) {
			return "1.0";
		}

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				_assetEntry.getCompanyId(), className);

		if ((objectDefinition != null) && objectDefinition.isCMS()) {
			return "2.0";
		}

		return "1.0";
	}

	private String _getAnalyticsExternalReferenceCode() {
		if (_assetRenderer == null) {
			return null;
		}

		if (_assetRenderer.getAssetObject() instanceof
				ExternalReferenceCodeModel externalReferenceCodeModel) {

			return externalReferenceCodeModel.getExternalReferenceCode();
		}

		return null;
	}

	private String _getAnalyticsSubtype() {
		if (_assetRenderer == null) {
			return null;
		}

		Object assetObject = _assetRenderer.getAssetObject();

		if (assetObject instanceof JournalArticle journalArticle) {
			String ddmStructureKey = journalArticle.getDDMStructureKey();

			if (Validator.isNotNull(ddmStructureKey)) {
				return StringUtil.toLowerCase(ddmStructureKey);
			}
		}

		InfoItemObjectVariationProvider<Object>
			infoItemObjectVariationProvider =
				InfoItemServiceRegistryUtil.getFirstInfoItemService(
					InfoItemObjectVariationProvider.class,
					_assetEntry.getClassName());

		if (infoItemObjectVariationProvider == null) {
			return null;
		}

		return infoItemObjectVariationProvider.getInfoItemFormVariationKey(
			assetObject);
	}

	private String _getAnalyticsType() {
		String className = _assetEntry.getClassName();

		if (className.startsWith(_CLASS_NAME_OBJECT_DEFINITION)) {
			ObjectDefinitionLocalService objectDefinitionLocalService =
				_objectDefinitionLocalServiceSnapshot.get();

			if (objectDefinitionLocalService != null) {
				ObjectDefinition objectDefinition =
					objectDefinitionLocalService.
						fetchObjectDefinitionByClassName(
							_assetEntry.getCompanyId(), className);

				if (objectDefinition != null) {
					String name = objectDefinition.getName();

					if (Validator.isNotNull(name)) {
						return TextFormatter.format(name, TextFormatter.K);
					}
				}
			}

			return "object-entry";
		}

		String type = _types.get(className);

		if (Validator.isNotNull(type)) {
			return type;
		}

		return className;
	}

	private boolean _isAnalyticsEnabled() {
		AnalyticsSettingsManager analyticsSettingsManager =
			_analyticsSettingsManagerSnapshot.get();

		if (analyticsSettingsManager == null) {
			return true;
		}

		try {
			return analyticsSettingsManager.isAnalyticsEnabled(
				_assetEntry.getCompanyId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return true;
		}
	}

	private static final String _CLASS_NAME_OBJECT_DEFINITION =
		"com.liferay.object.model.ObjectDefinition";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAnalyticsAttributesProvider.class);

	private static final Snapshot<AnalyticsSettingsManager>
		_analyticsSettingsManagerSnapshot = new Snapshot<>(
			AssetAnalyticsAttributesProvider.class,
			AnalyticsSettingsManager.class);
	private static final Snapshot<ObjectDefinitionLocalService>
		_objectDefinitionLocalServiceSnapshot = new Snapshot<>(
			AssetAnalyticsAttributesProvider.class,
			ObjectDefinitionLocalService.class);
	private static final Map<String, String> _types = HashMapBuilder.put(
		BlogsEntry.class.getName(), "blog"
	).put(
		DLFileEntry.class.getName(), "document"
	).put(
		JournalArticle.class.getName(), "web-content"
	).build();

	private final AssetEntry _assetEntry;
	private final AssetRenderer<?> _assetRenderer;
	private final Locale _locale;
	private final boolean _shouldTrack;

}