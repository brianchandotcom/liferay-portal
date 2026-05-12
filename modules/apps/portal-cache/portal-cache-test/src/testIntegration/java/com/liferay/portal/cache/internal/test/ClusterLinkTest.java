/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jiefeng Wu
 */
@RunWith(Arquillian.class)
public class ClusterLinkTest implements Serializable {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	public static final TomcatClusterTestRule tomcatClusterTestRule =
		new TomcatClusterTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		TomcatCluster.Builder builder1 =
			tomcatClusterTestRule.buildTomcatNode();

		_tomcatNode1 = builder1.build();

		_tomcatNode1.start(true);
	}

	@Test
	public void testControlChannelProperties() throws Exception {
		_testControlChannelProperties(
			false, "TCP",
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL + "=tcp.xml");
		_testControlChannelProperties(
			true, "UDP",
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL + "=udp.xml",
			"cluster.link.channel.properties.transport.0=udp.xml");
	}

	@Test
	public void testTransportChannelProperties() throws Exception {
		_testTransportChannelProperties(
			false, "TCP",
			"cluster.link.channel.properties.transport.0=tcp.xml");
		_testTransportChannelProperties(
			true, "UDP",
			"cluster.link.channel.properties.transport.0=udp.xml");
	}

	private static String _getControlChannelTransportName() {
		return SystemBundleUtil.callService(
			ClusterExecutor.class,
			clusterExecutor -> {
				Object clusterChannel = ReflectionTestUtil.getFieldValue(
					clusterExecutor, "_clusterChannel");

				Object jChannel = ReflectionTestUtil.getFieldValue(
					clusterChannel, "_jChannel");

				Object protocolStack = ReflectionTestUtil.invoke(
					jChannel, "getProtocolStack", new Class<?>[0]);

				Object transport = ReflectionTestUtil.invoke(
					protocolStack, "getTransport", new Class<?>[0]);

				return transport.getClass().getSimpleName();
			});
	}

	private static String _getTransportChannelTransportName() {
		return SystemBundleUtil.callService(
			ClusterLink.class,
			clusterLink -> {
				List<?> clusterChannels = ReflectionTestUtil.getFieldValue(
					clusterLink, "_clusterChannels");

				Object jChannel = ReflectionTestUtil.getFieldValue(
					clusterChannels.get(0), "_jChannel");

				Object protocolStack = ReflectionTestUtil.invoke(
					jChannel, "getProtocolStack", new Class<?>[0]);

				Object transport = ReflectionTestUtil.invoke(
					protocolStack, "getTransport", new Class<?>[0]);

				return transport.getClass().getSimpleName();
			});
	}

	private Closeable _applyPortalExtPropertiesLines(
			boolean keepStarted, TomcatNode tomcatNode,
			String... portalExtPropertiesLines)
		throws Exception {

		tomcatNode.stop();

		Path path = tomcatNode.getPortalExtPropertiesPath();

		byte[] bytes = Files.readAllBytes(path);

		Files.write(
			path, Arrays.asList(portalExtPropertiesLines),
			StandardOpenOption.APPEND);

		tomcatNode.start(true);

		return () -> {
			try {
				tomcatNode.stop();

				Files.write(
					path, bytes, StandardOpenOption.TRUNCATE_EXISTING,
					StandardOpenOption.WRITE);

				if (keepStarted) {
					tomcatNode.start(true);
				}
			}
			catch (Exception exception) {
				throw new IOException(exception);
			}
		};
	}

	private void _testControlChannelProperties(
			boolean keepStarted, String expectedTransportName,
			String... portalExtPropertiesLines)
		throws Exception {

		try (Closeable closeable = _applyPortalExtPropertiesLines(
				keepStarted, _tomcatNode1, portalExtPropertiesLines)) {

			// Assert portal-ext.properties lines are set correctly on node 1

			for (String portalExtLine : portalExtPropertiesLines) {
				List<String> parts = StringUtil.split(
					portalExtLine, CharPool.EQUAL);

				String key = parts.get(0);
				String expectedValue = parts.get(1);

				Assert.assertEquals(
					expectedValue,
					_tomcatNode1.syncExecute(() -> PropsUtil.get(key)));
			}

			// Assert node 1 can get its cluster node successfully

			Assert.assertNotNull(
				_tomcatNode1.syncExecute(
					ClusterExecutorUtil::getLocalClusterNode));

			Assert.assertEquals(
				expectedTransportName,
				_tomcatNode1.syncExecute(
					ClusterLinkTest::_getControlChannelTransportName));
		}
	}

	private void _testTransportChannelProperties(
			boolean keepStarted, String expectedTransportName,
			String... portalExtPropertiesLines)
		throws Exception {

		try (Closeable closeable = _applyPortalExtPropertiesLines(
				keepStarted, _tomcatNode1, portalExtPropertiesLines)) {

			// Assert portal-ext.properties lines are set correctly on node 1

			for (String portalExtLine : portalExtPropertiesLines) {
				List<String> parts = StringUtil.split(
					portalExtLine, CharPool.EQUAL);

				String key = parts.get(0);
				String expectedValue = parts.get(1);

				Assert.assertEquals(
					expectedValue,
					_tomcatNode1.syncExecute(() -> PropsUtil.get(key)));
			}

			// Assert node 1 can get its cluster node successfully

			Assert.assertNotNull(
				_tomcatNode1.syncExecute(
					ClusterExecutorUtil::getLocalClusterNode));

			Assert.assertEquals(
				expectedTransportName,
				_tomcatNode1.syncExecute(
					ClusterLinkTest::_getTransportChannelTransportName));
		}
	}

	private static transient TomcatNode _tomcatNode1;

}
