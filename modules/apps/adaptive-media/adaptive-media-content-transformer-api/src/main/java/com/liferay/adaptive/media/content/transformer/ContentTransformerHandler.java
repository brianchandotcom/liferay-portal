/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.content.transformer;

/**
 * @author Lance Ji
 */
public interface ContentTransformerHandler {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #transform(String)}
	 */
	@Deprecated
	public <T> T transform(
		ContentTransformerContentType<T> contentTransformerContentType,
		T originalContent);

	public String transform(String originalContent);

}