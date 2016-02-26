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

package com.liferay.portal.kernel.dao.orm;

import java.sql.Connection;

/**
 * @author Brian Wing Shun Chan
 */
public interface SessionFactory {

	public void closeSession(Session session) throws ORMException;

	public Session getCurrentSession() throws ORMException;

	public Dialect getDialect() throws ORMException;

	public Session openNewSession(Connection connection) throws ORMException;

	public Session openSession() throws ORMException;

}