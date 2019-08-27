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

package com.liferay.portal.dao.orm.hibernate.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class SuspendedSessionConnectionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testConnectionOpenAfterTransactionSuspended() throws Throwable {
		Connection[] connectionHolder = new Connection[1];

		TransactionInvokerUtil.invoke(
			_outerTransactionConfig,
			() -> {
				Session session = _companyPersistence.getCurrentSession();

				org.hibernate.Session hibernateSession =
					(org.hibernate.Session)session.getWrappedSession();

				hibernateSession.doWork(
					connection -> {
						Assert.assertFalse(connection.isClosed());

						try {
							TransactionInvokerUtil.invoke(
								_innerTransactionConfig, () -> null);
						}
						catch (Throwable t) {
							throw new RuntimeException(t);
						}

						Assert.assertFalse(connection.isClosed());

						connectionHolder[0] = connection;
					});

				return null;
			});

		Assert.assertTrue(connectionHolder[0].isClosed());
	}

	@Inject
	private static CompanyPersistence _companyPersistence;

	private static final TransactionConfig _innerTransactionConfig;
	private static final TransactionConfig _outerTransactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);

		_innerTransactionConfig = builder.build();

		builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.SUPPORTS);

		_outerTransactionConfig = builder.build();
	}

}