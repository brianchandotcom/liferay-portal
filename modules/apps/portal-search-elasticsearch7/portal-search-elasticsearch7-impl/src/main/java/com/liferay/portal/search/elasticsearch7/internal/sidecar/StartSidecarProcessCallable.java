/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;

import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.transport.BoundTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.http.HttpServerTransport;
import org.elasticsearch.node.Node;

/**
 * @author Tina Tian
 */
public class StartSidecarProcessCallable implements ProcessCallable<String> {

	public StartSidecarProcessCallable(String[] arguments) {
		_arguments = arguments;
	}

	@Override
	public String call() throws ProcessException {
		Node node = ElasticsearchServerUtil.start(_arguments);

		Injector injector = node.injector();

		HttpServerTransport httpServerTransport = injector.getInstance(
			HttpServerTransport.class);

		BoundTransportAddress boundTransportAddress =
			httpServerTransport.boundAddress();

		TransportAddress publishAddress =
			boundTransportAddress.publishAddress();

		return publishAddress.toString();
	}

	private static final long serialVersionUID = 1L;

	private final String[] _arguments;

}