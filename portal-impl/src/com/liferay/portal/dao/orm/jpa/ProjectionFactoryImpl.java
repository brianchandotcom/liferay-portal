/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactory;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class ProjectionFactoryImpl implements ProjectionFactory {

	@Override
	public Projection alias(Projection projection, String alias) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection avg(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection count(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection countDistinct(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection distinct(Projection projection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection groupProperty(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection max(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection min(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ProjectionList projectionList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection property(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection rowCount() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Projection sqlProjection(
		String sql, String[] columnAliases, Type[] types) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Projection sum(String propertyName) {
		throw new UnsupportedOperationException();
	}

}