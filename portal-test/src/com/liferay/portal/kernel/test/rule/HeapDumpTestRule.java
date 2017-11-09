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

package com.liferay.portal.kernel.test.rule;

import com.liferay.petra.process.EchoOutputProcessor;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.util.HeapUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Date;
import java.util.concurrent.Future;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class HeapDumpTestRule implements TestRule {

	public static final HeapDumpTestRule INSTANCE = new HeapDumpTestRule(true);

	public HeapDumpTestRule(boolean live) {
		_live = live;
	}

	@Override
	public Statement apply(
		final Statement statement, final Description description) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Date date = new Date();

				GCUtil.fullGC(_live);

				Future<?> future = HeapUtil.heapDump(
					_live, true,
					StringBundler.concat(
						description.toString(), "-", String.valueOf(date),
						"-before.bin"),
					EchoOutputProcessor.INSTANCE);

				future.get();

				try {
					statement.evaluate();
				}
				finally {
					GCUtil.fullGC(_live);

					future = HeapUtil.heapDump(
						_live, true,
						StringBundler.concat(
							description.toString(), "-", String.valueOf(date),
							"-after.bin"),
						EchoOutputProcessor.INSTANCE);

					future.get();
				}
			}

		};
	}

	private final boolean _live;

}