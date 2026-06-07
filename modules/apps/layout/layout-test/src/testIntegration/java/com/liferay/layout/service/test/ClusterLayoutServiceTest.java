/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Date;
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
public class ClusterLayoutServiceTest implements Serializable {

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

		TomcatCluster.Builder builder2 =
			tomcatClusterTestRule.buildTomcatNode();

		_tomcatNode2 = builder2.build();

		_tomcatNode2.start(true);
	}

	@Test
	public void testSchedulePublishToLive() throws Exception {
		TomcatNode masterTomcatNode = _tomcatNode2;
		TomcatNode slaveTomcatNode = _tomcatNode1;

		if (_tomcatNode1.syncExecute(ClusterMasterExecutorUtil::isMaster)) {
			masterTomcatNode = _tomcatNode1;
			slaveTomcatNode = _tomcatNode2;
		}

		long groupId = masterTomcatNode.syncExecute(
			() -> {
				Group group = GroupTestUtil.addGroup();

				return group.getGroupId();
			});

		String groupName = RandomTestUtil.randomString();

		Assert.assertEquals(
			0,
			(int)slaveTomcatNode.syncExecute(
				() -> {
					List<SchedulerResponse> schedulerResponses =
						SchedulerEngineHelperUtil.getScheduledJobs(
							groupName, StorageType.PERSISTED);

					return schedulerResponses.size();
				}));

		long userId = TestPropsValues.getUserId();

		try {
			masterTomcatNode.syncExecute(
				() -> {
					PermissionThreadLocal.setPermissionChecker(
						PermissionCheckerFactoryUtil.create(
							TestPropsValues.getUser()));

					String originalName = PrincipalThreadLocal.getName();

					PrincipalThreadLocal.setName(userId);

					try {
						LayoutServiceUtil.schedulePublishToLive(
							groupId, groupId, false, new long[0],
							ExportImportConfigurationParameterMapFactoryUtil.
								buildParameterMap(),
							groupName, "0 0 0 * * ?",
							new Date(System.currentTimeMillis() + Time.DAY),
							null, "Staging Schedule Title");

						return null;
					}
					finally {
						PermissionThreadLocal.setPermissionChecker(null);
						PrincipalThreadLocal.setName(originalName);
					}
				});

			SchedulerResponse[] schedulerResponses =
				slaveTomcatNode.syncExecute(
					() -> {
						List<SchedulerResponse> schedulerResponseList =
							SchedulerEngineHelperUtil.getScheduledJobs(
								groupName, StorageType.PERSISTED);

						return schedulerResponseList.toArray(
							new SchedulerResponse[0]);
					});

			Assert.assertEquals(
				Arrays.toString(schedulerResponses), 1,
				schedulerResponses.length);

			SchedulerResponse schedulerResponse = schedulerResponses[0];

			Assert.assertEquals(
				"Staging Schedule Title", schedulerResponse.getDescription());
			Assert.assertEquals(
				DestinationNames.LAYOUTS_LOCAL_PUBLISHER,
				schedulerResponse.getDestinationName());
		}
		finally {
			masterTomcatNode.syncExecute(
				() -> {
					SchedulerEngineHelperUtil.delete(
						groupName, StorageType.PERSISTED);

					return null;
				});
		}
	}

	private static transient TomcatNode _tomcatNode1;
	private static transient TomcatNode _tomcatNode2;

}