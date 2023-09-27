/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.markdown.converter.factory;

import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;

/**
 * @author Sergio González
 */
public interface MarkdownConverterFactory {

	public MarkdownConverter create();

}