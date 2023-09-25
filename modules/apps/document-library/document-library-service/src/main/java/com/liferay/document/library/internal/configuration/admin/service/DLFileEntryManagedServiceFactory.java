/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.admin.service;

import com.liferay.document.library.internal.configuration.helper.DLFileEntryConfigurationHelper;
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
 * @author Marco Galluzzi
 */
@Component(
	property = Constants.SERVICE_PID + "=com.liferay.document.library.configuration.DLFileEntryConfiguration.scoped",
	service = ManagedServiceFactory.class
)
public class DLFileEntryManagedServiceFactory implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_dlFileEntryConfigurationHelper.unmapPid(pid);
	}

	@Override
	public String getName() {
		return "com.liferay.document.library.internal.configuration." +
			"DLFileEntryConfiguration.scoped";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_dlFileEntryConfigurationHelper.unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_dlFileEntryConfigurationHelper.updateCompanyConfiguration(
				companyId, pid, dictionary);
		}

		long groupId = GetterUtil.getLong(
			dictionary.get("groupId"), GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			_dlFileEntryConfigurationHelper.updateGroupConfiguration(
				groupId, pid, dictionary);
		}
	}

	@Reference
	private DLFileEntryConfigurationHelper _dlFileEntryConfigurationHelper;

}