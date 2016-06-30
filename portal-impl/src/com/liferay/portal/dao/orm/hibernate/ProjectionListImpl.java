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

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

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
		StringBundler sb = new StringBundler(3);

		sb.append("{_projectionList=");

		if (_projectionList != null) {
			sb.append(_projectionList.toString());
		}
		else {
			sb.append(StringPool.NULL);
		}

		sb.append("}");

		return sb.toString();
	}

	private final org.hibernate.criterion.ProjectionList _projectionList;

}