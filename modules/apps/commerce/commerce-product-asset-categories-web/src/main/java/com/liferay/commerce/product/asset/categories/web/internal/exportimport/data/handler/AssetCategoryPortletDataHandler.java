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

package com.liferay.commerce.product.asset.categories.web.internal.exportimport.data.handler;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.product.asset.categories.web.internal.constants.CommerceProductAssetCategoriesPortletKeys;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceProductAssetCategoriesPortletKeys.ASSET_CATEGORIES_ADMIN,
		"service.ranking:Integer=100"
	},
	service = PortletDataHandler.class
)
public class AssetCategoryPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "asset_category";

	public static final String SCHEMA_VERSION = "4.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() throws PortletDataException {
		setDataAlwaysStaged(_portletDataHandler.isDataAlwaysStaged());
		setDeletionSystemEventStagedModelTypes(
			_portletDataHandler.getDeletionSystemEventStagedModelTypes());
		setExportControls(_portletDataHandler.getExportControls());
		setPublishToLiveByDefault(
			_portletDataHandler.isPublishToLiveByDefault());
		setRank(_portletDataHandler.getRank());
		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return _portletDataHandler.deleteData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Document document = SAXReaderUtil.read(
			_portletDataHandler.exportData(
				portletDataContext, portletId, portletPreferences));

		Element rootElement = document.getRootElement();

		portletDataContext.setExportDataRootElement(rootElement);

		Element assetCategoriesElement =
			portletDataContext.getExportDataGroupElement(AssetCategory.class);

		List<Element> assetCategoryElements = assetCategoriesElement.elements();

		for (Element assetCategoryElement : assetCategoryElements) {
			long groupId = GetterUtil.getLong(
				assetCategoryElement.attributeValue("group-id"));
			String uuid = GetterUtil.getString(
				assetCategoryElement.attributeValue("uuid"));

			AssetCategory assetCategory =
				_assetCategoryLocalService.fetchAssetCategoryByUuidAndGroupId(
					uuid, groupId);

			if (assetCategory == null) {
				continue;
			}

			List<FriendlyURLEntry> friendlyURLEntries =
				_friendlyURLEntryLocalService.getFriendlyURLEntries(
					assetCategory.getGroupId(),
					_portal.getClassNameId(AssetCategory.class),
					assetCategory.getCategoryId());

			for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, friendlyURLEntry);

				portletDataContext.addReferenceElement(
					friendlyURLEntry,
					portletDataContext.getExportDataElement(friendlyURLEntry),
					assetCategory, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
					false);
			}

			List<CPAttachmentFileEntry> cpAttachmentFileEntries =
				_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
					_portal.getClassNameId(AssetCategory.class),
					assetCategory.getCategoryId(),
					CPAttachmentFileEntryConstants.TYPE_IMAGE,
					WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			for (CPAttachmentFileEntry cpAttachmentFileEntry :
					cpAttachmentFileEntries) {

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, cpAttachmentFileEntry);
			}
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletPreferences = _portletDataHandler.importData(
			portletDataContext, portletId, portletPreferences, data);

		Element friendlyURLEntriesElement =
			portletDataContext.getImportDataGroupElement(
				FriendlyURLEntry.class);

		for (Element friendlyURLEntryElement :
				friendlyURLEntriesElement.elements()) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, friendlyURLEntryElement);
		}

		Element cpAttachmentFileEntriesElement =
			portletDataContext.getImportDataGroupElement(
				CPAttachmentFileEntry.class);

		for (Element cpAttachmentFileEntryElement :
				cpAttachmentFileEntriesElement.elements()) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, cpAttachmentFileEntryElement);
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		_portletDataHandler.prepareManifestSummary(
			portletDataContext, portletPreferences);
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(component.name=com.liferay.asset.categories.admin.web.internal.exportimport.data.handler.AssetCategoryPortletDataHandler)"
	)
	private PortletDataHandler _portletDataHandler;

}