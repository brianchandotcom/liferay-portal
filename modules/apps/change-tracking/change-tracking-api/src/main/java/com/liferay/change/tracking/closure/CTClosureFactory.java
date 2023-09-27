/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.closure;

import java.util.Set;

/**
 * @author Preston Crary
 */
public interface CTClosureFactory {

	public CTClosure create(long ctCollectionId);

	public CTClosure create(long ctCollectionId, long classNameId);

	public CTClosure create(long ctCollectionId, Set<Long> classNameIds);

}