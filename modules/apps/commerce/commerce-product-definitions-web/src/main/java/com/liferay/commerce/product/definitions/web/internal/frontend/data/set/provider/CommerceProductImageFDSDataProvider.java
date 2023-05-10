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

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.provider;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.media.CommerceMediaResolverUtil;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductFDSNames;
import com.liferay.commerce.product.definitions.web.internal.model.ProductMedia;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "fds.data.provider.key=" + CommerceProductFDSNames.PRODUCT_IMAGES,
	service = FDSDataProvider.class
)
public class CommerceProductImageFDSDataProvider
	implements FDSDataProvider<ProductMedia> {

	@Override
	public List<ProductMedia> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<ProductMedia> productMedia = new ArrayList<>();

		Locale locale = _portal.getLocale(httpServletRequest);

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
				_portal.getClassNameId(CPDefinition.class), cpDefinitionId,
				fdsKeywords.getKeywords(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE,
				WorkflowConstants.STATUS_ANY, fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition());

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			long cpAttachmentFileEntryId =
				cpAttachmentFileEntry.getCPAttachmentFileEntryId();

			String title = cpAttachmentFileEntry.getTitle(
				_language.getLanguageId(locale));

			String extension = StringPool.BLANK;

			FileEntry fileEntry = cpAttachmentFileEntry.fetchFileEntry();

			if (fileEntry != null) {
				extension = HtmlUtil.escape(fileEntry.getExtension());
			}

			Date modifiedDate = cpAttachmentFileEntry.getModifiedDate();

			String modifiedDateDescription = _language.getTimeDescription(
				httpServletRequest,
				System.currentTimeMillis() - modifiedDate.getTime(), true);

			String statusDisplayStyle = StringPool.BLANK;

			if (cpAttachmentFileEntry.getStatus() ==
					WorkflowConstants.STATUS_APPROVED) {

				statusDisplayStyle = "success";
			}

			productMedia.add(
				new ProductMedia(
					cpAttachmentFileEntryId,
					new ImageField(
						title, "rounded", "lg",
						CommerceMediaResolverUtil.getThumbnailURL(
							AccountConstants.ACCOUNT_ENTRY_ID_ADMIN,
							cpAttachmentFileEntryId)),
					title, extension, cpAttachmentFileEntry.getPriority(),
					_language.format(
						httpServletRequest, "x-ago", modifiedDateDescription,
						false),
					new LabelField(
						statusDisplayStyle,
						_language.get(
							httpServletRequest,
							WorkflowConstants.getStatusLabel(
								cpAttachmentFileEntry.getStatus())))));
		}

		return productMedia;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		return _cpAttachmentFileEntryService.getCPAttachmentFileEntriesCount(
			_portal.getClassNameId(CPDefinition.class), cpDefinitionId,
			fdsKeywords.getKeywords(),
			CPAttachmentFileEntryConstants.TYPE_IMAGE,
			WorkflowConstants.STATUS_ANY);
	}

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}