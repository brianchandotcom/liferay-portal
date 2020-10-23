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

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.media.CommerceMediaResolverUtil;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.ProductMedia;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Sort;
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
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_IMAGES,
	service = ClayDataSetDataProvider.class
)
public class CommerceProductImageDataSetDataProvider
	implements ClayDataSetDataProvider<ProductMedia> {

	@Override
	public List<ProductMedia> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<ProductMedia> productMedia = new ArrayList<>();

		Locale locale = _portal.getLocale(httpServletRequest);

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
				_portal.getClassNameId(CPDefinition.class), cpDefinitionId,
				CPAttachmentFileEntryConstants.TYPE_IMAGE,
				WorkflowConstants.STATUS_ANY, pagination.getStartPosition(),
				pagination.getEndPosition());

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			long cpAttachmentFileEntryId =
				cpAttachmentFileEntry.getCPAttachmentFileEntryId();

			String title = cpAttachmentFileEntry.getTitle(
				LanguageUtil.getLanguageId(locale));

			FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

			Date modifiedDate = cpAttachmentFileEntry.getModifiedDate();

			String modifiedDateDescription = LanguageUtil.getTimeDescription(
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
						CommerceMediaResolverUtil.getThumbnailUrl(
							cpAttachmentFileEntryId)),
					title, fileEntry.getExtension(),
					cpAttachmentFileEntry.getPriority(),
					LanguageUtil.format(
						httpServletRequest, "x-ago", modifiedDateDescription,
						false),
					new LabelField(
						statusDisplayStyle,
						LanguageUtil.get(
							httpServletRequest,
							WorkflowConstants.getStatusLabel(
								cpAttachmentFileEntry.getStatus())))));
		}

		return productMedia;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		return _cpAttachmentFileEntryService.getCPAttachmentFileEntriesCount(
			_portal.getClassNameId(CPDefinition.class), cpDefinitionId,
			CPAttachmentFileEntryConstants.TYPE_IMAGE,
			WorkflowConstants.STATUS_ANY);
	}

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private Portal _portal;

}