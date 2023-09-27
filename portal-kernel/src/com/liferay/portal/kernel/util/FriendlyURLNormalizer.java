/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Julio Camarero
 */
@ProviderType
public interface FriendlyURLNormalizer {

	public String normalize(String friendlyURL);

	public String normalizeWithEncoding(String friendlyURL);

	public String normalizeWithPeriods(String friendlyURL);

	public String normalizeWithPeriodsAndSlashes(String friendlyURL);

}