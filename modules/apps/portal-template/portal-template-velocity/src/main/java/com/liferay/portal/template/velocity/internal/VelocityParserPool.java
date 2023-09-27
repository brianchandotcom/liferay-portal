/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.velocity.internal;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.velocity.runtime.ParserPool;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.Parser;

/**
 * @author Preston Crary
 */
public class VelocityParserPool implements ParserPool {

	@Override
	public Parser get() {
		Parser parser = _parsers.poll();

		if (parser == null) {
			parser = _runtimeServices.createNewParser();
		}
		else {
			_counter.getAndDecrement();
		}

		return parser;
	}

	@Override
	public void initialize(RuntimeServices runtimeServices) {
		_maxSize = runtimeServices.getInt("parser.pool.size", 20);

		_runtimeServices = runtimeServices;
	}

	@Override
	public void put(Parser parser) {
		if (_counter.get() < _maxSize) {
			_counter.getAndIncrement();

			_parsers.offer(parser);
		}
	}

	private final AtomicInteger _counter = new AtomicInteger();
	private int _maxSize;
	private final Queue<Parser> _parsers = new ConcurrentLinkedQueue<>();
	private RuntimeServices _runtimeServices;

}