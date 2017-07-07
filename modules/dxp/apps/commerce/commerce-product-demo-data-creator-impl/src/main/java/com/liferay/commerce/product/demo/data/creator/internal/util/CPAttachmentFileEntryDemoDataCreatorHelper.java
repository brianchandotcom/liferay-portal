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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;

import java.io.InputStream;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPAttachmentFileEntryDemoDataCreatorHelper.class)
public class CPAttachmentFileEntryDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addAssetCategoryAttachmentFileEntries(
			long userId, long groupId, long categoryId, JSONArray jsonArray)
		throws Exception {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		long classeNameId = _portal.getClassNameId(AssetCategory.class);

		Folder folder = _cpAttachmentFileEntryLocalService.getAttachmentsFolder(
			userId, groupId, AssetCategory.class.getName(), categoryId);

		for (int i = 0; i < jsonArray.length(); i++) {
			String fileName = jsonArray.getString(i);

			Map<Locale, String> titleMap = Collections.singletonMap(
				Locale.US, fileName);

			String uniqueFileName = _portletFileRepository.getUniqueFileName(
				groupId, folder.getFolderId(), fileName);

			InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/commerce/product/demo/data/creator/internal" +
					"/dependencies/images/" + fileName);

			FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
				groupId, userId, AssetCategory.class.getName(), categoryId,
				CPConstants.SERVICE_NAME, folder.getFolderId(), inputStream,
				uniqueFileName, "image/png", true);

			createCPAttachmentFileEntry(
				userId, groupId, classeNameId, categoryId,
				fileEntry.getFileEntryId(), titleMap, null, 0,
				CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE);
		}
	}

	public void addCPDefinitionAttachmentFileEntries(
			long userId, long groupId, long cpDefinitionId, JSONArray jsonArray)
		throws Exception {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		long classeNameId = _portal.getClassNameId(CPDefinition.class);

		Folder folder = _cpAttachmentFileEntryLocalService.getAttachmentsFolder(
			userId, groupId, CPDefinition.class.getName(), cpDefinitionId);

		for (int i = 0; i < jsonArray.length(); i++) {
			String fileName = jsonArray.getString(i);

			Map<Locale, String> titleMap = Collections.singletonMap(
				Locale.US, fileName);

			String uniqueFileName = _portletFileRepository.getUniqueFileName(
				groupId, folder.getFolderId(), fileName);

			InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/commerce/product/demo/data/creator/internal" +
					"/dependencies/images/" + fileName);

			FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
				groupId, userId, CPDefinition.class.getName(), cpDefinitionId,
				CPConstants.SERVICE_NAME, folder.getFolderId(), inputStream,
				uniqueFileName, "image/jpeg", true);

			List<CPDefinitionOptionRel> cpDefinitionOptionRels =
				_cpOptionDemoDataCreatorHelper.getCPDefinitionOptionRels();
			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				_cpDefinitionOptionValueRelDemoDataCreatorHelper.
					getCPDefinitionOptionValueRels();

			JSONArray optionIdsJSONArray = JSONFactoryUtil.createJSONArray();

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					cpDefinitionOptionRels) {

				if (cpDefinitionOptionRel.getCPDefinitionId() ==
						cpDefinitionId) {

					long cpDefinitionOptionRelId =
						cpDefinitionOptionRel.getCPDefinitionOptionRelId();

					optionIdsJSONArray.put(cpDefinitionOptionRelId);

					for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
							cpDefinitionOptionValueRels) {

						if (cpDefinitionOptionValueRel.
								getCPDefinitionOptionRelId() ==
									cpDefinitionOptionRelId) {

							optionIdsJSONArray.put(
								cpDefinitionOptionValueRel.
									getCPDefinitionOptionValueRelId());

							StringBuilder sb = new StringBuilder(9);

							sb.append("[{\"cpDefinitionOptionRelId\":\"");
							sb.append(optionIdsJSONArray.get(0));
							sb.append(StringPool.QUOTE);
							sb.append(StringPool.COMMA);
							sb.append("\"cpDefinitionOptionValueRelId\"");
							sb.append(StringPool.COLON);
							sb.append(StringPool.QUOTE);
							sb.append(optionIdsJSONArray.get(1));
							sb.append("\"}]");

							createCPAttachmentFileEntry(
								userId, groupId, classeNameId, cpDefinitionId,
								fileEntry.getFileEntryId(), titleMap,
								sb.toString(), 0,
								CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE);
						}
						else {
							continue;
						}
					}
				}
				else {
					continue;
				}
			}
		}
	}

	public CPAttachmentFileEntry createCPAttachmentFileEntry(
			long userId, long groupId, long classNameId, long classPK,
			long fileEntryId, Map<Locale, String> titleMap, String json,
			int priority, int type)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH) - 1;
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH) + 1;
		int expirationDateDay = expirationCalendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);
		int expirationDateAmPm = expirationCalendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		return _cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
			classNameId, classPK, fileEntryId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, true, titleMap, json,
			priority, type, serviceContext);
	}

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionOptionValueRelDemoDataCreatorHelper
		_cpDefinitionOptionValueRelDemoDataCreatorHelper;

	@Reference
	private CPOptionDemoDataCreatorHelper _cpOptionDemoDataCreatorHelper;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

}