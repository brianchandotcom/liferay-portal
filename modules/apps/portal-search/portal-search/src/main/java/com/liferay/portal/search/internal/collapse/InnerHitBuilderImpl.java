/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.collapse;

import com.liferay.portal.search.collapse.InnerCollapse;
import com.liferay.portal.search.collapse.InnerHit;
import com.liferay.portal.search.collapse.InnerHitBuilder;
import com.liferay.portal.search.sort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class InnerHitBuilderImpl implements InnerHitBuilder {

	@Override
	public InnerHitBuilder addSort(Sort sort) {
		_sorts.add(sort);

		return this;
	}

	@Override
	public InnerHit build() {
		return new InnerHitImpl(_innerCollapse, _name, _size, _sorts);
	}

	@Override
	public InnerHitBuilder innerCollapse(InnerCollapse innerCollapse) {
		_innerCollapse = innerCollapse;

		return this;
	}

	@Override
	public InnerHitBuilder name(String name) {
		_name = name;

		return this;
	}

	@Override
	public InnerHitBuilder size(Integer size) {
		_size = size;

		return this;
	}

	private InnerCollapse _innerCollapse;
	private String _name;
	private Integer _size;
	private final List<Sort> _sorts = new ArrayList<>();

}