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

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class KaleoTaskInstanceTokenFinderBaseImpl
	extends BasePersistenceImpl<KaleoTaskInstanceToken> {

	public KaleoTaskInstanceTokenFinderBaseImpl() {
		setModelClass(KaleoTaskInstanceToken.class);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected KaleoTaskInstanceTokenPersistence
		kaleoTaskInstanceTokenPersistence;

	static {
		try {
			Class.forName(KaleoPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}