/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPAttachmentFileEntry",
	service = DTOConverter.class
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

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntry(
				(Long)dtoConverterContext.getId());

		Company company = _companyLocalService.getCompany(
			cpAttachmentFileEntry.getCompanyId());

		String portalURL = _portal.getPortalURL(
			company.getVirtualHostname(), _portal.getPortalServerPort(false),
			true);

		String downloadURL = _commerceMediaResolver.getDownloadURL(
			AccountConstants.ACCOUNT_ENTRY_ID_ADMIN,
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());

		return new Attachment() {
			{
				cdnEnabled = cpAttachmentFileEntry.isCDNEnabled();
				cdnURL = cpAttachmentFileEntry.getCDNURL();
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					CPAttachmentFileEntry.class.getName(),
					cpAttachmentFileEntry.getCPAttachmentFileEntryId(),
					cpAttachmentFileEntry.getCompanyId(),
					dtoConverterContext.getLocale());
				displayDate = cpAttachmentFileEntry.getDisplayDate();
				expirationDate = cpAttachmentFileEntry.getExpirationDate();
				externalReferenceCode =
					cpAttachmentFileEntry.getExternalReferenceCode();
				fileEntryId = cpAttachmentFileEntry.getFileEntryId();
				id = cpAttachmentFileEntry.getCPAttachmentFileEntryId();
				options = _getAttachmentOptions(cpAttachmentFileEntry);
				priority = cpAttachmentFileEntry.getPriority();
				src = portalURL + downloadURL;
				title = LanguageUtils.getLanguageIdMap(
					cpAttachmentFileEntry.getTitleMap());
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
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}