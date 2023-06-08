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

package com.liferay.ai.creator.openai.internal.manager;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.ai.creator.openai.manager.AICreatorOpenAIManager;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
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

		if (!FeatureFlagManagerUtil.isEnabled("LPS-179483") ||
			!Objects.equals(
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