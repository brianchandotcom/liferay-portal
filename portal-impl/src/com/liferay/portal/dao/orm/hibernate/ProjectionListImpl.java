/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionList;

/**
 * @author Brian Wing Shun Chan
 */
public class ProjectionListImpl
	extends ProjectionImpl implements ProjectionList {

	public ProjectionListImpl(
		org.hibernate.criterion.ProjectionList projectionList) {

		super(projectionList);

		_projectionList = projectionList;
	}

	@Override
	public ProjectionList add(Projection projection) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		_projectionList.add(projectionImpl.getWrappedProjection());

		return this;
	}

	@Override
	public ProjectionList add(Projection projection, String alias) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		_projectionList.add(projectionImpl.getWrappedProjection(), alias);

		return this;
	}

	public org.hibernate.criterion.ProjectionList getWrappedProjectionList() {
		return _projectionList;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{_projectionList=", _projectionList, "}");
	}

	private final org.hibernate.criterion.ProjectionList _projectionList;

}