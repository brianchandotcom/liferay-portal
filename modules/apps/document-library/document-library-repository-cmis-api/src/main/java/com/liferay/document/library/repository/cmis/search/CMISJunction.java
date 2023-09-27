/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public abstract class CMISJunction implements CMISCriterion {

	public void add(CMISCriterion cmisCriterion) {
		_cmisCriterions.add(cmisCriterion);
	}

	public boolean isEmpty() {
		return _cmisCriterions.isEmpty();
	}

	public List<CMISCriterion> list() {
		return _cmisCriterions;
	}

	@Override
	public abstract String toQueryFragment();

	private final List<CMISCriterion> _cmisCriterions = new ArrayList<>();

}