/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.highlight;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 */
@ProviderType
public interface Highlights {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             HighlightBuilderFactory#builder()}
	 */
	@Deprecated
	public HighlightBuilder builder();

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public FieldConfig fieldConfig(String field);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             FieldConfigBuilderFactory#builder(String)}
	 */
	@Deprecated
	public FieldConfigBuilder fieldConfigBuilder();

	public Highlight highlight(FieldConfig fieldConfig);

}