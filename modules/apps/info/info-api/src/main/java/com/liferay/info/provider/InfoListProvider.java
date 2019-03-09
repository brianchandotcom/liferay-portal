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

package com.liferay.info.provider;

import aQute.bnd.annotation.ConsumerType;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
@ConsumerType
public interface InfoListProvider<T> {

	public List<T> getInfoList(Map<String, Object> contextMap);

	public int getInfoListCount(Map<String, Object> contextMap);

	public String getLabel(Locale locale);

	public default boolean isAvailable(Map<String, Object> contextMap) {
		return true;
	}

}