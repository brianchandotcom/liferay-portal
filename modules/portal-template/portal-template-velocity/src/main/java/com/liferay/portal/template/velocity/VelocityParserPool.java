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

package com.liferay.portal.template.velocity;

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