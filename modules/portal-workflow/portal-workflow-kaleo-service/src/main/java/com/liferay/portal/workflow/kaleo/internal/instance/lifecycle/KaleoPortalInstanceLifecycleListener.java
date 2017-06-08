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

package com.liferay.portal.workflow.kaleo.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.model.Company;
import com.liferay.portal.workflow.kaleo.manager.PortalKaleoManager;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class KaleoPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_portalKaleoManager.deployKaleoDefaults(company.getCompanyId());
	}

	@Reference(unbind = "-")
	protected void setKaleoConditionLocalService(
		KaleoConditionLocalService kaleoConditionLocalService) {
	}

	@Reference(unbind = "-")
	protected void setKaleoDefinitionLocalService(
		KaleoDefinitionLocalService kaleoDefinitionLocalService) {
	}

	@Reference(unbind = "-")
	protected void setKaleoNodeLocalService(
		KaleoNodeLocalService kaleoNodeLocalService) {
	}

	@Reference(unbind = "-")
	protected void setKaleoTaskLocalService(
		KaleoTaskLocalService kaleoTaskLocalService) {
	}

	@Reference(unbind = "-")
	protected void setKaleoTransitionLocalService(
		KaleoTransitionLocalService kaleoTransitionLocalService) {
	}

	@Reference(unbind = "-")
	protected void setPortalKaleoManager(
		PortalKaleoManager portalKaleoManager) {

		_portalKaleoManager = portalKaleoManager;
	}

	private PortalKaleoManager _portalKaleoManager;

}