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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionImpl;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLADefinitionVersionFinder;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = WorkflowMetricsSLADefinitionVersionFinder.class)
public class WorkflowMetricsSLADefinitionVersionFinderImpl
	extends WorkflowMetricsSLADefinitionVersionFinderBaseImpl
	implements WorkflowMetricsSLADefinitionVersionFinder {

	public static final String FIND_BY_C_CD_P_S =
		WorkflowMetricsSLADefinitionVersionFinder.class.getName() +
			".findByC_CD_P_S";

	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByC_CD_P_S(
		long companyId, Date createDate, Long processId, int status, int start,
		int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_CD_P_S);

			if (processId == null) {
				sql = StringUtil.replace(
					sql, "(WMSLADefinitionVersion.processId = ? ) AND",
					StringPool.BLANK);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				"WorkflowMetricsSLADefinitionVersion",
				WorkflowMetricsSLADefinitionVersionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(createDate);

			if (processId != null) {
				qPos.add(processId);
			}

			qPos.add(status);

			return (List<WorkflowMetricsSLADefinitionVersion>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}