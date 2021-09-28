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

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Attachment;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false, property = "dto.class.name=CPAttachmentFileEntry",
	service = {AttachmentDTOConverter.class, DTOConverter.class}
)
public class AttachmentDTOConverter
	implements DTOConverter<CPAttachmentFileEntry, Attachment> {

	@Override
	public String getContentType() {
		return Attachment.class.getSimpleName();
	}

	@Override
	public Attachment toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AttachmentDTOConverterContext attachmentDTOConverterContext =
			(AttachmentDTOConverterContext)dtoConverterContext;

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntry(
				(Long)attachmentDTOConverterContext.getId());

		Company company = _companyLocalService.getCompany(
			cpAttachmentFileEntry.getCompanyId());

		String portalURL = company.getPortalURL(0);

		String downloadURL = _commerceMediaResolver.getDownloadURL(
			attachmentDTOConverterContext.getCommerceAccountId(),
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());

		return new Attachment() {
			{
				displayDate = cpAttachmentFileEntry.getDisplayDate();
				expirationDate = cpAttachmentFileEntry.getExpirationDate();
				id = cpAttachmentFileEntry.getCPAttachmentFileEntryId();
				options = _getAttachmentOptions(cpAttachmentFileEntry);
				priority = cpAttachmentFileEntry.getPriority();
				src = portalURL + downloadURL;
				title = cpAttachmentFileEntry.getTitle(
					LanguageUtil.getLanguageId(
						attachmentDTOConverterContext.getLocale()));
				type = cpAttachmentFileEntry.getType();
			}
		};
	}

	private Map<String, String> _getAttachmentOptions(
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		String json = cpAttachmentFileEntry.getJson();

		if (Validator.isNull(json)) {
			return Collections.emptyMap();
		}

		Map<String, String> options = new HashMap<>();

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (Object element : jsonArray) {
			JSONObject jsonObject = (JSONObject)element;

			if (!jsonObject.has("key")) {
				continue;
			}

			options.put(
				jsonObject.getString("key"), jsonObject.getString("value"));
		}

		return options;
	}

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}