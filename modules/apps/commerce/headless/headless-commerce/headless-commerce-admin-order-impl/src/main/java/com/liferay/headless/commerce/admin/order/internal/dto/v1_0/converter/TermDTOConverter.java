/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Status;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Term;
import com.liferay.portal.kernel.language.Language;
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
	property = "dto.class.name=com.liferay.commerce.term.model.CommerceTermEntry",
	service = DTOConverter.class
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
				typeLocalized = _language.get(
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
							label_i18n = _language.get(
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

	@Reference
	private Language _language;

}