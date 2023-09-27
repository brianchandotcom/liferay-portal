/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.webdav.methods;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.webdav.methods.MethodFactory;
import com.liferay.portal.kernel.webdav.methods.MethodFactoryRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class MethodFactoryRegistryImpl implements MethodFactoryRegistry {

	@Override
	public MethodFactory getDefaultMethodFactory() {
		return _defaultMethodFactory;
	}

	@Override
	public List<MethodFactory> getMethodFactories() {
		return ListUtil.fromMapValues(_methodFactories);
	}

	@Override
	public MethodFactory getMethodFactory(String className) {
		return _methodFactories.get(className);
	}

	@Override
	public void registerMethodFactory(MethodFactory methodFactory) {
		Class<?> clazz = methodFactory.getClass();

		MethodFactory previousMethodFactory = _methodFactories.put(
			clazz.getName(), methodFactory);

		if (previousMethodFactory == _defaultMethodFactory) {
			_defaultMethodFactory = methodFactory;
		}

		if (_log.isWarnEnabled() && (previousMethodFactory != null)) {
			_log.warn(
				StringBundler.concat(
					"Replacing ", previousMethodFactory, " for class name ",
					clazz.getName(), " with ", methodFactory));
		}
	}

	public void setDefaultMethodFactory(MethodFactory defaultMethodFactory) {
		_defaultMethodFactory = defaultMethodFactory;
	}

	@Override
	public void unregisterMethodFactory(MethodFactory methodFactory) {
		Class<?> clazz = methodFactory.getClass();

		_methodFactories.remove(clazz.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MethodFactoryRegistryImpl.class);

	private MethodFactory _defaultMethodFactory;
	private final Map<String, MethodFactory> _methodFactories =
		new ConcurrentHashMap<>();

}