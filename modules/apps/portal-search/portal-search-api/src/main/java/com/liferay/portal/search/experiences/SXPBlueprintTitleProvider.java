/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.experiences;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Almir Ferreira
 */
@ProviderType
public interface SXPBlueprintTitleProvider {

	public String getSXPBlueprintTitle(
		long companyId, String languageId,
		String sxpBlueprintExternalReferenceCode);

}