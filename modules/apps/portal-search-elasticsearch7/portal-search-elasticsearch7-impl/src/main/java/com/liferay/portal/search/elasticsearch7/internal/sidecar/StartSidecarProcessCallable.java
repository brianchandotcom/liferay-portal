/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.reflect.Method;

import org.elasticsearch.common.transport.BoundTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.http.HttpServerTransport;

/**
 * @author Tina Tian
 */
public class StartSidecarProcessCallable implements ProcessCallable<String> {

	public StartSidecarProcessCallable(String[] arguments) {
		_arguments = arguments;
	}

	@Override
	public String call() throws ProcessException {
		Object nodeObject = ElasticsearchServerUtil.start(_arguments);

		try {
			ClassLoader classLoader =
				StartSidecarProcessCallable.class.getClassLoader();

			Method injectorMethod = ReflectionUtil.getDeclaredMethod(
				classLoader.loadClass("org.elasticsearch.node.Node"),
				"injector");

			Object injectorObject = injectorMethod.invoke(nodeObject);

			Method method = ReflectionUtil.getDeclaredMethod(
				classLoader.loadClass(
					"org.elasticsearch.injection.guice.Injector"),
				"getInstance", Class.class);

			HttpServerTransport httpServerTransport =
				(HttpServerTransport)method.invoke(
					injectorObject, HttpServerTransport.class);

			BoundTransportAddress boundTransportAddress =
				httpServerTransport.boundAddress();

			TransportAddress publishAddress =
				boundTransportAddress.publishAddress();

			return publishAddress.toString();
		}
		catch (Exception exception) {
			throw new ProcessException(exception);
		}
	}

	private static final long serialVersionUID = 1L;

	private final String[] _arguments;

}