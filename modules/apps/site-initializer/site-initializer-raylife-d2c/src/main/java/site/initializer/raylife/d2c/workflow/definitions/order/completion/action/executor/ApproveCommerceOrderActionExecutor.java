/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package site.initializer.raylife.d2c.workflow.definitions.order.completion.action.executor;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = ActionExecutor.class)
public class ApproveCommerceOrderActionExecutor implements ActionExecutor {

	@Override
	public void execute(
			KaleoAction kaleoAction, ExecutionContext executionContext)
		throws ActionExecutorException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		try {
			CommerceOrder commerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					kaleoInstanceToken.getClassPK());

			commerceOrder.setOrderStatus(
				CommerceOrderConstants.ORDER_STATUS_PROCESSING);

			commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
				commerceOrder);

			commerceOrder.setOrderStatus(
				CommerceOrderConstants.ORDER_STATUS_COMPLETED);

			_commerceOrderLocalService.updateCommerceOrder(commerceOrder);
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
	private CommerceOrderLocalService _commerceOrderLocalService;

}