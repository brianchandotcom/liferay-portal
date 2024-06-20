/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.instance.lifecycle;

import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.model.Company;

import java.util.HashMap;
import java.util.List;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class ConfigurationCleanupPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstancePreunregistered(Company company)
		throws Exception {

		if (!DBPartition.isPartitionEnabled()) {
			return;
		}

		List<String> pids = DBPartitionUtil.getConfigurationPids(
			company.getCompanyId());

		_configMap.put(company.getCompanyId(), pids);
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		if (!DBPartition.isPartitionEnabled()) {
			return;
		}

		for (String pid : _configMap.get(company.getCompanyId())) {
			Configuration configuration = _configurationAdmin.getConfiguration(
				pid, "?");

			if (configuration != null) {
				configuration.delete();
			}
		}

		_configMap.remove(company.getCompanyId());
	}

	private static final HashMap<Long, List<String>> _configMap =
		new HashMap<>();

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}