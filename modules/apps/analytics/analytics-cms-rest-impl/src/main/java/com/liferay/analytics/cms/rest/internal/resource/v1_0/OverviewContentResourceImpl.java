/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.resource.v1_0;

import com.liferay.analytics.cms.rest.dto.v1_0.OverviewContent;
import com.liferay.analytics.cms.rest.dto.v1_0.Trend;
import com.liferay.analytics.cms.rest.resource.v1_0.OverviewContentResource;
import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRelTable;
import com.liferay.asset.kernel.model.AssetCategoryTable;
import com.liferay.asset.kernel.model.AssetEntries_AssetTagsTable;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.object.model.ObjectDefinitionTable;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectFolderTable;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rachael Koestartyo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/overview-content.properties",
	scope = ServiceScope.PROTOTYPE, service = OverviewContentResource.class
)
public class OverviewContentResourceImpl
	extends BaseOverviewContentResourceImpl {

	@Override
	public OverviewContent getOverviewContent(
			String languageId, Integer rangeKey, Integer spaceId)
		throws Exception {

		return _toOverviewContent(
			_getOverviewContentObjects(rangeKey),
			_getPreviousTotalCount(rangeKey));
	}

	private Object[] _getOverviewContentObjects(int rangeKey) {
		AssetCategoryTable assetCategoryTable = AssetCategoryTable.INSTANCE;
		AssetEntries_AssetTagsTable assetEntriesAssetTagsTable =
			AssetEntries_AssetTagsTable.INSTANCE;
		AssetEntryAssetCategoryRelTable assetEntryAssetCategoryRelTable =
			AssetEntryAssetCategoryRelTable.INSTANCE;
		AssetEntryTable assetEntryTable = AssetEntryTable.INSTANCE;
		ObjectDefinitionTable objectDefinitionTable =
			ObjectDefinitionTable.INSTANCE;
		ObjectEntryTable objectEntryTable = ObjectEntryTable.INSTANCE;
		ObjectFolderTable objectFolderTable = ObjectFolderTable.INSTANCE;

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			DSLFunctionFactoryUtil.countDistinct(
				assetEntryAssetCategoryRelTable.assetCategoryId
			).as(
				"categoriesCount"
			),
			DSLFunctionFactoryUtil.countDistinct(
				assetEntriesAssetTagsTable.tagId
			).as(
				"tagsCount"
			),
			DSLFunctionFactoryUtil.count(
				objectEntryTable.objectEntryId
			).as(
				"totalCount"
			),
			DSLFunctionFactoryUtil.countDistinct(
				assetCategoryTable.vocabularyId
			).as(
				"vocabulariesCount"
			)
		).from(
			objectFolderTable
		).innerJoinON(
			objectDefinitionTable,
			objectDefinitionTable.objectFolderId.eq(
				objectFolderTable.objectFolderId)
		).innerJoinON(
			objectEntryTable,
			objectEntryTable.objectDefinitionId.eq(
				objectDefinitionTable.objectDefinitionId)
		).innerJoinON(
			assetEntryTable,
			assetEntryTable.classPK.eq(objectEntryTable.objectEntryId)
		).leftJoinOn(
			assetEntriesAssetTagsTable,
			assetEntriesAssetTagsTable.entryId.eq(assetEntryTable.entryId)
		).leftJoinOn(
			assetEntryAssetCategoryRelTable,
			assetEntryAssetCategoryRelTable.assetEntryId.eq(
				assetEntryTable.entryId)
		).leftJoinOn(
			assetCategoryTable,
			assetCategoryTable.categoryId.eq(
				assetEntryAssetCategoryRelTable.assetCategoryId)
		).where(
			objectFolderTable.externalReferenceCode.eq(
				"L_CMS_CONTENT_STRUCTURES"
			).and(
				objectEntryTable.createDate.gte(_getStartDate(rangeKey))
			)
		);

		List<Object[]> results = _objectEntryLocalService.dslQuery(dslQuery);

		if (results.isEmpty()) {
			return new Object[] {0, 0, 0, 0};
		}

		return results.get(0);
	}

	private Date _getPreviousStartDate(int rangeKey) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, -(rangeKey * 2));

		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	private long _getPreviousTotalCount(int rangeKey) {
		AssetEntryTable assetEntryTable = AssetEntryTable.INSTANCE;
		ObjectDefinitionTable objectDefinitionTable =
			ObjectDefinitionTable.INSTANCE;
		ObjectEntryTable objectEntryTable = ObjectEntryTable.INSTANCE;
		ObjectFolderTable objectFolderTable = ObjectFolderTable.INSTANCE;

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			DSLFunctionFactoryUtil.count(
				objectEntryTable.objectEntryId
			).as(
				"totalCount"
			)
		).from(
			objectFolderTable
		).innerJoinON(
			objectDefinitionTable,
			objectDefinitionTable.objectFolderId.eq(
				objectFolderTable.objectFolderId)
		).innerJoinON(
			objectEntryTable,
			objectEntryTable.objectDefinitionId.eq(
				objectDefinitionTable.objectDefinitionId)
		).innerJoinON(
			assetEntryTable,
			assetEntryTable.classPK.eq(objectEntryTable.objectEntryId)
		).where(
			objectFolderTable.externalReferenceCode.eq(
				"L_CMS_CONTENT_STRUCTURES"
			).and(
				objectEntryTable.createDate.gte(_getPreviousStartDate(rangeKey))
			).and(
				objectEntryTable.createDate.lt(_getStartDate(rangeKey))
			)
		);

		List<Object[]> results = _objectEntryLocalService.dslQuery(dslQuery);

		if (results.isEmpty()) {
			return 0;
		}

		return GetterUtil.getLong(results.get(0));
	}

	private Date _getStartDate(int rangeKey) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, -rangeKey);

		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	private OverviewContent _toOverviewContent(
		long categoriesCount, Trend.Classification classification,
		double percentage, long tagsCount, long totalCount,
		long vocabulariesCount) {

		Trend trend = new Trend();

		trend.setClassification(() -> classification);
		trend.setPercentage(() -> percentage);

		OverviewContent overviewContent = new OverviewContent();

		overviewContent.setCategoriesCount(() -> categoriesCount);
		overviewContent.setTagsCount(() -> tagsCount);
		overviewContent.setTotalCount(() -> totalCount);
		overviewContent.setTrend(() -> trend);
		overviewContent.setVocabulariesCount(() -> vocabulariesCount);

		return overviewContent;
	}

	private OverviewContent _toOverviewContent(
		Object[] objects, long previousTotalCount) {

		long categoriesCount = (Long)objects[0];
		long tagsCount = (Long)objects[1];
		long totalCount = (Long)objects[2];
		long vocabulariesCount = (Long)objects[3];

		Trend.Classification classification = Trend.Classification.NEUTRAL;
		double percentage = 0.0;

		if (previousTotalCount > 0) {
			double diff = totalCount - previousTotalCount;

			percentage = diff / previousTotalCount * 100.0;

			if (percentage > 0) {
				classification = Trend.Classification.POSITIVE;
			}
			else if (percentage < 0) {
				classification = Trend.Classification.NEGATIVE;
			}
		}
		else if (totalCount > 0) {
			classification = Trend.Classification.POSITIVE;
			percentage = 100.0;
		}

		return _toOverviewContent(
			categoriesCount, classification, percentage, tagsCount, totalCount,
			vocabulariesCount);
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}