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

package com.liferay.portal.spring.hibernate;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.SessionHolder;

/**
 * @author Shuyang Zhou
 */
public class LastSessionRecorderHibernateTransactionManager
	extends HibernateTransactionManager {

	@Override
	protected Object doGetTransaction() {
		SessionHolder sessionHolder =
			SpringHibernateThreadLocalUtil.getResource(getSessionFactory());

		if (sessionHolder != null) {
			LastSessionRecorderUtil.setLastSession(sessionHolder.getSession());
		}

		return super.doGetTransaction();
	}

	static {
		try {
			Class.forName(SpringHibernateThreadLocalUtil.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}