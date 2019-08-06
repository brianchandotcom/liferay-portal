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

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.service.base.KaleoConditionLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

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
			long kaleoDefinitionVersionId, long kaleoNodeId,
			Condition condition, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		long kaleoConditionId = counterLocalService.increment();

		KaleoCondition kaleoCondition = kaleoConditionPersistence.create(
			kaleoConditionId);

		kaleoCondition.setCompanyId(user.getCompanyId());
		kaleoCondition.setUserId(user.getUserId());
		kaleoCondition.setUserName(user.getFullName());
		kaleoCondition.setCreateDate(now);
		kaleoCondition.setModifiedDate(now);
		kaleoCondition.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoCondition.setKaleoNodeId(kaleoNodeId);
		kaleoCondition.setScript(condition.getScript());

		ScriptLanguage scriptLanguage = condition.getScriptLanguage();

		kaleoCondition.setScriptLanguage(scriptLanguage.getValue());

		kaleoCondition.setScriptRequiredContexts(
			condition.getScriptRequiredContexts());

		kaleoConditionPersistence.update(kaleoCondition);

		return kaleoCondition;
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

}