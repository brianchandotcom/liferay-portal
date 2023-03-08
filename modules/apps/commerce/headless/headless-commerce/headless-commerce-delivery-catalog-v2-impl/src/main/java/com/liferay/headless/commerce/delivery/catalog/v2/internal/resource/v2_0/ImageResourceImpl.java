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

package com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0;

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
import com.liferay.headless.commerce.delivery.catalog.v2.dto.v2_0.Image;
import com.liferay.headless.commerce.delivery.catalog.v2.dto.v2_0.Product;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.dto.v2_0.converter.ImageDTOConverter;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.dto.v2_0.converter.ImageDTOConverterContext;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.ImageResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Crescenzo Rega
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/image.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {ImageResource.class, NestedFieldSupport.class}
)
@CTAware
public class ImageResourceImpl
	extends BaseImageResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = Product.class, value = "images")
	@Override
	public Page<Image> getChannelIdProductImagesPage(
			Long channelId, @NestedFieldId("productId") Long productId,
			Long accountId, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + productId);
		}

		return _getImagePage(
			cpDefinition,
			_getAccountId(
				accountId,
				_commerceChannelLocalService.getCommerceChannel(channelId)),
			CPAttachmentFileEntryConstants.TYPE_IMAGE, pagination);
	}

	private Long _getAccountId(Long accountId, CommerceChannel commerceChannel)
		throws Exception {

		int countUserCommerceAccounts =
			_commerceAccountHelper.countUserCommerceAccounts(
				contextUser.getUserId(), commerceChannel.getGroupId());

		if (countUserCommerceAccounts > 1) {
			if (accountId == null) {
				throw new NoSuchAccountException();
			}
		}
		else {
			long[] commerceAccountIds =
				_commerceAccountHelper.getUserCommerceAccountIds(
					contextUser.getUserId(), commerceChannel.getGroupId());

			if (commerceAccountIds.length == 0) {
				CommerceAccount commerceAccount =
					_commerceAccountLocalService.getGuestCommerceAccount(
						contextCompany.getCompanyId());

				commerceAccountIds = new long[] {
					commerceAccount.getCommerceAccountId()
				};
			}

			return commerceAccountIds[0];
		}

		return accountId;
	}

	private Page<Image> _getImagePage(
			CPDefinition cpDefinition, long accountId, int type,
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
					_classNameLocalService.getClassNameId(
						cpDefinition.getModelClass()),
					cpDefinition.getCPDefinitionId(), type,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition()),
				cpAttachmentFileEntry -> _toImage(
					accountId, cpAttachmentFileEntry)),
			pagination,
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntriesCount(
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClass()),
				cpDefinition.getCPDefinitionId(), type,
				WorkflowConstants.STATUS_APPROVED));
	}

	private Image _toImage(
			long accountId, CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		return _imageDTOConverter.toDTO(
			new ImageDTOConverterContext(
				cpAttachmentFileEntry.getCPAttachmentFileEntryId(),
				contextAcceptLanguage.getPreferredLocale(), accountId));
	}

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

	@Reference
	private ImageDTOConverter _imageDTOConverter;

}