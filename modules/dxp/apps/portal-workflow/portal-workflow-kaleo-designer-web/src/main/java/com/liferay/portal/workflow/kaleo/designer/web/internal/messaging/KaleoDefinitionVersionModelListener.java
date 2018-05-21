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

package com.liferay.portal.workflow.kaleo.designer.web.internal.messaging;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionVersionModelListener
	extends BaseModelListener<KaleoDefinitionVersion> {

	@Override
	public void onAfterCreate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			ServiceContext serviceContext = getServiceContext();

			_resourceLocalService.addModelResources(
				kaleoDefinitionVersion, serviceContext);
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onAfterRemove(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			_resourceLocalService.deleteResource(
				kaleoDefinitionVersion, ResourceConstants.SCOPE_COMPANY);
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	protected ServiceContext getServiceContext() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setAddGuestPermissions(true);
			serviceContext.setAddGroupPermissions(true);
		}

		return serviceContext;
	}

	@Reference
	private ResourceLocalService _resourceLocalService;

}