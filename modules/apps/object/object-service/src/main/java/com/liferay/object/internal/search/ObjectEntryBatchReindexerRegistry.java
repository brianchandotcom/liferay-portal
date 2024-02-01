/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search;

import java.util.Collection;

/**
 * @author Feliphe Marinho
 * @author Gabriel Albuquerque
 */
public interface ObjectEntryBatchReindexerRegistry {

	public Collection<ObjectEntryBatchReindexer>
		getObjectEntryBatchReindexers();

}