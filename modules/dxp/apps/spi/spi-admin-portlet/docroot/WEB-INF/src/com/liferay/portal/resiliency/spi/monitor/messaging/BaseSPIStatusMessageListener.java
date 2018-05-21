/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.monitor.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;
import com.liferay.portal.resiliency.spi.util.PortletKeys;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public abstract class BaseSPIStatusMessageListener extends BaseMessageListener {

	public void setInterestedStatus(Integer... interestedStatuses) {
		Collections.addAll(_interestedStatuses, interestedStatuses);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long spiDefinitionId = GetterUtil.getLong(
			message.get("spiDefinitionId"));

		if (spiDefinitionId == 0) {
			return;
		}

		int status = GetterUtil.getInteger(message.get("status"));

		if (!_interestedStatuses.contains(status)) {
			return;
		}

		SPIDefinition spiDefinition =
			SPIDefinitionLocalServiceUtil.getSPIDefinition(spiDefinitionId);

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			spiDefinition.getCompanyId());

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				spiDefinition.getCompanyId(), companyGroup.getGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP,
				PortletKeys.PREFS_PLID_SHARED, PortletKeys.SPI_ADMIN, null);

		processSPIStatus(portletPreferences, spiDefinition, status);
	}

	protected abstract void processSPIStatus(
			PortletPreferences portletPreferences, SPIDefinition spiDefinition,
			int status)
		throws Exception;

	private final Set<Integer> _interestedStatuses = new HashSet<>();

}