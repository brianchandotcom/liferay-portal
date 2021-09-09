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

package com.liferay.commerce.punchout.internal.model.listener;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.punchout.service.PunchOutAccountRoleHelper;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(enabled = false, immediate = true, service = ModelListener.class)
public class CommerceOrderModelListener
	extends BaseModelListener<CommerceOrder> {

	@Override
	public void onAfterUpdate(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder) {

		try {
			if ((commerceOrder.getStatus() != WorkflowConstants.STATUS_DRAFT) ||
				!_punchOutAccountRoleHelper.hasPunchOutRole(
					commerceOrder.getUserId(),
					commerceOrder.getCommerceAccountId())) {

				return;
			}

			_commerceOrderLocalService.updateStatus(
				commerceOrder.getUserId(), commerceOrder.getCommerceOrderId(),
				WorkflowConstants.STATUS_APPROVED, new ServiceContext(),
				Collections.emptyMap());
		}
		catch (PortalException portalException) {
			_log.error(
				"Failed to update workflow status to Approved on punch out " +
					"order" + commerceOrder.getCommerceOrderId(),
				portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderModelListener.class);

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private PunchOutAccountRoleHelper _punchOutAccountRoleHelper;

}