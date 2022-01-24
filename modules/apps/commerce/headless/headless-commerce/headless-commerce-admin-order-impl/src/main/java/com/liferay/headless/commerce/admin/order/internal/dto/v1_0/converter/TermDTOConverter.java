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

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Status;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Term;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.term.model.CommerceTermEntry",
	service = {DTOConverter.class, TermDTOConverter.class}
)
public class TermDTOConverter implements DTOConverter<CommerceTermEntry, Term> {

	@Override
	public String getContentType() {
		return Term.class.getSimpleName();
	}

	@Override
	public Term toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.getCommerceTermEntry(
				(Long)dtoConverterContext.getId());

		return new Term() {
			{
				actions = dtoConverterContext.getActions();
				active = commerceTermEntry.isActive();
				description = commerceTermEntry.getLanguageIdToDescriptionMap();
				displayDate = commerceTermEntry.getDisplayDate();
				expirationDate = commerceTermEntry.getExpirationDate();
				externalReferenceCode =
					commerceTermEntry.getExternalReferenceCode();
				id = commerceTermEntry.getCommerceTermEntryId();
				label = commerceTermEntry.getLanguageIdToLabelMap();
				name = commerceTermEntry.getName();
				priority = commerceTermEntry.getPriority();
				type = commerceTermEntry.getType();
				typeLocalized = LanguageUtil.get(
					LanguageResources.getResourceBundle(
						dtoConverterContext.getLocale()),
					commerceTermEntry.getType());
				typeSettings = commerceTermEntry.getTypeSettings();

				setWorkflowStatusInfo(
					() -> new Status() {
						{
							code = commerceTermEntry.getStatus();
							label = WorkflowConstants.getStatusLabel(
								commerceTermEntry.getStatus());
							label_i18n = LanguageUtil.get(
								LanguageResources.getResourceBundle(
									dtoConverterContext.getLocale()),
								WorkflowConstants.getStatusLabel(
									commerceTermEntry.getStatus()));
						}
					});
			}
		};
	}

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

}