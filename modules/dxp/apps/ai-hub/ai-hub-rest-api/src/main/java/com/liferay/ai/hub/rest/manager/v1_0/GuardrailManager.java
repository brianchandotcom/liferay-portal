/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.manager.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.Guardrail;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

/**
 * @author João Victor Alves
 */
public interface GuardrailManager {

	public void deleteGuardrail(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode)
		throws Exception;

	public Guardrail postGuardrail(
			long companyId, DTOConverterContext dtoConverterContext,
			Guardrail guardrail)
		throws Exception;

	public Guardrail putGuardrail(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, Guardrail guardrail)
		throws Exception;

}