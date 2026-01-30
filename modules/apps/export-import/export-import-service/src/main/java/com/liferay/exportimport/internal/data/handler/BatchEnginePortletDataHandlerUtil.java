/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.constants.CreateStrategy;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.staging.StagingGroupHelper;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vendel Toreki
 * @author Alejandro Tardín
 * @author Petteri Karttunen
 */
public class BatchEnginePortletDataHandlerUtil {

	public static Map<String, Serializable> buildExportParameters(
		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			exportImportDescriptor,
		GroupLocalService groupLocalService,
		PortletDataContext portletDataContext,
		StagingGroupHelper stagingGroupHelper) {

		HashMap<String, Serializable> exportParameters =
			HashMapBuilder.<String, Serializable>put(
				"batchNestedFields",
				() -> {
					List<String> batchNestedFields = new ArrayList<>();

					batchNestedFields.add("customFields.attributeType");

					if (MapUtil.getBoolean(
							portletDataContext.getParameterMap(),
							PortletDataHandlerKeys.COMMENTS)) {

						batchNestedFields.add("comments");
					}

					if (MapUtil.getBoolean(
							portletDataContext.getParameterMap(),
							PortletDataHandlerKeys.PERMISSIONS)) {

						batchNestedFields.add("permissions");
					}

					if (ListUtil.isNotEmpty(
							exportImportDescriptor.getNestedFields())) {

						batchNestedFields.addAll(
							exportImportDescriptor.getNestedFields());
					}

					if (batchNestedFields.isEmpty()) {
						return null;
					}

					return StringUtil.merge(
						batchNestedFields, StringPool.COMMA);
				}
			).put(
				"filter",
				() -> {
					if ((portletDataContext.getEndDate() == null) &&
						(portletDataContext.getStartDate() == null)) {

						return null;
					}

					StringBundler sb = new StringBundler(5);

					if (portletDataContext.getEndDate() != null) {
						sb.append("dateModified le ");
						sb.append(
							_format.format(portletDataContext.getEndDate()));
					}

					if (portletDataContext.getStartDate() != null) {
						if (sb.length() > 0) {
							sb.append(" and ");
						}

						sb.append("dateModified ge ");
						sb.append(
							_format.format(portletDataContext.getStartDate()));
					}

					return sb.toString();
				}
			).put(
				"modelClassName", exportImportDescriptor.getModelClassName()
			).put(
				"modelNameLanguageKey",
				exportImportDescriptor.getLabelLanguageKey()
			).putAll(
				exportImportDescriptor.getParameters(portletDataContext)
			).build();

		Group group = groupLocalService.fetchGroup(
			portletDataContext.getScopeGroupId());

		if (!_isCompanyScoped(group, stagingGroupHelper)) {
			exportParameters.put(
				"siteExternalReferenceCode", group.getExternalReferenceCode());

			Map<String, String[]> map = portletDataContext.getParameterMap();

			String[] siteIds = GetterUtil.getStringValues(map.get("siteId"));

			if (ArrayUtil.isNotEmpty(siteIds)) {
				exportParameters.put("siteId", siteIds[0]);
			}
			else {
				exportParameters.put("siteId", group.getGroupId());
			}
		}

		return exportParameters;
	}

	public static Map<String, Serializable> buildImportParameters(
		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			exportImportDescriptor,
		GroupLocalService groupLocalService,
		PortletDataContext portletDataContext,
		StagingGroupHelper stagingGroupHelper) {

		HashMap<String, Serializable> importParameters =
			HashMapBuilder.<String, Serializable>put(
				"batchRestrictFields",
				() -> {
					List<String> batchRestrictFields = new ArrayList<>();

					if (!MapUtil.getBoolean(
							portletDataContext.getParameterMap(),
							PortletDataHandlerKeys.COMMENTS)) {

						batchRestrictFields.add("comments");
					}

					if (!MapUtil.getBoolean(
							portletDataContext.getParameterMap(),
							PortletDataHandlerKeys.PERMISSIONS)) {

						batchRestrictFields.add("permissions");
					}

					if (batchRestrictFields.isEmpty()) {
						return null;
					}

					return StringUtil.merge(
						batchRestrictFields, StringPool.COMMA);
				}
			).put(
				"createStrategy", CreateStrategy.UPSERT.getDBOperation()
			).put(
				"importCreatorStrategy",
				() -> {
					if (!UserIdStrategy.CURRENT_USER_ID.equals(
							MapUtil.getString(
								portletDataContext.getParameterMap(),
								PortletDataHandlerKeys.USER_ID_STRATEGY))) {

						return null;
					}

					return BatchEngineImportTaskConstants.
						IMPORT_CREATOR_STRATEGY_KEEP_CREATOR;
				}
			).put(
				"modelClassName", exportImportDescriptor.getModelClassName()
			).put(
				"modelNameLanguageKey",
				exportImportDescriptor.getLabelLanguageKey()
			).putAll(
				exportImportDescriptor.getParameters(portletDataContext)
			).build();

		Group group = groupLocalService.fetchGroup(
			portletDataContext.getScopeGroupId());

		if (!_isCompanyScoped(group, stagingGroupHelper)) {
			importParameters.put(
				"siteExternalReferenceCode", group.getExternalReferenceCode());
			importParameters.put("siteId", group.getGroupId());
		}

		return importParameters;
	}

	private static boolean _isCompanyScoped(
		Group group, StagingGroupHelper stagingGroupHelper) {

		if ((group == null) || stagingGroupHelper.isCompanyGroup(group)) {
			return true;
		}

		return false;
	}

	private static final Format _format =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

}