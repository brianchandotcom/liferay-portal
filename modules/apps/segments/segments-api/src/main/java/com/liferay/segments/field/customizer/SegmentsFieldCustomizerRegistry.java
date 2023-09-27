/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.field.customizer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides methods for retrieving segment field customizers defined by {@link
 * SegmentsFieldCustomizer} implementations.
 *
 * @author Eduardo García
 */
@ProviderType
public interface SegmentsFieldCustomizerRegistry {

	public SegmentsFieldCustomizer getSegmentsFieldCustomizer(
		String entityName, String fieldName);

}