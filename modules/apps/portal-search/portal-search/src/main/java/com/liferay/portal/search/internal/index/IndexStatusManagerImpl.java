/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.search.configuration.IndexStatusManagerConfiguration;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.tools.DBUpgrader;

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
	service = IndexStatusManager.class
)
public class IndexStatusManagerImpl implements IndexStatusManager {

	@Override
	public boolean isIndexReadOnly() {
		if (DBUpgrader.isUpgradeClient() ||
			IndexStatusManagerThreadLocal.isIndexReadOnly() || _indexReadOnly ||
			StartupHelperUtil.isUpgrading()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isIndexReadOnly(String className) {
		return _indexReadOnlyModels.contains(className);
	}

	@Override
	public void requireIndexReadWrite(boolean required) {
		if (required) {
			if (_indexReadOnly) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						new Throwable(
							StringBundler.concat(
								"Setting index to Read-Write as required by ",
								"incoming operation, overriding currently set ",
								"Read-Only (see exception for Read-Write call ",
								"stack and nested exception for Read-Only ",
								"call stack)"),
							_indexReadOnlyCallStackThrowable));
				}

				_indexReadOnly = false;
			}

			_requireIndexReadWriteCallStackThrowable = new Throwable();
		}

		_readWriteRequired = required;
	}

	@Override
	public void setIndexReadOnly(boolean indexReadOnly) {
		if (indexReadOnly) {
			if (_readWriteRequired) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						new Throwable(
							StringBundler.concat(
								"Suppressing attempt to set index to ",
								"Read-Only while ongoing operation requires ",
								"Read-Write (see exception for Read-Only call ",
								"stack and nested exception for Read-Write ",
								"call stack)"),
							_requireIndexReadWriteCallStackThrowable));
				}

				return;
			}

			_indexReadOnlyCallStackThrowable = new Throwable();
		}

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

	private static final Log _log = LogFactoryUtil.getLog(
		IndexStatusManagerImpl.class);

	private volatile boolean _indexReadOnly;
	private Throwable _indexReadOnlyCallStackThrowable;
	private final Set<String> _indexReadOnlyModels = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private boolean _readWriteRequired;
	private Throwable _requireIndexReadWriteCallStackThrowable;

}