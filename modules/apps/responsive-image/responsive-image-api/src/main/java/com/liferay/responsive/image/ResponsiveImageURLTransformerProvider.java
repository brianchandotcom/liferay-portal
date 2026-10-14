/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import java.util.Set;

/**
 * @author Daniel Sanz
 */
public interface ResponsiveImageURLTransformerProvider {

	/**
	 * Returns the transformer of the given name, or <code>null</code> when the
	 * name is blank or names nothing deployed.
	 */
	public ResponsiveImageURLTransformer getResponsiveImageURLTransformer(
		String responsiveImageURLTransformerName);

	public Set<String> getResponsiveImageURLTransformerNames();

}