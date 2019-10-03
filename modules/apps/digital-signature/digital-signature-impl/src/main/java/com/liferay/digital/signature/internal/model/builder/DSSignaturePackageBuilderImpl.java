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

package com.liferay.digital.signature.internal.model.builder;

import com.liferay.digital.signature.internal.model.DSSignaturePackageImpl;
import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSEmailNotification;
import com.liferay.digital.signature.model.DSParticipant;
import com.liferay.digital.signature.model.DSParticipantRole;
import com.liferay.digital.signature.model.DSSignaturePackage;
import com.liferay.digital.signature.model.builder.DSSignaturePackageBuilder;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class DSSignaturePackageBuilderImpl
	implements DSSignaturePackageBuilder {

	public DSSignaturePackageBuilderImpl(PortalUUID portalUUID) {
		_portalUUID = portalUUID;
	}

	@Override
	public DSSignaturePackageBuilder addDSDocuments(
		Collection<DSDocument> dsDocuments) {

		_dsDocuments.addAll(dsDocuments);

		return this;
	}

	@Override
	public DSSignaturePackageBuilder addDSDocuments(DSDocument... dsDocuments) {
		Collections.addAll(_dsDocuments, dsDocuments);

		return this;
	}

	@Override
	public DSSignaturePackageBuilder addDSParticipants(
		Collection<DSParticipant> dsParticipants) {

		dsParticipants.forEach(this::addDSParticipant);

		return this;
	}

	@Override
	public DSSignaturePackageBuilder addDSParticipants(
		DSParticipant... dsParticipants) {

		for (final DSParticipant dsParticipant : dsParticipants) {
			addDSParticipant(dsParticipant);
		}

		return this;
	}

	@Override
	public DSSignaturePackage getDSSignatureRequest() {
		DSSignaturePackageImpl dsSignaturePackageImpl =
			new DSSignaturePackageImpl();

		dsSignaturePackageImpl.setAuthoritative(_authoritative);
		dsSignaturePackageImpl.setDSEmailNotification(_dsEmailNotification);
		dsSignaturePackageImpl.addDSDocuments(_dsDocuments);
		dsSignaturePackageImpl.setDSParticipantMap(_dsParticipantsMap);

		if (Validator.isNull(_dsSignatureRequestKey)) {
			_dsSignatureRequestKey = _portalUUID.generate();
		}

		dsSignaturePackageImpl.setDSSignatureRequestKey(_dsSignatureRequestKey);

		return dsSignaturePackageImpl;
	}

	@Override
	public DSSignaturePackageBuilder setAuthoritative(Boolean authoritative) {
		_authoritative = authoritative;

		return this;
	}

	@Override
	public DSSignaturePackageBuilder setDSEmailNotification(
		DSEmailNotification dsEmailNotification) {

		_dsEmailNotification = dsEmailNotification;

		return this;
	}

	@Override
	public DSSignaturePackageBuilder setDSSignatureRequestKey(
		String dsSignatureRequestKey) {

		_dsSignatureRequestKey = dsSignatureRequestKey;

		return this;
	}

	protected void addDSParticipant(DSParticipant dsParticipant) {
		Collection<DSParticipant> dsParticipants =
			_dsParticipantsMap.computeIfAbsent(
				dsParticipant.getDSParticipantRole(),
				participantRole -> new ArrayList<>());

		if (Validator.isNull(dsParticipant.getParticipantKey())) {
			BeanPropertiesUtil.setPropertySilent(
				dsParticipant, "participantKey",
				String.valueOf(_participantCounter));
		}

		dsParticipants.add(dsParticipant);

		_participantCounter++;
	}

	private Boolean _authoritative;
	private final Set<DSDocument> _dsDocuments = new HashSet<>();
	private DSEmailNotification _dsEmailNotification;
	private final Map<DSParticipantRole, Collection<DSParticipant>>
		_dsParticipantsMap = new HashMap<>();
	private String _dsSignatureRequestKey;
	private int _participantCounter;
	private final PortalUUID _portalUUID;

}