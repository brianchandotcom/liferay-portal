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

import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class SessionFactoryImpl implements SessionFactory {

	@Override
	public void closeSession(Session session) throws ORMException {
		if (session != null) {
			session.close();
		}
	}

	@Override
	public Session getCurrentSession() {
		return _session;
	}

	@Override
	public Dialect getDialect() throws ORMException {
		return new DialectImpl();
	}

	@Override
	public Session openNewSession(Connection connection) throws ORMException {
		EntityManager entityManager =
			_entityManagerFactory.createEntityManager();

		return new NewSessionImpl(entityManager);
	}

	@Override
	public Session openSession() throws ORMException {
		return _session;
	}

	@PersistenceUnit
	public void setEntityManagerFactory(
		EntityManagerFactory entityManagerFactory) {

		_entityManagerFactory = entityManagerFactory;
	}

	public void setSession(Session session) {
		_session = session;
	}

	private EntityManagerFactory _entityManagerFactory;
	private Session _session;

}