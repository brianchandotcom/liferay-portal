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

package com.liferay.digital.signature.manager;

import com.liferay.digital.signature.model.DSEnvelope;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface DSEnvelopeManager {

	public DSEnvelope addDSEnvelope(long groupId, DSEnvelope dsEnvelope);

	public void deleteDSEnvelopes(long groupId, String... dsEnvelopeIds);

	public DSEnvelope getDSEnvelope(long groupId, String dsEnvelopeId);

	public List<DSEnvelope> getDSEnvelopes(long groupId, String fromDateString);

	public List<DSEnvelope> getDSEnvelopes(
		long groupId, String... dsEnvelopeIds);

}