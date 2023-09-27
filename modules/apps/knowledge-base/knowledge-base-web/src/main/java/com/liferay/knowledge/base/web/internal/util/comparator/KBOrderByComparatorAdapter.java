/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorAdapter;

/**
 * @author Adolfo Pérez
 */
public class KBOrderByComparatorAdapter<S, T>
	extends OrderByComparatorAdapter<S, T> {

	public KBOrderByComparatorAdapter(OrderByComparator<T> orderByComparator) {
		super(orderByComparator);
	}

	@Override
	public T adapt(S s) {
		return (T)s;
	}

}