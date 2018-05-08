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

package com.liferay.portal.instance.lifecycle.internal;

import com.liferay.portal.instance.lifecycle.Clusterable;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.instance.lifecycle.PortalInstanceLifecycleManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = PortalInstanceLifecycleManager.class)
public class PortalInstanceLifecycleListenerManagerImpl
	implements PortalInstanceLifecycleManager {

	@Override
	public void preregisterCompany(long companyId) {
		for (PortalInstanceLifecycleListener portalInstanceLifecycleListener :
				_portalInstanceLifecycleListeners) {

			preregisterCompany(portalInstanceLifecycleListener, companyId);
		}
	}

	@Override
	public void registerCompany(Company company) {
		_companies.add(company);

		for (PortalInstanceLifecycleListener portalInstanceLifecycleListener :
				_portalInstanceLifecycleListeners) {

			registerCompany(portalInstanceLifecycleListener, company);
		}
	}

	@Override
	public void unregisterCompany(Company company) {
		_companies.remove(company);

		for (PortalInstanceLifecycleListener portalInstanceLifecycleListener :
				_portalInstanceLifecycleListeners) {

			unregisterCompany(portalInstanceLifecycleListener, company);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removePortalInstanceLifecycleListener"
	)
	protected void addPortalInstanceLifecycleListener(
		PortalInstanceLifecycleListener portalInstanceLifecycleListener) {

		_portalInstanceLifecycleListeners.add(portalInstanceLifecycleListener);

		if (_companies.isEmpty()) {
			return;
		}

		for (Company company : _companies) {
			registerCompany(portalInstanceLifecycleListener, company);
		}
	}

	protected void preregisterCompany(
		PortalInstanceLifecycleListener portalInstanceLifecycleListener,
		long companyId) {

		if (!(portalInstanceLifecycleListener instanceof Clusterable) &&
			!clusterMasterExecutor.isMaster()) {

			if (_log.isDebugEnabled()) {
				_log.debug("Skipping " + portalInstanceLifecycleListener);
			}

			return;
		}

		portalInstanceLifecycleListener.portalInstancePreregistered(companyId);
	}

	protected void registerCompany(
		PortalInstanceLifecycleListener portalInstanceLifecycleListener,
		Company company) {

		if (!(portalInstanceLifecycleListener instanceof Clusterable) &&
			!clusterMasterExecutor.isMaster()) {

			if (_log.isDebugEnabled()) {
				_log.debug("Skipping " + portalInstanceLifecycleListener);
			}

			return;
		}

		Long companyId = CompanyThreadLocal.getCompanyId();

		try {
			CompanyThreadLocal.setCompanyId(company.getCompanyId());

			portalInstanceLifecycleListener.portalInstanceRegistered(company);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to register portal instance " + company, e);
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
	}

	protected void removePortalInstanceLifecycleListener(
		PortalInstanceLifecycleListener portalInstanceLifecycleListener) {

		if (!(portalInstanceLifecycleListener instanceof Clusterable) &&
			!clusterMasterExecutor.isMaster()) {

			if (_log.isDebugEnabled()) {
				_log.debug("Skipping " + portalInstanceLifecycleListener);
			}

			return;
		}

		_portalInstanceLifecycleListeners.remove(
			portalInstanceLifecycleListener);
	}

	protected void unregisterCompany(
		PortalInstanceLifecycleListener portalInstanceLifecycleListener,
		Company company) {

		if (!(portalInstanceLifecycleListener instanceof Clusterable) &&
			!clusterMasterExecutor.isMaster()) {

			if (_log.isDebugEnabled()) {
				_log.debug("Skipping " + portalInstanceLifecycleListener);
			}

			return;
		}

		try {
			portalInstanceLifecycleListener.portalInstanceUnregistered(company);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to register portal instance " + company, e);
			}
		}
	}

	@Reference
	protected ClusterMasterExecutor clusterMasterExecutor;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstanceLifecycleListenerManagerImpl.class);

	private final Set<Company> _companies = new CopyOnWriteArraySet<>();
	private final Set<PortalInstanceLifecycleListener>
		_portalInstanceLifecycleListeners = new CopyOnWriteArraySet<>();

}