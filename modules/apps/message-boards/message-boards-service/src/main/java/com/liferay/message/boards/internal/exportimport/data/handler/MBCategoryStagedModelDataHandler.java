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

package com.liferay.message.boards.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class MBCategoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBCategory> {

	public static final String[] CLASS_NAMES = {MBCategory.class.getName()};

	@Override
	public void deleteStagedModel(MBCategory category) throws PortalException {
		_mbCategoryLocalService.deleteCategory(category);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		MBCategory category = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (category != null) {
			deleteStagedModel(category);
		}
	}

	@Override
	public MBCategory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mbCategoryLocalService.fetchMBCategoryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MBCategory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mbCategoryLocalService.getMBCategoriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MBCategory>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(MBCategory category) {
		return category.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBCategory category)
		throws Exception {

		if ((category.getCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(category.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		if (category.getParentCategory() != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, category.getParentCategory(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element categoryElement = portletDataContext.getExportDataElement(
			category);

		portletDataContext.addClassedModel(
			categoryElement, ExportImportPathUtil.getModelPath(category),
			category);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBCategory category)
		throws Exception {

		long userId = portletDataContext.getUserId(category.getUserUuid());

		String emailAddress = null;
		String inProtocol = null;
		String inServerName = null;
		int inServerPort = 0;
		boolean inUseSSL = false;
		String inUserName = null;
		String inPassword = null;
		int inReadInterval = 0;
		String outEmailAddress = null;
		boolean outCustom = false;
		String outServerName = null;
		int outServerPort = 0;
		boolean outUseSSL = false;
		String outUserName = null;
		String outPassword = null;
		boolean allowAnonymous = false;
		boolean mailingListActive = false;

		// Parent category

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, category.getParentCategoryId(),
			category.getParentCategoryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			category);

		MBCategory importedCategory = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MBCategory existingCategory = fetchStagedModelByUuidAndGroupId(
				category.getUuid(), portletDataContext.getScopeGroupId());

			if (existingCategory == null) {
				serviceContext.setUuid(category.getUuid());

				importedCategory = _mbCategoryLocalService.addCategory(
					userId, parentCategoryId, category.getName(),
					category.getDescription(), category.getDisplayStyle(),
					emailAddress, inProtocol, inServerName, inServerPort,
					inUseSSL, inUserName, inPassword, inReadInterval,
					outEmailAddress, outCustom, outServerName, outServerPort,
					outUseSSL, outUserName, outPassword, allowAnonymous,
					mailingListActive, serviceContext);
			}
			else {
				importedCategory = _mbCategoryLocalService.updateCategory(
					existingCategory.getCategoryId(), parentCategoryId,
					category.getName(), category.getDescription(),
					category.getDisplayStyle(), emailAddress, inProtocol,
					inServerName, inServerPort, inUseSSL, inUserName,
					inPassword, inReadInterval, outEmailAddress, outCustom,
					outServerName, outServerPort, outUseSSL, outUserName,
					outPassword, allowAnonymous, mailingListActive, false,
					serviceContext);
			}
		}
		else {
			importedCategory = _mbCategoryLocalService.addCategory(
				userId, parentCategoryId, category.getName(),
				category.getDescription(), category.getDisplayStyle(),
				emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
				inUserName, inPassword, inReadInterval, outEmailAddress,
				outCustom, outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, allowAnonymous, mailingListActive, serviceContext);
		}

		portletDataContext.importClassedModel(category, importedCategory);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, MBCategory category)
		throws Exception {

		MBCategory existingCategory = fetchStagedModelByUuidAndGroupId(
			category.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingCategory == null) || !existingCategory.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			MBCategory.class.getName());

		if (trashHandler.isRestorable(existingCategory.getCategoryId())) {
			long userId = portletDataContext.getUserId(category.getUserUuid());

			trashHandler.restoreTrashEntry(
				userId, existingCategory.getCategoryId());
		}
	}

	@Reference(unbind = "-")
	protected void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {

		_mbCategoryLocalService = mbCategoryLocalService;
	}

	private MBCategoryLocalService _mbCategoryLocalService;

}