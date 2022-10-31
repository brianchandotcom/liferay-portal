/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.client.extension;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.Mockito;

/**
 * @author Rafael Praxedes
 */
@RunWith(Parameterized.class)
public class ActionExecutorClientExtensionTrackerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(name = "key={0}, description={1}")
	public static List<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"function#client-extension-1", "Client Extension 1"},
				{"function#client-extension-2", "function#client-extension-2"},
				{"function#client-extension-3", "function#client-extension-3"}
			});
	}

	@BeforeClass
	public static void setUpClass() {
		setUpClientExtensionTracker();
		setUpJSONFactoryUtil();
		setUpServiceTrackerMap();
	}

	@Test
	public void testGetClientExtensionsJSONArray() throws Exception {
		JSONObject clientExtensionJSONObject = _findClientExtension(
			_actionExecutorClientExtensionTracker.
				getClientExtensionsJSONArray(),
			key);

		Assert.assertNotNull(clientExtensionJSONObject);

		Assert.assertEquals(
			clientExtensionJSONObject.toString(), description,
			clientExtensionJSONObject.getString("description"));
	}

	@Parameterized.Parameter(1)
	public String description;

	@Parameterized.Parameter
	public String key;

	protected static void setUpClientExtensionTracker() {
		ReflectionTestUtil.setFieldValue(
			_actionExecutorClientExtensionTracker, "_serviceTrackerMap",
			_serviceTrackerMap);
	}

	protected static void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected static void setUpServiceTrackerMap() {
		Mockito.when(
			_serviceTrackerMap.keySet()
		).thenReturn(
			_clientExtensionPropertiesMap.keySet()
		);

		for (Map.Entry<String, Map<String, Object>> entry :
				_clientExtensionPropertiesMap.entrySet()) {

			Mockito.when(
				_serviceTrackerMap.getService(entry.getKey())
			).thenReturn(
				new ServiceWrapper<ActionExecutor>() {

					@Override
					public Map<String, Object> getProperties() {
						return entry.getValue();
					}

					@Override
					public ActionExecutor getService() {
						return null;
					}

				}
			);
		}
	}

	private JSONObject _findClientExtension(
		JSONArray clientExtensionsJSONArray, String key) {

		for (int i = 0; i < clientExtensionsJSONArray.length(); i++) {
			JSONObject clientExtensionJSONObject =
				clientExtensionsJSONArray.getJSONObject(i);

			if (Objects.equals(
					clientExtensionJSONObject.getString("key"), key)) {

				return clientExtensionJSONObject;
			}
		}

		return null;
	}

	private static final ActionExecutorClientExtensionTracker
		_actionExecutorClientExtensionTracker =
			new ActionExecutorClientExtensionTracker();
	private static final Map<String, Map<String, Object>>
		_clientExtensionPropertiesMap =
			HashMapBuilder.<String, Map<String, Object>>put(
				"function#client-extension-1",
				HashMapBuilder.<String, Object>put(
					ActionExecutorClientExtensionTracker.
						ACTION_EXECUTOR_LANGUAGE_KEY,
					"function#client-extension-1"
				).put(
					"client.extension.description", "Client Extension 1"
				).build()
			).put(
				"function#client-extension-2",
				HashMapBuilder.<String, Object>put(
					ActionExecutorClientExtensionTracker.
						ACTION_EXECUTOR_LANGUAGE_KEY,
					"function#client-extension-2"
				).build()
			).put(
				"function#client-extension-3",
				HashMapBuilder.<String, Object>put(
					ActionExecutorClientExtensionTracker.
						ACTION_EXECUTOR_LANGUAGE_KEY,
					"function#client-extension-3"
				).put(
					"client.extension.description", StringPool.BLANK
				).build()
			).build();
	private static final ServiceTrackerMap
		<String, ServiceWrapper<ActionExecutor>> _serviceTrackerMap =
			Mockito.mock(ServiceTrackerMap.class);

}