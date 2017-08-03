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

package com.liferay.lcs.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class CacheMetricsMessage extends MetricsMessage {

	public Map<String, Object> getHibernateMetrics() {
		return _hibernateMetrics;
	}

	public Map<String, Map<String, Object>> getLiferayMultiVMMetrics() {
		return _liferayMultiVMMetrics;
	}

	public Map<String, Map<String, Object>> getLiferaySingleVMMetrics() {
		return _liferaySingleVMMetrics;
	}

	public void setHibernateMetrics(Map<String, Object> hibernateMetrics) {
		_hibernateMetrics = hibernateMetrics;
	}

	public void setLiferayMultiVMMetrics(
		Map<String, Map<String, Object>> liferayMultiVMMetrics) {

		_liferayMultiVMMetrics = liferayMultiVMMetrics;
	}

	public void setLiferaySingleVMMetrics(
		Map<String, Map<String, Object>> liferaySingleVMMetrics) {

		_liferaySingleVMMetrics = liferaySingleVMMetrics;
	}

	private Map<String, Object> _hibernateMetrics =
		new HashMap<String, Object>();
	private Map<String, Map<String, Object>> _liferayMultiVMMetrics =
		new HashMap<String, Map<String, Object>>();
	private Map<String, Map<String, Object>> _liferaySingleVMMetrics =
		new HashMap<String, Map<String, Object>>();

}