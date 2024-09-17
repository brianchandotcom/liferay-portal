/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.action.executor;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.internal.configuration.FunctionActionExecutorImplConfiguration;
import com.liferay.portal.workflow.kaleo.runtime.util.ScriptingContextBuilder;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Paulo Albuquerque
 */
public class FunctionActionExecutorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDoExecute() throws Exception {

		// State node

		FunctionActionExecutorImpl functionActionExecutorImpl = Mockito.spy(
			new FunctionActionExecutorImpl());

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_jsonFactory", _mockJSONFactory());

		ExecutionContext executionContext = _mockExecutionContext(0);

		ScriptingContextBuilder scriptingContextBuilder = Mockito.mock(
			ScriptingContextBuilder.class);

		Mockito.when(
			scriptingContextBuilder.buildScriptingContext(executionContext)
		).thenReturn(
			Collections.emptyMap()
		);

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_scriptingContextBuilder",
			scriptingContextBuilder);

		Mockito.doNothing(
		).when(
			functionActionExecutorImpl
		).launch(
			Mockito.any(), Mockito.any()
		);

		_mockWorkflowHandlerRegistryUtil();

		ArgumentCaptor<JSONObject> argumentCaptor = ArgumentCaptor.forClass(
			JSONObject.class);

		KaleoAction kaleoAction = Mockito.mock(KaleoAction.class);

		functionActionExecutorImpl.doExecute(kaleoAction, executionContext);

		Mockito.verify(
			functionActionExecutorImpl, Mockito.times(1)
		).launch(
			argumentCaptor.capture(), Mockito.any()
		);

		JSONObject payloadjsonobject = argumentCaptor.getValue();

		Assert.assertFalse(payloadjsonobject.has("nextTransitionNames"));
		Assert.assertFalse(payloadjsonobject.has("transitionURL"));
		Assert.assertFalse(payloadjsonobject.has("workflowTaskId"));

		// Task node

		long workflowTaskId = RandomTestUtil.randomLong();

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_workflowTaskManager",
			_mockWorkflowTaskManager(kaleoAction, workflowTaskId));

		executionContext = _mockExecutionContext(workflowTaskId);

		Mockito.when(
			scriptingContextBuilder.buildScriptingContext(executionContext)
		).thenReturn(
			HashMapBuilder.<String, Object>put(
				"kaleoTaskInstanceToken", Collections.emptyMap()
			).build()
		);

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_scriptingContextBuilder",
			scriptingContextBuilder);

		functionActionExecutorImpl.doExecute(kaleoAction, executionContext);

		Mockito.verify(
			functionActionExecutorImpl, Mockito.times(2)
		).launch(
			argumentCaptor.capture(), Mockito.any()
		);

		payloadjsonobject = argumentCaptor.getValue();

		Assert.assertTrue(payloadjsonobject.has("nextTransitionNames"));
		Assert.assertTrue(payloadjsonobject.has("transitionURL"));
		Assert.assertTrue(payloadjsonobject.has("workflowTaskId"));
	}

	@Test
	public void testLaunch() throws Exception {
		FunctionActionExecutorImpl functionActionExecutorImpl = Mockito.spy(
			new FunctionActionExecutorImpl());

		long companyId = RandomTestUtil.randomLong();

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_companyId", companyId);

		long userId = RandomTestUtil.randomLong();

		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		Mockito.when(
			userLocalService.getUserIdByScreenName(
				companyId, "default-service-account")
		).thenReturn(
			userId
		);

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_userLocalService", userLocalService);

		FunctionActionExecutorImplConfiguration
			functionActionExecutorImplConfiguration = Mockito.mock(
				FunctionActionExecutorImplConfiguration.class);

		Mockito.when(
			functionActionExecutorImplConfiguration.
				oAuth2ApplicationExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			functionActionExecutorImplConfiguration.resourcePath()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl,
			"_functionActionExecutorImplConfiguration",
			functionActionExecutorImplConfiguration);

		PortalCatapult portalCatapult = Mockito.mock(PortalCatapult.class);

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_portalCatapult", portalCatapult);

		functionActionExecutorImpl.launch(
			new JSONObjectImpl(),
			Collections.singletonList(
				new WorkflowTaskAssignee(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomLong())));

		Mockito.verify(
			portalCatapult, Mockito.times(1)
		).launch(
			Mockito.anyLong(), Mockito.eq(Http.Method.POST),
			Mockito.anyString(), Mockito.any(), Mockito.anyString(),
			Mockito.eq(userId)
		);

		functionActionExecutorImpl.launch(new JSONObjectImpl(), null);

		Mockito.verify(
			portalCatapult, Mockito.times(2)
		).launch(
			Mockito.anyLong(), Mockito.eq(Http.Method.POST),
			Mockito.anyString(), Mockito.any(), Mockito.anyString(),
			Mockito.eq(userId)
		);

		userId = RandomTestUtil.randomLong();

		functionActionExecutorImpl.launch(
			new JSONObjectImpl(),
			Collections.singletonList(
				new WorkflowTaskAssignee(User.class.getName(), userId)));

		Mockito.verify(
			portalCatapult, Mockito.times(1)
		).launch(
			Mockito.anyLong(), Mockito.eq(Http.Method.POST),
			Mockito.anyString(), Mockito.any(), Mockito.anyString(),
			Mockito.eq(userId)
		);
	}

	private ExecutionContext _mockExecutionContext(long workflowTaskId) {
		ExecutionContext executionContext = Mockito.mock(
			ExecutionContext.class);

		if (workflowTaskId == 0) {
			Mockito.when(
				executionContext.getKaleoTaskInstanceToken()
			).thenReturn(
				null
			);

			return executionContext;
		}

		KaleoTaskInstanceToken kaleoTaskInstanceToken = Mockito.mock(
			KaleoTaskInstanceToken.class);

		Mockito.when(
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId()
		).thenReturn(
			workflowTaskId
		);

		Mockito.when(
			executionContext.getKaleoTaskInstanceToken()
		).thenReturn(
			kaleoTaskInstanceToken
		);

		return executionContext;
	}

	private JSONFactory _mockJSONFactory() throws Exception {
		JSONFactory jsonFactory = Mockito.mock(JSONFactory.class);

		Mockito.when(
			jsonFactory.createJSONObject()
		).thenReturn(
			new JSONObjectImpl()
		);

		String json = RandomTestUtil.randomString();

		Mockito.when(
			jsonFactory.serialize(Mockito.any())
		).thenReturn(
			json
		);

		Mockito.when(
			jsonFactory.createJSONObject(json)
		).thenReturn(
			new JSONObjectImpl()
		);

		return jsonFactory;
	}

	private void _mockWorkflowHandlerRegistryUtil() {
		Mockito.when(
			_serviceTrackerMap.getService(Mockito.isNull())
		).thenReturn(
			(WorkflowHandler)_workflowHandler
		);

		ReflectionTestUtil.setFieldValue(
			WorkflowHandlerRegistryUtil.class,
			"_workflowHandlerServiceTrackerMap", _serviceTrackerMap);
	}

	private Object _mockWorkflowTaskManager(
			KaleoAction kaleoAction, long workflowTaskId)
		throws Exception {

		WorkflowTaskManager workflowTaskManager = Mockito.mock(
			WorkflowTaskManager.class);

		Mockito.when(
			workflowTaskManager.getNextTransitionNames(
				kaleoAction.getUserId(), workflowTaskId)
		).thenReturn(
			Collections.singletonList(RandomTestUtil.randomString())
		);

		return workflowTaskManager;
	}

	private final ServiceTrackerMap<String, WorkflowHandler<?>>
		_serviceTrackerMap = Mockito.mock(ServiceTrackerMap.class);
	private final WorkflowHandler<?> _workflowHandler = Mockito.mock(
		WorkflowHandler.class);

}