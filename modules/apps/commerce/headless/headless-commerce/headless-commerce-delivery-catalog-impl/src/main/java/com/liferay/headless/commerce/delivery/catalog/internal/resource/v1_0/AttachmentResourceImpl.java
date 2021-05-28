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

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.AttachmentDTOConverter;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.AttachmentDTOConverterContext;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.AttachmentResource;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/attachment.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AttachmentResource.class, NestedFieldSupport.class}
)
public class AttachmentResourceImpl
	extends BaseAttachmentResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = Product.class, value = "attachments")
	@Override
	public Page<Attachment> getChannelProductAttachmentsPage(
			Long channelId, @NestedFieldId("productId") Long productId,
			Long accountId, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + productId);
		}

		return _getAttachmentPage(
			_commerceChannelLocalService.getCommerceChannel(channelId),
			cpDefinition, accountId, CPAttachmentFileEntryConstants.TYPE_OTHER,
			pagination);
	}

	@NestedField(parentClass = Product.class, value = "images")
	@Override
	public Page<Attachment> getChannelProductImagesPage(
			Long channelId, @NestedFieldId("productId") Long productId,
			Long accountId, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + productId);
		}

		return _getAttachmentPage(
			_commerceChannelLocalService.getCommerceChannel(channelId),
			cpDefinition, accountId, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			pagination);
	}

	private Long _getAccountId(
			Long accountId, long commerceChannelGroupId,
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		int countUserCommerceAccounts =
			_commerceAccountHelper.countUserCommerceAccounts(
				cpAttachmentFileEntry.getUserId(), commerceChannelGroupId);

		if (countUserCommerceAccounts > 1) {
			if (accountId == null) {
				throw new NoSuchAccountException();
			}
		}
		else {
			long[] commerceAccountIds =
				_commerceAccountHelper.getUserCommerceAccountIds(
					cpAttachmentFileEntry.getUserId(), commerceChannelGroupId);

			if (commerceAccountIds.length == 0) {
				CommerceAccount commerceAccount =
					_commerceAccountLocalService.getGuestCommerceAccount(
						cpAttachmentFileEntry.getCompanyId());

				commerceAccountIds = new long[] {
					commerceAccount.getCommerceAccountId()
				};
			}

			return commerceAccountIds[0];
		}

		return accountId;
	}

	private Page<Attachment> _getAttachmentPage(
			CommerceChannel commerceChannel, CPDefinition cpDefinition,
			long accountId, int type, Pagination pagination)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClass()),
				cpDefinition.getCPDefinitionId(), type,
				WorkflowConstants.STATUS_APPROVED,
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntriesCount(
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClass()),
				cpDefinition.getCPDefinitionId(), type,
				WorkflowConstants.STATUS_APPROVED);

		return Page.of(
			_toAttachments(cpAttachmentFileEntries, commerceChannel, accountId),
			pagination, totalItems);
	}

	private List<Attachment> _toAttachments(
			List<CPAttachmentFileEntry> cpAttachmentFileEntries,
			CommerceChannel commerceChannel, Long accountId)
		throws Exception {

		List<Attachment> attachments = new ArrayList<>();

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			attachments.add(
				_attachmentDTOConverter.toDTO(
					new AttachmentDTOConverterContext(
						cpAttachmentFileEntry.getCPAttachmentFileEntryId(),
						contextAcceptLanguage.getPreferredLocale(),
						_getAccountId(
							accountId, commerceChannel.getGroupId(),
							cpAttachmentFileEntry))));
		}

		return attachments;
	}

	@Reference
	private AttachmentDTOConverter _attachmentDTOConverter;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

}