/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.service.base.KaleoConditionLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoCondition",
	service = AopService.class
)
public class KaleoConditionLocalServiceImpl
	extends KaleoConditionLocalServiceBaseImpl {

	@Override
	public KaleoCondition addKaleoCondition(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long kaleoNodeId, Condition condition,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(
			serviceContext.getGuestOrUserId());
		Date date = new Date();

		long kaleoConditionId = counterLocalService.increment();

		KaleoCondition kaleoCondition = kaleoConditionPersistence.create(
			kaleoConditionId);

		kaleoCondition.setCompanyId(user.getCompanyId());
		kaleoCondition.setUserId(user.getUserId());
		kaleoCondition.setUserName(user.getFullName());
		kaleoCondition.setCreateDate(date);
		kaleoCondition.setModifiedDate(date);
		kaleoCondition.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoCondition.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoCondition.setKaleoNodeId(kaleoNodeId);
		kaleoCondition.setScript(condition.getScript());

		ScriptLanguage scriptLanguage = condition.getScriptLanguage();

		kaleoCondition.setScriptLanguage(scriptLanguage.getValue());

		kaleoCondition.setScriptRequiredContexts(
			condition.getScriptRequiredContexts());

		return kaleoConditionPersistence.update(kaleoCondition);
	}

	@Override
	public void deleteCompanyKaleoConditions(long companyId) {
		kaleoConditionPersistence.removeByCompanyId(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoCondition(
		long kaleoDefinitionVersionId) {

		kaleoConditionPersistence.removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	@Override
	public KaleoCondition getKaleoNodeKaleoCondition(long kaleoNodeId)
		throws PortalException {

		return kaleoConditionPersistence.findByKaleoNodeId(kaleoNodeId);
	}

	@Reference
	private UserLocalService _userLocalService;

}