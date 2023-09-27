/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Zsigmond Rab
 */
public class ExpandoBridgeUtil {

	public static void copyExpandoBridgeAttributes(
		ExpandoBridge oldExpandoBridge, ExpandoBridge newExpandoBridge) {

		newExpandoBridge.setAttributes(
			oldExpandoBridge.getAttributes(false), false);
	}

	public static void setExpandoBridgeAttributes(
		ExpandoBridge oldExpandoBridge, ExpandoBridge newExpandoBridge,
		ServiceContext serviceContext) {

		Map<String, Serializable> expandoBridgeAttributes =
			oldExpandoBridge.getAttributes(false);

		expandoBridgeAttributes.putAll(
			serviceContext.getExpandoBridgeAttributes());

		newExpandoBridge.setAttributes(expandoBridgeAttributes, false);
	}

}