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

package com.liferay.portlet.expando.util;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;

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

		Map<String, Serializable> serviceContextAttributes =
			serviceContext.getExpandoBridgeAttributes();

		for (String key : serviceContextAttributes.keySet()) {
			expandoBridgeAttributes.put(key, serviceContextAttributes.get(key));
		}

		newExpandoBridge.setAttributes(expandoBridgeAttributes, false);
	}

}