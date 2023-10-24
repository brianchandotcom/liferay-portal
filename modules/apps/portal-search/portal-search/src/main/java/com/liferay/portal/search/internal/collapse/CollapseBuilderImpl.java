/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.collapse;

import com.liferay.portal.search.collapse.Collapse;
import com.liferay.portal.search.collapse.CollapseBuilder;
import com.liferay.portal.search.collapse.InnerHit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class CollapseBuilderImpl implements CollapseBuilder {

	@Override
	public CollapseBuilder addInnerHit(InnerHit innerHit) {
		_innerHits.add(innerHit);

		return this;
	}

	@Override
	public Collapse build() {
		return new CollapseImpl(
			_field, _innerHits, _maxConcurrentGroupRequests);
	}

	@Override
	public CollapseBuilder field(String field) {
		_field = field;

		return this;
	}

	@Override
	public CollapseBuilder maxConcurrentGroupRequests(
		Integer maxConcurrentGroupRequests) {

		_maxConcurrentGroupRequests = maxConcurrentGroupRequests;

		return this;
	}

	private String _field;
	private final List<InnerHit> _innerHits = new ArrayList<>();
	private Integer _maxConcurrentGroupRequests;

}