/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.manager.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.ModelArmorTemplate;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

/**
 * @author João Victor Alves
 */
public interface ModelArmorTemplateManager {

	public void deleteModelArmorTemplate(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode)
		throws Exception;

	public ModelArmorTemplate postModelArmorTemplate(
			long companyId, DTOConverterContext dtoConverterContext,
			ModelArmorTemplate modelArmorTemplate)
		throws Exception;

	public ModelArmorTemplate putModelArmorTemplate(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ModelArmorTemplate modelArmorTemplate)
		throws Exception;

}