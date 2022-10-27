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

package com.liferay.commerce.product.internal.exportimport.data.handler;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPAttachmentFileEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPAttachmentFileEntry> {

	public static final String[] CLASS_NAMES = {
		CPAttachmentFileEntry.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPAttachmentFileEntry cpAttachmentFileEntry) {
		return cpAttachmentFileEntry.getTitle();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext,
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(cpAttachmentFileEntry.getCreateDate());
		serviceContext.setModifiedDate(cpAttachmentFileEntry.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		Element cpAttachmentFileEntryElement =
			portletDataContext.getExportDataElement(cpAttachmentFileEntry);

		cpAttachmentFileEntryElement.addAttribute(
			"resource-class-name", cpAttachmentFileEntry.getClassName());

		AssetCategory assetCategory =
			_assetCategoryLocalService.getAssetCategory(
				cpAttachmentFileEntry.getClassPK());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpAttachmentFileEntry, assetCategory,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY);

		if (cpAttachmentFileEntry.getFileEntryId() > 0) {
			FileEntry fileEntry = _portletFileRepository.getPortletFileEntry(
				cpAttachmentFileEntry.getFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpAttachmentFileEntry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		portletDataContext.addClassedModel(
			cpAttachmentFileEntryElement,
			ExportImportPathUtil.getModelPath(cpAttachmentFileEntry),
			cpAttachmentFileEntry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		User user = _userLocalService.getUser(
			portletDataContext.getUserId(cpAttachmentFileEntry.getUserUuid()));

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpAttachmentFileEntry);

		CPAttachmentFileEntry importedCPAttachmentFileEntry = null;

		Date displayDate = cpAttachmentFileEntry.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR);
			displayDateMinute = displayCal.get(Calendar.MINUTE);

			if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
				displayDateHour += 12;
			}
		}

		boolean neverExpire = true;

		Date expirationDate = cpAttachmentFileEntry.getExpirationDate();

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationDateHour += 12;
			}

			neverExpire = false;
		}

		CPAttachmentFileEntry existingCPAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.
				fetchCPAttachmentFileEntryByUuidAndGroupId(
					cpAttachmentFileEntry.getUuid(),
					portletDataContext.getScopeGroupId());

		if (existingCPAttachmentFileEntry == null) {
			if (portletDataContext.isDataStrategyMirror()) {
				serviceContext.setUuid(cpAttachmentFileEntry.getUuid());
			}

			String externalReferenceCode =
				cpAttachmentFileEntry.getExternalReferenceCode();

			CPAttachmentFileEntry companyCPAttachmentFileEntry =
				_cpAttachmentFileEntryLocalService.
					fetchCPAttachmentFileEntryByExternalReferenceCode(
						cpAttachmentFileEntry.getCompanyId(),
						cpAttachmentFileEntry.getExternalReferenceCode());

			if (companyCPAttachmentFileEntry != null) {
				externalReferenceCode = null;
			}

			Element cpAttachmentFileEntryElement =
				portletDataContext.getImportDataStagedModelElement(
					cpAttachmentFileEntry);

			String className = cpAttachmentFileEntryElement.attributeValue(
				"resource-class-name");

			Map<Long, Long> newPrimaryKeysMap =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					className);

			long classPK = MapUtil.getLong(
				newPrimaryKeysMap, cpAttachmentFileEntry.getClassPK(),
				cpAttachmentFileEntry.getClassPK());

			importedCPAttachmentFileEntry =
				_cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
					externalReferenceCode, cpAttachmentFileEntry.getUserId(),
					portletDataContext.getScopeGroupId(),
					cpAttachmentFileEntry.getClassNameId(), classPK,
					cpAttachmentFileEntry.getFileEntryId(),
					cpAttachmentFileEntry.isCDNEnabled(),
					cpAttachmentFileEntry.getCDNURL(), displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire,
					cpAttachmentFileEntry.getTitleMap(),
					cpAttachmentFileEntry.getJson(),
					cpAttachmentFileEntry.getPriority(),
					cpAttachmentFileEntry.getType(), serviceContext);
		}
		else {
			importedCPAttachmentFileEntry =
				_cpAttachmentFileEntryLocalService.updateCPAttachmentFileEntry(
					cpAttachmentFileEntry.getUserId(),
					existingCPAttachmentFileEntry.getCPAttachmentFileEntryId(),
					cpAttachmentFileEntry.getFileEntryId(),
					cpAttachmentFileEntry.isCDNEnabled(),
					cpAttachmentFileEntry.getCDNURL(), displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire,
					cpAttachmentFileEntry.getTitleMap(),
					cpAttachmentFileEntry.getJson(),
					cpAttachmentFileEntry.getPriority(),
					cpAttachmentFileEntry.getType(), serviceContext);
		}

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		importedCPAttachmentFileEntry.setFileEntryId(
			MapUtil.getLong(
				fileEntryIds, cpAttachmentFileEntry.getFileEntryId(), 0));

		importedCPAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.updateCPAttachmentFileEntry(
				importedCPAttachmentFileEntry);

		if ((existingCPAttachmentFileEntry != null) &&
			(existingCPAttachmentFileEntry.getFileEntryId() != 0) &&
			(cpAttachmentFileEntry.getFileEntryId() == 0)) {

			_portletFileRepository.deletePortletFileEntry(
				existingCPAttachmentFileEntry.getFileEntryId());
		}

		portletDataContext.importClassedModel(
			cpAttachmentFileEntry, importedCPAttachmentFileEntry);
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private UserLocalService _userLocalService;

}