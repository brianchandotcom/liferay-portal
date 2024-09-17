/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition;

import com.liferay.portal.kernel.model.LayoutSet;

import java.util.List;

/**
 * @author Iván Zaera
 */
public interface FrontendTokenDefinitionRegistry {

	public FrontendTokenDefinition getFrontendTokenDefinition(
		LayoutSet layoutSet);

	public List<FrontendTokenDefinition> getFrontendTokenDefinitions(
		long companyId);

}