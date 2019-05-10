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

package com.liferay.digital.signature.model.field.builder;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface DSFieldBuildersFactory {

	public ApproveDSFieldBuilder createApproveDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public CheckboxDSFieldBuilder createCheckboxDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public CompanyNameDSFieldBuilder createCompanyNameDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public DateDSFieldBuilder createDateDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public DateSignedDSFieldBuilder createDateSignedDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public DeclineDSFieldBuilder createDeclineDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public EmailAddressDSFieldBuilder createEmailAddressDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public FirstNameDSFieldBuilder createFirstNameDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public FormulaDSFieldBuilder createFormulaDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public FullNameDSFieldBuilder createFullNameDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public InitialHereDSFieldBuilder createInitialHereDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public LastNameDSFieldBuilder createLastNameDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public ListDSFieldBuilder createListDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public NotarizeDSFieldBuilder createNotarizeDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public NoteDSFieldBuilder createNoteDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public NumberDSFieldBuilder createNumberDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public ParticipantEmailAddressDSFieldBuilder
		createParticipantEmailAddressDSFieldBuilder(
			String documentKey, String fieldKey, Integer pageNumber);

	public PostalCodeDSFieldBuilder createPostalCodeDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public RadioDSFieldBuilder createRadioDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public RadioGroupDSFieldBuilder createRadioGroupDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public SignaturePackageKeyDSFieldBuilder
		createSignaturePackageKeyDSFieldBuilder(
			String documentKey, String fieldKey, Integer pageNumber);

	public SignerAttachmentDSFieldBuilder createSignerAttachmentDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public SignHereDSFieldBuilder createSignHereDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public SocialSecurityNumberDSFieldBuilder
		createSocialSecurityNumberDSFieldBuilder(
			String documentKey, String fieldKey, Integer pageNumber);

	public TextDSFieldBuilder createTextDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public TitleDSFieldBuilder createTitleDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

	public ViewDSFieldBuilder createViewDSFieldBuilder(
		String documentKey, String fieldKey, Integer pageNumber);

}