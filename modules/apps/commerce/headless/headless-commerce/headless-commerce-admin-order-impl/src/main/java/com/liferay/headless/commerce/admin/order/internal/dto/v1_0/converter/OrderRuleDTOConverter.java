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

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Status;
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
	property = "dto.class.name=com.liferay.commerce.order.rule.model.COREntry",
	service = {DTOConverter.class, OrderRuleDTOConverter.class}
)
public class OrderRuleDTOConverter
	implements DTOConverter<COREntry, OrderRule> {

	@Override
	public String getContentType() {
		return OrderRule.class.getSimpleName();
	}

	@Override
	public OrderRule toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		COREntry corEntry = _corEntryService.getCOREntry(
			(Long)dtoConverterContext.getId());

		return new OrderRule() {
			{
				actions = dtoConverterContext.getActions();
				active = corEntry.isActive();
				description = corEntry.getDescription();
				displayDate = corEntry.getDisplayDate();
				expirationDate = corEntry.getExpirationDate();
				externalReferenceCode = corEntry.getExternalReferenceCode();
				id = corEntry.getCOREntryId();
				name = corEntry.getName();
				type = corEntry.getType();
				typeSettings = corEntry.getTypeSettings();
				workflowStatusInfo = _toStatus(
					WorkflowConstants.getStatusLabel(corEntry.getStatus()),
					_language.get(
						LanguageResources.getResourceBundle(
							dtoConverterContext.getLocale()),
						WorkflowConstants.getStatusLabel(corEntry.getStatus())),
					corEntry.getStatus());
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
	private COREntryService _corEntryService;

	@Reference
	private Language _language;

}