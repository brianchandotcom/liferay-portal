/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.EveryNodeEveryStartup;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.security.exportimport.UserImporter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class LDAPPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener
	implements EveryNodeEveryStartup {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!_clusterMasterExecutor.isMaster()) {
			return;
		}

		if (_ldapSettings.isImportOnStartup(company.getCompanyId())) {
			try {
				_userImporter.importUsers(company.getCompanyId());
			}
			catch (Exception exception) {
				_log.error(
					"Unable to import users for company " +
						company.getCompanyId(),
					exception);
			}
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPPortalInstanceLifecycleListener.class);

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private LDAPSettings _ldapSettings;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile UserImporter _userImporter;

}