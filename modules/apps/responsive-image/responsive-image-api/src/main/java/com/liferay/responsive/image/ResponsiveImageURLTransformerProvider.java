/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import java.util.Set;

/**
 * Hands back the deployed {@link ResponsiveImageURLTransformer} a
 * configuration names.
 *
 * @author Daniel Sanz
 */
public interface ResponsiveImageURLTransformerProvider {

	/**
	 * Returns the transformer of the given name, or <code>null</code> when the
	 * name is blank or names nothing deployed.
	 *
	 * @param  responsiveImageURLTransformerName the configured name
	 * @return the transformer, or <code>null</code>
	 */
	public ResponsiveImageURLTransformer getResponsiveImageURLTransformer(
		String responsiveImageURLTransformerName);

	/**
	 * Returns the names of the deployed transformers, which are the names a
	 * configuration may give.
	 *
	 * @return the deployed names
	 */
	public Set<String> getResponsiveImageURLTransformerNames();

}