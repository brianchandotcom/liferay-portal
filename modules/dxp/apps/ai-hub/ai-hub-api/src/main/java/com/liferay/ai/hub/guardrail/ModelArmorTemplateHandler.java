/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.guardrail;

import java.util.Map;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public interface ModelArmorTemplateHandler {

	public void createModelArmorTemplate(
			long companyId, String externalReferenceCode,
			Map<String, Object> properties)
		throws Exception;

	public void deleteModelArmorTemplate(
			long companyId, String externalReferenceCode, String location)
		throws Exception;

	public void updateModelArmorTemplate(
			long companyId, String externalReferenceCode,
			Map<String, Object> properties)
		throws Exception;

}