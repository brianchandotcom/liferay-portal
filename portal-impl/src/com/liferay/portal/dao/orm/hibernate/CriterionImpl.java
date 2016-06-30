/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Brian Wing Shun Chan
 */
public class CriterionImpl implements Criterion {

	public CriterionImpl(org.hibernate.criterion.Criterion criterion) {
		_criterion = criterion;
	}

	public org.hibernate.criterion.Criterion getWrappedCriterion() {
		return _criterion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(3);

		sb.append("{_criterion=");

		if (_criterion != null) {
			sb.append(_criterion.toString());
		}
		else {
			sb.append(StringPool.NULL);
		}

		sb.append("}");

		return sb.toString();
	}

	private final org.hibernate.criterion.Criterion _criterion;
}