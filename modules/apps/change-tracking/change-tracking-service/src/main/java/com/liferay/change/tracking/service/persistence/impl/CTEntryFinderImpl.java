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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.persistence.CTEntryFinder;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(service = CTEntryFinder.class)
public class CTEntryFinderImpl
	extends CTEntryFinderBaseImpl implements CTEntryFinder {

	@Override
	public long findByMCNI_MCPK_SD(
		long modelClassNameId, long modelClassPK, Date statusDate) {

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				DSLQueryFactoryUtil.select(
					CTEntryTable.INSTANCE.ctCollectionId
				).from(
					CTEntryTable.INSTANCE
				).innerJoinON(
					CTCollectionTable.INSTANCE,
					CTCollectionTable.INSTANCE.ctCollectionId.eq(
						CTEntryTable.INSTANCE.ctCollectionId
					).and(
						CTCollectionTable.INSTANCE.status.eq(
							WorkflowConstants.STATUS_APPROVED)
					)
				).where(
					CTEntryTable.INSTANCE.modelClassNameId.eq(
						modelClassNameId
					).and(
						CTEntryTable.INSTANCE.modelClassPK.eq(modelClassPK)
					).and(
						CTCollectionTable.INSTANCE.statusDate.gt(statusDate)
					)
				).orderBy(
					CTCollectionTable.INSTANCE.statusDate.ascending()
				));

			sqlQuery.addScalar(
				CTEntryTable.INSTANCE.ctCollectionId.getName(), Type.LONG);

			List<Long> ctCollectionIds = (List<Long>)QueryUtil.list(
				sqlQuery, getDialect(), 0, 1);

			if (ctCollectionIds.isEmpty()) {
				return CTConstants.CT_COLLECTION_ID_PRODUCTION;
			}

			return ctCollectionIds.get(0);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

}