/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.rest.internal.dto.v1_0.util;

import com.liferay.dispatch.rest.dto.v1_0.DispatchTrigger;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nilton Vieira
 */
public class DispatchTriggerUtil {

	public static DispatchTrigger toDispatchTrigger(
		com.liferay.dispatch.model.DispatchTrigger dispatchTrigger) {

		return new DispatchTrigger() {
			{
				active = dispatchTrigger.getActive();
				companyId = dispatchTrigger.getCompanyId();
				cronExpression = dispatchTrigger.getCronExpression();
				dispatchTaskClusterMode =
					dispatchTrigger.getDispatchTaskClusterMode();
				dispatchTaskExecutorType =
					dispatchTrigger.getDispatchTaskExecutorType();
				dispatchTaskSettings = toSettingsMap(
					dispatchTrigger.getDispatchTaskSettingsUnicodeProperties());
				endDate = dispatchTrigger.getEndDate();
				externalReferenceCode =
					dispatchTrigger.getExternalReferenceCode();
				id = dispatchTrigger.getDispatchTriggerId();
				name = dispatchTrigger.getName();
				overlapAllowed = dispatchTrigger.getOverlapAllowed();
				startDate = dispatchTrigger.getStartDate();
				system = dispatchTrigger.getSystem();
				timeZoneId = dispatchTrigger.getTimeZoneId();
				userId = dispatchTrigger.getUserId();
			}
		};
	}

	public static Map<String, String> toSettingsMap(
		UnicodeProperties settingsUnicodeProperties) {

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<String, String> entry :
				settingsUnicodeProperties.entrySet()) {

			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}

	public static UnicodeProperties toSettingsUnicodeProperties(
		Map<String, ?> parameters) {

		if (parameters == null) {
			return new UnicodeProperties();
		}

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<String, ?> entry : parameters.entrySet()) {
			map.put(entry.getKey(), String.valueOf(entry.getValue()));
		}

		return UnicodePropertiesBuilder.create(
			map, true
		).build();
	}

}