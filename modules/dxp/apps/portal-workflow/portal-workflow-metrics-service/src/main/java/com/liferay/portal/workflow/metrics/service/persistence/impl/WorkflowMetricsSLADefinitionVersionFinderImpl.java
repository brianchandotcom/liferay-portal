/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.persistence.impl;

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
				sql = StringUtil.removeSubstring(
					sql, "(WMSLADefinitionVersion.processId = ? ) AND");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"WorkflowMetricsSLADefinitionVersion",
				WorkflowMetricsSLADefinitionVersionImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(createDate);

			if (processId != null) {
				queryPos.add(processId);
			}

			queryPos.add(status);

			return (List<WorkflowMetricsSLADefinitionVersion>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
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