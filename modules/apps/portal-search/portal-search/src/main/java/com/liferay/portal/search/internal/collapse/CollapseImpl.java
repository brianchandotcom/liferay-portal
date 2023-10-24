/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.collapse;

import com.liferay.portal.search.collapse.Collapse;
import com.liferay.portal.search.collapse.InnerHit;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class CollapseImpl implements Collapse {

	public CollapseImpl(
		String field, List<InnerHit> innerHits,
		Integer maxConcurrentGroupRequests) {

		_field = field;
		_innerHits = innerHits;
		_maxConcurrentGroupRequests = maxConcurrentGroupRequests;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public List<InnerHit> getInnerHits() {
		return _innerHits;
	}

	@Override
	public Integer getMaxConcurrentGroupRequests() {
		return _maxConcurrentGroupRequests;
	}

	private final String _field;
	private final List<InnerHit> _innerHits;
	private final Integer _maxConcurrentGroupRequests;

}