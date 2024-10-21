/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.resource.v1_0.ExportTaskResource;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Modify the value of _testableClassNames to test specific class names.
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class ExportTaskResourceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		if (!_testableClassNames.isEmpty()) {
			return;
		}

		Bundle bundle = FrameworkUtil.getBundle(ExportTaskResourceTest.class);

		_serviceTracker = new ServiceTracker<Object, String>(
			bundle.getBundleContext(),
			FrameworkUtil.createFilter(
				StringBundler.concat(
					"(&(batch.engine.entity.class.name=*)",
					"(batch.planner.export.enabled=true)",
					"(batch.planner.import.enabled=true))")),
			new ServiceTrackerCustomizer<Object, String>() {

				@Override
				public String addingService(
					ServiceReference<Object> serviceReference) {

					String className = (String)serviceReference.getProperty(
						"batch.engine.entity.class.name");

					try {
						Object service = bundle.getBundleContext(
						).getService(
							serviceReference
						);

						Class<?> serviceClass = service.getClass();

						Method getAvailableCreateStrategiesMethod =
							serviceClass.getMethod(
								"getAvailableCreateStrategies");

						Set<String> createStrategies =
							(Set<String>)
								getAvailableCreateStrategiesMethod.invoke(
									service);

						if ((createStrategies != null) &&
							createStrategies.contains("UPSERT")) {

							return className;
						}
					}
					catch (Exception exception) {
						_log.error(
							"Error while checking create strategies for " +
								className,
							exception);
					}

					return null;
				}

				@Override
				public void modifiedService(
					ServiceReference<Object> serviceReference,
					String className) {
				}

				@Override
				public void removedService(
					ServiceReference<Object> serviceReference,
					String className) {
				}

			});

		_serviceTracker.open();

		Map<ServiceReference<Object>, String> map =
			_serviceTracker.getTracked();

		_testableClassNames = TransformUtil.transform(
			map.values(),
			className -> {
				if (_untestableDTOClassNames.contains(className) ||
					StringUtil.startsWith(
						className,
						"com.liferay.object.rest.dto.v1_0.ObjectEntry#C_")) {

					return null;
				}

				return className;
			});
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	@Before
	public void setUp() {
		_logCaptures.add(
			LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineExportTaskExecutorImpl",
				LoggerTestUtil.ERROR));
		_logCaptures.add(
			LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR));
	}

	@After
	public void tearDown() {
		_logCaptures.forEach(LogCapture::close);
	}

	@Test
	public void testPostExportTask() throws Exception {
		Assert.assertFalse(_testableClassNames.isEmpty());

		StringBundler sb = new StringBundler();

		for (String className : _testableClassNames) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Testing " + className);
				}

				_testPostExportTask(className);
			}
			catch (Throwable throwable) {
				sb.append(className);
				sb.append(": ");
				sb.append(throwable.getMessage());
				sb.append("\n");
			}
		}

		if (sb.length() > 0) {
			throw new AssertionError(sb.toString());
		}
	}

	private void _assertExecuteStatusEquals(
			ExportTask.ExecuteStatus expectedExecuteStatus,
			ExportTask exportTask, ExportTaskResource exportTaskResource)
		throws Exception {

		String externalReferenceCode = exportTask.getExternalReferenceCode();

		while (true) {
			exportTask =
				exportTaskResource.getExportTaskByExternalReferenceCode(
					externalReferenceCode);

			ExportTask.ExecuteStatus executeStatus =
				exportTask.getExecuteStatus();

			if ((executeStatus == ExportTask.ExecuteStatus.COMPLETED) ||
				(executeStatus == ExportTask.ExecuteStatus.FAILED)) {

				if (expectedExecuteStatus != executeStatus) {
					throw new AssertionError(exportTask.getErrorMessage());
				}

				break;
			}
		}
	}

	private Map<String, String> _splitClassName(String className) {
		Map<String, String> classNamePartsMap = new HashMap<>();

		if (className.contains("#")) {
			String[] classNameParts = className.split("#");

			classNamePartsMap.put("className", classNameParts[0]);
			classNamePartsMap.put("taskItemDelegateName", classNameParts[1]);
		}
		else {
			classNamePartsMap.put("className", className);
		}

		return classNamePartsMap;
	}

	private void _testPostExportTask(String className) throws Exception {
		ExportTaskResource.Builder builder = ExportTaskResource.builder();

		ExportTaskResource exportTaskResource = builder.authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).build();

		Map<String, String> classNamePartsMap = _splitClassName(className);

		Group group = _groupLocalService.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.GUEST);

		builder.parameter("siteId", String.valueOf(group.getGroupId()));

		ExportTask exportTask = exportTaskResource.postExportTask(
			classNamePartsMap.get("className"), "jsont", null, null, null,
			classNamePartsMap.get("taskItemDelegateName"));

		String externalReferenceCode = exportTask.getExternalReferenceCode();

		_assertExecuteStatusEquals(
			ExportTask.ExecuteStatus.COMPLETED, exportTask, exportTaskResource);

		String json = null;

		exportTaskResource = builder.authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM
		).build();

		HttpInvoker.HttpResponse httpResponse =
			exportTaskResource.
				getExportTaskByExternalReferenceCodeContentHttpResponse(
					externalReferenceCode);

		try (InputStream inputStream = new UnsyncByteArrayInputStream(
				httpResponse.getBinaryContent())) {

			ZipInputStream zipInputStream = new ZipInputStream(inputStream);

			zipInputStream.getNextEntry();

			json = StringUtil.read(zipInputStream);
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		JSONObject actionsJSONObject = jsonObject.getJSONObject("actions");

		Assert.assertNotNull(actionsJSONObject);

		JSONObject createBatchJSONObject = actionsJSONObject.getJSONObject(
			"createBatch");

		Assert.assertNotNull(createBatchJSONObject);

		Assert.assertNotNull(createBatchJSONObject.getString("href"));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		ImportTaskResource importTaskResource = ImportTaskResource.builder(
		).authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).header(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON
		).build();

		builder.parameter("siteId", String.valueOf(group.getGroupId()));

		ImportTask importTask = importTaskResource.postImportTask(
			classNamePartsMap.get("className"), null, "UPSERT", null, null,
			null, classNamePartsMap.get("taskItemDelegateName"),
			itemsJSONArray);

		externalReferenceCode = importTask.getExternalReferenceCode();

		while (true) {
			importTask =
				importTaskResource.getImportTaskByExternalReferenceCode(
					externalReferenceCode);

			if (Objects.equals(
					importTask.getExecuteStatusAsString(), "COMPLETED")) {

				break;
			}
			else if (Objects.equals(
						importTask.getExecuteStatusAsString(), "FAILED")) {

				throw new AssertionError(
					StringBundler.concat(
						"Import task for ", classNamePartsMap.get("className"),
						" has FAILED with an error:\n",
						importTask.getErrorMessage()));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportTaskResourceTest.class);

	private static ServiceTracker<Object, String> _serviceTracker;

	/**
	 * Modify the value of _testableClassNames to test specific class names.
	 */
	private static Collection<String> _testableClassNames = Arrays.asList(
		//"com.liferay.data.engine.rest.dto.v2_0.DataDefinition",
		//"com.liferay.data.engine.rest.dto.v2_0.DataDefinitionFieldLink"
	);

	private static final List<String> _untestableDTOClassNames = Arrays.asList(
		"com.liferay.headless.admin.user.dto.v1_0.PostalAddress",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship",
		"com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory",
		"com.liferay.headless.delivery.dto.v1_0.WikiPage");

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Http _http;

	@Inject
	private JSONFactory _jsonFactory;

	private final List<LogCapture> _logCaptures = new ArrayList<>();

}