/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Junction;

/**
 * @author Raymond Augé
 */
public class ConjunctionImpl extends CriterionImpl implements Conjunction {

	public ConjunctionImpl(org.hibernate.criterion.Conjunction conjunction) {
		super(conjunction);

		_conjunction = conjunction;
	}

	@Override
	public Junction add(Criterion criterion) {
		CriterionImpl criterionImpl = (CriterionImpl)criterion;

		_conjunction.add(criterionImpl.getWrappedCriterion());

		return this;
	}

	public org.hibernate.criterion.Conjunction getWrappedConjunction() {
		return _conjunction;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{_conjunction=", _conjunction, "}");
	}

	private final org.hibernate.criterion.Conjunction _conjunction;

}