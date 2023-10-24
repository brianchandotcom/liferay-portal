/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.collapse;

import com.liferay.portal.search.collapse.InnerCollapse;
import com.liferay.portal.search.collapse.InnerHit;
import com.liferay.portal.search.sort.Sort;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class InnerHitImpl implements InnerHit {

	public InnerHitImpl(
		InnerCollapse innerCollapse, String name, Integer size,
		List<Sort> sorts) {

		_innerCollapse = innerCollapse;
		_name = name;
		_size = size;
		_sorts = sorts;
	}

	@Override
	public InnerCollapse getInnerCollapse() {
		return _innerCollapse;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getSize() {
		return _size;
	}

	@Override
	public List<Sort> getSorts() {
		return _sorts;
	}

	private final InnerCollapse _innerCollapse;
	private final String _name;
	private final Integer _size;
	private final List<Sort> _sorts;

}