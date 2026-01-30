/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.model.listener;

import com.liferay.configuration.admin.util.ConfigurationFilterStringUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;

import java.io.IOException;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan McCann
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterRemove(Group group) throws ModelListenerException {
		try {
			_onAfterRemove(group);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _onAfterRemove(Group group) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getGroupScopedFilterString(
				group.getCompanyId(), group.getGroupId(), null));

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			try {
				configuration.delete();
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupModelListener.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}