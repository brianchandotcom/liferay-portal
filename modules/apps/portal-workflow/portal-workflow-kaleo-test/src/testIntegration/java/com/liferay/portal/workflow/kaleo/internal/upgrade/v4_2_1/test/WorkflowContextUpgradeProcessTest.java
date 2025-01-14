/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v4_2_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jhosseph Gonzalez
 */
@RunWith(Arquillian.class)
public class WorkflowContextUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		serviceContext.setUserId(TestPropsValues.getUserId());

		KaleoInstance kaleoInstance =
			_kaleoInstanceLocalService.addKaleoInstance(
				1, 1, RandomTestUtil.randomString(), 1,
				HashMapBuilder.<String, Serializable>put(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
					"com.liferay.blogs.model.BlogsEntry"
				).put(
					WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
					(Serializable)serviceContext
				).build(),
				serviceContext);

		JSONObject workflowContextJSONObject = JSONFactoryUtil.createJSONObject(
			kaleoInstance.getWorkflowContext());

		JSONObject mapJSONObject = workflowContextJSONObject.getJSONObject(
			"map");

		JSONObject serviceContextJSONObject = mapJSONObject.getJSONObject(
			"serviceContext");

		serviceContextJSONObject.put(
			"javaClass",
			"com.liferay.headless.common.spi.service.context." +
				"ServiceContextUtil$1");

		JSONObject serializableJSONObject =
			serviceContextJSONObject.getJSONObject("serializable");

		serviceContextJSONObject.remove("serializable");

		serviceContextJSONObject.put("serviceContext", serializableJSONObject);

		kaleoInstance.setWorkflowContext(workflowContextJSONObject.toString());

		_kaleoInstance = _kaleoInstanceLocalService.updateKaleoInstance(
			kaleoInstance);
	}

	@Test
	public void testUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}

		KaleoInstance kaleoInstance =
			_kaleoInstanceLocalService.fetchKaleoInstance(
				_kaleoInstance.getKaleoInstanceId());

		String workflowContext = kaleoInstance.getWorkflowContext();

		Assert.assertTrue(
			workflowContext.contains(
				"com.liferay.portal.kernel.service.ServiceContext"));
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.workflow.kaleo.internal.upgrade.v4_2_1." +
			"WorkflowContextUpgradeProcess";

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.kaleo.internal.upgrade.registry.KaleoServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	private KaleoInstance _kaleoInstance;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}