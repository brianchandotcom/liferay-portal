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

package com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderType;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Status;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.model.CommerceOrderType",
	service = {DTOConverter.class, OrderTypeDTOConverter.class}
)
public class OrderTypeDTOConverter
	implements DTOConverter<CommerceOrderType, OrderType> {

	@Override
	public String getContentType() {
		return OrderType.class.getSimpleName();
	}

	@Override
	public OrderType toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.getCommerceOrderType(
				(Long)dtoConverterContext.getId());

		ExpandoBridge expandoBridge = commerceOrderType.getExpandoBridge();

		return new OrderType() {
			{
				actions = dtoConverterContext.getActions();
				active = commerceOrderType.isActive();
				customFields = expandoBridge.getAttributes();
				description = LanguageUtils.getLanguageIdMap(
					commerceOrderType.getDescriptionMap());
				displayDate = commerceOrderType.getDisplayDate();
				displayOrder = commerceOrderType.getDisplayOrder();
				expirationDate = commerceOrderType.getExpirationDate();
				externalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				id = commerceOrderType.getCommerceOrderTypeId();
				name = LanguageUtils.getLanguageIdMap(
					commerceOrderType.getNameMap());
				workflowStatusInfo = _toStatus(
					WorkflowConstants.getStatusLabel(
						commerceOrderType.getStatus()),
					_language.get(
						LanguageResources.getResourceBundle(
							dtoConverterContext.getLocale()),
						WorkflowConstants.getStatusLabel(
							commerceOrderType.getStatus())),
					commerceOrderType.getStatus());
			}
		};
	}

	private Status _toStatus(
		String orderTypeStatusLabel, String orderTypeStatusLabelI18n,
		int statusCode) {

		return new Status() {
			{
				code = statusCode;
				label = orderTypeStatusLabel;
				label_i18n = orderTypeStatusLabelI18n;
			}
		};
	}

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private Language _language;

}