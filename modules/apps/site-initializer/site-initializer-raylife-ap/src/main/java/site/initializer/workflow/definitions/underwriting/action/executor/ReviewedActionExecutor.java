/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package site.initializer.workflow.definitions.underwriting.action.executor;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutorException;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = ActionExecutor.class)
public class ReviewedActionExecutor implements ActionExecutor {

	@Override
	public void execute(
			KaleoAction kaleoAction, ExecutionContext executionContext)
		throws ActionExecutorException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		try {
			ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
				kaleoInstanceToken.getClassPK());

			Map<String, Serializable> values = objectEntry.getValues();

			values.put("applicationStatus", "reviewed");

			long userId = _userLocalService.getUserIdByEmailAddress(
				kaleoInstanceToken.getCompanyId(),
				"ryan.underwriter@mailinator.com");

			_objectEntryLocalService.updateObjectEntry(
				userId, kaleoInstanceToken.getClassPK(), values,
				executionContext.getServiceContext());
		}
		catch (PortalException portalException) {
			throw new ActionExecutorException(portalException);
		}
	}

	@Override
	public String getActionExecutorKey() {
		return "java";
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}