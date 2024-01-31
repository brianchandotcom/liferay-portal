/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;

/**
 * Provides a wrapper for {@link KaleoDefinitionService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionService
 * @generated
 */
public class KaleoDefinitionServiceWrapper
	implements KaleoDefinitionService, ServiceWrapper<KaleoDefinitionService> {

	public KaleoDefinitionServiceWrapper() {
		this(null);
	}

	public KaleoDefinitionServiceWrapper(
		KaleoDefinitionService kaleoDefinitionService) {

		_kaleoDefinitionService = kaleoDefinitionService;
	}

	@Override
	public KaleoDefinition addKaleoDefinition(
			String name, String title, String description, String content,
			String scope, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionService.addKaleoDefinition(
			name, title, description, content, scope, version, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoDefinitionService.getOSGiServiceIdentifier();
	}

	@Override
	public KaleoDefinition updateKaleoDefinition(
			long kaleoDefinitionId, String title, String description,
			String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionService.updateKaleoDefinition(
			kaleoDefinitionId, title, description, content, serviceContext);
	}

	@Override
	public KaleoDefinitionService getWrappedService() {
		return _kaleoDefinitionService;
	}

	@Override
	public void setWrappedService(
		KaleoDefinitionService kaleoDefinitionService) {

		_kaleoDefinitionService = kaleoDefinitionService;
	}

	private KaleoDefinitionService _kaleoDefinitionService;

}