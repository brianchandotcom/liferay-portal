/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.list.internal.exportimport.content.processor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.model.adapter.StagedGroup;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.asset.list.model.AssetListEntry",
	service = {
		AssetListEntryExportImportContentProcessor.class,
		ExportImportContentProcessor.class
	}
)
public class AssetListEntryExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.load(content);

		long[] groupIds = GetterUtil.getLongValues(
			StringUtil.split(unicodeProperties.getProperty("groupIds", null)));

		_addGroupMappingsElement(portletDataContext, groupIds);

		long[] classNameIds = GetterUtil.getLongValues(
			StringUtil.split(
				unicodeProperties.getProperty("classNameIds", null)));

		LongStream classNameIdsStream = Arrays.stream(classNameIds);

		String[] classNames = classNameIdsStream.mapToObj(
			classNameId -> _portal.getClassName(classNameId)
		).toArray(
			String[]::new
		);

		unicodeProperties.setProperty(
			"classNames", StringUtil.merge(classNames, ","));

		long defaultClassNameId = GetterUtil.getLong(
			unicodeProperties.getProperty("anyAssetType", null));

		if (defaultClassNameId > 0) {
			unicodeProperties.setProperty(
				"anyAssetTypeClassName",
				_portal.getClassName(defaultClassNameId));
		}

		List<AssetRendererFactory<?>> assetRendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				portletDataContext.getCompanyId());

		for (AssetRendererFactory assetRendererFactory :
				assetRendererFactories) {

			Class<?> clazz = assetRendererFactory.getClass();

			String className = clazz.getSimpleName();

			long[] classTypeIds = GetterUtil.getLongValues(
				StringUtil.split(
					unicodeProperties.getProperty("classTypeIds" + className)));

			if (ArrayUtil.isEmpty(classTypeIds)) {
				continue;
			}

			for (long classTypeId : classTypeIds) {
				DDMStructure ddmStructure =
					_ddmStructureLocalService.fetchStructure(classTypeId);

				if (ddmStructure != null) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, stagedModel, ddmStructure,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);

					continue;
				}

				DLFileEntryType dlFileEntryType =
					_dlFileEntryTypeLocalService.fetchFileEntryType(
						classTypeId);

				if (dlFileEntryType != null) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, stagedModel, dlFileEntryType,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
				}
			}
		}

		return unicodeProperties.toString();
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.load(content);

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element groupIdMappingsElement = rootElement.element(
			"group-id-mappings");

		StagedModelDataHandler<StagedGroup> stagedModelDataHandler =
			(StagedModelDataHandler<StagedGroup>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					StagedGroup.class.getName());

		for (Element groupIdMappingElement :
				groupIdMappingsElement.elements("group-id-mapping")) {

			stagedModelDataHandler.importMissingReference(
				portletDataContext, groupIdMappingElement);
		}

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long[] oldGroupIds = GetterUtil.getLongValues(
			StringUtil.split(unicodeProperties.getProperty("groupIds", null)));

		LongStream oldGroupIdsStream = Arrays.stream(oldGroupIds);

		long[] newGroupIds = oldGroupIdsStream.map(
			oldGroupId -> MapUtil.getLong(groupIds, oldGroupId, oldGroupId)
		).filter(
			oldGroupId -> _groupLocalService.fetchGroup(oldGroupId) != null
		).toArray();

		unicodeProperties.put("groupIds", StringUtil.merge(newGroupIds));

		String[] classNames = StringUtil.split(
			unicodeProperties.getProperty("classNames"));

		Stream<String> classNamesStream = Arrays.stream(classNames);

		long[] classNameIds = classNamesStream.mapToLong(
			className -> _portal.getClassNameId(className)
		).toArray();

		unicodeProperties.setProperty(
			"classNameIds", StringUtil.merge(classNameIds));

		String anyAssetTypeClassName = unicodeProperties.getProperty(
			"anyAssetTypeClassName");

		if (Validator.isNotNull(anyAssetTypeClassName)) {
			unicodeProperties.setProperty(
				"anyAssetType",
				String.valueOf(_portal.getClassNameId(anyAssetTypeClassName)));
		}

		List<AssetRendererFactory<?>> assetRendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				portletDataContext.getCompanyId());

		for (AssetRendererFactory assetRendererFactory :
				assetRendererFactories) {

			Class<?> clazz = assetRendererFactory.getClass();

			String className = clazz.getSimpleName();

			long[] classTypeIds = GetterUtil.getLongValues(
				StringUtil.split(
					unicodeProperties.getProperty("classTypeIds" + className)));

			if (ArrayUtil.isEmpty(classTypeIds)) {
				continue;
			}

			LongStream classTypeIdsStream = Arrays.stream(classTypeIds);

			Map<Long, Long> ddmStructureIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class);
			Map<Long, Long> dlFileEntryTypeIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFileEntryType.class);

			long[] newClassTypeIds = classTypeIdsStream.map(
				classTypeId -> {
					long newClassTypeId = classTypeId;

					newClassTypeId = MapUtil.getLong(
						ddmStructureIds, classTypeId, classTypeId);

					if (newClassTypeId != classTypeId) {
						return newClassTypeId;
					}

					newClassTypeId = MapUtil.getLong(
						dlFileEntryTypeIds, classTypeId, classTypeId);

					return newClassTypeId;
				}
			).toArray();

			unicodeProperties.setProperty(
				"classTypeIds" + className, StringUtil.merge(newClassTypeIds));
		}

		return unicodeProperties.toString();
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {
	}

	private void _addGroupMappingsElement(
			PortletDataContext portletDataContext, long[] groupIds)
		throws Exception {

		Element rootElement = portletDataContext.getExportDataRootElement();

		Element groupIdMappingsElement = rootElement.addElement(
			"group-id-mappings");

		for (long groupId : groupIds) {
			Element groupIdMappingElement = groupIdMappingsElement.addElement(
				"group-id-mapping");

			Group group = _groupLocalService.getGroup(groupId);

			long liveGroupId = group.getLiveGroupId();

			if (group.isStagedRemotely()) {
				liveGroupId = group.getRemoteLiveGroupId();
			}

			groupIdMappingElement.addAttribute(
				"group-id", String.valueOf(groupId));
			groupIdMappingElement.addAttribute(
				"live-group-id", String.valueOf(liveGroupId));
			groupIdMappingElement.addAttribute(
				"group-key", group.getGroupKey());
		}
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference(unbind = "-")
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}