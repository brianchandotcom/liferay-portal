/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.reindexer;

import java.util.Collection;
import java.util.Set;

/**
 * @author Gustavo Lima
 */
public interface IndexReindexerRegistry {

	public IndexReindexer getIndexReindexer(String className);

	public Set<String> getIndexReindexerClassNames();

	public Collection<IndexReindexer> getIndexReindexers();

}