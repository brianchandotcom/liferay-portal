/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionImpl;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLADefinitionVersionFinder;

import java.util.Date;
import java.util.List;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLADefinitionVersionFinderImpl
	extends WorkflowMetricsSLADefinitionVersionFinderBaseImpl
	implements WorkflowMetricsSLADefinitionVersionFinder {

	public static final String FIND_BY_C_WMSLAD_V =
		WorkflowMetricsSLADefinitionVersionFinder.class.getName() +
			".findByC_WMSLAD_V";

	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByC_WMSLAD_V(
		long companyId, Date createDate, int status, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
				_customSQL.get(getClass(), FIND_BY_C_WMSLAD_V));

			q.addEntity(
				"WorkflowMetricsSLADefinitionVersion",
				WorkflowMetricsSLADefinitionVersionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(createDate);
			qPos.add(status);

			return (List<WorkflowMetricsSLADefinitionVersion>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}