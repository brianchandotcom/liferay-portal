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

package com.liferay.portal.fabric.netty.worker;

import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.local.ReturnProcessCallable;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.util.SerializableUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricWorkerConfigTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		long id = 10;

		try {
			new NettyFabricWorkerConfig<String>(id, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Process config is null", npe.getMessage());
		}

		Builder builder = new Builder();

		ProcessConfig processConfig = builder.build();

		try {
			new NettyFabricWorkerConfig<String>(0, processConfig, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Process callable is null", npe.getMessage());
		}

		ProcessCallable<String> processCallable = new ReturnProcessCallable(
			null);

		NettyFabricWorkerConfig<String> nettyFabricWorkerConfig =
			new NettyFabricWorkerConfig<String>(
				id, processConfig, processCallable);

		Assert.assertEquals(id, nettyFabricWorkerConfig.getId());
		Assert.assertSame(
			processConfig, nettyFabricWorkerConfig.getProcessConfig());
		Assert.assertSame(
			processCallable, nettyFabricWorkerConfig.getProcessCallable());
	}

	@Test
	public void testSerialization() throws ProcessException {
		long id = 10;

		List<String> arguments = Arrays.asList("x", "y", "z");

		String bootstrapClassPath = "bootstrapClassPath";
		String javaExecutable = "java";
		String runtimeClassPath = "runtimeClassPath";

		Builder builder = new Builder();

		builder.setArguments(arguments);
		builder.setBootstrapClassPath(bootstrapClassPath);
		builder.setJavaExecutable(javaExecutable);
		builder.setReactClassLoader(
			NettyFabricWorkerConfigTest.class.getClassLoader());
		builder.setRuntimeClassPath(runtimeClassPath);

		ProcessCallable<String> processCallable = new ReturnProcessCallable(
			"Test ProcessCallable");

		NettyFabricWorkerConfig<String> copyNettyFabricWorkerConfig =
			(NettyFabricWorkerConfig<String>)SerializableUtil.deserialize(
				SerializableUtil.serialize(
					new NettyFabricWorkerConfig<String>(
						id, builder.build(), processCallable)));

		Assert.assertEquals(id, copyNettyFabricWorkerConfig.getId());

		ProcessConfig copyProcessConfig =
			copyNettyFabricWorkerConfig.getProcessConfig();

		Assert.assertEquals(arguments, copyProcessConfig.getArguments());
		Assert.assertEquals(
			bootstrapClassPath, copyProcessConfig.getBootstrapClassPath());
		Assert.assertEquals(
			javaExecutable, copyProcessConfig.getJavaExecutable());
		Assert.assertNull(copyProcessConfig.getReactClassLoader());
		Assert.assertEquals(
			runtimeClassPath, copyProcessConfig.getRuntimeClassPath());

		ProcessCallable<String> copyProcessCallable =
			copyNettyFabricWorkerConfig.getProcessCallable();

		Assert.assertEquals(processCallable.call(), copyProcessCallable.call());
	}

}