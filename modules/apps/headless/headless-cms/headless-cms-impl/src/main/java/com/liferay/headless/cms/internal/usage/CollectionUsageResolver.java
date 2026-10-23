/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.usage;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.model.AssetListEntryUsageTable;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.list.service.AssetListEntryUsageLocalService;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.cms.internal.util.CMSSearchUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.Query;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Mikel Lorza
 */
public class CollectionUsageResolver {

	public CollectionUsageResolver(
		AssetEntryLocalService assetEntryLocalService,
		AssetListAssetEntryProvider assetListAssetEntryProvider,
		AssetListEntryAssetEntryRelLocalService
			assetListEntryAssetEntryRelLocalService,
		AssetListEntryLocalService assetListEntryLocalService,
		AssetListEntrySegmentsEntryRelLocalService
			assetListEntrySegmentsEntryRelLocalService,
		AssetListEntryUsageLocalService assetListEntryUsageLocalService,
		DepotEntryLocalService depotEntryLocalService,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldLocalService objectFieldLocalService, Portal portal,
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_assetListAssetEntryProvider = assetListAssetEntryProvider;
		_assetListEntryAssetEntryRelLocalService =
			assetListEntryAssetEntryRelLocalService;
		_assetListEntryLocalService = assetListEntryLocalService;
		_assetListEntrySegmentsEntryRelLocalService =
			assetListEntrySegmentsEntryRelLocalService;
		_assetListEntryUsageLocalService = assetListEntryUsageLocalService;
		_depotEntryLocalService = depotEntryLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_portal = portal;
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	public CollectionUsages getCollectionUsages(long companyId) {
		CollectionUsages collectionUsages = new CollectionUsages();

		ResolutionContext resolutionContext = new ResolutionContext(
			collectionUsages, companyId);

		long collectionStyledLayoutStructureItemClassNameId =
			_portal.getClassNameId(CollectionStyledLayoutStructureItem.class);
		long portletClassNameId = _portal.getClassNameId(Portlet.class);

		List<Object[]> assetListEntryUsageObjectsList =
			_assetListEntryUsageLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					AssetListEntryUsageTable.INSTANCE.groupId,
					AssetListEntryUsageTable.INSTANCE.plid,
					AssetListEntryUsageTable.INSTANCE.classNameId,
					AssetListEntryUsageTable.INSTANCE.key,
					AssetListEntryUsageTable.INSTANCE.containerKey,
					AssetListEntryUsageTable.INSTANCE.containerType
				).from(
					AssetListEntryUsageTable.INSTANCE
				).where(
					AssetListEntryUsageTable.INSTANCE.companyId.eq(
						companyId
					).and(
						AssetListEntryUsageTable.INSTANCE.containerType.in(
							new Long[] {
								collectionStyledLayoutStructureItemClassNameId,
								portletClassNameId
							})
					)
				));

		for (Object[] row : assetListEntryUsageObjectsList) {
			long groupId = (Long)row[0];
			long plid = (Long)row[1];
			long classNameId = (Long)row[2];
			String key = (String)row[3];
			String containerKey = (String)row[4];
			long containerType = (Long)row[5];

			try {
				Layout layout = resolutionContext.getLayout(plid);

				if (containerType == portletClassNameId) {
					layout = _getPortletLayout(
						layout, containerKey, resolutionContext);
				}

				if ((layout == null) || !_isPublished(layout)) {
					continue;
				}

				Set<Long> connectedDepotGroupIds =
					resolutionContext.getConnectedDepotGroupIds(groupId);

				if (connectedDepotGroupIds.isEmpty()) {
					continue;
				}

				_resolveUsage(
					classNameId, containerKey, groupId, key, layout,
					resolutionContext);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}

		return collectionUsages;
	}

	private ObjectDefinition _getCMSObjectDefinition(
		long companyId, String className) {

		if (!StringUtil.startsWith(
				className,
				ObjectDefinitionConstants.
					CLASS_NAME_PREFIX_CUSTOM_OBJECT_DEFINITION)) {

			return null;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				companyId, className);

		if ((objectDefinition == null) || !objectDefinition.isCMS()) {
			return null;
		}

		return objectDefinition;
	}

	private CollectionStyledLayoutStructureItem
		_getCollectionStyledLayoutStructureItem(
			LayoutStructure layoutStructure, String itemId) {

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		if (layoutStructureItem instanceof
				CollectionStyledLayoutStructureItem) {

			return (CollectionStyledLayoutStructureItem)layoutStructureItem;
		}

		return null;
	}

	private Query _getInfoCollectionProviderQuery(
			JSONObject configJSONObject, long groupId,
			ObjectDefinition objectDefinition,
			ResolutionContext resolutionContext)
		throws Exception {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			QueriesUtil.term(
				Field.ENTRY_CLASS_NAME, objectDefinition.getClassName()),
			CMSSearchUtil.getTermsQuery(
				Field.GROUP_ID,
				ArrayUtil.toStringArray(
					resolutionContext.getConnectedDepotGroupIds(groupId))),
			QueriesUtil.term(Field.STATUS, WorkflowConstants.STATUS_APPROVED));

