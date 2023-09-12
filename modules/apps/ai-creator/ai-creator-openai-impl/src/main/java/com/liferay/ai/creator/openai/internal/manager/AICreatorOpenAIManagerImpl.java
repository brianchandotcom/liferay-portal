/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.internal.manager;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.ai.creator.openai.manager.AICreatorOpenAIManager;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = AICreatorOpenAIManager.class)
public class AICreatorOpenAIManagerImpl implements AICreatorOpenAIManager {

	@Override
	public boolean isAICreatorToolbarEnabled(
		long companyId, long groupId, String portletNamespace) {

		if (!Objects.equals(
				portletNamespace,
				_portal.getPortletNamespace(JournalPortletKeys.JOURNAL))) {

			return false;
		}

		try {
			if (_aiCreatorOpenAIConfigurationManager.
					isAICreatorOpenAIGroupEnabled(companyId, groupId)) {

				return true;
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AICreatorOpenAIManagerImpl.class);

	@Reference
	private AICreatorOpenAIConfigurationManager
		_aiCreatorOpenAIConfigurationManager;

	@Reference
	private Portal _portal;

}