/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.frontend.js.audiences;

import com.liferay.frontend.js.audiences.AudiencesDefinition;
import com.liferay.frontend.js.audiences.AudiencesDefinitionProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = AudiencesDefinitionProvider.class)
public class AudiencesDefinitionProviderImpl
	implements AudiencesDefinitionProvider {

	@Override
	public AudiencesDefinition getAudiencesDefinition(long companyId) {
		return null;
	}

}