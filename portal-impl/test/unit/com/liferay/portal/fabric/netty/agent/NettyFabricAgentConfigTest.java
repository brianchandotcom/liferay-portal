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

package com.liferay.portal.fabric.netty.agent;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.util.SerializableUtil;

import java.io.File;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentConfigTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		try {
			new NettyFabricAgentConfig(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		File repositoryFolder = new File("RepositoryFolder");

		NettyFabricAgentConfig nettyFabricAgentConfig =
			new NettyFabricAgentConfig(repositoryFolder);

		Assert.assertEquals(
			repositoryFolder.toPath(),
			nettyFabricAgentConfig.getRepositoryPath());
	}

	@Test
	public void testSerialization() {
		File repositoryFolder = new File("RepositoryFolder");

		NettyFabricAgentConfig copyNettyFabricAgentConfig =
			(NettyFabricAgentConfig)SerializableUtil.deserialize(
				SerializableUtil.serialize(
					new NettyFabricAgentConfig(repositoryFolder)));

		Assert.assertEquals(
			repositoryFolder.toPath(),
			copyNettyFabricAgentConfig.getRepositoryPath());
	}

}