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

package com.liferay.portal.fabric.status;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.Serializable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LocalFabricStatusTest extends BaseFabricStatusTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testObjectNames() {
		doTestObjectNames(LocalFabricStatus.INSTANCE);
	}

	@Test
	public void testProcessCallableExecutor() throws Exception {
		JMXProxyUtil.ProcessCallableExecutor processCallableExecutor =
			LocalFabricStatus.processCallableExecutor;

		final Serializable serializable = new Serializable() {
		};

		Future<Serializable> future = processCallableExecutor.execute(
			new ProcessCallable<Serializable>() {

				@Override
				public Serializable call() {
					return serializable;
				}

			});

		Assert.assertSame(serializable, future.get());

		final ProcessException processException = new ProcessException(
			StringPool.BLANK);

		future = processCallableExecutor.execute(
			new ProcessCallable<Serializable>() {

				@Override
				public Serializable call() throws ProcessException {
					throw processException;
				}

			});

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(processException, ee.getCause());
		}
	}

}