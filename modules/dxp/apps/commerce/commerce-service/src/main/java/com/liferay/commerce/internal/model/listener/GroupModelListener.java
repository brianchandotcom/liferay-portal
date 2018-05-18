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

import com.liferay.commerce.service.CommerceAvailabilityRangeLocalService;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceWarehouseLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
@Component(immediate = true, service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setLanguageId(group.getDefaultLanguageId());
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(group.getCreatorUserId());

		try {
			_commerceCountryLocalService.importDefaultCountries(serviceContext);
			_commerceWarehouseLocalService.importDefaultCommerceWarehouse(
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onBeforeRemove(Group group) {
		try {
			_commerceAvailabilityRangeLocalService.
				deleteCommerceAvailabilityRanges(group.getGroupId());
			_commerceOrderLocalService.deleteCommerceOrders(group.getGroupId());
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupModelListener.class);

	@Reference
	private CommerceAvailabilityRangeLocalService
		_commerceAvailabilityRangeLocalService;

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceWarehouseLocalService _commerceWarehouseLocalService;

}