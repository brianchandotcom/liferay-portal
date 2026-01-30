/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.search;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.AssetRendererFactoryLookup;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.view.count.service.ViewCountEntryLocalService;

import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = DocumentContributor.class)
public class AssetEntryDocumentContributor
	implements DocumentContributor<AssetEntry> {

	@Override
	public void contribute(Document document, BaseModel<AssetEntry> baseModel) {
		String className = document.get(Field.ENTRY_CLASS_NAME);

		AssetRendererFactory<?> assetRendererFactory =
			_assetRendererFactoryLookup.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSelectable()) {

			return;
		}

		AssetEntry assetEntry = null;

		Date displayDate = new Date();

		if (document.hasField(Field.DISPLAY_DATE)) {
			try {
				displayDate = document.getDate(Field.DISPLAY_DATE);
			}
			catch (ParseException parseException) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to parse data ", parseException);
				}
			}
		}

		if (displayDate.getTime() > System.currentTimeMillis()) {
			String uuid = GetterUtil.getString(document.get(Field.UUID));

			long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

			assetEntry = _assetEntryLocalService.fetchEntry(groupId, uuid);
		}
		else {
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			assetEntry = _getAssetEntry(
				_classNameLocalService.getClassNameId(className), classPK);
		}

		if (assetEntry == null) {
			return;
		}

		document.addNumber(Field.ASSET_ENTRY_ID, assetEntry.getEntryId());

		if (!document.hasField(Field.CREATE_DATE)) {
			document.addDate(Field.CREATE_DATE, assetEntry.getCreateDate());
		}

		if (assetEntry.getExpirationDate() != null) {
			document.addDate(
				Field.EXPIRATION_DATE, assetEntry.getExpirationDate());
		}
		else {
			document.addDate(
				Field.EXPIRATION_DATE, new Date(Document.MAX_DATE_TIME));
		}

		if (!document.hasField(Field.MODIFIED_DATE)) {
			document.addDate(Field.MODIFIED_DATE, assetEntry.getModifiedDate());
		}

		document.addNumber(Field.PRIORITY, assetEntry.getPriority());

		if (assetEntry.getPublishDate() != null) {
			document.addDate(Field.PUBLISH_DATE, assetEntry.getPublishDate());
		}
		else {
			document.addDate(Field.PUBLISH_DATE, new Date(0));
		}

		document.addLocalizedKeyword(
			"localized_title",
			_localization.populateLocalizationMap(
				assetEntry.getTitleMap(), assetEntry.getDefaultLanguageId(),
				assetEntry.getGroupId()),
			true, true);
		document.addNumber(
			"viewCount",
			_viewCountEntryLocalService.getViewCount(
				assetEntry.getCompanyId(),
				_classNameLocalService.getClassNameId(AssetEntry.class),
				assetEntry.getPrimaryKey()));
		document.addKeyword("visible", assetEntry.isVisible());
	}

	private AssetEntry _getAssetEntry(long classNameId, long classPK) {
		Map<Long, AssetEntry> assetEntries =
			ReindexCacheThreadLocal.getScopeReindexCache(
				AssetEntryDocumentContributor.class.getName(),
				String.valueOf(classNameId),
				() -> _assetEntryLocalService.dslQueryCount(
					DSLQueryFactoryUtil.count(
					).from(
						AssetEntryTable.INSTANCE
					),
					false),
				() -> _assetEntryLocalService.dslQueryCount(
					DSLQueryFactoryUtil.count(
					).from(
						AssetEntryTable.INSTANCE
					).where(
						AssetEntryTable.INSTANCE.classNameId.eq(classNameId)
					),
					false),
				count -> {
					Map<Long, AssetEntry> localAssetEntries = new HashMap<>();

					if (count == 0) {
						return localAssetEntries;
					}

					DSLQuery dslQuery = DSLQueryFactoryUtil.select(
						AssetEntryTable.INSTANCE
					).from(
						AssetEntryTable.INSTANCE
					).where(
						AssetEntryTable.INSTANCE.classNameId.eq(classNameId)
					);

					for (AssetEntry assetEntry :
							(List<AssetEntry>)_assetEntryLocalService.dslQuery(
								dslQuery, false)) {

						localAssetEntries.put(
							assetEntry.getClassPK(), assetEntry);
					}

					return localAssetEntries;
				});

		if (assetEntries == null) {
			return _assetEntryLocalService.fetchEntry(classNameId, classPK);
		}

		return assetEntries.get(classPK);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryDocumentContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetRendererFactoryLookup _assetRendererFactoryLookup;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Localization _localization;

	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

}