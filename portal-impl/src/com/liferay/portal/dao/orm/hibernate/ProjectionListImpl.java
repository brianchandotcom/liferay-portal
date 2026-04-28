/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ProjectionListImpl implements ProjectionList {

	@Override
	public ProjectionList add(Projection projection) {
		_projections.add(projection);

		return this;
	}

	@Override
	public ProjectionList add(Projection projection, String alias) {
		if (alias == null) {
			return add(projection);
		}

		_projections.add(new ProjectionImpl(alias, projection));

		return this;
	}

	public List<Projection> getProjections() {
		return _projections;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"[", StringUtil.merge(_projections, ", "), "]");
	}

	private final List<Projection> _projections = new ArrayList<>();

}