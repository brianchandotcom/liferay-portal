/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.velocity.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.app.event.MethodExceptionEventHandler;

/**
 * @author Raymond Augé
 * @author Peter Shin
 */
public class LiferayMethodExceptionEventHandler
	implements MethodExceptionEventHandler {

	@Override
	public Object methodException(
			@SuppressWarnings("rawtypes") Class clazz, String method,
			Exception exception)
		throws Exception {

		_log.error(
			StringBundler.concat(
				"Unable to execute method ", method, " {exception=", exception,
				StringPool.COMMA_AND_SPACE, _getKeyValuePair(clazz),
				StringPool.CLOSE_CURLY_BRACE),
			exception);

		return null;
	}

	private String _getKeyValuePair(Class<?> clazz) {
		if (clazz == null) {
			return "class=null";
		}

		if (!ProxyUtil.isProxyClass(clazz)) {
			return "className=" + clazz.getName();
		}

		Class<?>[] interfaceClasses = clazz.getInterfaces();

		if (interfaceClasses == null) {
			return "className=" + clazz.getName();
		}

		List<String> proxyInterfaceClassNames = new ArrayList<>();

		for (Class<?> interfaceClass : interfaceClasses) {
			proxyInterfaceClassNames.add(interfaceClass.getName());
		}

		String mergedProxyInterfaceClassNames = StringUtil.merge(
			proxyInterfaceClassNames, StringPool.COMMA_AND_SPACE);

		return "proxyInterfaceClassNames=" + mergedProxyInterfaceClassNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayMethodExceptionEventHandler.class);

}