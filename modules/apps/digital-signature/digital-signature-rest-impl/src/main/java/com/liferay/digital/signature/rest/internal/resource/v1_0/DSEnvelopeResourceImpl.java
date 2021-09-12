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

package com.liferay.digital.signature.rest.internal.resource.v1_0;

import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.rest.dto.v1_0.DSEnvelope;
import com.liferay.digital.signature.rest.internal.dto.v1_0.util.DSEnvelopeUtil;
import com.liferay.digital.signature.rest.resource.v1_0.DSEnvelopeResource;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author José Abelenda
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/ds-envelope.properties",
	scope = ServiceScope.PROTOTYPE, service = DSEnvelopeResource.class
)
public class DSEnvelopeResourceImpl extends BaseDSEnvelopeResourceImpl {

	@Override
	public DSEnvelope getDSEnvelope(
			Long companyId, Long groupId, String envelopeId)
		throws Exception {

		com.liferay.digital.signature.model.DSEnvelope dsEnvelope =
			_dsEnvelopeManager.getDSEnvelope(companyId, groupId, envelopeId);

		if (Validator.isNull(dsEnvelope.getDSEnvelopeId())) {
			throw new Exception("Envelope does not exist!");
		}

		return DSEnvelopeUtil.toDSEnvelope(dsEnvelope);
	}

	@Override
	public DSEnvelope postDSEnvelope(
			Long companyId, Long groupId, DSEnvelope dsEnvelope)
		throws Exception {

		return DSEnvelopeUtil.toDSEnvelope(
			_dsEnvelopeManager.addDSEnvelope(
				companyId, groupId, DSEnvelopeUtil.toDSEnvelope(dsEnvelope)));
	}

	@Reference
	private DSEnvelopeManager _dsEnvelopeManager;

}