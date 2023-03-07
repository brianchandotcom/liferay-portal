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

package com.liferay.headless.commerce.delivery.catalog.v2.internal.dto.v2_0.converter;

import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.headless.commerce.delivery.catalog.v2.dto.v2_0.Image;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
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
 * @author Crescenzo Rega
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.delivery.catalog.v2.dto.v2_0.Image",
	service = {DTOConverter.class, ImageDTOConverter.class}
)
public class ImageDTOConverter
	implements DTOConverter<CPAttachmentFileEntry, Image> {

	@Override
	public String getContentType() {
		return Image.class.getSimpleName();
	}

	@Override
	public Image toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		ImageDTOConverterContext imageDTOConverterContext =
			(ImageDTOConverterContext)dtoConverterContext;

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntry(
				(Long)imageDTOConverterContext.getId());

		Company company = _companyLocalService.getCompany(
			cpAttachmentFileEntry.getCompanyId());

		String portalURL = company.getPortalURL(0);

		String downloadURL = _commerceMediaResolver.getDownloadURL(
			imageDTOConverterContext.getCommerceAccountId(),
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());

		return new Image() {
			{
				displayDate = cpAttachmentFileEntry.getDisplayDate();
				expirationDate = cpAttachmentFileEntry.getExpirationDate();
				id = cpAttachmentFileEntry.getCPAttachmentFileEntryId();
				options = _getImageOptions(cpAttachmentFileEntry);
				priority = cpAttachmentFileEntry.getPriority();
				src = portalURL + downloadURL;
				title = cpAttachmentFileEntry.getTitle(
					_language.getLanguageId(
						imageDTOConverterContext.getLocale()));
				type = cpAttachmentFileEntry.getType();
			}
		};
	}

	private Map<String, String> _getImageOptions(
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

	@Reference
	private Language _language;

}