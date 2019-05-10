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

package com.liferay.digital.signature.model.builder;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface DSModelBuildersFactory {

	public AgentDSParticipantBuilder createAgentDSParticipantBuilder(
		String emailAddress, String name, int routingOrder);

	public CarbonCopyDSParticipantBuilder createCarbonCopyDSParticipantBuilder(
		String emailAddress, String name, int routingOrder);

	public CertifiedDeliveryDSParticipantBuilder
		createCertifiedDeliveryDSParticipantBuilder(
			String emailAddress, String name, int routingOrder);

	public DSDocumentBuilder createDSDocumentBuilder(
		String documentKey, String name);

	public DSEmailNotificationBuilder createDSEmailNotificationBuilder(
		String message, String subject);

	public DSSignaturePackageBuilder createDSSignatureRequestBuilder();

	public EditorDSParticipantBuilder createEditorDSParticipantBuilder(
		String emailAddress, String name, int routingOrder);

	public InPersonSignerDSParticipantBuilder
		createInPersonSignerDSParticipantBuilder(
			String hostEmailAddress, String hostName, int routingOrder,
			String signerEmailAddress, String signerName);

	public InPersonSignerNotaryDSParticipantBuilder
		createInPersonSignerNotaryDSParticipantBuilder(
			String emailAddress, String name, String notaryEmailAddress,
			String notaryName, String notaryParticipantKey, int routingOrder);

	public IntermediaryDSParticipantBuilder
		createIntermediaryDSParticipantBuilder(
			String emailAddress, String name, int routingOrder);

	public SealDSParticipantBuilder createSealDSParticipantBuilder(
		String emailAddress, String name, String participantKey,
		int routingOrder);

	public SignerDSParticipantBuilder createSignerDSParticipantBuilder(
		String emailAddress, int routingOrder, String name);

}