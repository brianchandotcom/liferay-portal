/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceOrderItemTable;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItemTable;
import com.liferay.commerce.model.CommerceShipmentTable;
import com.liferay.commerce.model.impl.CommerceShipmentImpl;
import com.liferay.commerce.service.persistence.CommerceShipmentFinder;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alec Sloan
 */
@Component(service = CommerceShipmentFinder.class)
public class CommerceShipmentFinderImpl
	extends CommerceShipmentFinderBaseImpl implements CommerceShipmentFinder {

	@Override
	public int countByCommerceOrderId(long commerceOrderId) {
		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				_getGroupByStep(
					DSLQueryFactoryUtil.select(
						DSLFunctionFactoryUtil.count(
							CommerceShipmentTable.INSTANCE.commerceShipmentId
						).as(
							COUNT_COLUMN_NAME
						)),
					commerceOrderId));

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceShipment> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				_getGroupByStep(
					DSLQueryFactoryUtil.select(CommerceShipmentTable.INSTANCE),
					commerceOrderId
				).orderBy(
					CommerceShipmentTable.INSTANCE.createDate.descending()
				));

			sqlQuery.addEntity("CommerceShipment", CommerceShipmentImpl.class);

			return (List<CommerceShipment>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int[] findCommerceShipmentStatusesByCommerceOrderId(
		long commerceOrderId) {

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				_getGroupByStep(
					DSLQueryFactoryUtil.selectDistinct(
						CommerceShipmentTable.INSTANCE.status),
					commerceOrderId));

			List<Integer> commerceShipmentStatuses =
				(List<Integer>)QueryUtil.list(
					sqlQuery, getDialect(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			return ArrayUtil.toIntArray(commerceShipmentStatuses);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private GroupByStep _getGroupByStep(
		FromStep fromStep, long commerceOrderId) {

		return fromStep.from(
			CommerceShipmentTable.INSTANCE
		).where(
			CommerceShipmentTable.INSTANCE.commerceShipmentId.in(
				DSLQueryFactoryUtil.select(
					CommerceShipmentItemTable.INSTANCE.commerceShipmentId
				).from(
					CommerceShipmentItemTable.INSTANCE
				).innerJoinON(
					CommerceOrderItemTable.INSTANCE,
					CommerceOrderItemTable.INSTANCE.commerceOrderItemId.eq(
						CommerceShipmentItemTable.INSTANCE.commerceOrderItemId)
				).where(
					CommerceOrderItemTable.INSTANCE.commerceOrderId.eq(
						commerceOrderId)
				))
		);
	}

}