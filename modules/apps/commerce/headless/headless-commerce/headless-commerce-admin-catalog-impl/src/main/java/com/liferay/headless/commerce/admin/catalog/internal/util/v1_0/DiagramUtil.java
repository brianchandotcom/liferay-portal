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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.shop.by.diagram.constants.CSDiagramSettingsConstants;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Diagram;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class DiagramUtil {

	public static CSDiagramSetting addCSDiagramSetting(
			long companyId,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			long cpDefinitionId,
			CSDiagramSettingService csDiagramSettingService, Diagram diagram,
			long groupId, Locale locale,
			ServiceContextHelper serviceContextHelper,
			UniqueFileNameProvider uniqueFileNameProvider)
		throws Exception {

		diagram = _addOrUpdateDiagramImage(
			ClassNameLocalServiceUtil.getClassNameId(
				CPDefinition.class.getName()),
			cpDefinitionId, companyId, cpAttachmentFileEntryService, diagram,
			groupId, locale, serviceContextHelper, uniqueFileNameProvider);

		return csDiagramSettingService.addCSDiagramSetting(
			cpDefinitionId, GetterUtil.getLong(diagram.getImageId()),
			GetterUtil.getString(diagram.getColor()),
			GetterUtil.getDouble(diagram.getRadius()),
			GetterUtil.getString(diagram.getType()));
	}

	public static CSDiagramSetting addOrUpdateCSDiagramSetting(
			long companyId,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			long cpDefinitionId,
			CSDiagramSettingService csDiagramSettingService, Diagram diagram,
			long groupId, Locale locale,
			ServiceContextHelper serviceContextHelper,
			UniqueFileNameProvider uniqueFileNameProvider)
		throws Exception {

		CSDiagramSetting csDiagramSetting =
			csDiagramSettingService.fetchCSDiagramSettingByCPDefinitionId(
				cpDefinitionId);

		if (csDiagramSetting == null) {
			return addCSDiagramSetting(
				companyId, cpAttachmentFileEntryService, cpDefinitionId,
				csDiagramSettingService, diagram, groupId, locale,
				serviceContextHelper, uniqueFileNameProvider);
		}

		return updateCSDiagramSetting(
			companyId, cpAttachmentFileEntryService, csDiagramSetting,
			csDiagramSettingService, diagram, groupId, locale,
			serviceContextHelper, uniqueFileNameProvider);
	}

	public static CSDiagramSetting updateCSDiagramSetting(
			long companyId,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			CSDiagramSetting csDiagramSetting,
			CSDiagramSettingService csDiagramSettingService, Diagram diagram,
			long groupId, Locale locale,
			ServiceContextHelper serviceContextHelper,
			UniqueFileNameProvider uniqueFileNameProvider)
		throws Exception {

		diagram = _addOrUpdateDiagramImage(
			ClassNameLocalServiceUtil.getClassNameId(
				CPDefinition.class.getName()),
			csDiagramSetting.getCPDefinitionId(), companyId,
			cpAttachmentFileEntryService, diagram, groupId, locale,
			serviceContextHelper, uniqueFileNameProvider);

		return csDiagramSettingService.updateCSDiagramSetting(
			csDiagramSetting.getCSDiagramSettingId(),
			GetterUtil.get(
				diagram.getImageId(),
				csDiagramSetting.getCPAttachmentFileEntryId()),
			GetterUtil.getString(
				diagram.getColor(), csDiagramSetting.getColor()),
			GetterUtil.getDouble(
				diagram.getRadius(), csDiagramSetting.getRadius()),
			GetterUtil.getString(
				diagram.getType(), csDiagramSetting.getType()));
	}

	private static Diagram _addOrUpdateDiagramImage(
			long classNameId, long classPK, long companyId,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			Diagram diagram, long groupId, Locale locale,
			ServiceContextHelper serviceContextHelper,
			UniqueFileNameProvider uniqueFileNameProvider)
		throws Exception {

		if (diagram.getAttachmentBase64() == null) {
			return diagram;
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			AttachmentUtil.addOrUpdateCPAttachmentFileEntry(
				cpAttachmentFileEntryService, uniqueFileNameProvider,
				diagram.getAttachmentBase64(), classNameId, classPK,
				CSDiagramSettingsConstants.TYPE_DIAGRAM,
				_getDiagramServiceContext(
					companyId, diagram, groupId, locale, serviceContextHelper));

		diagram.setImageId(cpAttachmentFileEntry.getCPAttachmentFileEntryId());

		return diagram;
	}

	private static ServiceContext _getDiagramServiceContext(
			long companyId, Diagram diagram, long groupId, Locale locale,
			ServiceContextHelper serviceContextHelper)
		throws Exception {

		ServiceContext serviceContext = serviceContextHelper.getServiceContext(
			groupId);

		AttachmentBase64 attachmentBase64 = diagram.getAttachmentBase64();

		if (attachmentBase64 == null) {
			return serviceContext;
		}

		Map<String, Serializable> expandoBridgeAttributes =
			CustomFieldsUtil.toMap(
				CPAttachmentFileEntry.class.getName(), companyId,
				attachmentBase64.getCustomFields(), locale);

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		return serviceContext;
	}

}