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

package com.liferay.portal.workflow.kaleo.model.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.workflow.kaleo.manager.PortalKaleoManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.springframework.context.ApplicationContext;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterRemove(Company company) throws ModelListenerException {
		try {
			_portalKaleoManager.deleteKaleoData(company.getCompanyId());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference(
		target = "(org.springframework.context.service.name=com.liferay.portal.workflow.kaleo.service)",
		unbind = "-"
	)
	protected void setApplicationContext(
		ApplicationContext applicationContext) {
	}

	@Reference(unbind = "-")
	protected void setPortalKaleoManager(
		PortalKaleoManager portalKaleoManager) {

		_portalKaleoManager = portalKaleoManager;
	}

	private PortalKaleoManager _portalKaleoManager;

}