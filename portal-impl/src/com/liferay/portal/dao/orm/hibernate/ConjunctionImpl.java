/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class ConjunctionImpl implements Conjunction {

	@Override
	public Junction add(Criterion criterion) {
		_criterions.add(criterion);

		return this;
	}

	public List<Criterion> getCriterions() {
		return _criterions;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"(", StringUtil.merge(_criterions, " and "), ")");
	}

	private final List<Criterion> _criterions = new ArrayList<>();

}