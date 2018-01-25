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
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Portal;

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
				CPAttachmentFileEntryConstants.TYPE_IMAGE);
		}
	}

	public void addCPDefinitionAttachmentFileEntries(
			long userId, long groupId, long cpDefinitionId,
			JSONArray cpAttachmentFileEntriesJSONArray)
		throws Exception {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		long classeNameId = _portal.getClassNameId(CPDefinition.class);

		Folder folder = _cpAttachmentFileEntryLocalService.getAttachmentsFolder(
			userId, groupId, CPDefinition.class.getName(), cpDefinitionId);

		for (int i = 0; i < cpAttachmentFileEntriesJSONArray.length(); i++) {
			String fileName = cpAttachmentFileEntriesJSONArray.getString(i);

			Map<Locale, String> titleMap = Collections.singletonMap(
				Locale.US, fileName);

			int priority = i + 1;

			String uniqueFileName = _portletFileRepository.getUniqueFileName(
				groupId, folder.getFolderId(), fileName);

			InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/commerce/product/demo/data/creator/internal" +
					"/dependencies/images/" + fileName);

			FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
				groupId, userId, CPDefinition.class.getName(), cpDefinitionId,
				CPConstants.SERVICE_NAME, folder.getFolderId(), inputStream,
				uniqueFileName, "image/jpeg", true);

			Map<Long, List<CPDefinitionOptionRel>> cpDefinitionOptionRelsMap =
				_cpOptionDemoDataCreatorHelper.getCPDefinitionOptionRels();
			Map<Long, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionValueRelsMap =
					_cpDefinitionOptionValueRelDemoDataCreatorHelper.
						getCPDefinitionOptionValueRels();

			List<CPDefinitionOptionRel> cpDefinitionOptionRels =
				cpDefinitionOptionRelsMap.get(cpDefinitionId);

			JSONArray optionIdsJSONArray = _jsonFactory.createJSONArray();
			JSONArray jsonArray = _jsonFactory.createJSONArray();

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					cpDefinitionOptionRels) {

				long cpDefinitionOptionRelId =
					cpDefinitionOptionRel.getCPDefinitionOptionRelId();

				optionIdsJSONArray.put(cpDefinitionOptionRelId);

				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
					cpDefinitionOptionValueRelsMap.get(cpDefinitionOptionRelId);

				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
						cpDefinitionOptionValueRels) {

					if (fileName.contains(
							cpDefinitionOptionValueRel.getKey())) {

						optionIdsJSONArray.put(
							cpDefinitionOptionValueRel.
								getCPDefinitionOptionValueRelId());

						JSONObject jsonObject = _jsonFactory.createJSONObject();

						jsonObject.put("key", optionIdsJSONArray.get(0));

						JSONArray valueJSONArray =
							_jsonFactory.createJSONArray();

						Object object = optionIdsJSONArray.get(1);

						valueJSONArray.put(object.toString());

						jsonObject.put("value", valueJSONArray);

						jsonArray.put(jsonObject);
					}
				}
			}

			createCPAttachmentFileEntry(
				userId, groupId, classeNameId, cpDefinitionId,
				fileEntry.getFileEntryId(), titleMap, jsonArray.toString(),
				priority, CPAttachmentFileEntryConstants.TYPE_IMAGE);
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

		displayCalendar.add(Calendar.YEAR, -1);

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
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
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

}