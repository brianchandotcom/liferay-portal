/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.Junction;

/**
 * @author Raymond Augé
 */
public class DisjunctionImpl extends CriterionImpl implements Disjunction {

	public DisjunctionImpl(org.hibernate.criterion.Disjunction disjunction) {
		super(disjunction);

		_disjunction = disjunction;
	}

	@Override
	public Junction add(Criterion criterion) {
		CriterionImpl criterionImpl = (CriterionImpl)criterion;

		_disjunction.add(criterionImpl.getWrappedCriterion());

		return this;
	}

	public org.hibernate.criterion.Disjunction getWrappedDisjunction() {
		return _disjunction;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{_disjunction=", _disjunction, "}");
	}

	private final org.hibernate.criterion.Disjunction _disjunction;

}