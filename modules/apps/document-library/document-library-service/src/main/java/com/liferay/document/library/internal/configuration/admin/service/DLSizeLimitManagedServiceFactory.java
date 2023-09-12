/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.admin.service;

import com.liferay.document.library.internal.configuration.helper.DLSizeLimitConfigurationHelper;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = Constants.SERVICE_PID + "=com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration.scoped",
	service = ManagedServiceFactory.class
)
public class DLSizeLimitManagedServiceFactory implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_dlSizeLimitConfigurationHelper.unmapPid(pid);
	}

	@Override
	public String getName() {
		return "com.liferay.document.library.internal.configuration." +
			"DLSizeLimitConfiguration.scoped";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_dlSizeLimitConfigurationHelper.unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_dlSizeLimitConfigurationHelper.updateCompanyConfiguration(
				companyId, pid, dictionary);
		}

		long groupId = GetterUtil.getLong(
			dictionary.get("groupId"), GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			_dlSizeLimitConfigurationHelper.updateGroupConfiguration(
				groupId, pid, dictionary);
		}
	}

	@Reference
	private DLSizeLimitConfigurationHelper _dlSizeLimitConfigurationHelper;

}