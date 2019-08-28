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

package com.liferay.blogs.service.persistence.impl;

import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.blogs.service.persistence.impl.constants.BlogsPersistenceConstants;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BlogsStatsUserFinderBaseImpl
	extends BasePersistenceImpl<BlogsStatsUser> {

	public BlogsStatsUserFinderBaseImpl() {
		setModelClass(BlogsStatsUser.class);
	}

	@Override
	@Reference(
		target = BlogsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);
	}

	@Override
	@Reference(
		target = BlogsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BlogsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;

	static {
		try {
			Class.forName(BlogsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}