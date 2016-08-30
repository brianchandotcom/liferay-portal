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

package com.liferay.portal.search.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.SearchEngineHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class SearchIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstancePreregistered(long companyId) {
		try {
			if (_clusterMasterExecutor.isMaster()) {
				_searchEngineHelper.initialize(companyId);
			}
		}
		catch (Exception e) {
			_log.error("Search engine failure initializing " + companyId, e);
		}
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try {
			if (_clusterMasterExecutor.isMaster()) {
				_searchEngineHelper.initialize(company.getCompanyId());
			}
		}
		catch (Exception e) {
			_log.error("Search engine failure initializing " + company, e);
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		try {
			if (_clusterMasterExecutor.isMaster()) {
				_searchEngineHelper.removeCompany(company.getCompanyId());
			}
		}
		catch (Exception e) {
			_log.error("Search engine failure removing " + company, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchIndexPortalInstanceLifecycleListener.class);

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private SearchEngineHelper _searchEngineHelper;

}