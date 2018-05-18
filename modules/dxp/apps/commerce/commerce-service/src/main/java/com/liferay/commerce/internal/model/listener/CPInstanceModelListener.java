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

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceWarehouseItemLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = ModelListener.class)
public class CPInstanceModelListener extends BaseModelListener<CPInstance> {

	@Override
	public void onBeforeRemove(CPInstance cpInstance) {
		try {
			long cpInstanceId = cpInstance.getCPInstanceId();

			_commerceOrderItemLocalService.
				deleteCommerceOrderItemsByCPInstanceId(cpInstanceId);
			_commerceWarehouseItemLocalService.deleteCommerceWarehouseItems(
				cpInstanceId);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceModelListener.class);

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceWarehouseItemLocalService
		_commerceWarehouseItemLocalService;

}