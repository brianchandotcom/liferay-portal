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

import com.liferay.mail.NoSuchCyrusUserException;
import com.liferay.mail.model.CyrusUser;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class CyrusUserPersistenceImpl
	extends BasePersistenceImpl<Dummy> implements CyrusUserPersistence {

	@Override
	public CyrusUser findByPrimaryKey(long userId)
		throws NoSuchCyrusUserException, SystemException {

		Session session = null;

		try {
			session = openSession();

			return (CyrusUser)session.load(
				CyrusUser.class, String.valueOf(userId));
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchCyrusUserException();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public void remove(long userId)
		throws NoSuchCyrusUserException, SystemException {

		Session session = null;

		try {
			session = openSession();

			CyrusUser user = (CyrusUser)session.load(
				CyrusUser.class, String.valueOf(userId));

			session.delete(user);

			session.flush();
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchCyrusUserException();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public void update(CyrusUser user) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			try {
				CyrusUser userModel = (CyrusUser)session.load(
					CyrusUser.class, String.valueOf(user.getUserId()));

				userModel.setPassword(user.getPassword());

				session.flush();
			}
			catch (ObjectNotFoundException onfe) {
				CyrusUser userModel = new CyrusUser(
					user.getUserId(), user.getPassword());

				session.save(userModel);

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