		if (configJSONObject == null) {
			return booleanQuery;
		}

		for (String fieldName : configJSONObject.keySet()) {
			String fieldValue = configJSONObject.getString(fieldName);

			JSONArray fieldValuesJSONArray = configJSONObject.getJSONArray(
				fieldName);

			if (fieldValuesJSONArray != null) {
				fieldValue = fieldValuesJSONArray.getString(0);
			}

			if (Validator.isNull(fieldValue)) {
				continue;
			}

			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				objectDefinition.getObjectDefinitionId(), fieldName);

			if (objectField == null) {
				continue;
			}

			Query query = null;

			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN)) {

				query = _getNestedFieldQuery(
					fieldName, "value_boolean",
					GetterUtil.getBoolean(fieldValue));
			}
			else if (Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

				if (objectField.isIndexedAsKeyword()) {
					query = _getNestedFieldQuery(
						fieldName, "value_keyword",
						StringUtil.lowerCase(fieldValue));
				}
				else {
					query = _getNestedFieldQuery(
						fieldName, "value_keyword_lowercase", fieldValue);
				}
			}

			if (query == null) {
				return null;
			}

			booleanQuery.addFilterQueryClauses(query);
		}

		return booleanQuery;
	}

	private Query _getNestedFieldQuery(
		String fieldName, String valueFieldName, Object value) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			QueriesUtil.term("nestedFieldArray.fieldName", fieldName),
			QueriesUtil.term("nestedFieldArray." + valueFieldName, value));

		return QueriesUtil.nested("nestedFieldArray", booleanQuery);
	}

	private Layout _getPortletLayout(
		Layout layout, String portletId, ResolutionContext resolutionContext) {

		if (layout == null) {
			return null;
		}

		if (layout.isDraftLayout()) {
			layout = resolutionContext.getLayout(layout.getClassPK());

			if (layout == null) {
				return null;
			}
		}

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.fetchPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId);

		if (portletPreferences == null) {
			return null;
		}

		return layout;
	}

	private boolean _isPublished(Layout layout) {
		if (layout.isDraftLayout()) {
			return false;
		}

		if (Objects.equals(
				layout.getType(), LayoutConstants.TYPE_ASSET_DISPLAY)) {

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

			if ((layoutPageTemplateEntry != null) &&
				(layoutPageTemplateEntry.getStatus() ==
					WorkflowConstants.STATUS_APPROVED)) {

				return true;
			}

			return false;
		}

		if ((layout.isTypeContent() && layout.isPublished()) ||
			layout.isTypePortlet()) {

			return true;
		}

		return false;
	}

	private void _resolveAssetListEntryUsage(
			String key, ResolutionContext resolutionContext)
		throws Exception {

		Set<String> resolutionKeys = resolutionContext.getResolutionKeys();

		if (!resolutionKeys.add("assetListEntry#" + key)) {
			return;
		}

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				GetterUtil.getLong(key));

		if (assetListEntry == null) {
			return;
		}

		CollectionUsages collectionUsages =
			resolutionContext.getCollectionUsages();

		if (assetListEntry.getType() ==
				AssetListEntryTypeConstants.TYPE_MANUAL) {

			for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
					_assetListEntryAssetEntryRelLocalService.
						getAssetListEntryAssetEntryRels(
							assetListEntry.getAssetListEntryId(),
							QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					assetListEntryAssetEntryRel.getAssetEntryId());

				if ((assetEntry == null) ||
					!StringUtil.startsWith(
						assetEntry.getClassName(),
						ObjectDefinitionConstants.
							CLASS_NAME_PREFIX_CUSTOM_OBJECT_DEFINITION)) {

					continue;
				}

				collectionUsages.addObjectEntryId(assetEntry.getClassPK());
			}

			return;
		}

		List<AssetListEntrySegmentsEntryRel> assetListEntrySegmentsEntryRels =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRels(
					assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		long[] segmentsEntryIds = {0};

		if (!assetListEntrySegmentsEntryRels.isEmpty()) {
			segmentsEntryIds = ListUtil.toLongArray(
				assetListEntrySegmentsEntryRels,
				AssetListEntrySegmentsEntryRel::getSegmentsEntryId);
		}

		for (long segmentsEntryId : segmentsEntryIds) {
			String booleanQueryKey = StringBundler.concat(
				assetListEntry.getGroupId(), StringPool.POUND,
				assetListEntry.getAssetEntryType(), StringPool.POUND,
				assetListEntry.getAssetEntrySubtype(), StringPool.POUND,
				assetListEntry.getTypeSettings(segmentsEntryId));

			if (collectionUsages.hasBooleanQuery(booleanQueryKey)) {
				continue;
			}

			com.liferay.portal.kernel.search.BooleanQuery booleanQuery =
				_assetListAssetEntryProvider.getBooleanQuery(
					assetListEntry, segmentsEntryId);

			if (booleanQuery == null) {
				continue;
			}

			AssetEntryQuery assetEntryQuery =
				_assetListAssetEntryProvider.getAssetEntryQuery(
					assetListEntry, new long[] {segmentsEntryId},
					StringPool.BLANK);

			collectionUsages.addBooleanQuery(
				booleanQuery, assetEntryQuery.getGroupIds(), booleanQueryKey);
		}
	}

	private void _resolveInfoCollectionProviderUsage(
			String containerKey, long groupId, String key, Layout layout,
			ObjectDefinition objectDefinition,
			ResolutionContext resolutionContext)
		throws Exception {

		JSONObject configJSONObject = null;

		LayoutStructure layoutStructure = resolutionContext.getLayoutStructure(
			groupId, layout.getPlid());

		if (layoutStructure != null) {
			CollectionStyledLayoutStructureItem
				collectionStyledLayoutStructureItem =
					_getCollectionStyledLayoutStructureItem(
						layoutStructure, containerKey);

			if (collectionStyledLayoutStructureItem != null) {
				JSONObject collectionJSONObject =
					collectionStyledLayoutStructureItem.
						getCollectionJSONObject();

				configJSONObject = collectionJSONObject.getJSONObject("config");
			}
		}

		Set<String> resolutionKeys = resolutionContext.getResolutionKeys();

		if (!resolutionKeys.add(
				StringBundler.concat(
					"provider#", groupId, StringPool.POUND, key,
					StringPool.POUND, configJSONObject))) {

			return;
		}

		Query query = _getInfoCollectionProviderQuery(
			configJSONObject, groupId, objectDefinition, resolutionContext);

		if (query != null) {
			CollectionUsages collectionUsages =
				resolutionContext.getCollectionUsages();

			collectionUsages.addQuery(query);
		}
	}

	private void _resolveUsage(
			long classNameId, String containerKey, long groupId, String key,
			Layout layout, ResolutionContext resolutionContext)
		throws Exception {

		if (classNameId == _portal.getClassNameId(AssetListEntry.class)) {
			_resolveAssetListEntryUsage(key, resolutionContext);

			return;
		}

		ObjectDefinition objectDefinition = _getCMSObjectDefinition(
			resolutionContext.getCompanyId(), key);

		if (objectDefinition != null) {
			_resolveInfoCollectionProviderUsage(
				containerKey, groupId, key, layout, objectDefinition,
				resolutionContext);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionUsageResolver.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final AssetListAssetEntryProvider _assetListAssetEntryProvider;
	private final AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;
	private final AssetListEntryLocalService _assetListEntryLocalService;
	private final AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;
	private final AssetListEntryUsageLocalService
		_assetListEntryUsageLocalService;
	private final DepotEntryLocalService _depotEntryLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final Portal _portal;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;

	private class ResolutionContext {

		public CollectionUsages getCollectionUsages() {
			return _collectionUsages;
		}

		public long getCompanyId() {
			return _companyId;
		}

		public Set<Long> getConnectedDepotGroupIds(long groupId)
			throws Exception {

			Set<Long> connectedDepotGroupIds = _connectedDepotGroupIdsMap.get(
				groupId);

			if (connectedDepotGroupIds != null) {
				return connectedDepotGroupIds;
			}

			connectedDepotGroupIds = new HashSet<>(
				TransformUtil.transform(
					_depotEntryLocalService.getGroupConnectedDepotEntries(
						groupId, DepotConstants.TYPE_SPACE, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS),
					DepotEntry::getGroupId));

			_connectedDepotGroupIdsMap.put(groupId, connectedDepotGroupIds);

			return connectedDepotGroupIds;
		}

		public Layout getLayout(long plid) {
			return _layouts.computeIfAbsent(
				plid, _layoutLocalService::fetchLayout);
		}

		public LayoutStructure getLayoutStructure(long groupId, long plid) {
			LayoutStructure layoutStructure = _layoutStructures.get(plid);

			if (layoutStructure != null) {
				return layoutStructure;
			}

			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(groupId, plid);

			if (layoutPageTemplateStructure == null) {
				return null;
			}

			layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

			_layoutStructures.put(plid, layoutStructure);

			return layoutStructure;
		}

		public Set<String> getResolutionKeys() {
			return _resolutionKeys;
		}

		private ResolutionContext(
			CollectionUsages collectionUsages, long companyId) {

			_collectionUsages = collectionUsages;
			_companyId = companyId;
		}

		private final CollectionUsages _collectionUsages;
		private final long _companyId;
		private final Map<Long, Set<Long>> _connectedDepotGroupIdsMap =
			new HashMap<>();
		private final Map<Long, LayoutStructure> _layoutStructures =
			new HashMap<>();
		private final Map<Long, Layout> _layouts = new HashMap<>();
		private final Set<String> _resolutionKeys = new HashSet<>();

	}

}