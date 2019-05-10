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

import com.liferay.digital.signature.model.DSEmailNotification;
import com.liferay.digital.signature.model.DSParticipant;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface DSParticipantBuilder {

	public <T extends DSParticipant> T getDSParticipant();

	public DSParticipantBuilder setAccessCode(String accessCode);

	public DSParticipantBuilder setClientUserKey(String clientUserKey);

	public DSParticipantBuilder setCustomFieldKeys(String... customFieldKeys);

	public DSParticipantBuilder setDSEmailNotification(
		DSEmailNotification dsEmailNotification);

	public DSParticipantBuilder setNote(String note);

	public DSParticipantBuilder setParticipantKey(String participantKey);

	public DSParticipantBuilder setRoleName(String roleName);

}