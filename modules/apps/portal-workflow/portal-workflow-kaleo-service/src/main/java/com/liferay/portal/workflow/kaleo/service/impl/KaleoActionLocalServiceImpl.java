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
import com.liferay.portal.workflow.kaleo.definition.Action;
import com.liferay.portal.workflow.kaleo.definition.ActionType;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.definition.ScriptAction;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.definition.UpdateStatusAction;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.service.base.KaleoActionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoAction",
	service = AopService.class
)
public class KaleoActionLocalServiceImpl
	extends KaleoActionLocalServiceBaseImpl {

	@Override
	public KaleoAction addKaleoAction(
			String kaleoClassName, long kaleoClassPK, long kaleoDefinitionId,
			long kaleoDefinitionVersionId, String kaleoNodeName, Action action,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(
			serviceContext.getGuestOrUserId());
		Date date = new Date();

		long kaleoActionId = counterLocalService.increment();

		KaleoAction kaleoAction = kaleoActionPersistence.create(kaleoActionId);

		kaleoAction.setCompanyId(user.getCompanyId());
		kaleoAction.setUserId(user.getUserId());
		kaleoAction.setUserName(user.getFullName());
		kaleoAction.setCreateDate(date);
		kaleoAction.setModifiedDate(date);
		kaleoAction.setKaleoClassName(kaleoClassName);
		kaleoAction.setKaleoClassPK(kaleoClassPK);
		kaleoAction.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoAction.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoAction.setKaleoNodeName(kaleoNodeName);
		kaleoAction.setName(action.getName());
		kaleoAction.setDescription(action.getDescription());

		ExecutionType executionType = action.getExecutionType();

		kaleoAction.setExecutionType(executionType.getValue());

		kaleoAction.setPriority(action.getPriority());

		if (action instanceof ScriptAction) {
			ScriptAction scriptAction = (ScriptAction)action;

			kaleoAction.setScript(scriptAction.getScript());

			ScriptLanguage scriptLanguage = scriptAction.getScriptLanguage();

			kaleoAction.setScriptLanguage(scriptLanguage.getValue());

			kaleoAction.setScriptRequiredContexts(
				scriptAction.getScriptRequiredContexts());
		}
		else if (action instanceof UpdateStatusAction) {
			UpdateStatusAction updateStatusAction = (UpdateStatusAction)action;

			kaleoAction.setStatus(updateStatusAction.getStatus());
		}

		ActionType actionType = action.getActionType();

		kaleoAction.setType(actionType.name());

		return kaleoActionPersistence.update(kaleoAction);
	}

	@Override
	public void deleteCompanyKaleoActions(long companyId) {
		kaleoActionPersistence.removeByCompanyId(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoActions(
		long kaleoDefinitionVersionId) {

		kaleoActionPersistence.removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	@Override
	public List<KaleoAction> getKaleoActions(
		long companyId, String kaleoClassName, long kaleoClassPK) {

		return kaleoActionPersistence.findByC_KCN_KCPK(
			companyId, kaleoClassName, kaleoClassPK);
	}

	@Override
	public List<KaleoAction> getKaleoActions(
		long companyId, String kaleoClassName, long kaleoClassPK,
		String executionType) {

		return kaleoActionPersistence.findByC_KCN_KCPK_ET(
			companyId, kaleoClassName, kaleoClassPK, executionType);
	}

	@Reference
	private UserLocalService _userLocalService;

}