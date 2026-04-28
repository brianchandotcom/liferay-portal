/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionList;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ProjectionListImpl
	extends ProjectionImpl implements ProjectionList {

	public ProjectionListImpl() {
		super(ProjectionType.PROJECTION_LIST, (String)null);
	}

	@Override
	public ProjectionList add(Projection projection) {
		return add(projection, null);
	}

	@Override
	public ProjectionList add(Projection projection, String alias) {
		_projections.add(
			new AbstractMap.SimpleImmutableEntry<>(projection, alias));

		return this;
	}

	public List<Map.Entry<Projection, String>> getProjections() {
		return _projections;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("[");

		for (Map.Entry<Projection, String> entry : _projections) {
			sb.append(entry.getKey());

			String alias = entry.getValue();

			if (alias != null) {
				sb.append(" as ");
				sb.append(alias);
			}

			sb.append(", ");
		}

		if (!_projections.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("]");

		return sb.toString();
	}

	private final List<Map.Entry<Projection, String>> _projections =
		new ArrayList<>();

}