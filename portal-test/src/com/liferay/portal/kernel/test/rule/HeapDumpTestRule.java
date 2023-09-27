/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.rule;

import com.liferay.petra.process.EchoOutputProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.util.HeapUtil;

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
					StringBundler.concat(description, "-", date, "-before.bin"),
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
							description, "-", date, "-after.bin"),
						EchoOutputProcessor.INSTANCE);

					future.get();
				}
			}

		};
	}

	private final boolean _live;

}