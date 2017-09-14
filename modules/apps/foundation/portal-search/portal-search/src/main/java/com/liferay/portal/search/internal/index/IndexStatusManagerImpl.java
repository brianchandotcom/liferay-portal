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

package com.liferay.portal.search.internal.index;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.search.configuration.IndexStatusManagerConfiguration;
import com.liferay.portal.search.index.IndexStatusManager;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.IndexStatusManagerConfiguration",
	immediate = true, service = IndexStatusManager.class
)
public class IndexStatusManagerImpl implements IndexStatusManager {

	@Override
	public boolean isIndexReadOnly() {
		if (IndexStatusManagerThreadLocal.isIndexReadOnly() || _indexReadOnly) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isIndexReadOnly(String className) {
		return _indexReadOnlyModels.contains(className);
	}

	@Override
	public void setIndexReadOnly(boolean indexReadOnly) {
		_indexReadOnly = indexReadOnly;
	}

	@Override
	public void setIndexReadOnly(String className, boolean indexReadOnly) {
		if (indexReadOnly) {
			_indexReadOnlyModels.add(className);
		}
		else {
			_indexReadOnlyModels.remove(className);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		IndexStatusManagerConfiguration indexStatusManagerConfiguration =
			ConfigurableUtil.createConfigurable(
				IndexStatusManagerConfiguration.class, properties);

		_indexReadOnly = indexStatusManagerConfiguration.indexReadOnly();
	}

	private volatile boolean _indexReadOnly;
	private final Set<String> _indexReadOnlyModels = Collections.newSetFromMap(
		new ConcurrentHashMap<>());

}