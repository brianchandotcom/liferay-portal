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

package com.liferay.mail.service.persistence;

import com.liferay.mail.NoSuchCyrusVirtualException;
import com.liferay.mail.model.CyrusVirtual;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CyrusVirtualPersistenceImpl
	extends BasePersistenceImpl<Dummy> implements CyrusVirtualPersistence {

	public static final String FIND_BY_USER_ID =
		"SELECT cyrusVirtual FROM CyrusVirtual cyrusVirtual WHERE userId = ?";

	@Override
	public CyrusVirtual findByPrimaryKey(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException {

		Session session = null;

		try {
			session = openSession();

			return (CyrusVirtual)session.load(CyrusVirtual.class, emailAddress);
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchCyrusVirtualException();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CyrusVirtual> findByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(FIND_BY_USER_ID);

			q.setString(0, String.valueOf(userId));

			return q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public void remove(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException {

		Session session = null;

		try {
			session = openSession();

			CyrusVirtual virtual = (CyrusVirtual)session.load(
				CyrusVirtual.class, emailAddress);

			session.delete(virtual);

			session.flush();
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchCyrusVirtualException();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public void removeByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(FIND_BY_USER_ID);

			q.setString(0, String.valueOf(userId));

			Iterator<CyrusVirtual> itr = q.iterate();

			while (itr.hasNext()) {
				CyrusVirtual virtual = itr.next();

				session.delete(virtual);
			}

			closeSession(session);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public void update(CyrusVirtual virtual) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			try {
				CyrusVirtual virtualModel = (CyrusVirtual)session.load(
					CyrusVirtual.class, virtual.getEmailAddress());

				virtualModel.setUserId(virtual.getUserId());

				session.flush();
			}
			catch (ObjectNotFoundException onfe) {
				CyrusVirtual virtualModel = new CyrusVirtual(
					virtual.getEmailAddress(), virtual.getUserId());

				session.save(virtualModel);

				session.flush();
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

}